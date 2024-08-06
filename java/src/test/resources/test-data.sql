BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user1','$2a$10$i6k9Zr2v8gq8wNzWRNYRPO4qmduT8hDKbe9jKw1yTADtTb/SINMQS','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('user2','$2a$10$aQ2Trss22FHPy1QtAJhktOG3Nc6BR86HCYH5jvlI4vBgd.XB6gjvu','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('user3','$2a$10$YzW4XQomJq.Or77l39XHFe98sz5PndOuEqy4RmIMhFeQWdGTPmExa','ROLE_USER');

INSERT INTO user_details(user_id, display_name, elo_rating, is_staff) VALUES (
    1,
    'User1',
    800,
    FALSE
);
INSERT INTO user_details(user_id, display_name, elo_rating, is_staff) VALUES (
    2,
    'User2',
    1200,
    TRUE
);
INSERT INTO user_details(user_id, display_name, elo_rating, is_staff) VALUES (
    3,
    'User3',
    900,
    FALSE
);

COMMIT TRANSACTION;
