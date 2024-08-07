import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.apache.kafka.clients.producer.Callback
import org.apache.kafka.common.serialization.StringSerializer
import java.util.Properties


object Main {
  implicit def function2Callback(f: (RecordMetadata, Exception) => Unit): Callback = new Callback {
    def onCompletion(metadata: RecordMetadata, exception: Exception): Unit = f(metadata, exception)
  }
  def main(args: Array[String]): Unit = {
    println("Hello, World!")
    val properties = new Properties()
    properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer].getName())
    properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer].getName())

    val producer = new KafkaProducer[String, String](properties)
    for (i <- 1 to 100) {
      val producerRecord = new ProducerRecord[String, String]("test_topic", "key" + i, "value" + i)
      producer.send(producerRecord, (metadata:RecordMetadata, exception: Exception) => {
        if (exception != null) {
          exception.printStackTrace()
        } else {
          println("topic:" + metadata.topic() + " offset:" + metadata.offset() + " partition:" + metadata.partition())
        }
      })
    }
    producer.flush()
    producer.close()
  }
}