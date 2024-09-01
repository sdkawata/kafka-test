echo '
{
  "name" : "load-sales-data",
  "config" : {
    "connector.class" : "io.confluent.connect.jdbc.JdbcSourceConnector",
    "connection.url" : "jdbc:postgresql://postgres:5432/postgres",
    "connection.user" : "postgres",
    "connection.password": "postgres",
    "mode": "incrementing",
    "incrementing.column.name": "id",
    "table.whitelist": "sales",
    "topic.prefix" : "postgres-"
  }
}
' | curl -X POST -d @- http://localhost:8083/connectors -H "content-Type:application/json"