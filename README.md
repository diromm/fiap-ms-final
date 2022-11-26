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

## Running

1. Executando o Container Postgress
* Go to Postgres-docker folder and run docker-compose
```shell script
cd postgres-docker
docker-compose up -d
cd ..
```

* Para comodidade o ADMINER está exposto no seguinte endereço: http://localhost:8080 Com as seguintes credenciais. [docker-compose.yml](./postgres-docker/docker-compose.yml) 
* Selecione a DataBase **postgres** e o Esquema público. **public**
* Create option 'SQL Command' and execute the DDL:

```
CREATE SEQUENCE drones_id_seq;

CREATE TABLE tb_drones (
    id_drone integer NOT NULL DEFAULT nextval('drones_id_seq') ,
    nome_drone character varying(255) NOT NULL, 
    lat_drone double precision , 
    lng_drone double precision ,
    temperatura double precision , 
    umidade double precision ,
    rastreando BOOLEAN NOT NULL ,
	CONSTRAINT "tb_drones_pkey" PRIMARY KEY (id_drone)
);


ALTER SEQUENCE drones_id_seq
OWNED BY tb_drones.id_drone;

```
2. Executando a stack de monitoração.
* vá para [monitoracao](./monitoracao) e execute o docker-compose
```shell script
cd monitoracao
docker-compose up -d
cd ..
```
The prometheus can be accessed at http://localhost:9090 and grafana accessed at http://localhost:3000 with "admin" and "password" as login/pswd credentials

For the first login in grafana, import [Infinispan-Camel](./monitoracao/infinispan-grafana.json) dashboard and [Posgres-kafka-connector](./monitoracao/posgres-connector.json) dashboard.

4a. Running the source-sink camel quarkus with Debezium Embbeded
* Go to quarkus-kml-postgres2infinispan folder and execute the application
```shell script
cd quarkus-kml-postgres2infinispan
./run.sh
```
* Any additional documentation of this project is [here](./quarkus-kml-postgres2infinispan/README.md)

4b. Running a Kafka + Camel-Quarkus version
* Go to [kafka-docker](./kafka-docker/) folder abd run docker-compose
```shell script
cd kafka-docker
docker-compose up -d
cd ..
```
* Run the Kafka-Connect setup to create an connector to postgres with configuration in [connector.json](./kafka-docker/connector.json):
```shell script
cd kafka-docker
./setconnector.sh
cd ..
```

* Go to [quarkus-kml-kafka2infinispan](./quarkus-kml-kafka2infinispan/) folder and execute the application
```shell script
cd quarkus-kml-kafka2infinispan
./run.sh
```
* Any additional documentation of this project is [here](./quarkus-kml-kafka2infinispan/README.md)

## Working



## Backlog
### In this project
1. Enhance metrics labeling in prometheus and adjust grafana dashboard
2. Automate setting-up project

### Complement of project
1. Another component to listen to Infinispan events of the 'cliente' cache and calculaing how long it takes to go from table to cache using this approach.
2. Implement kubernetes version of running
