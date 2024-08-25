echo '
{
  "name" : "load-zaiko-data",
  "config" : {
    "connector.class" : "org.apache.kafka.connect.file.FileStreamSourceConnector",
    "file" : "/etc/zaiko.txt",
    "topic" : "zaiko-data"
  }
}
' | curl -X POST -d @- http://localhost:8083/connectors -H "content-Type:application/json"