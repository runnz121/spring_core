package kafka.main;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

//@Component
public class SimpleProducer {

//    @Bean
    public void setProperties() {
//
//        String topicName = "simple-topic";
//
//        // kafka config
//        Properties properties = new Properties();
//        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:10000");
//        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//
//        // kafka producer 객체 생성
//        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);
//        // producer record 객체 생성
//        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topicName, "id-001", "hello");
//        //kafka producer message send
//        kafkaProducer.send(producerRecord);

    }
}
