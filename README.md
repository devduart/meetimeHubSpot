# Getting Started

## ✅ Descrição do projeto
API REST desenvolvida em **Java 17** utilizando **Spring Boot** para integração com a API do **HubSpot**, com suporte a:
- Fluxo OAuth 2.0 (authorization code flow)
- Criação de contatos
- Recebimento de webhooks

> **Observação**: Todo o projeto, exemplos e recursos utilizam as principais features do **Java 17**, incluindo `records` para DTOs, o uso de `var` quando aplicável, streams e imutabilidade.

---

## ✅ Estrutura de pastas
```
src/main/java/com/meetime/hubspotintegration
 ├─ config
 ├─ controller
 ├─ dto
 ├─ model
 ├─ service
 └─ HubspotIntegrationApplication.java

src/test/java/com/meetime/hubspotintegration
 ├─ controller
 ├─ service
 └─ integration

Dockerfile
docker-compose.yml
build.sh
Makefile
pom.xml
README.md
```

---

## ✅ Tecnologias utilizadas
- Java 17 (aproveitando `records`, imutabilidade e sintaxe concisa)
- Spring Boot 3
- Spring Security (com OAuth2 Client)
- RestTemplate
- SLF4J para logging
- Optional e Stream API para paradigma funcional
- Design Patterns: Service Layer e Builder (via UriComponentsBuilder)
- Springdoc OpenAPI (Swagger UI)
- JUnit 5, Mockito e Jacoco

---

## ✅ Como gerar os valores de HUBSPOT_CLIENT_ID e HUBSPOT_CLIENT_SECRET
1. Acesse o site do HubSpot Developer: [https://developers.hubspot.com](https://developers.hubspot.com)

2. Faça login com sua conta.

3. No menu superior, clique em **Apps** e selecione **Create App**.

4. Preencha as informações do seu app (nome, descrição, URL do site, etc.).

5. No processo de criação, o HubSpot irá gerar automaticamente:
- **Client ID**: copie este valor e use como `HUBSPOT_CLIENT_ID`.
- **Client Secret**: copie este valor e use como `HUBSPOT_CLIENT_SECRET`.

6. Defina as permissões (scopes) necessárias — neste caso: `contacts` e `oauth`.

7. Salve as alterações e utilize esses valores na configuração local (como variáveis de ambiente ou no `application-local.yml`).

---

## ✅ Como executar
1. Clone o repositório:
```
git clone https://github.com/seu-usuario/seu-repo.git
cd seu-repo
```

2. Configure as variáveis de ambiente necessárias (ou edite o `docker-compose.yml` com seus valores):
```
export HUBSPOT_CLIENT_ID=seu-client-id
export HUBSPOT_CLIENT_SECRET=seu-client-secret
```

3. Construa e rode o projeto via Makefile:
```
make deploy
```
3.1 Construa e rode o projeto via script:
```
chmod +x build.sh
./build.sh
```


4. Acesse a aplicação em:
```
http://localhost:8080
```

5. Acesse a documentação Swagger/OpenAPI em:
```
http://localhost:8080/swagger-ui.html
```

6. Execute os testes com:
```
make test
```

7. Acesse o relatório Jacoco de cobertura em:
```
file://<seu-caminho-local>/target/site/jacoco/index.html
```

---

## ✅ Endpoints
| Endpoint                     | Método | Descrição                                      |
|------------------------------|--------|------------------------------------------------|
| `/hubspot/authorize-url`     | GET    | Gera a URL de autorização OAuth               |
| `/hubspot/callback`          | GET    | Processa o callback do OAuth e obtém o token  |
| `/hubspot/create-contact`    | POST   | Cria contato no HubSpot                       |
| `/hubspot/webhook`           | POST   | Recebe notificações via webhook               |

---

## ✅ Naming Conventions recomendados
- Pacotes e diretórios: sempre em letras minúsculas, separados por ponto.
- Classes: padrão PascalCase (ex.: HubspotService, HubspotController)
- Métodos: camelCase claros e verbosos (ex.: createContact, processWebhook)
- Variáveis: camelCase (ex.: accessToken, contactData)
- Constantes: UPPER_CASE com underscores.

---

## ✅ Diagrama de arquitetura (simplificado)
``` 
[ Client ] 
    |
    v
[ HubspotController ]  ---> chama --->  [ HubspotService ]  ---> consome API REST HubSpot
    |
    v
recebe webhooks <--- HubSpot envia eventos
```

---

## ✅ Possíveis melhorias futuras
- Persistência segura do token de acesso (Redis ou banco)
- Tratamento avançado de rate limit
- Integração com Spring Cloud OpenFeign
- Testes unitários e integração mais robustos
- Pipeline CI/CD integrado

---

## ✅ Referências
- [HubSpot Developer Docs](https://developers.hubspot.com/)
- [Spring Security OAuth2 Docs](https://docs.spring.io/spring-security/site/docs/current/reference/html5/#oauth2-client)
- [Springdoc OpenAPI](https://springdoc.org/)
- [Jacoco Maven Plugin](https://www.jacoco.org/jacoco/trunk/doc/maven.html)
