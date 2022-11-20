# google MAPS API KEy

AIzaSyBTC7qQKvH_S2a_BBHcQGuaWsm34XsEv_Q

# FIAP - Trabalho de conclusão - 1SCJRBB -  Integration e Development Tools.

## Trabalho implementando um microsserviços com uso do apache Camel, linguagem Java Quarkus, banco de dados Postgress

Esse projeto é uma prova de conceito de um microsserviço usando o framework Apache-Camel em um proejto quarkus para abastecer uma Captura de mudança de dados - Change Data Capture (CDC) utilizando a engine do Debezium e disponibilizando em um cache Clusterizado de Infinispan.

## Documentação das tecnologias

1. [Infinispan](https://infinispan.org/introduction/)

2. [Debezium-PostgresSql](https://debezium.io/documentation/reference/stable/connectors/postgresql.html) 

Existem duas versões do processo fonte-cache:
- Usando Camel-Quarkus com o plug-in do Debezium embutido (O Camel roteia diretamente do Postgress para o cache do Infinispan)
- Usando Kafka + Kafka-Connect com o debezium plugin + Camel-quarkus roteando do kafka para o Infinispan.


## Components

1. [Postgres container](./postgres-docker/docker-compose.yml) configured to use [logical decoding](https://www.postgresql.org/docs/current/logicaldecoding-explanation.html), following [Debezium Postgres Connector recomendation](https://debezium.io/documentation/reference/stable/connectors/postgresql.html) with a adminer for UI with the databases.

2. [Infinispan container](./infinispan-docker/docker-compose.yaml) (in a cluster of 3).

3. [Monitoring stack](./monitoracao/docker-compose.yml) with prometheus and grafana containers

4.  Implementação Fonte-Cache 
  a. [Quarkus component using Camel with Debezium e Infinispan extensions.](./quarkus-kml-postgres2infinispan/)
  b. [Kafka + Zookeeper + KafkaConnect + KafkaUI containers](./kafka-docker/docker-compose.yml) and [Quarkus-Camel component routing from kafka to Infinispan.](./quarkus-kml-kafka2infinispan/)

## Running
1. Setting-up Infinispan container
* Go to Infinispan-docker folder and run docker-compose
```shell script
cd infinispan-docker
docker-compose up -d
cd ..
```
The infinspan admin console can be accessed at http://localhost:11222 with the credentials in [identities.batch](./infinispan-docker/user-config/identities.batch)

* Create a cache named 'cliente' with default configuration:
```
{
  "distributed-cache": {
    "mode": "SYNC",
    "encoding": {
      "media-type": "application/x-protostream"
    },
    "statistics": true
  }
}
```

2. Setting-up Postgres
* Go to Postgres-docker folder and run docker-compose
```shell script
cd postgres-docker
docker-compose up -d
cd ..
```

* Acess adminer UI at http://localhost:8080 with the credentials in the [docker-compose.yml](./postgres-docker/docker-compose.yml) 
* Select DB **postgres** and schema **public**
* Create option 'SQL Command' and execute the DDL:

```
DROP TABLE IF EXISTS "ClienteCDC";
DROP SEQUENCE IF EXISTS "TabelaCDC_cd_cli_seq";
CREATE SEQUENCE "TabelaCDC_cd_cli_seq" INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 6 CACHE 1;

CREATE TABLE "public"."ClienteCDC" (
    "cd_cli" integer DEFAULT nextval('"TabelaCDC_cd_cli_seq"') NOT NULL,
    "nm_cli" character varying(60) NOT NULL,
    "cd_cpf" numeric(11,0) NOT NULL,
    "ts_atl" timestamp DEFAULT now() NOT NULL,
    CONSTRAINT "TabelaCDC_pkey" PRIMARY KEY ("cd_cli")
) WITH (oids = false);


DROP TABLE IF EXISTS "SalarioCDC";
CREATE TABLE "public"."SalarioCDC" (
    "cd_cli" integer NOT NULL,
    "AnoMes" integer NOT NULL,
    "renda" numeric(15,2) NOT NULL,
    "ts_atl" timestamp DEFAULT now() NOT NULL,
    CONSTRAINT "SalarioCDC_cd_cli_AnoMes" PRIMARY KEY ("cd_cli", "AnoMes")
) WITH (oids = false);


ALTER TABLE ONLY "public"."SalarioCDC" ADD CONSTRAINT "SalarioCDC_cd_cli_fkey" FOREIGN KEY (cd_cli) REFERENCES "ClienteCDC"(cd_cli) ON UPDATE CASCADE ON DELETE CASCADE NOT DEFERRABLE;
```
3. Running monitoring stack
* Go to [monitoracao](./monitoracao) folder and run docker-compose
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
Once the application is up, you can use the Adminer UI (localhost:8080) to add, update and delete rows and see how it appears in the infinispan web console (localhost:11222).

Using the option with kafka, you can also acess KafkaUI in (localhost:8180) to see and manage topics.

### Generating events in Postres Database

Insert command in Postgres Database to observe cache updates:
```
WITH RANDOMCODE AS (
   SELECT DISTINCT       floor(random() * 9999 + 1) AS cd_cli
   FROM generate_series(1, < NUMBER OF ROWS TO INSERT >)
)
INSERT INTO "ClienteCDC" (cd_cli, nm_cli, cd_cpf)  (
    SELECT
       RANDOMCODE.cd_cli,
       'Cliente '  || md5(random()::text),
       floor(random() * 99999999999 + 1) 
    FROM RANDOMCODE
) ON CONFLICT (cd_cli) DO 
    UPDATE SET nm_cli = 'Cliente '|| "ClienteCDC".cd_cli || ' Atualizado',
               ts_atl = now();
```

and 

```
WITH cliente AS (
SELECT cd_cli FROM "ClienteCDC" WHERE random() < 0.25 limit 1000
)
INSERT INTO "SalarioCDC" ("cd_cli", "AnoMes", "renda") (
    SELECT 
       cliente.cd_cli,
       (2022 - floor(random() * 40))*100 +  floor(random() * 12 ),
       round((random()::decimal * 99999 + 1) , 2) 
    FROM generate_series(1, < NUMBER OF ROWS TO INSERT >), cliente

) ON CONFLICT ON CONSTRAINT "SalarioCDC_cd_cli_AnoMes" DO NOTHING
```

## Backlog
### In this project
1. Enhance metrics labeling in prometheus and adjust grafana dashboard
2. Automate setting-up project

### Complement of project
1. Another component to listen to Infinispan events of the 'cliente' cache and calculaing how long it takes to go from table to cache using this approach.
2. Implement kubernetes version of running