# How to run these docker compose files

## Run for the zookeeper

```bash
docker-compose -f common.yml -f zookeeper.yml up -d
```
Run the follow command to check the zookeeper status:

```bash
echo ruok | nc localhost 2181
```

It should return `imok`

## Run for the kafka cluster

```bash
docker-compose -f common.yml -f kafka_cluster.yml up -d
```

## Run this only time to create the topics

```bash
docker-compose -f common.yml -f init_kafka.yml up
```