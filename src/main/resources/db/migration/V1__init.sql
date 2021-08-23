CREATE SEQUENCE auto_increment_id
    MINVALUE 1
    START WITH 1
    INCREMENT BY 1
    CACHE 10;

CREATE TABLE app_user
(
    id            BIGINT PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    password      VARCHAR(150) NOT NULL,
    email         VARCHAR(300) NOT NULL UNIQUE,
    creation_date DATE         NOT NULL
);

CREATE TABLE client_card
(
    id              BIGINT PRIMARY KEY,
    card_code       VARCHAR(20) NOT NULL UNIQUE,
    password        VARCHAR(150)      NOT NULL,
    cvv_code        BIGINT      NOT NULL,
    valid_to        DATE        NOT NULL,
    current_balance BIGINT      NOT NULL default 0,
    borrow_limit    BIGINT      NOT NULL default 5000,
    borrowed_money  BIGINT      NOT NULL default 0,
    user_id         BIGINT      NOT NULL,
    disabled        BIT         NOT NULL
);


CREATE TABLE transactions
(
    id                 BIGINT PRIMARY KEY,
    sender_card_code   VARCHAR(20) NOT NULL,
    receiver_card_code VARCHAR(20),
    amount             BIGINT      NOT NULL,
    fee                BIGINT      NOT NULL default 0,
    date               DATE        NOT NULL,
    status             VARCHAR(20) NOT NULL,
    user_id            BIGINT      NOT NULL
);


CREATE TABLE jwt_token
(
    id    BIGINT PRIMARY KEY,
    token VARCHAR(150) NOT NULL UNIQUE
);

