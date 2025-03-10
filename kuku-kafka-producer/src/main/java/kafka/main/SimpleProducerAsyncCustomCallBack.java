package kafka.main;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

import java.util.Properties;

//@Component
public class SimpleProducerAsyncCustomCallBack {

    public static final Logger logger = LoggerFactory.getLogger(SimpleProducerAsyncCustomCallBack.class.getName());

    // SEQ가 어떤 파티션으로 보내졌는지 확인하기 위한 커스텀 콜백 생성
    static class CustomCallback implements Callback {

        public static final Logger logger2 = LoggerFactory.getLogger(CustomCallback.class.getName());

        private int seq;

        public CustomCallback(int seq) {
            this.seq = seq;
        }

        @Override
        public void onCompletion(RecordMetadata recordMetadata,
                                 Exception exception) {

            if (exception == null) {
                logger2.info("seq : {} , partition : {}, offset : {}", seq, recordMetadata.partition(), recordMetadata.offset());
            } else {
                logger2.error("exception error : " + exception.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(KukuKafkaProducerApplication.class, args);

        // kakfa-ui 에서 토픽 생성시 해당 이름이랑 맞춰야됨
        String topicName = "multipart-topic";

        // kafka config
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:10000");

        // key 값을 Integer 로 설정시 Serializer도 해당 타입으로 설정
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // kafka producer 객체 생성
        KafkaProducer<Integer, String> kafkaProducer = new KafkaProducer<>(properties);

        // 여러개 Seq 생성
        for (int seq = 0; seq < 20; seq++) {

            // producer record 객체 생성 -> key가 integer 이면 파티션에 균등하게 간다
            ProducerRecord<Integer, String> producerRecord = new ProducerRecord<>(topicName, seq, "hello : " + seq);

            // 커스텀 콜백 설정 -> 커스텀 콜백 구현체로 설정
            Callback customCallback = new CustomCallback(seq);

            //kafka producer message send
            // main thread 가 아닌 다른 thread가 호출해서 비동기적으로 동작함
            kafkaProducer.send(producerRecord, customCallback);
        }

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
