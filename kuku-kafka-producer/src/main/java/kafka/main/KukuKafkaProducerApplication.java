package kafka.main;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication
public class KukuKafkaProducerApplication {

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
		kafkaProducer.send(producerRecord);

		kafkaProducer.flush();
		kafkaProducer.close();
	}

}
