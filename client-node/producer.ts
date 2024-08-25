import { Kafka, Partitioners } from 'kafkajs';

const kafka = new Kafka({
  clientId: 'my-app',
  brokers: ["localhost:9092", "localhost:9093", "localhost:9094"]
})

const producer = kafka.producer({
  allowAutoTopicCreation: false,
  createPartitioner: Partitioners.DefaultPartitioner,
})

const admin = kafka.admin()

const run = async () => {
  await admin.connect()
  const topiceResult = await admin.fetchTopicMetadata({ topics: ['test_topic'] })
  console.log('topic', JSON.stringify(topiceResult))
  await producer.connect()
  console.log('connected')
  const sendResult = await producer.send({
    topic: 'test_topic',
    messages: Array.from({length: 10}).map((_, i) => ({
      value: 'message_from_js_' + i,
    })),
  })
  console.log('sent', sendResult)
  await producer.disconnect()
  console.log('disconnected')
}

run()