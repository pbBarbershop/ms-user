# MS User


- URL: http://localhost:8081/api/barbershop/user
- [Swagger - OpenAPI](https://github.com/pbBarbershop/ms-user/blob/master/src/main/resources/openapi.yaml)
- MySQL Workbench
- [Dockerfile](https://github.com/pbBarbershop/ms-user/blob/master/Dockerfile)

![Cobertura de Testes](./src/main/resources/static/images/ms-use-cobertura-teste.png)

![Postman](./src/main/resources/static/images/postman-ms-user.png)

## Exemplo de cURL

### POST

`/auth/login`

```cURL
curl --location --request POST 'http://localhost:8081/api/barbershop/auth/login' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--data-raw '{
  "email": "aliquip culpa et dolore",
  "password": "ipsum Ut "
}'
```

### POST

`/auth/login`

```cURL
curl --location --request POST 'http://localhost:8081/api/barbershop/auth/login' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--data-raw '{
  "email": "aliquip culpa et dolore",
  "password": "ipsum Ut "
}'
```

### POST - Autenticar Usuário

`/auth/login`

```cURL
curl --location --request POST 'http://localhost:8081/api/barbershop/auth/login' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--data-raw '{
  "email": "aliquip culpa et dolore",
  "password": "ipsum Ut "
}'
```

### POST - Cadastrar Usuário

`/user`

```cURL
curl --location --request POST 'http://localhost:8081/api/barbershop/user' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--data-raw '{
  "name": "RCIcÃÉÉvurG",
  "password": "aliquip consequat magna sint",
  "phone": "8",
  "profileName": "Manager",
  "id": 81891569,
  "email": "ut ut voluptate",
  "description": "ut qui"
}'
```

### GET - Listar Usuário

`/user?name=culpa proident&page=99257794&size=15104249&sort=["ea aute dolor non","consequat Excepteur"]`

```cURL
curl --location -g --request GET 'http://localhost:8081/api/barbershop/user?name=culpa proident&page=99257794&size=15104249&sort=["ea aute dolor non","consequat Excepteur"]' \
--header 'Accept: application/json'
```

