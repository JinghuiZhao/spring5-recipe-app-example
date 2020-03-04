#### Run mysql in docker:
docker run --name  mysqldb -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql

#### Run maven with profile specified:
mvn spring-boot:run -Dspring-boot.run.profiles=dev

load mysql bootstrap data to see the result in the sql client.
