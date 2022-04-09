# CompanyCob-DDD
Debt update system as training for Domain-Driven Design

---

## Environment

* Java 11
* Gradle 7.1.1

---

## Starting
#### - Compile

In root directory, run:

```shell
$ ./gradlew build
```

#### - Run Registration API locally

##### - Prerequisites

First, you need to run 3 containers:
* Database postgres
* Keycloak
* Localstack

The database and localstack are specific to this project. So you can just run:
```shell
$ ./local_environment.sh
```

This will create the database and localstack containers.

To run keycloack, follow the instructions in `<ProjectRoot>/auth-server`.

Next step is to copy the `web/src/main/resources/application.yml.sample` e paste in the same directory with the name
`application.yml`. This file will be used as your local configurations.

##### - Run application

To run locally, you need use a spring profile called `local`. You can do this with several ways:

**First, make sure to be in the directory: `<ProjectRoot>/registration-api`**

1) In command line you can set system property or environment variable:
```shell
$ ./gradlew :web:bootRun --args="--spring.profiles.active=local"

or 

$ SPRING_PROFILES_ACTIVE=local ./gradlew :web:bootRun
```

2) In your IDE, set the environment variable `SPRING_PROFILES_ACTIVE=local` to be used when running the application.

Now you can open your browser or another http request tool and start using it.

For more information about the registration-api see the swagger documentation that will be available when you run the project with the following link:

`http://localhost:8080/swagger-ui/index.html`

or simply:

`http://localhost:8080/swagger-ui/`

Note that if you change the application port (default 8080), you must change in the swagger interface url as well.

---