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
$ gradle build
```

#### - Run Registration API
In directory `<ProjectRoot>/registration-api` run:
```shell
$ gradle bootRun
```

Now you can open your browser or another http request tool and start using it.

For more information about the registration-api see the swagger documentation that will be available when you run the project with the following link:

`http://localhost:8080/swagger-ui/index.html`

or simply:

`http://localhost:8080/swagger-ui/`

Note that if you change the application port (default 8080), you must change in the swagger interface url as well.

---