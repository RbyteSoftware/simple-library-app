/**
 * Tables section
 */
CREATE TABLE IF NOT EXISTS User (
    id IDENTITY primary key,
    login varchar(32) not null unique,
    password varchar(255) not null
);

CREATE TABLE IF NOT EXISTS Role(
    id IDENTITY primary key,
    systemName varchar(128)
);

CREATE TABLE IF NOT EXISTS UserRoles(
    userId bigint not null,
    roleId bigint not null
);

CREATE TABLE IF NOT EXISTS Person (
    id IDENTITY primary key,
    userId bigint not null,
    firstName varchar(255),
    lastName varchar(255),
    email varchar(255)
);
/**
 * Keys section
 */
ALTER TABLE if EXISTS Person
    ADD CONSTRAINT person_user_fk
    FOREIGN KEY (userId) references `User`;

ALTER TABLE if EXISTS UserRoles
    ADD CONSTRAINT user_to_role_fk
    FOREIGN KEY (userId) references `User`;

ALTER TABLE if EXISTS UserRoles
    ADD CONSTRAINT role_to_user_fk
    FOREIGN KEY (roleId) references `Role`;

/**
 * Initial INSERT section
 */
INSERT INTO `User` (id, login, password) values (1, 'Admin', '$2a$10$8/jsAvuu/5LNftzdUFhLte8FUD8OOT9Vh4UWbAsCEWGQwCz839b96'),
(2, 'User', '$2a$10$K2EYeGvHl62mvypIikz5luGT6AUvEQTtq7EjRhd1QxEI/2Yoz1HcG'), (3, 'Ben', '$2a$10$8/jsAvuu/5LNftzdUFhLte8FUD8OOT9Vh4UWbAsCEWGQwCz839b96');
INSERT INTO `Role` (id, systemName) values (1, 'ADMIN'), (2, 'USER');
INSERT INTO `UserRoles` (userId, roleId) values (1, 1), (1, 2), (2, 2);
INSERT INTO `Person` (id, userId, firstName, lastName, email) values (default, 1, 'Михаил', 'Смирнов', 'm@io.ru'),
(default, 2, 'Василий', 'Трубник', 't@io.ru');