# FIAP - Trabalho de conclusão - 1SCJRBB -  Integration e Development Tools.

## Trabalho para integração de microsservices

Esse é o projeto de conclusão da disciplina 1SCJRBB -  Integration e Development Tools. Sua função é a integrar um microsserviço de gerenciamento de Drones com um microsserviço que gera alertas a depender do determinados parâmetros.

## Pré requisitos

* Sistema operacional compatível com Docker. 
	* Todo projeto foi construindo no Ubuntu, pode apresentar problemas de conectividade em alguns serviços caso usado em WSL2.

* Docker-compose para startar os serviços.

* Recomendado uso de um client para execução de queries, por exemplo [DBeaver](https://dbeaver.io/download/).
	* para comodidade na construção do Postgress foi disponibilizado um client do ADMINER que execute em Localhost.

## Documentação das tecnologias


1. [Debezium-PostgresSql](https://debezium.io/documentation/reference/stable/connectors/postgresql.html) 
	* em resumo o Debezium é um conector kafka Open Source utilizado para Change Data Capture (CDC) - Captura de mudança de dados.
	
2. [Quarkus](https://quarkus.io/about/) + [Camel](https://camel.apache.org/manual/faq/what-is-camel.html)
	* Quarkus é um framework para java Cloud Native desenvolvido para aplicações java que vão executar na núvem e compatível com a arquitetura Servless. O Apache Camel é um [ENTERPRISE INTEGRATION PATTERNS](https://camel.apache.org/components/3.18.x/eips/enterprise-integration-patterns.html)

## Components

1. [Postgres container](./postgres-docker/docker-compose.yml) Configurado no modo de uso [logical decoding](https://www.postgresql.org/docs/current/logicaldecoding-explanation.html), Seguindo a seguinte documentação do Debezium: [Debezium Postgres Connector recomendation](https://debezium.io/documentation/reference/stable/connectors/postgresql.html).


4.  Implementação 
  a. [Kafka + Zookeeper + KafkaConnect + KafkaUI containers](./kafka-docker/docker-compose.yml)

## Executando

### 1. Executando o Container Postgress
* Go to Postgres-docker folder and run docker-compose
```shell script
cd postgres-docker
docker-compose up -d
cd ..
```

* Para comodidade o ADMINER está exposto no seguinte endereço: http://localhost:8080 Com as seguintes credenciais. [docker-compose.yml](./postgres-docker/docker-compose.yml) 
* Selecione a DataBase **postgres** e o Esquema público. **public**
* Na Opção importar, importe o arquivo [create_tables.sql](postgres-docker\sql\create_tables.sql)

![Importar Comandos](Imagens\importarComandosSQl.png)

### 2. Executando Kafka + Debezium
* Navegue até a pasta [kafka-docker](./kafka-docker/) e execute o docker-compose up
```shell script
cd kafka-docker
docker-compose up -d
cd ..
```
* Execute Kafka-Connect setup para criar um conector do postgres com as configurações do [connector.json](./kafka-docker/connector.json).
  * ATENÇÃO!! O IP contido em     "database.hostname": "localhost",   deve ser substituído pelo ip local da sua máquina

```shell script
cd kafka-docker
./setconnector.sh
cd ..
```

Após a execução do script é possível na interface gráfica do kafka verificar e gerenciar os tópicos.

[Tópicos kafka na UI](http://localhost:8180/ui/clusters/local/topics)

![Kakfa-ui](.\Imagens\Kakfa-ui.png)

Após isso o kafka estará totalmente configurado e produzindo mensagens com os dados inseridos nas tabelas. 

* Go to [quarkus-kml-kafka2infinispan](./quarkus-kml-kafka2infinispan/) folder and execute the application
```shell script
cd quarkus-kml-kafka2infinispan
./run.sh
```

## Executando a aplicação web e subscritor:



## Backlog
### In this project
1. Enhance metrics labeling in prometheus and adjust grafana dashboard
2. Automate setting-up project

### Complement of project
1. Another component to listen to Infinispan events of the 'cliente' cache and calculaing how long it takes to go from table to cache using this approach.
2. Implement kubernetes version of running
