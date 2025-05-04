package kuku.main.kukukafkaconsumers;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class ConsumerWakeUp {

    public static final Logger logger = LoggerFactory.getLogger(ConsumerWakeUp.class.getName());

    public static void main(String[] args) {

        String topicName = "pizza-topic";

        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:10000");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "group_01");
        props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(props);
        kafkaConsumer.subscribe(List.of(topicName));


        // 현재 스레드
        Thread mainThread = Thread.currentThread();

        // 메인 스레드 종료시 별도의 thread 로 kafkaConsumer wakeup() 메소드로 호출하게 한다.
        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    logger.info("wake up main program start to exit by calling wake up");
                    kafkaConsumer.wakeup();

                    // 메인 스레드가 종료할 떄까지 대기 (blocking)
                    try {
                        mainThread.join();
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }}
                ));

        try {
            // 지속 polling 하기위해
            while (true) {

                // 배치 단위로 polling 하고 여러개의 레코드를 갖고온다.
                // poll 할때 wake up exception 을 발생
                ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord record : consumerRecords) {
                    logger.info("record key : {}, record value : {}, record partition : {}, record offset : {}", record.key(), record.value(), record.partition(), record.offset());
                }
            }
        } catch (Exception ex) {
            logger.error("wakeup exception has been cancelled");
        } finally {
            logger.error("finally consumer is closing");
            // 카프카 연결 종료
            kafkaConsumer.close();
        }
    }
}
