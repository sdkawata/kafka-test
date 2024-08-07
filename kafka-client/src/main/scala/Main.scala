import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.apache.kafka.clients.producer.Callback
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.kafka.common.serialization.StringDeserializer
import scala.collection.immutable.List
import collection.JavaConverters._
import java.util.Properties
import java.util.Collection
import java.util.regex.Pattern
import java.util.UUID


object Main {
  implicit def function2Callback(f: (RecordMetadata, Exception) => Unit): Callback = new Callback {
    def onCompletion(metadata: RecordMetadata, exception: Exception): Unit = f(metadata, exception)
  }
  def produce() = {
    val properties = new Properties()
    properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer].getName())
    properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer].getName())

    val producer = new KafkaProducer[String, String](properties)
    for (i <- 1 to 100) {
      val producerRecord = new ProducerRecord("test_topic", "key" + i, "value" + i)
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
  def consume() = {
    val properties = new Properties()
    properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getName())
    properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getName())
    properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString())
    properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    val consumer = new KafkaConsumer(properties)
    val topics = List("test_topic").asJava
    consumer.subscribe(topics)
    println("start subscribing...")
    var count = 0
    while(true) {
      val records = consumer.poll(100)
      for (record <- records.asScala) {
        count+=1
        println("topic:" + record.topic() + " offset:" + record.offset() + " partition:" + record.partition() + " key:" + record.key() + " value:" + record.value() + " received count:" + count)
      }
    }
  }
  def main(args: Array[String]): Unit = {
    produce()
    consume()
  }
}