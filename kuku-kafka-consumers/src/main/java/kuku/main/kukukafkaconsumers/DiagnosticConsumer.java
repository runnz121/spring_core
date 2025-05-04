package kuku.main.kukukafkaconsumers;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.*;

public class DiagnosticConsumer {
    private static final Logger logger = LoggerFactory.getLogger(DiagnosticConsumer.class);

    public static void main(String[] args) {
        String topic = "pizza-topic";

        // 1) 설정: 수동 커밋
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:10000,localhost:10001");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group_diag");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");        // 수동 커밋
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "100");

        try (KafkaConsumer<String,String> consumer = new KafkaConsumer<>(props)) {
            // 2) 토픽 메타데이터 확인
            List<PartitionInfo> meta = consumer.partitionsFor(topic);
            logger.info("Cluster metadata for topic {}: {}", topic, meta);

            // 3) 구독 & 파티션 할당 대기
            consumer.subscribe(List.of(topic));
            consumer.poll(Duration.ofMillis(1000));
//            consumer.poll(Duration.ZERO);  // assignment 트리거
            Set<TopicPartition> assignment = consumer.assignment();
            logger.info("Partitions assigned: {}", assignment);

            // 4) 오프셋을 처음부터 읽도록 설정(테스트용)
            consumer.seekToBeginning(assignment);
            logger.info("Seek to beginning for all partitions");

            // 5) 폴링 루프
            while (true) {
                ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(1000));

                // 6) 메시지 처리
                for (ConsumerRecord<String,String> rec : records) {
                    logger.info("msg key={} value={} partition={} offset={}",
                            rec.key(), rec.value(), rec.partition(), rec.offset());
                }

                // 7) 현재 포지션 & 로그 끝(offset) 차이 확인
                Map<TopicPartition,Long> endOffsets = consumer.endOffsets(assignment);
                for (TopicPartition tp : assignment) {
                    long pos = consumer.position(tp);
                    long logEnd = endOffsets.get(tp);
                    logger.info("Partition {}: position={} logEnd={} lag={}",
                            tp.partition(), pos, logEnd, (logEnd - pos));
                }

                // 8) 수동 커밋
                if (!records.isEmpty()) {
                    consumer.commitSync();
                    logger.info("Offsets have been committed");
                }
            }
        }
    }
}