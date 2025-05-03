package kafka.main;

import com.github.javafaker.Faker;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class PizzaProducer {
    public static final Logger logger = LoggerFactory.getLogger(PizzaProducer.class.getName());

    // 메시지 보낼 메서드
    public static void sendPizzaMessage(KafkaProducer<String, String> kafkaProducer,
                                        String topicName, int iterCount,
                                        int interIntervalMillis, int intervalMillis,
                                        int intervalCount, boolean sync) {

        PizzaMessage pizzaMessage = new PizzaMessage();
        int iterSeq = 0;
        long seed = 2022;
        Random random = new Random(seed);
        Faker faker = Faker.instance(random);

        long startTime = System.currentTimeMillis();

        while( iterSeq++ != iterCount ) {

            HashMap<String, String> pMessage = pizzaMessage.produce_msg(faker, random, iterSeq);

            // record 생성
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topicName, pMessage.get("key"), pMessage.get("message"));

            // 메세지 송출
            sendMessage(kafkaProducer, producerRecord, pMessage, sync);

            if ((intervalCount > 0) && (iterSeq % intervalCount == 0)) {
                try {
                    logger.info("####### IntervalCount:" + intervalCount + " intervalMillis:" + intervalMillis + " #########");
                    Thread.sleep(intervalMillis);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                }
            }

            if (interIntervalMillis > 0) {
                try {
                    logger.info("interIntervalMillis:" + interIntervalMillis);
                    Thread.sleep(interIntervalMillis);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                }
            }

        }
        long endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;

        logger.info("{} millisecond elapsed for {} iterations", timeElapsed, iterCount);

    }

    public static void sendMessage(KafkaProducer<String, String> kafkaProducer,
                                   ProducerRecord<String, String> producerRecord,
                                   HashMap<String, String> pMessage, boolean sync) {
        // 비동기 방식으로 처리
        if (!sync) {
            kafkaProducer.send(producerRecord, (metadata, exception) -> {

                if (exception == null) {
                    logger.info("async message:" + pMessage.get("key") + " partition:" + metadata.partition() + " offset:" + metadata.offset());
                } else {
                    logger.error("exception error from broker " + exception.getMessage());
                }
            });
        }

        // 동기 방식으로 처리
        else {
            try {

                RecordMetadata metadata = kafkaProducer.send(producerRecord).get(); // .get() 방식은 동기방식이다.
                logger.info("sync message:" + pMessage.get("key") + " partition:" + metadata.partition() + " offset:" + metadata.offset());

            } catch (ExecutionException e) {
                logger.error(e.getMessage());
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }

    }

    public static void main(String[] args) {

        String topicName = "pizza-topic";
        //KafkaProducer configuration setting
        // null, "hello world"

        Properties props  = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:10000");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        props.setProperty(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, "50000"); // delivery.timeout.ms should be equal to or larger than linger.ms + request.timeout.ms
//        props.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "6"); // 이 값이 5보다 크면 idempotence 가 disabled 된다 -> 문서에 나와있음, 명시적으로 idempotence를 true로 주고, 해당 값을 5보다 크게 주면 에러가 발생한다.


//        props.setProperty(ProducerConfig.ACKS_CONFIG, "0");
//        props.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");

//        props.setProperty(ProducerConfig.ACKS_CONFIG, "0"); // acks 모드 추가
//        props.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, "32000"); // 배치 사이즈 만큼 적재
//        props.setProperty(ProducerConfig.LINGER_MS_CONFIG, "20"); // ms 단위 : 해당 시간동안 대기 후 배치 전송

        //KafkaProducer object creation
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(props);

        sendPizzaMessage(kafkaProducer,
                topicName,
                -1,
                1000,
                0,
                0,
                false);

        kafkaProducer.close();

    }
}