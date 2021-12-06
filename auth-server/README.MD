# Auth-Server

## Description
[Keycloak](https://www.keycloak.org/) is used for authentication and authorization in the company-cob-ddd apis.

---

## Installation
You can see [here](https://www.keycloak.org/docs/latest/server_installation/index.html) a few ways to install and configure your own Keycloak Server.

To simplify, you can use the docker-compose available in this project, inside `auth-server` directory. Just run:

```shell
$ docker-compose up -d
```

Once the Keycloak is available, access administration page:
`http://localhost:8081` (this is the default port, change when needed).

This keycloak container will use an in memory database (H2), but you can stop and start normally. Your configuration will not be lost.

---

## Configuration
Keycloak works with realms. You can read more about this [here]().

When you access the administration page, the default credentials are:
* user: admin
* password: admin

But this can be changed in docker-compose with environment variables.

When you log in in administration page, is recommended that you create a realm named `companycob`.

This realm will be used by company-cob-ddd apis. The default realm name expected is `companycob` but this can be changed in the apis using the environment variable `AUTH_REALM`.

Then, inside the realm you created, create an `client` named `registration-api`. Again, this is the default value expected by apis, but can be changed with environment variable `AUTH_RESOURCE`.

Last, in the apis is necessary to define the Keycloak URL. By default the expected URL are `http://localhost:8081`, but this can be changed with environment variable `AUTH_URL`