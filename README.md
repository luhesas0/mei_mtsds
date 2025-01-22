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
**Ferramenta de Desenvolvimento:** Intellij Idea.

**Paradigma de Arquitetura e Design:** Microserviços, APIs RESTful, CRUD e DDD.

**Frameworks e Linguagens:** Spring Boot, Java jdk17, Maven, Lombok.

**Serviço de Descoberta e Comunicação:** Eureka, RabitMQ (assíncrono).

**Pattern:** API Gateway

**Base de Dados:** PostgreSQL, MySQL para persistência de dados.

**Monitorização e Logs:** Spring Boot Actuator.

**Deployment:** Minikube (K8S)

## Rotas e Serviços Subjacentes
| **Microserviço**        | **Rota(api-gateway)localhost:8080**        | **Serviços Subjacentes**    |
|-------------------------|--------------------------------------------|-----------------------------|
| Gestão de Utilizadores  | [utilizadores-route] Path=/utilizadores/** | localhost:8071/utilizadores |
| Criação de Menus        | [menu-route] Path=/menu/**                 | localhost:8072/menu         |
| Verificação de Stock    | [stock-route] Path=/stock/**               | localhost:8073/stock        |
| Repositório de Entregas | [repositorio-route] Path=/repositorio/**   | localhost:8074/repositorio  |
| Cálculo de Rotas        | [rotas-route] Path=/rotas/**               | localhost:8075/rotas        |
| Serviço de Notificações | [notificacao] Path=/notificacoes/**        | localhost:8076/notificacoes |

## Base de Dados
Para aceder às bases de dados associadas aos microserviços, siga os passos em baixo:
1. Abrir um navegador e aceder ao localhost;
2. Escolher o serviço correspondente à base de dados que pretende utilizar.
3. Configurar o serviço como POSTGRESQL ou MYSQL, dependendo do microserviço.

| Serviço BD | Microserviço        | Base de Dados       | Username            | Password |
|------------|---------------------|---------------------|---------------------|----------|
| PostgreSQL | gestao_utilizadores | gestao_utilizadores | gestao_utilizadores | 123      | 
| PostgreSQL | criacao_menu        | XXXX                |  YYYY               | 111      |   
| PostgreSQL | verifica_stock      | XXXX                |  YYYY               | 111      |   
| PostgreSQL | repo_entregas       | XXXX                |  YYYY               | 111      |   
| PostgreSQL | calculo_rota        | XXXX                |  YYYY               | 111      |   
| PostgreSQL | Notificacoes        | XXXX                |  YYYY               | 111      | 


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