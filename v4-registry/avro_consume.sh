#! /bin/sh

docker compose exec schema-registry \
kafka-avro-console-consumer --bootstrap-server broker01:29092 --topic postgres-sales --from-beginning \
--property schema.registry.url=http://schema-registry:8081