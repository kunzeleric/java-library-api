# 📚 Requisitos — API de Gerenciamento de Biblioteca

## Objetivo

Construir uma API REST com **Java** e **Spring Boot** para gerenciar uma biblioteca. O sistema deve permitir o cadastro de autores e livros, além do controle de empréstimos com regras de negócio bem definidas.

## Modelagem de Dados

O sistema deve conter três entidades principais com os seguintes campos:

### Author

| Campo         | Tipo      | Regras                       |
| ------------- | --------- | ---------------------------- |
| `id`          | Long      | Gerado automaticamente       |
| `name`        | String    | Obrigatório                  |
| `nationality` | String    |                              |
| `birthDate`   | LocalDate | Deve ser uma data no passado |

- Um autor pode ter vários livros (relação `OneToMany`).

### Book

| Campo           | Tipo    | Regras                                 |
| --------------- | ------- | -------------------------------------- |
| `id`            | Long    | Gerado automaticamente                 |
| `title`         | String  | Obrigatório                            |
| `isbn`          | String  | Obrigatório, deve ser único no sistema |
| `publishedYear` | Integer |                                        |
| `genre`         | String  |                                        |
| `available`     | Boolean | Default `true`                         |

- Cada livro deve pertencer a um autor (relação `ManyToOne`).

### Loan (Empréstimo)

| Campo           | Tipo      | Regras                                      |
| --------------- | --------- | ------------------------------------------- |
| `id`            | Long      | Gerado automaticamente                      |
| `borrowerName`  | String    | Obrigatório                                 |
| `borrowerEmail` | String    | Obrigatório, formato de e-mail válido       |
| `loanDate`      | LocalDate | Preenchido automaticamente (data atual)     |
| `dueDate`       | LocalDate | Preenchido automaticamente (hoje + 14 dias) |
| `returnDate`    | LocalDate | Nullable — preenchido apenas na devolução   |

- Cada empréstimo deve se referir a um livro (relação `ManyToOne`).

---

## Endpoints a Implementar

### Authors — `/authors`

| Método   | Rota            | Descrição                                            | Status Esperado  |
| -------- | --------------- | ---------------------------------------------------- | ---------------- |
| `POST`   | `/authors`      | Criar um novo autor                                  | `201 Created`    |
| `GET`    | `/authors`      | Listar todos os autores (com paginação)              | `200 OK`         |
| `GET`    | `/authors/{id}` | Buscar autor por ID (incluindo lista de livros dele) | `200 OK`         |
| `PUT`    | `/authors/{id}` | Atualizar dados de um autor                          | `200 OK`         |
| `DELETE` | `/authors/{id}` | Deletar autor (ver regra 6)                          | `204 No Content` |

### Books — `/books`

| Método   | Rota          | Descrição                                                              | Status Esperado  |
| -------- | ------------- | ---------------------------------------------------------------------- | ---------------- |
| `POST`   | `/books`      | Criar livro (recebendo `authorId` no body)                             | `201 Created`    |
| `GET`    | `/books`      | Listar todos (com filtros opcionais: `genre`, `authorId`, `available`) | `200 OK`         |
| `GET`    | `/books/{id}` | Buscar livro por ID                                                    | `200 OK`         |
| `PUT`    | `/books/{id}` | Atualizar dados de um livro                                            | `200 OK`         |
| `DELETE` | `/books/{id}` | Deletar livro (ver regra 7)                                            | `204 No Content` |

### Loans — `/loans`

| Método  | Rota                 | Descrição                                                               | Status Esperado |
| ------- | -------------------- | ----------------------------------------------------------------------- | --------------- |
| `POST`  | `/loans`             | Registrar empréstimo (recebe `bookId`, `borrowerName`, `borrowerEmail`) | `201 Created`   |
| `GET`   | `/loans`             | Listar empréstimos (filtro opcional: `active=true`)                     | `200 OK`        |
| `PATCH` | `/loans/{id}/return` | Registrar devolução de um empréstimo                                    | `200 OK`        |

---

## Regras de Negócio

1. **Empréstimo de livro indisponível:** não deve ser permitido emprestar um livro que já está emprestado (`available = false`). A API deve retornar `409 Conflict`.

2. **Criação de empréstimo:** ao registrar um empréstimo, `loanDate` deve ser preenchido com a data atual e `dueDate` com a data atual + 14 dias. O campo `available` do livro correspondente deve ser atualizado para `false`.

3. **Devolução:** ao registrar uma devolução, `returnDate` deve receber a data atual e o campo `available` do livro deve voltar para `true`.

4. **Devolução com atraso:** se a devolução ocorrer após o `dueDate`, a resposta deve incluir os campos `"late": true` e `"daysLate": X` indicando a quantidade de dias de atraso.

5. **ISBN único:** tentativa de cadastrar um livro com ISBN já existente deve retornar `409 Conflict`.

6. **Deleção de autor com livros:** não deve ser permitido deletar um autor que possui livros cadastrados. A API deve retornar `409 Conflict` com mensagem explicativa.

7. **Deleção de livro com empréstimo ativo:** não deve ser permitido deletar um livro que possui um empréstimo em aberto (sem `returnDate`). A API deve retornar `409 Conflict`.

---

## Requisitos Técnicos

- **DTOs:** criar Request e Response DTOs separados. Não expor entidades JPA diretamente nos endpoints.
- **Validações:** utilizar Bean Validation (`@Valid`, `@NotBlank`, `@Email`, `@Past`, etc.) nos DTOs de entrada.
- **Tratamento de erros global:** implementar um `@RestControllerAdvice` com respostas de erro em formato padronizado.
- **Paginação:** os endpoints de listagem devem suportar `Pageable` do Spring Data.
- **Filtros:** os filtros de livros devem ser implementados com Query Methods ou `@Query`.
- **Camada de Service:** toda regra de negócio deve ficar na camada de Service, não no Controller.
- **HTTP status corretos:** seguir os status indicados na tabela de endpoints.
