BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user1','user1','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('user2','user2','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('user3','user3','ROLE_USER');

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
