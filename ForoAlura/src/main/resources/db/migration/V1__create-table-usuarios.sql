-- Creating the 'autorxs' table
CREATE TABLE autores (
    id BIGINT NOT NULL AUTO_INCREMENT,
    email VARCHAR(150) NOT NULL,
    clave VARCHAR(250) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

-- Inserting a record into the 'autorxs' table
INSERT INTO autores (id, email, clave) VALUES
(1, 'luis.beltran@alura.edu', '3x4aust#ed-7&igsaw-Mil-0V3r8e$ar');
