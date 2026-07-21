# 📚 Library API

Uma API REST para gerenciamento de uma biblioteca, construída com **Java 17** e **Spring Boot**. Ela gerencia autores, livros e empréstimos, com regras de negócio reais envolvendo disponibilidade, devoluções em atraso e integridade referencial.

O projeto é um exercício de estudo focado em construir uma API Spring Boot limpa e bem dividida em camadas — DTOs no lugar de entidades, regra de negócio na camada de service, tratamento de erros global, paginação e filtros.

---

## Objetivos

- Modelar três entidades relacionadas (`Author`, `Book`, `Loan`) com relacionamentos JPA adequados (`OneToMany` / `ManyToOne`).
- Expor uma API REST CRUD completa com os códigos de status HTTP corretos.
- Manter as entidades JPA fora da superfície da API usando DTOs de Request/Response separados.
- Concentrar as regras de negócio em uma camada de service dedicada, nunca nos controllers.
- Validar a entrada com Bean Validation (`@NotBlank`, `@Email`, `@Past`, `@Positive`, etc).
- Retornar respostas de erro padronizadas via um `@RestControllerAdvice` global usando `ProblemDetail`.
- Suportar paginação (`Pageable`) e filtros opcionais nos endpoints de listagem.

---

## Tecnologias

| Camada         | Tecnologia                               |
| -------------- | ---------------------------------------- |
| Linguagem      | Java 17                                  |
| Framework      | Spring Boot 4.1.0                        |
| Web            | Spring Web MVC                           |
| Persistência   | Spring Data JPA (Hibernate)              |
| Validação      | Spring Boot Starter Validation (Jakarta) |
| Banco de dados | PostgreSQL 16                            |
| Infra          | Docker Compose                           |
| Build          | Maven (Spring Boot Maven Plugin)         |
| Dev            | Spring Boot DevTools                     |

---

## Aprendizados & Conceitos

- **DTOs desacoplam a API do schema.** Records de request `Create`/`Update` separados e records de response dedicados impedem que entidades JPA vazem para a API, então mudanças na persistência não forçam mudanças que quebram a API.
- **Regras de negócio pertencem aos services.** Os controllers ficam enxutos — apenas mapeiam DTOs e definem os códigos de status. A alternância de disponibilidade, o cálculo de atraso e as travas de deleção ficam todos na camada de service.
- **`ProblemDetail` padroniza os erros.** Um único `GlobalExceptionHandler` mapeia cada exceção de domínio para uma resposta RFC 7807 consistente, com título e detalhe. Falhas de validação são enriquecidas com uma propriedade `fieldErrors`, então o cliente recebe mensagens por campo.
- **Campos derivados podem ficar no DTO de response.** `late` e `daysLate` são calculados no `LoanResponse` a partir de `dueDate`/`returnDate` usando `ChronoUnit.DAYS`, mantendo a entidade livre de lógica de apresentação.
- **Conflito (409) em vez de falha silenciosa.** Violações de domínio — emprestar um livro indisponível, ISBN duplicado, deletar um autor que ainda tem livros — surgem como respostas `409 Conflict` explícitas, em vez de 500s vagos.

---

## Instalação & Configuração

### Pré-requisitos

- Java 17+
- Maven (ou o wrapper `./mvnw` incluído)
- Docker + Docker Compose

### 1. Suba o banco de dados

```bash
docker compose up -d
```

Isso sobe o PostgreSQL 16 na porta `5432` com o banco `library-db` (usuário/senha `postgres`/`postgres`), conforme o `src/main/resources/application.properties`.

### 2. Rode a aplicação

```bash
./mvnw spring-boot:run
```

O Hibernate roda com `ddl-auto=update`, então o schema é criado/atualizado automaticamente na inicialização. A API fica disponível em `http://localhost:8080`.

### 3. (Opcional) Popular dados

Carregue o `seed.sql` no banco em execução se quiser registros de exemplo:

```bash
docker exec -i library-api psql -U postgres -d library-db < seed.sql
```

---

## Referência da API

URL base: `http://localhost:8080`

### Autores — `/authors`

| Método   | Rota                   | Descrição                                    | Sucesso          |
| -------- | ---------------------- | -------------------------------------------- | ---------------- |
| `POST`   | `/authors`             | Criar um autor                               | `201 Created`    |
| `GET`    | `/authors`             | Listar autores (com paginação)               | `200 OK`         |
| `GET`    | `/authors/{id}`        | Buscar autor por ID, incluindo seus livros   | `200 OK`         |
| `PUT`    | `/authors/{id}`        | Atualizar um autor                           | `200 OK`         |
| `DELETE` | `/authors/{id}`        | Deletar um autor (falha se ele tiver livros) | `204 No Content` |
| `GET`    | `/authors/name/{name}` | Buscar autores por nome                      | `200 OK`         |

**Request Body**

```json
{
  "name": "George Orwell",
  "nationality": "British",
  "birthDate": "1903-06-25"
}
```

### Livros — `/books`

| Método   | Rota          | Descrição                                                            | Sucesso          |
| -------- | ------------- | -------------------------------------------------------------------- | ---------------- |
| `POST`   | `/books`      | Criar um livro (recebe `authorId` no corpo)                          | `201 Created`    |
| `GET`    | `/books`      | Listar livros (paginação; filtros: `genre`, `authorId`, `available`) | `200 OK`         |
| `GET`    | `/books/{id}` | Buscar livro por ID                                                  | `200 OK`         |
| `PUT`    | `/books/{id}` | Atualizar um livro                                                   | `200 OK`         |
| `DELETE` | `/books/{id}` | Deletar um livro (falha se houver empréstimo em aberto)              | `204 No Content` |

**Request Body**

```json
{
  "title": "1984",
  "isbn": "978-0451524935",
  "publishedYear": 1949,
  "genre": "Dystopia",
  "authorId": 1
}
```

### Empréstimos — `/loans`

| Método  | Rota                 | Descrição                                                        | Sucesso       |
| ------- | -------------------- | ---------------------------------------------------------------- | ------------- |
| `POST`  | `/loans`             | Registrar empréstimo (`bookId`, `borrowerName`, `borrowerEmail`) | `201 Created` |
| `GET`   | `/loans`             | Listar empréstimos (paginação; filtro: `active=true`)            | `200 OK`      |
| `PATCH` | `/loans/{id}/return` | Registrar devolução                                              | `200 OK`      |

**Request Body**

```json
{
  "borrowerName": "Jane Doe",
  "borrowerEmail": "jane@example.com",
  "bookId": 1
}
```

Na criação, `loanDate` recebe a data atual e `dueDate` a data atual + 14 dias; o livro fica indisponível. Na devolução, `returnDate` é preenchido e o livro volta a ficar disponível. A resposta do empréstimo inclui `late` e `daysLate`, calculados a partir de `dueDate` versus a data de devolução (ou atual).

### Regras de negócio & respostas de erro

Os erros usam `ProblemDetail` com `title` e `detail`; erros de validação adicionam um array `fieldErrors`.

| Regra                                     | Status            |
| ----------------------------------------- | ----------------- |
| Emprestar um livro indisponível           | `409 Conflict`    |
| ISBN duplicado                            | `409 Conflict`    |
| Deletar um autor que ainda tem livros     | `409 Conflict`    |
| Deletar um livro com empréstimo em aberto | `409 Conflict`    |
| Devolver um empréstimo já devolvido       | `409 Conflict`    |
| Recurso não encontrado                    | `404 Not Found`   |
| Corpo de requisição inválido              | `400 Bad Request` |
