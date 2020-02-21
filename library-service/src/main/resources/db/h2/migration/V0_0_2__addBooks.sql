CREATE TABLE IF NOT EXISTS Book
(
    id        IDENTITY PRIMARY KEY,
    isbNumber VARCHAR(100),
    author    VARCHAR(255),
    title     VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS BookCardEvents
(
    id        IDENTITY PRIMARY KEY,
    bookId    BIGINT,
    createdBy BIGINT,
    event     VARCHAR(255),
    createdAt DATETIME
);

INSERT INTO Book (isbNumber, title, author)
VALUES ('222-2222-2222', 'Agile Web Development with Rails', 'Sam Ruby, Dave Thomas, David Heinemeier Hansson'),
       ('222-2222-2223', 'Agile web development with rails: a Pragmatic guide',
        'Dave Thomas, David Heinemeier Hansson, etc'),
       ('222-2222-2224', 'Professional JavaScript for Web Developers', 'Nicholas C. Zakas'),
       ('222-2222-2225', 'Web development solutions : Ajax, APIs, libraries', 'Christian Heilmann'),
       ('222-2222-2226', 'Learn Java for Web Development: Modern Java Web Development', 'Vishal Layka'),
       ('222-2222-2227', 'Spring Web Flow 2 Web Development', 'Sven Lüppken, Markus Stäuble');

INSERT INTO BookCardEvents (bookId, createdBy, event, createdAt)
values (2, 2, 'TAKE_BOOK', now()),
       (1, 1, 'TAKE_BOOK', now());