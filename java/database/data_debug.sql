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





INSERT INTO user_details(user_id, display_name, elo_rating, is_staff) VALUES
    (1, 'user', 1150, FALSE),
    (2, 'admin', 1100, TRUE),
    (3, 'Andrew', 1200, FALSE),
    (4, 'Drew', 1100, TRUE),
    (5, 'A-Andrew', 1200, TRUE),
    (6, 'user2', 900, TRUE),
    (7, 'Glitter Badger', 1250, TRUE),
    (8, 'FalconX', 1300, FALSE),
    (9, 'Bob', 1250, FALSE),
    (10, 'usera50', 1250, TRUE),
    (11, 'usera51', 1250, TRUE),
    (12, 'user52', 1250, FALSE),
    (13, 'user53', 1250, FALSE),
    (14, 'user54', 1250, FALSE),
    (15, 'user55', 1250, FALSE),
    (16, 'user56', 1250, FALSE),
    (17, 'user57', 1250, FALSE),
    (18, 'user58', 1250, FALSE),
    (19, 'user59', 1250, FALSE),
    (20, 'usera60', 1250, FALSE);



INSERT INTO game (name, description, win_type) VALUES
    ('Chess', 'A game of wits', (SELECT condition_id FROM win_condition WHERE name = 'MAX')),
    ('Golf', 'Surprisingly frustrating and calming', (SELECT condition_id FROM win_condition WHERE name = 'MIN')),
    ('CS2', 'The bomb has been planted', (SELECT condition_id FROM win_condition WHERE name = 'MAX')),
    ('War', 'A game of luck', (SELECT condition_id FROM win_condition WHERE name = 'MAX')),
    ('Minecraft', 'How does one win?', (SELECT condition_id FROM win_condition WHERE name = 'MAX')),
    ('Badminton', 'Watch it fly!', (SELECT condition_id FROM win_condition WHERE name = 'MAX'));


INSERT INTO match (game_id, is_scrim) VALUES
    ((SELECT game_id FROM game WHERE name = 'Minecraft'), FALSE),
    ((SELECT game_id FROM game WHERE name = 'Minecraft'), FALSE),
    ((SELECT game_id FROM game WHERE name = 'Minecraft'), FALSE),
    ((SELECT game_id FROM game WHERE name = 'Minecraft'), FALSE),
    ((SELECT game_id FROM game WHERE name = 'Minecraft'), FALSE),
    ((SELECT game_id FROM game WHERE name = 'Minecraft'), FALSE),
      ((SELECT game_id FROM game WHERE name = 'Minecraft'), FALSE);



INSERT INTO bracket (bracket_id, parent_bracket, match_id, name) VALUES
    ((SELECT nextval('seq_bracket_id')), null, 2, 'Final'),
    ((SELECT currval('seq_bracket_id')) + 1, (SELECT currval('seq_bracket_id')), 2, 'Semi-Final'),
    ((SELECT currval('seq_bracket_id')) + 2, (SELECT currval('seq_bracket_id')), 2, 'Semi-Final'),
    ((SELECT currval('seq_bracket_id')) + 3, (SELECT currval('seq_bracket_id')) + 1, 2, 'Quarter-Final'),
    ((SELECT currval('seq_bracket_id')) + 4, (SELECT currval('seq_bracket_id')) + 1, 2, 'Quarter-Final'),
    ((SELECT currval('seq_bracket_id')) + 5, (SELECT currval('seq_bracket_id')) + 2, 2, 'Quarter-Final'),
    ((SELECT currval('seq_bracket_id')) + 6, (SELECT currval('seq_bracket_id')) + 2, 2, 'Quarter-Final'),
    ((SELECT nextval('seq_bracket_id')), null, 7, 'Final'),
    ((SELECT currval('seq_bracket_id')) + 1, (SELECT currval('seq_bracket_id')), 6, 'Semi-Final'),
    ((SELECT currval('seq_bracket_id')) + 2, (SELECT currval('seq_bracket_id')), 5, 'Semi-Final'),
      ((SELECT currval('seq_bracket_id')) + 3, (SELECT currval('seq_bracket_id')) + 1, 4, 'Quarter-Final'),
        ((SELECT currval('seq_bracket_id')) + 4, (SELECT currval('seq_bracket_id')) + 1, 3, 'Quarter-Final'),
        ((SELECT currval('seq_bracket_id')) + 5, (SELECT currval('seq_bracket_id')) + 2, 2, 'Quarter-Final'),
        ((SELECT currval('seq_bracket_id')) + 6, (SELECT currval('seq_bracket_id')) + 2, 1, 'Quarter-Final');

INSERT INTO tournament (game_id, bracket_id, creator_id, name, max_participants, is_scrim, is_online, location, start_date, end_date) VALUES
    ((SELECT game_id FROM game WHERE name = 'Badminton'), 255, 3, 'Epic Game of Badminton', 30, FALSE, FALSE, '1200 Smith Street', CURRENT_DATE, NULL),
    ((SELECT game_id FROM game WHERE name = 'Golf'), 255, 2, 'Golf Tournament', 20, FALSE, FALSE, '650 Yankee Avenue', CURRENT_DATE, NULL),
    ((SELECT game_id FROM game WHERE name = 'Golf'), 255, 2, 'Golfy Tournament', 15, TRUE, TRUE, NULL, CURRENT_DATE, NULL),
    ((SELECT game_id FROM game WHERE name = 'Minecraft'), 510, 3, 'Minecraft Tournament of Champs', 8 , FALSE, TRUE, NULL, '2024-01-01', '2024-01-02');



INSERT INTO address (tournament_id, city, province, country) VALUES
    (1, 'Los Angeles', 'California', 'USA'),
    (2, 'Los Angeles', 'California', 'USA'),
    (2, 'Annapolis', 'Maryland', 'USA'),
    (3, 'Lewes', 'Deleware', 'USA');

INSERT INTO result (is_draw, elo_change, winner_id) VALUES
    (FALSE, 50,  (SELECT user_id FROM users WHERE username = 'usera50'));

-- BRIDGE TABLES
INSERT INTO match_players (match_id, user_id) VALUES (
    (SELECT match_id FROM match WHERE match_id = 1),
    (SELECT user_id FROM users WHERE username = 'andrew501')
);

--INSERT INTO match_results (match_id, user_id) VALUES (
--    (SELECT match_id FROM match WHERE match_id = 1),
--    (SELECT user_id FROM users WHERE username = 'user2')
--);

INSERT INTO tournament_players (user_id, tournament_id) VALUES (
    (SELECT user_id FROM users WHERE username = 'andrew501'),
    (SELECT tournament_id FROM tournament WHERE name = 'Golf Tournament')
);

COMMIT TRANSACTION;
