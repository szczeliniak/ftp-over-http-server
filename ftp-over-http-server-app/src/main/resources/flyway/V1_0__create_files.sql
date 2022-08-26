CREATE TABLE files
(
    id              INTEGER        NOT NULL,
    name            VARCHAR(255)   NOT NULL,
    size            BIGINT         NOT NULL,
    content_type    VARCHAR(255)   NOT NULL,
    CONSTRAINT pk_file_id PRIMARY KEY (id),
    CONSTRAINT uk_file_name UNIQUE (name)
);

CREATE SEQUENCE seq_file_id INCREMENT 1 START 1;
ALTER SEQUENCE seq_file_id OWNED BY files.id;