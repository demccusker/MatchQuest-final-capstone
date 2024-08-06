BEGIN TRANSACTION;
--ROLLBACK;
DROP TABLE IF EXISTS  users, user_details, team, team_games,
game, win_condition, tournament, tournament_teams, bracket,
match, match_teams, match_results, result
CASCADE;
DROP SEQUENCE IF EXISTS seq_detail_id;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_users PRIMARY KEY (user_id)
);

CREATE SEQUENCE seq_detail_id
    INCREMENT BY 1
    START WITH 1000
    NO MAXVALUE;

-- user_details Table
CREATE TABLE user_details (
    detail_id INT NOT NULL DEFAULT nextval('seq_detail_id'),
    user_id INT NOT NULL,
    display_name VARCHAR(50),
    elo_rating INT NOT NULL,
    is_staff BOOLEAN NOT NULL,
    CONSTRAINT pk_user_details PRIMARY KEY (detail_id),
    CONSTRAINT fk_user_details_users FOREIGN KEY (user_id) REFERENCES users (user_id)
);

-- win_condition Table
CREATE TABLE win_condition (
    condition_id SERIAL NOT NULL,
    name VARCHAR(50) UNIQUE NOT NULL,
    description VARCHAR(255),
    CONSTRAINT pk_win_condition PRIMARY KEY (condition_id)
);

-- game Table
CREATE TABLE game (
    game_id SERIAL NOT NULL,
    name VARCHAR(50) UNIQUE NOT NULL,
    description VARCHAR(255),
    win_type INT NOT NULL,
    max_players INT NOT NULL DEFAULT -1, -- Assuming this is intended to be -1
    min_players INT NOT NULL DEFAULT -1, -- Assuming this is intended to be -1
    CONSTRAINT pk_game PRIMARY KEY (game_id),
    CONSTRAINT fk_game_win_condition FOREIGN KEY (win_type) REFERENCES win_condition (condition_id)
);

-- team Table
CREATE TABLE team (
    team_id SERIAL NOT NULL,
    name VARCHAR(50) UNIQUE NOT NULL,
    owner_id INT NOT NULL,
    create_date DATE NOT NULL,
    CONSTRAINT pk_team PRIMARY KEY (team_id),
    CONSTRAINT fk_team_users FOREIGN KEY (owner_id) REFERENCES users (user_id)
);

-- bridge table between users and team table
CREATE TABLE team_players (
    user_id INT NOT NULL,
    team_id INT NOT NULL,

    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT fk_team_id FOREIGN KEY (team_id) REFERENCES team (team_id)
);

-- team_games Table
CREATE TABLE team_games (
    team_id INT NOT NULL,
    game_id INT NOT NULL,
    CONSTRAINT pk_team_games PRIMARY KEY (team_id, game_id),
    CONSTRAINT fk_team_games_team FOREIGN KEY (team_id) REFERENCES team (team_id),
    CONSTRAINT fk_team_games_game FOREIGN KEY (game_id) REFERENCES game (game_id)
);

-- match Table
CREATE TABLE match (
    match_id SERIAL NOT NULL,
    game_id INT NOT NULL,
    round_count INT NOT NULL,
    is_scrim BOOLEAN NOT NULL,
    CONSTRAINT pk_match PRIMARY KEY (match_id),
    CONSTRAINT fk_match_game FOREIGN KEY (game_id) REFERENCES game (game_id)
);

-- bracket Table
CREATE TABLE bracket (
    bracket_id SERIAL NOT NULL,
    parent_bracket INT REFERENCES bracket (bracket_id), -- Self-referencing foreign key
    match_id INT NOT NULL,
    name VARCHAR(50),
    CONSTRAINT pk_bracket PRIMARY KEY (bracket_id),
    CONSTRAINT fk_bracket_match FOREIGN KEY (match_id) REFERENCES match (match_id)
);

CREATE TABLE address (
    address_id SERIAL NOT NULL,
    building_number INT NOT NULL,
    street VARCHAR(50),
    city VARCHAR(50),
    province VARCHAR(50),
    country VARCHAR(50),

    CONSTRAINT pk_tournament PRIMARY KEY(address_id)
);

-- tournament Table
CREATE TABLE tournament (
    tournament_id SERIAL NOT NULL,
    bracket_id INT NOT NULL,
    game_id INT NOT NULL,
    creator_id INT NOT NULL,
    name VARCHAR(50),
    is_scrim BOOLEAN NOT NULL,
    is_online BOOLEAN NOT NULL,
    location INT NULL,
    start_date DATE NOT NULL,
    end_date DATE NULL,

    CONSTRAINT pk_tournament PRIMARY KEY (tournament_id),
    CONSTRAINT fk_tournament_bracket FOREIGN KEY (bracket_id) REFERENCES bracket (bracket_id),
    CONSTRAINT fk_tournament_game FOREIGN KEY (game_id) REFERENCES game (game_id),
    CONSTRAINT fk_tournament_creator FOREIGN KEY (creator_id) REFERENCES users (user_id),
    CONSTRAINT fk_tournament_location FOREIGN KEY (location) REFERENCES address (address_id)
);

-- tournament_teams Table
CREATE TABLE tournament_teams (
    team_id INT NOT NULL,
    tournament_id INT NOT NULL,
    CONSTRAINT pk_tournament_teams PRIMARY KEY (team_id, tournament_id),
    CONSTRAINT fk_tournament_teams_team FOREIGN KEY (team_id) REFERENCES team (team_id),
    CONSTRAINT fk_tournament_teams_tournament FOREIGN KEY (tournament_id) REFERENCES tournament (tournament_id)
);

-- match_teams Table
CREATE TABLE match_teams (
    match_id INT NOT NULL,
    team_id INT NOT NULL,
    CONSTRAINT pk_match_teams PRIMARY KEY (match_id, team_id),
    CONSTRAINT fk_match_teams_match FOREIGN KEY (match_id) REFERENCES match (match_id),
    CONSTRAINT fk_match_teams_team FOREIGN KEY (team_id) REFERENCES team (team_id)
);
-- result Table
CREATE TABLE result (
    result_id SERIAL NOT NULL,
    is_winner BOOLEAN NOT NULL,
    is_draw BOOLEAN NOT NULL,
    elo_change DOUBLE PRECISION NOT NULL DEFAULT 0,
    CONSTRAINT pk_result PRIMARY KEY (result_id)
);

-- match_results Table
CREATE TABLE match_results (
    match_id INT NOT NULL,
    result_id INT NOT NULL,
    CONSTRAINT pk_match_results PRIMARY KEY (match_id, result_id),
    CONSTRAINT fk_match_results_match FOREIGN KEY (match_id) REFERENCES match (match_id),
    CONSTRAINT fk_match_results_result FOREIGN KEY (result_id) REFERENCES result (result_id)
);

COMMIT TRANSACTION;
