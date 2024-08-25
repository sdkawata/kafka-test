import { Kafka } from 'kafkajs';

const kafka = new Kafka({
  clientId: 'my-app',
  brokers: ["localhost:9092", "localhost:9093", "localhost:9094"]
})

const randStr = () => {
  const S="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
  const N=16
  return Array.from(crypto.getRandomValues(new Uint8Array(N))).map((n)=>S[n%S.length]).join('')
}

const consumer = kafka.consumer({ groupId: 'test-group-' + randStr() })

const run = async () => {
  await consumer.connect()
  await consumer.subscribe({ topic: 'test_topic', fromBeginning: true})
  await consumer.run({
    eachMessage: async ({topic, partition, message}) => {
      console.log({partition, value: message.value.toString(), offset: message.offset})
    }
  })
}

run()