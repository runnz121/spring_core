package kafka.main;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

//@Component
public class SimpleProducerSync {

    public static final Logger logger = LoggerFactory.getLogger(SimpleProducerSync.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(KukuKafkaProducerApplication.class, args);

        // kakfa-ui 에서 토픽 생성시 해당 이름이랑 맞춰야됨
        String topicName = "simple-topic";

        // kafka config
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:10000");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // kafka producer 객체 생성
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);
        // producer record 객체 생성
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topicName, "id-001", "hello");

        //kafka producer message send
        try {

            // get() 을 통해 동기방식으로 데이터를 보내고 받는다
            RecordMetadata recordMetadata = kafkaProducer.send(producerRecord).get();

            logger.info("record meta data \n");
            logger.info("partition : " + recordMetadata.partition() + "\n");
            logger.info("offset : " + recordMetadata.offset() + "\n");
            logger.info("timeStamp : " + recordMetadata.timestamp());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            kafkaProducer.close();
        }

        kafkaProducer.flush();
        kafkaProducer.close();
    }
}
