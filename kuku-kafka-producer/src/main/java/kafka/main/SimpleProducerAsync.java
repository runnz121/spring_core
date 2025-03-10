package kafka.main;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

import java.util.Properties;

//@Component
public class SimpleProducerAsync {

    public static final Logger logger = LoggerFactory.getLogger(SimpleProducerAsync.class.getName());

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
        // main thread 가 아닌 다른 thread가 호출해서 비동기적으로 동작함
        kafkaProducer.send(producerRecord, (recordMetadata, exception) -> {

            if (exception == null) {

                // 메세지가 와야 출력이 된다 -> 비동기라 촐력이 안될 수 있다.
                logger.info("record meta data \n");
                logger.info("partition : " + recordMetadata.partition() + "\n");
                logger.info("offset : " + recordMetadata.offset() + "\n");
                logger.info("timeStamp : " + recordMetadata.timestamp());

            } else {
                logger.error("exception error : " + exception.getMessage());
            }
        });

        // 메세지 출력을 위해 thread sleep 적용
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        kafkaProducer.flush();
        kafkaProducer.close();
    }
}
