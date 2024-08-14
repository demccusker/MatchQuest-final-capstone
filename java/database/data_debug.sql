BEGIN TRANSACTION;

INSERT INTO win_condition (name) values ('MIN');
INSERT INTO win_condition (name) values ('MAX');

--Testing data PW is the usernames
INSERT INTO users (username,password_hash,role) VALUES
    ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER'),
    ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN'),
    ('andrew500', '$2a$10$VPhB5.Tv16s02uul4lAJ6uahtkdP6M.QUnN0uI06A26IXrETFlzBy', 'ROLE_USER'),
    ('andrew501', '$2a$10$CWafZTSVTpRp6.5tpUJgN.0.jEjKIZhQvlZwjaNvlZKjugvIMOShS', 'ROLE_USER'),
    ('ale200', '$2a$10$svmq0rFHa4fJK3m.g3fYFOC/vcHrKM.9PNw7Ngz9bXR5sFMuQ7iKC', 'ROLE_USER'),
    ('user1', '$2a$10$UybPItqtS4FMj0M0.N9QSOkEVlnzEsz6Kac8QTvNAuG/Z5L/tioy2', 'ROLE_USER'),
    ('user2', '$2a$10$/67qk31UotdsiEAqPRq4wumVN2y1dVC4kz8gF60R/VuJyEKUG8Jc6', 'ROLE_USER'),
    ('user3', '$2a$10$KOdlfPM9NsSFXnONUttBXeYB5F1kwck9ICBsPcjlNPjIVPbJEBt2y', 'ROLE_USER'),
    ('user4', '$2a$10$P7bOg1bC0aeTUFAdrM34TeF6BEosqqWwqHPv3pOXSD6Jys4YviCMK', 'ROLE_USER'),
    ('user5', '$2a$10$8VvMnODnlWlGL0w6RvekaOlHLqlXjI5JOerJMLY13ewQWVloCbhYS', 'ROLE_USER'),
    ('bob', '$2a$10$sAIk.gDXQ9Sfte43sAIyFeUCAHiBs5MGR219FpUiFOQ5NzROLXZha', 'ROLE_USER'),
    ('usera50', '$2a$10$92N1WJDZKOsLc9FfJs/a8OIP8rdSYNOKvtIZievyoV6Mx96JwF6Hi', 'ROLE_USER'),
    ('usera51', '$2a$10$ueNoLJgQ8v7YczMrlxL6N.BDOwApHzLctpzNWExI87P2mdUKLZCaa', 'ROLE_USER'),
    ('usera52', '$2a$10$SjwQ8WD2emAFiid95v3znuwrY.VZorSPdZTdvJIBAq8qEODZQV5QK', 'ROLE_USER'),
    ('usera53', '$2a$10$/41kmQDbf7XUGlX/AxLYpe7tP1.kf1qfqltONhXcRusvYh7cRlSCC', 'ROLE_USER'),
    ('usera54', '$2a$10$rjHcjxrs/VFDeCgoenYENOUfvAwkZdePvHwILhyAWJm0I68lTSCF6', 'ROLE_USER'),
    ('usera55', '$2a$10$ecUWs3tydaJNpxSl3hicRulBxHRphSa0Elkz/FJVPcep7YfNEGqpS', 'ROLE_USER'),
    ('usera56', '$2a$10$o8QEdITCxckNQwBwZ4NbB.T2znm/wOexHRZMtFrQF3MOXvL8LjDuS', 'ROLE_USER'),
    ('usera57', '$2a$10$.cK/zLVkWLrHLvo1wB3FGuvYJ/wn5g.jrbSlPhpbLkkMptGBqFzNS', 'ROLE_USER'),
    ('usera58', '$2a$10$wq7ExJIecLZ3YwMPVDpGCexgKa/ORYYEov7L.5oRquHtm4kgDVX1K', 'ROLE_USER'),
    ('usera59', '$2a$10$hnwjnSGuve27jqzdCRSo3.VcmF1c26K5i3S.JU4LSn.iUDZUYj5FO', 'ROLE_USER'),
    ('usera60', '$2a$10$2DtfOEfKdImZfyM7ROkTceXdAUwZOHVfFpTOcgfirXbCTq40geGsS', 'ROLE_USER');





  INSERT INTO user_details (user_id, display_name, elo_rating, is_staff) VALUES
      (1, 'user', 1150, FALSE),
      (2, 'admin', 1100, TRUE),
      (3, 'Andrew', 1200, FALSE),
      (4, 'Drew', 1100, TRUE),
      (5, 'A-Andrew', 1200, TRUE),
      (6, 'user1', 900, TRUE),
      (7, 'Glitter Badger', 1250, TRUE),
      (8, 'user2', 1300, FALSE),
      (9, 'user4', 1250, FALSE),
      (10, 'user5', 1250, FALSE),
      (11, 'Bob', 1250, FALSE),
      (12, 'usera50', 1250, TRUE),
      (13, 'usera51', 1250, TRUE),
      (14, 'user52', 1250, FALSE),
      (15, 'user53', 1250, FALSE),
      (16, 'user54', 1250, FALSE),
      (17, 'user55', 1250, FALSE),
      (18, 'user56', 1250, FALSE),
      (19, 'user57', 1250, FALSE),
      (20, 'user58', 1250, FALSE),
      (21, 'user59', 1250, FALSE),
      (22, 'usera60', 1250, FALSE);

INSERT INTO game (name, description, win_type) VALUES
    ('Chess', 'A game of wits', (SELECT condition_id FROM win_condition WHERE name = 'MAX')),
    ('Golf', 'Surprisingly frustrating and calming', (SELECT condition_id FROM win_condition WHERE name = 'MIN')),
    ('CS2', 'The bomb has been planted', (SELECT condition_id FROM win_condition WHERE name = 'MAX')),
    ('War', 'A game of luck', (SELECT condition_id FROM win_condition WHERE name = 'MAX')),
    ('Minecraft', 'How does one win?', (SELECT condition_id FROM win_condition WHERE name = 'MAX')),
    ('Badminton', 'Watch it fly!', (SELECT condition_id FROM win_condition WHERE name = 'MAX'));



---- Inserting Matches
INSERT INTO match (game_id, is_scrim, player1_id, player2_id, player1_score, player2_score, winner_id, is_draw) VALUES
    ((SELECT game_id FROM game WHERE name = 'Minecraft'), FALSE, 13, 14, 40, 30, 13, FALSE),
    ((SELECT game_id FROM game WHERE name = 'Minecraft'), FALSE, 15, 16, 40, 30, 15, FALSE),
    ((SELECT game_id FROM game WHERE name = 'Minecraft'), FALSE, 17, 18, 40, 30, 17, FALSE),
    ((SELECT game_id FROM game WHERE name = 'Minecraft'), FALSE, 19, 20, 40, 30, 19, FALSE),
    ((SELECT game_id FROM game WHERE name = 'Minecraft'), FALSE, 13, 15, 40, 30, 13, FALSE),
    ((SELECT game_id FROM game WHERE name = 'Minecraft'), FALSE, 17, 19, 40, 30, 17, FALSE),
    ((SELECT game_id FROM game WHERE name = 'Minecraft'), FALSE, 13, 17, 40, 30, 13, FALSE),
    ((SELECT game_id FROM game WHERE name = 'Minecraft'), FALSE, 1, 2, 40, 30, 1, FALSE);



-- Inserting into Bracket
INSERT INTO bracket (bracket_id, parent_bracket, match_id, name) VALUES
    ((SELECT nextval('seq_bracket_id')), NULL, 8, 'Final'),  -- Final match
    ((SELECT currval('seq_bracket_id')) + 1, (SELECT currval('seq_bracket_id')), 8, 'Semi-Final'),  -- Semi-Final 1
    ((SELECT currval('seq_bracket_id')) + 2, (SELECT currval('seq_bracket_id')), 8, 'Semi-Final'),  -- Semi-Final 2
    ((SELECT currval('seq_bracket_id')) + 3, (SELECT currval('seq_bracket_id')) + 1, 8, 'Quarter-Final'),  -- Quarter-Final 1
    ((SELECT currval('seq_bracket_id')) + 4, (SELECT currval('seq_bracket_id')) + 1, 8, 'Quarter-Final'),  -- Quarter-Final 2
    ((SELECT currval('seq_bracket_id')) + 5, (SELECT currval('seq_bracket_id')) + 2, 8, 'Quarter-Final'),  -- Quarter-Final 3
    ((SELECT currval('seq_bracket_id')) + 6, (SELECT currval('seq_bracket_id')) + 2, 8, 'Quarter-Final'),  -- Quarter-Final 4
    ((SELECT nextval('seq_bracket_id')), NULL, 7, 'Final'),  -- Final match for another bracket
    ((SELECT currval('seq_bracket_id')) + 1, (SELECT currval('seq_bracket_id')), 6, 'Semi-Final'),  -- Semi-Final 1 for second bracket
    ((SELECT currval('seq_bracket_id')) + 2, (SELECT currval('seq_bracket_id')), 5, 'Semi-Final'),  -- Semi-Final 2 for second bracket
    ((SELECT currval('seq_bracket_id')) + 3, (SELECT currval('seq_bracket_id')) + 1, 4, 'Quarter-Final'),  -- Quarter-Final 1 for second bracket
    ((SELECT currval('seq_bracket_id')) + 4, (SELECT currval('seq_bracket_id')) + 1, 3, 'Quarter-Final'),  -- Quarter-Final 2 for second bracket
    ((SELECT currval('seq_bracket_id')) + 5, (SELECT currval('seq_bracket_id')) + 2, 2, 'Quarter-Final'),  -- Quarter-Final 3 for second bracket
    ((SELECT currval('seq_bracket_id')) + 6, (SELECT currval('seq_bracket_id')) + 2, 1, 'Quarter-Final');  -- Quarter-Final 4 for second bracket


--
---- Inserting into Tournament
INSERT INTO tournament (game_id, bracket_id, creator_id, name, max_participants, is_scrim, is_online, location, start_date, end_date) VALUES
    ((SELECT game_id FROM game WHERE name = 'Badminton'), null, 1, 'Epic Game of Badminton', 30, FALSE, FALSE, '1200 Smith Street', CURRENT_DATE, NULL),
    ((SELECT game_id FROM game WHERE name = 'Golf'), null, 2, 'Golf Tournament', 20, FALSE, FALSE, '650 Yankee Avenue', CURRENT_DATE, NULL),
    ((SELECT game_id FROM game WHERE name = 'Golf'), null, 2, 'Golfy Tournament', 15, TRUE, TRUE, NULL, CURRENT_DATE, NULL),
    ((SELECT game_id FROM game WHERE name = 'Minecraft'), null, 3, 'Minecraft Tournament of Champs', 8, FALSE, TRUE, NULL, '2024-01-01', '2024-01-02'),
    ((SELECT game_id FROM game WHERE name = 'Chess'), null, 3, 'Mind Games Tournament', 64, FALSE, TRUE, NULL, '2026-01-01', NULL);


INSERT INTO address (tournament_id, city, province, country) VALUES
    (1, 'Los Angeles', 'California', 'USA'),
    (2, 'Los Angeles', 'California', 'USA'),
    (2, 'Annapolis', 'Maryland', 'USA'),
    (3, 'Lewes', 'Deleware', 'USA');


INSERT INTO tournament_players (user_id, tournament_id) VALUES
    ((SELECT user_id FROM users WHERE username = 'usera51'),
     (SELECT tournament_id FROM tournament WHERE name = 'Minecraft Tournament of Champs')),
    ((SELECT user_id FROM users WHERE username = 'usera52'),
     (SELECT tournament_id FROM tournament WHERE name = 'Minecraft Tournament of Champs')),
    ((SELECT user_id FROM users WHERE username = 'usera53'),
     (SELECT tournament_id FROM tournament WHERE name = 'Minecraft Tournament of Champs')),
    ((SELECT user_id FROM users WHERE username = 'usera54'),
     (SELECT tournament_id FROM tournament WHERE name = 'Minecraft Tournament of Champs')),
    ((SELECT user_id FROM users WHERE username = 'usera55'),
     (SELECT tournament_id FROM tournament WHERE name = 'Minecraft Tournament of Champs')),
    ((SELECT user_id FROM users WHERE username = 'usera56'),
     (SELECT tournament_id FROM tournament WHERE name = 'Minecraft Tournament of Champs')),
    ((SELECT user_id FROM users WHERE username = 'usera57'),
     (SELECT tournament_id FROM tournament WHERE name = 'Minecraft Tournament of Champs')),
    ((SELECT user_id FROM users WHERE username = 'usera58'),
     (SELECT tournament_id FROM tournament WHERE name = 'Minecraft Tournament of Champs')),
    ((SELECT user_id FROM users WHERE username = 'user1'),
     (SELECT tournament_id FROM tournament WHERE name = 'Mind Games Tournament')),
    ((SELECT user_id FROM users WHERE username = 'user2'),
     (SELECT tournament_id FROM tournament WHERE name = 'Mind Games Tournament')),
    ((SELECT user_id FROM users WHERE username = 'user3'),
     (SELECT tournament_id FROM tournament WHERE name = 'Mind Games Tournament')),
    ((SELECT user_id FROM users WHERE username = 'user4'),
     (SELECT tournament_id FROM tournament WHERE name = 'Mind Games Tournament')),
    ((SELECT user_id FROM users WHERE username = 'user5'),
     (SELECT tournament_id FROM tournament WHERE name = 'Mind Games Tournament'));


COMMIT TRANSACTION;
