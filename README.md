# Coupon API

API REST desenvolvida com Spring Boot para gerenciamento de cupons de desconto.

A aplicação permite:
- criação de cupons
- busca de cupons por ID
- remoção de cupons
- validações de negócio
- documentação automática com Swagger/OpenAPI
- persistência em banco H2

---

# Tecnologias utilizadas

- Java 21
- Spring Boot 4
- Maven
- Hibernate / JPA
- H2 Database
- Swagger / OpenAPI
- Lombok
- Docker

---

# Funcionalidades

## Criar cupom

Cria um novo cupom com:
- código
- descrição
- valor de desconto
- data de expiração
- status de publicação

### Regras de negócio

- O código do cupom:
    - remove caracteres especiais antes de salvar
    - deve possuir ao menos 6 caracteres alfanuméricos após sanitização

Exemplo:

| Entrada | Resultado |
|---|---|
| `ABC-123` | válido → salvo como `ABC123` |
| `ABC_@-)-_12` | inválido |

---

## Buscar cupom por ID

Busca um cupom persistido pelo ID.

---

## Remover cupom

Remove um cupom do sistema pelo ID.

---

# Estrutura do projeto

```txt
src/main/java/com/test/coupon_api
│
├── config
├── controller
├── dto
│   ├── request
│   └── response
├── exception
│   ├── model
│   └── response
├── model
│   ├── mapper
│   └── enums
├── repository
├── service
├── usecase
└── utils
```

---

# Como executar localmente

## Pré-requisitos

- Java 21+
- Maven 3.9+

---

# Clonar o projeto

```bash
git clone <url-do-repositorio>
```

---

# Entrar na pasta

```bash
cd coupon-api
```

---

# Executar aplicação

```bash
mvn spring-boot:run
```

---

# Aplicação disponível em

```txt
http://localhost:8080
```

---

# Executando com Docker

A aplicação possui um `Dockerfile` e um `compose.yml` na raiz do projeto.

---

# Estrutura esperada

```txt
coupon-api/
│
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── src/
```

---

# Pré-requisitos

- Docker Desktop
- Docker Compose

---

# Subindo a aplicação

Na raiz do projeto execute:

```bash
docker compose up --build
```

O comando:
- cria a imagem da aplicação
- compila o projeto Maven
- gera o JAR
- sobe o container da API

---

# Aplicação disponível em

```txt
http://localhost:8080
```

---

# Parando os containers

```bash
docker compose down
```

---

# Rebuild da imagem

Caso altere dependências ou o Dockerfile:

```bash
docker compose up --build
```

---

# Banco de dados H2

A aplicação utiliza H2 em memória para ambiente local.

---

# Acesso ao console H2

```txt
http://localhost:8080/h2-console
```

---

# Configuração H2

| Campo | Valor |
|---|---|
| JDBC URL | `jdbc:h2:mem:coupondb` |
| User Name | `sa` |
| Password | vazio |

---

# Swagger / OpenAPI

A documentação da API é gerada automaticamente com Swagger/OpenAPI.

---

# Swagger UI

```txt
http://localhost:8080/swagger-ui/index.html
```

---

# OpenAPI JSON

```txt
http://localhost:8080/v3/api-docs
```

---

# Endpoints

## Criar cupom

### Request

```http
POST /coupons
```

### Exemplo de body

```json
{
  "code": "ABC-123",
  "description": "Cupom de desconto de 10%",
  "discountValue": 10.5,
  "expirationDate": "2026-12-31T23:59:59",
  "published": true
}
```

### Response

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "code": "ABC123",
  "description": "Cupom de desconto de 10%",
  "discountValue": 10.5,
  "expirationDate": "2026-12-31T23:59:59",
  "status": "ACTIVE",
  "published": true,
  "redeemed": false
}
```

---

## Buscar cupom por ID

### Request

```http
GET /coupons/{id}
```

### Response

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "code": "ABC123",
  "description": "Cupom de desconto de 10%",
  "discountValue": 10.5,
  "expirationDate": "2026-12-31T23:59:59",
  "status": "ACTIVE",
  "published": true,
  "redeemed": false
}
```

---

## Remover cupom

### Request

```http
DELETE /coupons/{id}
```

### Response

```http
204 No Content
```

---

# Tratamento de erros

A API possui tratamento global de exceções com respostas padronizadas.

---

# Exemplo de erro de validação

```json
{
  "code": 400,
  "message": "Erro de validação",
  "errorsValidation": [
    "O ID do cupom é obrigatório"
  ],
  "status": "400 BAD_REQUEST",
  "timestamp": "2026-05-10T20:55:04.5710405"
}
```

---

# Validações implementadas

| Campo | Regra |
|---|---|
| `code` | obrigatório |
| `code` | mínimo de 6 caracteres alfanuméricos após sanitização |
| `description` | obrigatório |
| `discountValue` | mínimo de 0.5 |
| `expirationDate` | deve ser futura |
| `published` | opcional |

---

# Build do projeto

## Gerar JAR

```bash
mvn clean package
```

---

# Executar JAR

```bash
java -jar target/coupon-api-0.0.1-SNAPSHOT.jar
```

---

# Autor

Projeto desenvolvido para fins de estudo e demonstração de conhecimentos em:
- desenvolvimento backend Java
- arquitetura REST
- validações
- documentação OpenAPI
- Docker
- boas práticas com Spring Boot