CREATE TABLE tb_person (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    birthDate DATE NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    UNIQUE(cpf)
);

CREATE TABLE tb_address (
    id UUID PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    number INT NOT NULL,
    neighborhood VARCHAR(255) NOT NULL,
    state VARCHAR(255) NOT NULL,
    zip_code VARCHAR(255) NOT NULL,
    person_id UUID,
    CONSTRAINT fk_person_id FOREIGN KEY (person_id) REFERENCES tb_person(id)
);
