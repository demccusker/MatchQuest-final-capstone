BEGIN TRANSACTION;

INSERT INTO win_condition (name) values ('MIN');
INSERT INTO win_condition (name) values ('MAX');

--Testing data PW is the usernames
INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');
INSERT INTO users (username,password_hash,role) VALUES ('andrew500', '$2a$10$VPhB5.Tv16s02uul4lAJ6uahtkdP6M.QUnN0uI06A26IXrETFlzBy', 'ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('andrew501', '$2a$10$CWafZTSVTpRp6.5tpUJgN.0.jEjKIZhQvlZwjaNvlZKjugvIMOShS', 'ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('ale200', '$2a$10$svmq0rFHa4fJK3m.g3fYFOC/vcHrKM.9PNw7Ngz9bXR5sFMuQ7iKC', 'ROLE_USER');

INSERT INTO team (owner_id, name, create_date) VALUES(
    (SELECT user_id FROM users WHERE username = 'andrew500'),
    'Struggle Bus E-sports',
    CURRENT_DATE
);
INSERT INTO team (owner_id, name, create_date) VALUES(
    (SELECT user_id FROM users WHERE username = 'andrew501'),
    'Golfing Masters',
    CURRENT_DATE
);
INSERT INTO game (name, description, win_type) values (
    'Chess',
    'A game of wits',
    (SELECT condition_id FROM win_condition WHERE name = 'MAX')
);
INSERT INTO game (name, description, win_type) values (
    'Soccer',
    'Kick the ball',
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

INSERT INTO match (game_id,round_count,is_scrim)
VALUES ((SELECT game_id
	   FROM game
	   WHERE name = 'Golf'),3,FALSE);

INSERT INTO bracket (parent_bracket,match_id,name)
VALUES (NULL, 1, 'First bracket' );

INSERT INTO tournament (game_id,bracket_id, creator_id, name,is_scrim)
VALUES ((SELECT game_id
	   FROM game
	   WHERE name = 'Golf'),1,3,'Golf Tournament', FALSE);

-- BRIDGE TABLES
INSERT INTO team_players (user_id, team_id) VALUES(
    (SELECT user_id FROM users WHERE username = 'andrew501'),
    (SELECT team_id FROM team WHERE name = 'Struggle Bus E-sports')
);
INSERT INTO team_players (user_id, team_id) VALUES(
    (SELECT user_id FROM users WHERE username = 'andrew501'),
    (SELECT team_id FROM team WHERE name = 'Golfing Masters')
);
INSERT INTO team_players (user_id, team_id) VALUES(
    (SELECT user_id FROM users WHERE username = 'andrew500'),
    (SELECT team_id FROM team WHERE name = 'Struggle Bus E-sports')
);
INSERT INTO team_players (user_id, team_id) VALUES(
    (SELECT user_id FROM users WHERE username = 'andrew500'),
    (SELECT team_id FROM team WHERE name = 'Golfing Masters')
);
INSERT INTO team_players (user_id, team_id) VALUES(
    (SELECT user_id FROM users WHERE username = 'ale200'),
    (SELECT team_id FROM team WHERE name = 'Struggle Bus E-sports')
);
INSERT INTO team_games (team_id, game_id) VALUES (
    (SELECT team_id FROM team WHERE name = 'Struggle Bus E-sports'),
    (SELECT game_id FROM game WHERE name = 'CS2')
);
INSERT INTO team_games (team_id, game_id) VALUES (
    (SELECT team_id FROM team WHERE name = 'Struggle Bus E-sports'),
    (SELECT game_id FROM game WHERE name = 'Golf')
);
INSERT INTO team_games (team_id, game_id) VALUES (
    (SELECT team_id FROM team WHERE name = 'Golfing Masters'),
    (SELECT game_id FROM game WHERE name = 'Golf')
);

COMMIT TRANSACTION;
