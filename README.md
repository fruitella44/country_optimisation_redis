## DataBase optimisation

> * Import backup to MySQL
> * Mapped: dbEntity, redisEntity
> * Created DAO and Service. Used Criteria API
> * Command to create MySQL in docker container with volume:
> * > docker run --name <db_name> -d -p 3306:3306 -e <your_password>=root --restart unless-stopped -v mysql:/var/lib/mysql mysql:8
> * Command to create Redis in docker container:
> * > docker run -d --name redis -p 6379:6379 redis:latest

* Final result with test: redis=77ms, mysql=116ms
* Logs write into file: "appLog.log" to directory: "/resources/logs" - removed from index git
* Connection to MySql with "hibernate.cfg.xml" - removed from index git
* Connection to Redis with: "url - localhost", "port - 6379"
* Download: ![dump](https://javarush.com/downloads/ide/javarush/dump-hibernate-final.sql)
------------------------------------
* Used technologies:
  JDK-17, Maven, Hibernate-5, Criteria API, Docker, MySQL-8, Redis, Log4j2, Jackson, Lombok.
------------------------------------
* Result:
* ![redis](https://i.imgur.com/ePV8hn8.png)
* ![result](https://i.imgur.com/5Zb9d9R.png)