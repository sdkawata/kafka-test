echo '
{
  "name" : "sink-sales-data",
  "config" : {
    "connector.class" : "io.confluent.connect.s3.S3SinkConnector",
    "store.url": "http://localstack:4566",
    "s3.bucket.name": "kafka-out",
    "s3.region": "ap-northeast-1",
    "storage.class": "io.confluent.connect.s3.storage.S3Storage",
    "format.class": "io.confluent.connect.s3.format.json.JsonFormat",
    "flush.size": "3",
    "topics": "postgres-sales",
    "tasks.max": "1",
    "aws.access.key.id": "dummy",
    "aws.secret.access.key": "dummy"
  }
}
' | curl -X POST -d @- http://localhost:8083/connectors -H "content-Type:application/json"