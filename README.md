To build this project, just run (on linux/macos machine)

```
./build.sh
```
That runs gradle, all tests and build a docker image.

Then you can run docker compose with 
```
docker-compose up
```

Docker compose 1.29 and above is required.

Give a little bit of time to everything to startup. Then, swagger ui will be accessible under

```
http://localhost:8080/swagger-ui/index.html
```
