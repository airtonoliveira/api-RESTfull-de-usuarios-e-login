# api-RESTful-de-usuarios-com-login
H2/Maven/Hibernate/Spring/Heroku/Java 8/JWT/JUnit

Aplicação RESTful de cadastro de usuários para login e geração de tokens.
- Senhas criptografadas
- Testes Unitários

## API online (Heroku): 
https://airton-restfull-jwt.herokuapp.com/ 

Tokens expiram em 24 horas.

## Rotas:
https://documenter.getpostman.com/view/3256997/S1M3uk9U

- /signup
- /signin
- /me


## /signup
- https://airton-restfull-jwt.herokuapp.com/signup

Requisição via JSON:
```json
   {
        "firstName": "Airton",
        "lastName": "Oliveira",
        "email": "airton@oliveira.com",
        "password": "senha123",
        "phones": [
            {
                "number": 978782002,
                "area_code": 81,
                "country_code": "+55"
            }
        ]
    }
```
    
Resultado (exemplo):
```json
    {
        "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhaXJ0b25Ab2xpdmVpcmEuY29tIiwiZXhwIjoxNTU4MjMwODU4fQ.u8QPl0lu-XT8cdqdtZL9VbXvzA-yNauWA1eVFENIGIFzqzy_-kyX9cY8dhI2EC49uAZvAZ-9kC-UR_LwzLhR7w"
    }
```

## /signin
- https://airton-restfull-jwt.herokuapp.com/signin

Requisição via JSON:

```json
{
    "email": "airton@oliveira.com",
    "password": "senha123"
}
```
  
Resultado (exemplo):
```json
    {
        "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhaXJ0b25Ab2xpdmVpcmEuY29tIiwiZXhwIjoxNTU4MjMwODU4fQ.u8QPl0lu-XT8cdqdtZL9VbXvzA-yNauWA1eVFENIGIFzqzy_-kyX9cY8dhI2EC49uAZvAZ-9kC-UR_LwzLhR7w"
    }
```

## /me
- https://airton-restfull-jwt.herokuapp.com/me

Requisição com o token via header.

Authorization:
eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhaXJ0b25Ab2xpdmVpcmEuY29tIiwiZXhwIjoxNTU4MjMwODU4fQ.u8QPl0lu-XT8cdqdtZL9VbXvzA-yNauWA1eVFENIGIFzqzy_-kyX9cY8dhI2EC49uAZvAZ-9kC-UR_LwzLhR7w

Resultado (exemplo):

```json
{
    "firstName": "Airton",
    "lastName": "Oliveira",
    "email": "airton@oliveira.com",
    "phones": [
        {
            "id_telefone": 2,
            "number": 978782002,
            "area_code": 81,
            "country_code": "+55"
        }
    ],
    "created_at": "19/05/2019 01:04",
    "last_login": "19/05/2019 01:49"
}
```

