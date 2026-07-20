-- ===========================
-- Autores
-- ===========================
INSERT INTO authors (name, nationality, birth_date) VALUES
('J.R.R. Tolkien', 'British', '1892-01-03'),
('George Orwell', 'British', '1903-06-25'),
('Machado de Assis', 'Brazilian', '1839-06-21'),
('Agatha Christie', 'British', '1890-09-15'),
('Gabriel García Márquez', 'Colombian', '1927-03-06');

-- ===========================
-- Livros
-- ===========================
INSERT INTO books (title, isbn, published_year, genre, available, author_id) VALUES
('The Hobbit', '978-0-618-00221-4', 1937, 'Fantasy', true,
    (SELECT id FROM authors WHERE name = 'J.R.R. Tolkien')),

('The Lord of the Rings', '978-0-618-64015-7', 1954, 'Fantasy', true,
    (SELECT id FROM authors WHERE name = 'J.R.R. Tolkien')),

('1984', '978-0-451-52493-5', 1949, 'Dystopian', true,
    (SELECT id FROM authors WHERE name = 'George Orwell')),

('Animal Farm', '978-0-451-52634-2', 1945, 'Satire', true,
    (SELECT id FROM authors WHERE name = 'George Orwell')),

('Dom Casmurro', '978-85-260-1235-4', 1899, 'Fiction', true,
    (SELECT id FROM authors WHERE name = 'Machado de Assis')),

('Memórias Póstumas de Brás Cubas', '978-85-260-1236-1', 1881, 'Fiction', true,
    (SELECT id FROM authors WHERE name = 'Machado de Assis')),

('Murder on the Orient Express', '978-0-06-207350-4', 1934, 'Mystery', true,
    (SELECT id FROM authors WHERE name = 'Agatha Christie')),

('And Then There Were None', '978-0-06-207348-1', 1939, 'Mystery', true,
    (SELECT id FROM authors WHERE name = 'Agatha Christie')),

('One Hundred Years of Solitude', '978-0-06-088328-7', 1967, 'Magical Realism', true,
    (SELECT id FROM authors WHERE name = 'Gabriel García Márquez')),

('Love in the Time of Cholera', '978-0-307-38973-7', 1985, 'Romance', true,
    (SELECT id FROM authors WHERE name = 'Gabriel García Márquez'));