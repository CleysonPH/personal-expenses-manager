# Personal Expenses Manager

Projeto com objetivo de exemplificar o desenvolvimento de uma API REST com Spring Boot utilizando TDD, Git Flow, CI e CD.

## Dependências de Produção

- Spring Boot
- Spring Web MVC
- Spring Data JPA
- Bean Validation
- Springfox Swagger
- PostgreSQL Driver

## Dependências de Desenvolvimento

- Spring Boot Devtools
- Lombok
- H2 Driver

## Requisitos do Projeto

- JDK 17
- Maven 3.8.4

## Como Executar o Projeto

Clone o repositório e entre na pasta do projeto

```sh
git clone https://github.com/treinaweb/multistack-ediaristas-java.git
cd personal-expenses-manager
```

Execute o projeto através do Maven

```sh
mvn spring-boot:run
```

Com a aplicação rodando você pode acessar a documentação do projeto a partir do endereço [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).

As rotas da API podem ser acessadas a partir de [http://localhost:8080/api/v1](http://localhost:8080/api/v1). Veja a documentação para saber quais os endpoints disponíveis