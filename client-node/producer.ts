import { Kafka, Partitioners } from 'kafkajs';

const kafka = new Kafka({
  clientId: 'my-app',
  brokers: ["localhost:9092", "localhost:9093", "localhost:9094"]
})

const producer = kafka.producer({
  createPartitioner: Partitioners.DefaultPartitioner
})
const admin = kafka.admin()

const run = async () => {
  try {
    const result = await admin.fetchTopicMetadata({
      topics: ['test-topic']
    })
    console.log(JSON.stringify(result))
  } catch (e) {
    console.error(e)
  }
  await producer.connect()
  console.log('connected')
  const sendResult = await producer.send({
    topic: 'test-topic',
    messages: Array.from({length: 5}).map((_, i) => ({
      value: 'message:' + i,
    })),
  })
  console.log('sent', sendResult)
  await producer.disconnect()
  console.log('disconnected')
}

run()