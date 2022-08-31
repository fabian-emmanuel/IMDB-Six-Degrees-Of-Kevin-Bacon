--
-- -- The Below Query is used to generate the data - movies.csv for the movie table

select tb.tconst, tb.primarytitle, tb.startyear, tp.nconst, nb.primaryname
from title_basics tb
join title_principals tp on tb.tconst = tp.tconst
join name_basics nb on tp.nconst = nb.nconst
where tb.titletype='movie'
and tp.category='actor' or tp.category='actress';


CREATE TABLE movies (
    movie_id TEXT,
    movie_title TEXT,
    movie_year INTEGER,
    actor_id TEXT,
    actor_name TEXT);
COPY movies FROM '/Users/decagon/Desktop/movies.csv' DELIMITER ',';

CREATE TABLE acted_with (
    actor_id text,
    actor_name text,
    movie_title text,
    movie_year text,
    other_actor_id text,
    other_actor_name text);

INSERT INTO acted_with (
    SELECT
        a.actor_id,
        a.actor_name,
        a.movie_title,
        a.movie_year,
        b.actor_id AS other_actor_id,
        b.actor_name AS other_actor_name
    FROM movies AS a
    LEFT JOIN movies AS b
        ON a.movie_id = b.movie_id
    WHERE a.actor_id != b.actor_id);