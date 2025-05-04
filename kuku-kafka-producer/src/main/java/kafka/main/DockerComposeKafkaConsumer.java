//package kafka.main;
//
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.time.Duration;
//import java.util.Arrays;
//import java.util.Properties;
//
//public class DockerComposeKafkaConsumer {
//    private static final Logger logger = LoggerFactory.getLogger(DockerComposeKafkaConsumer.class);
//
//    public static void main(String[] args) {
//        // 토픽 이름
//        String topicName = "pizza-topic";
//
//        // Consumer 설정
//        Properties props = new Properties();
//        // docker-compose 에서 EXTERNAL 리스너로 매핑된 호스트 포트
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:10000");
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        // consumer group
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group_01");
//        // 처음 시작할 때 토픽의 처음부터 읽고 싶으면 earliest
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//        // 자동 커밋 활성화 (필요시 false 로 하고 수동 커밋)
//        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
//        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
//
//        // Consumer 생성 및 토픽 구독
//        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
//            consumer.subscribe(Arrays.asList(topicName));
//            logger.info("Subscribed to topic {}", topicName);
//
//            // 무한 루프 polling
//            while (true) {
//                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
//                for (ConsumerRecord<String, String> record : records) {
//                    logger.info("partition = {}, offset = {}, key = {}, value = {}",
//                            record.partition(),
//                            record.offset(),
//                            record.key(),
//                            record.value());
//                }
//            }
//        } catch (Exception e) {
//            logger.error("Error in consumer loop", e);
//        }
//    }
//}
