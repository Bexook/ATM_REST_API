BEGIN ;
INSERT INTO app_user
VALUES (1, 'Andrew', '$2a$12$eUlu.mO43N0ohjXsU/c4MermZDcXDYLSeS63bSqlO5YEalsFa/X.a', 'andrik.mykytyn@gmail.com',
        '2008-11-11');


INSERT INTO client_card (id,
                         card_code,
                         password,
                         cvv_code,
                         valid_to,
                         current_balance,
                         user_id,
                         disabled)
VALUES (auto_increment_id.nextval, '45625654568715', '$2a$12$4S3T/hFMn98.MF/4EUH5vO3U2Fp0nogPt/EU4YvTG2QhkUnjB5uHC',
        111, '2222-11-11', 1000.0, 1, 0);

COMMIT;