CREATE SEQUENCE IF NOT EXISTS movie_rating_id_seq START 1;
CREATE SEQUENCE hibernate_sequence START 1;

CREATE TABLE movierating (
    id integer NOT NULL DEFAULT nextval('movie_rating_id_seq'::regclass),
    unique_value character varying(60),
    user_key character varying(30),
    title character varying(255),
    imdb_id character varying(20),
    imdb_rating character varying(10),
    box_office  character varying(30)
);

CREATE INDEX idx_unique_value ON movierating(unique_value);

INSERT INTO movierating (id, unique_value, user_key, title, imdb_id, imdb_rating, box_office)
                VALUES (1, 'tt0947798abc', 'abc', 'Black Swan', 'tt0947798', '7.5', '$106,954,678');

INSERT INTO movierating (id, unique_value, user_key, title, imdb_id, imdb_rating, box_office)
                VALUES (2, 'tt0964517xyz', 'xys', 'The Fighter', 'tt0964517', '6.5', '$93,617,009');

INSERT INTO movierating (id, unique_value, user_key, title, imdb_id, imdb_rating, box_office)
                VALUES (3, 'tt0947798def', 'def', 'Black Swan', 'tt0947798', '8.5', '$106,954,678');

INSERT INTO movierating (id, unique_value, user_key, title, imdb_id, imdb_rating, box_office)
                VALUES (4, 'tt0964517def', 'def', 'The Fighter', 'tt0964517', '8.5', '$93,617,009');

INSERT INTO movierating (id, unique_value, user_key, title, imdb_id, imdb_rating, box_office)
                VALUES (5, 'tt0379725ghi', 'ghi', 'Capote', 'tt0379725', '6.0', '$28,750,530');

INSERT INTO movierating (id, unique_value, user_key, title, imdb_id, imdb_rating, box_office)
                VALUES (6, 'tt0379725abc', 'abc', 'Capote', 'tt0379725', '7.0', '$28,750,530');

INSERT INTO movierating (id, unique_value, user_key, title, imdb_id, imdb_rating, box_office)
                VALUES (7, 'tt0947798a', 'a', 'Black Swan', 'tt0947798', '4.5', '$106,954,678');

INSERT INTO movierating (id, unique_value, user_key, title, imdb_id, imdb_rating, box_office)
                VALUES (8, 'tt0964517b', 'b', 'The Fighter', 'tt0964517', '5.5', '$93,617,009');

INSERT INTO movierating (id, unique_value, user_key, title, imdb_id, imdb_rating, box_office)
                VALUES (9, 'tt0947798z', 'z', 'Black Swan', 'tt0947798', '7.3', '$106,954,678');

INSERT INTO movierating (id, unique_value, user_key, title, imdb_id, imdb_rating, box_office)
                VALUES (10, 'tt0964517t', 't', 'The Fighter', 'tt0964517', '6.5', '$93,617,009');

INSERT INTO movierating (id, unique_value, user_key, title, imdb_id, imdb_rating, box_office)
                VALUES (11, 'tt0379725i', 'i', 'Capote', 'tt0379725', '5.0', '$28,750,530');

INSERT INTO movierating (id, unique_value, user_key, title, imdb_id, imdb_rating, box_office)
                VALUES (12, 'tt0379725m', 'm', 'Capote', 'tt0379725', '5.0', '$28,750,530');

INSERT INTO movierating (id, unique_value, user_key, title, imdb_id, imdb_rating, box_office)
                VALUES (13, 'tt1324076ac', 'ac', 'Fox', 'tt1324076', '8.4', 'N/A');

INSERT INTO movierating (id, unique_value, user_key, title, imdb_id, imdb_rating, box_office)
                VALUES (14, 'tt0060025th', 'th', 'Skippy', 'tt0060025', '7.7', 'N/A');

INSERT INTO movierating (id, unique_value, user_key, title, imdb_id, imdb_rating, box_office)
                VALUES (15, 'tt1324076az', 'az', 'Fox', 'tt1324076', '4.4', 'N/A');

INSERT INTO movierating (id, unique_value, user_key, title, imdb_id, imdb_rating, box_office)
                VALUES (16, 'tt0060025ze', 'ze', 'Skippy', 'tt0060025', '5.5', 'N/A');
