CREATE TABLE IF NOT EXISTS Book
(
    id        IDENTITY PRIMARY KEY,
    isbNumber VARCHAR(100),
    author    VARCHAR(255),
    title     VARCHAR(255),
    created   DATETIME,
    inserted  BIGINT
);

CREATE TABLE IF NOT EXISTS BookCardEvents
(
    id        IDENTITY PRIMARY KEY,
    bookId    BIGINT,
    createdBy BIGINT,
    event     VARCHAR(255),
    createdAt DATETIME
);