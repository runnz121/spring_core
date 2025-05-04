package kafka.main;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.clients.producer.internals.StickyPartitionCache;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.InvalidRecordException;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class CustomPartitioner implements Partitioner {

//    DefaultPartitioner

    public static final Logger logger = LoggerFactory.getLogger(CustomPartitioner.class.getName());

    private String specialKeyName;

    private final StickyPartitionCache stickyPartitionCache = new StickyPartitionCache();

    @Override
    public int partition(String topic,
                         Object key,
                         byte[] keyBytes,
                         Object value,
                         byte[] valueBytes,
                         Cluster cluster) {

        // 파티션 정보들이 담겨있음
        List<PartitionInfo> partitionInfos = cluster.partitionsForTopic(topic);
        int numPartitions = partitionInfos.size();
        int numSpecialPartitions = (int) (numPartitions * 0.5);
        int partitionIndex = 0;

        if (keyBytes == null) {
            // 스티키로 하던지 혹은 에러로 하던지
//            return stickyPartitionCache.partition(topic, cluster);
            throw new InvalidRecordException("key should not be null");
        }

        // 0, 1 번 파티션에만 P001 키가 전송된다
        if (((String)key).equals(specialKeyName)) {
            // DefaultPartition 기본 반환 값
            partitionIndex = Utils.toPositive(Utils.murmur2(valueBytes)) % numSpecialPartitions;
        }

        else {
            // 0, 1 제외
            partitionIndex = Utils.toPositive(Utils.murmur2(keyBytes)) % (numPartitions - numSpecialPartitions) + numSpecialPartitions;
        }

        logger.info("key : {} is sent to partition : {}", key, partitionIndex);

        return partitionIndex;
    }

    // 파티셔너 종료될 때
    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

        specialKeyName = configs.get("custom.specialKey").toString();
    }
}
