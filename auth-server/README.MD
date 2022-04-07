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
Keycloak works with realms. You can read more about this [here](https://www.keycloak.org/docs/latest/server_admin/).

When you access the administration page, the default credentials are:
* user: admin
* password: admin

But this can be changed in docker-compose with environment variables.

When you log in there will be a realm named `companycob`. Make sure to select it. This realm will be used by company-cob-ddd apis.

Inside the realm there will be already a `client` named `registration-api`.
Check if exists an user named `registration-api-user` as well. The default password for this user is `password`. If you change the password in keycloak, don't forget to change in postman's token request too.

Now make a postman call and if everything goes right, you will receive a token.

---

## Consume

In the consumer apis is necessary to define the Keycloak URL, the resource and the realm. See the configuration necessary with default values and environment variabled recommended to change it in each application:

| Attribute | Default | Recommended environemnt variable |
|---|---|---|
| Keycloak url | http://localhost:8081 | AUTH_URL |
| Resource | registration-api | AUTH_RESOURCE |
| Realm | companycob | AUTH_REALM |
| User | registration-api-user |  |



## Help with import/export realms

This [link](https://keepgrowing.in/tools/keycloak-in-docker-5-how-to-export-a-realm-with-users-and-secrets/) is very usefull for export a realm with it's users.

### Troubleshoot

#### Permission Denied
If you create a volume, try export your realm to this volume directory and receive a permission denied error, try recreate the container and change the container user to root.