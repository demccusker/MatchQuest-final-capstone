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



INSERT INTO game (name, description, win_type) values (
    'Chess',
    'A game of wits',
    (SELECT condition_id FROM win_condition WHERE name = 'MAX')
);

INSERT INTO game (name, description, win_type) values (
    'Golf',
    'Surprisingly frustrating and calming',
    (SELECT condition_id FROM win_condition WHERE name = 'MIN')
);
INSERT INTO game (name, description, win_type) values (
    'CS2',
    'The bomb has been planted',
    (SELECT condition_id FROM win_condition WHERE name = 'MAX')
);
INSERT INTO game (name, description, win_type) values (
    'War',
    'A game of luck',
    (SELECT condition_id FROM win_condition WHERE name = 'MAX')

);
INSERT INTO game (name, description, win_type) values (
    'Minecraft',
    'How does one win?',
    (SELECT condition_id FROM win_condition WHERE name = 'MAX')
);

INSERT INTO game (name, description, win_type) values (
    'Badminton',
    'What it fly!',
    (SELECT condition_id FROM win_condition WHERE name = 'MAX')
);


INSERT INTO match (game_id, is_scrim) VALUES (
    (SELECT game_id FROM game WHERE name = 'Golf'),
    FALSE
);

INSERT INTO match (game_id, is_scrim) VALUES (
    (SELECT game_id FROM game WHERE name = 'Golf'),
    FALSE
);

INSERT INTO match (game_id, is_scrim) VALUES (
      (SELECT game_id FROM game WHERE name = 'Golf'),
      FALSE
  );

INSERT INTO match (game_id, is_scrim) VALUES (
      (SELECT game_id FROM game WHERE name = 'Golf'),
      FALSE
  );

INSERT INTO match (game_id, is_scrim) VALUES (
      (SELECT game_id FROM game WHERE name = 'Golf'),
      FALSE
  );

INSERT INTO match (game_id, is_scrim) VALUES (
      (SELECT game_id FROM game WHERE name = 'Golf'),
      FALSE
  );

INSERT INTO match (game_id, is_scrim) VALUES (
      (SELECT game_id FROM game WHERE name = 'Golf'),
      FALSE
  );

INSERT INTO match (game_id, is_scrim) VALUES (
      (SELECT game_id FROM game WHERE name = 'Golf'),
      FALSE
  );




INSERT INTO bracket (parent_bracket, match_id, name) VALUES (
    NULL,
    1,
    'First bracket'
);

INSERT INTO bracket (parent_bracket, match_id, name) VALUES (
    NULL,
    2,
    'Second bracket'
);
INSERT INTO bracket (parent_bracket, match_id, name) VALUES (
    NULL,
    3,
    'Third bracket'
);

INSERT INTO bracket (parent_bracket, match_id, name) VALUES (
    NULL,
    4,
    'Fourth bracket'
);
INSERT INTO bracket (parent_bracket, match_id, name) VALUES (
    1,
    5,
    'Fifth bracket'
);
INSERT INTO bracket (parent_bracket, match_id, name) VALUES (
    2,
    6,
    'Sixth bracket'
);

INSERT INTO bracket (parent_bracket, match_id, name) VALUES (
    3,
    7,
    'Seventh bracket'
);

INSERT INTO bracket (parent_bracket, match_id, name) VALUES (
    4,
    8,
    'Eighth bracket'
);


INSERT INTO tournament (game_id, bracket_id, creator_id, name, is_scrim, is_online, location, start_date, end_date) VALUES (
    (SELECT game_id FROM game WHERE name = 'Golf'),
    1,
    2,
    'Golf Tournament',
    FALSE,
    TRUE,
    1,
    CURRENT_DATE,
    NULL
);

INSERT INTO tournament (game_id, bracket_id, creator_id, name, is_scrim, is_online, location, start_date, end_date) VALUES (
    (SELECT game_id FROM game WHERE name = 'Golf'),
    2,
    2,
    'Golfy Tournament',
    TRUE,
    FALSE,
    NULL,
    CURRENT_DATE,
    NULL
);

INSERT INTO tournament (game_id, bracket_id, creator_id, name, is_scrim, is_online, location, start_date, end_date) VALUES (
    (SELECT game_id FROM game WHERE name = 'Golf'),
    2,
    3,
    'Golf Tourney',
    TRUE,
    FALSE,
    NULL,
    CURRENT_DATE,
    NULL
);


INSERT INTO address (tournament_id, city, province, country) VALUES (
    1,
    'Los Angeles',
    'California',
    'USA'
);

INSERT INTO address (tournament_id, city, province, country) VALUES (
    2,
    'Annapolis',
    'Maryland',
    'USA'
);
INSERT INTO address (tournament_id, city, province, country) VALUES (
    3,
    'Lewes',
    'Deleware',
    'USA'
);



INSERT INTO result (is_draw, elo_change, winner_id) VALUES
(FALSE, 50,  (SELECT user_id FROM users WHERE username = 'andrew501'));



COMMIT TRANSACTION;
