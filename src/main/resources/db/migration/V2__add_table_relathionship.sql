ALTER TABLE transactions
    ADD CONSTRAINT transactions_fk FOREIGN KEY (user_id) REFERENCES app_user (id);

ALTER TABLE client_card
    ADD CONSTRAINT client_card_fk FOREIGN KEY (user_id) REFERENCES app_user (id);

