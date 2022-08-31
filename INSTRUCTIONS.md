# IMDb CopyCat + Six Degrees of Kevin Bacon


## The Details
This Project is based on the popular website [IMDb](https://www.imdb.com/)
which offers movie and TV show information. They have kindly made their dataset
publicly available at [IMDb Datasets](https://www.imdb.com/interfaces/). 

This Project's mission, is to write a web application that can
fulfil the following requirements:

### Requirement #1 (easy):

IMDb copycat: Present the user with endpoint for allowing them to search by
movie’s primary title or original title. The outcome should be related
information to that title, including cast and crew.

### Requirement #2 (easy):

Top rated movies: Given a query by the user, you must provide what are the top
rated movies for a genre (If the user searches horror, then it should show a
list of top rated horror movies).

### Requirement #3 (difficult):

[Six degrees of Kevin
Bacon](https://en.wikipedia.org/wiki/Six_Degrees_of_Kevin_Bacon): Given a query
by the user, you must provide what’s the degree of separation between the person
(e.g. actor or actress) the user has entered and Kevin Bacon. 

## Setup

To speed up the Project development, a `docker-compose` file has been provided that
will run a [PostgreSQL](https://www.postgresql.org/) instance with all the data loaded,
which you can start with the following command: `docker-compose up`. You can connect to
this instance from your application with the following values:

```
JDBC URL = jdbc:postgresql://localhost:5432/lunatech_imdb
Username = postgres
Password = postgres
```

The schema is defined within the file `schema.cql` in the `database-init` folder. There
you'll find what each column represents on every table, this is taken from
[IMDb interfaces](https://www.imdb.com/interfaces/).

Running this Docker Compose will download the dataset and insert the values in the DB.
The process should take roughly 20 to 30 minutes. We've truncated the original dataset,
removing 'tvEpisodes', to make the whole process faster. If you want to take a look at
how that's done, see the file `data-cleanup.sh` (you'll need `wget` and `ammonite` to
run the script).

After the import process is done, you'll see a message with a summary of the inserted
data (small differences between the Input and Row counts are fine)

### Data import takes too long

If you experience a longer import time than 30 minutes, you can try to use a minimal
dataset. Edit the `docker-compose.yaml` file, and set the environment variable `MINIMAL_DATASET`
to `true` (Remember to remove all previous Docker containers and volumes).

### Troubleshooting

If you experience other issues, please be sure to check the [`TROUBLESHOOTING.md`](TROUBLESHOOTING.md)
file.
