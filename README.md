# mei_mtsds
Trabalho prático da cadeira: Métodos e Técnicas de Suporte ao Desenvolvimento de Software

## Resumo

Este repositório documenta o desenvolvimento da plataforma **FoodStream**, apresentada no contexto académico da unidade curricular de **Métodos e Técnicas de Suporte ao Desenvolvimento de Software**. Este projeto é desenvolvido pelo Grupo 7 - 2024/2025.

## Conteúdo
-[Resumo](#resumo)
-[Introdução](#introdução)
-[Microserviços](#microserviços)
-[Ferramentas e Tecnologias Utilizadas](#ferramentas-e-tecnologias-utilizadas)
-[Roteamento dos requests](#roteamento-dos-requests)
-[Base de Dados](#base-de-dados)



## Introdução



## Microserviços



## Ferramentas e Tecnologias Utilizadas
**Frameworks e Linguagens:** Spring Boot, Java jdk17.

**Comunicação:** REST API, RabitMQ (assíncrono).

**Base de Dados:** PostgreSQL, MySQL para persistência de dados.

**Gestão de Configuração:** Spring Cloud Config.

**Monitorização e Logs:** Spring Boot Actuator.

**Deployment:** Minikube (K8S)

## Roteamento dos requests
| **Rota(api-gateway)localhost:8070**        | **Serviços Subjacentes**    |
|--------------------------------------------|-----------------------------|
| [utilizadores-route] Path=/utilizadores/** | localhost:8071/utilizadores |
| [menu-route] Path=/menu/**                 | localhost:8072/menu         |
| [stock-route] Path=/stock/**               | localhost:8073/stock        |
| [repositorio-route] Path=/repositorio/**   | localhost:8074/repositorio  |
| [rotas-route] Path=/rotas/**               | localhost:8075/rotas        |


## Base de Dados
Para aceder às bases de dados associadas aos microserviços, siga os passos em baixo:
1. Abrir um navegador e aceder ao localhost;
2. Escolher o serviço correspondente à base de dados que pretende utilizar.
3. Configurar o serviço como POSTGRESQL ou MYSQL, dependendo do microserviço.

| Serviço BD | Microserviço   | Base de Dados       | Username     | Password   |
|------------|----------------|---------------------|--------------|------------|
| PostgreSQL | /utilizadores  | gestao_utilizadores | utilizadores | utilizador |


## Como Executar o Projeto


## Pré-Requisitos
- Java: jdk17
- Maven
- Docker e Kubernetes (Minikube)

### Passos


## Configurações Especiais
1. Spring Cloud Config
2. RabbitMQ
3. Criação de Imagens Docker:




## Comandos Git Mais Comuns


## Teste e Deploy

## Autores

- Luís Henrique dos Santos Soares, nº 8210396
- Célia Cristina Pereira de Paiva, nº 8220954
- José Filipe Ferreira Soares, nº 8240047
- André Filipe Nunes de Matos, nº 8240634

Mestrado de Engenharia Informática