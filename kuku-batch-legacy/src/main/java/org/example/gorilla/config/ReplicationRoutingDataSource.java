package org.example.gorilla.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class ReplicationRoutingDataSource extends AbstractRoutingDataSource {

    private enum Type {
        READ, WRITE
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                ? Type.READ
                : Type.WRITE;
    }

    public static ReplicationRoutingDataSource of(final DataSource readDataSource,
                                                  final DataSource writeDataSource) {

        Map<Object, Object> datasourceMap = new HashMap<>();
        datasourceMap.put(Type.READ, readDataSource);
        datasourceMap.put(Type.WRITE, writeDataSource);

        ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();
        routingDataSource.setTargetDataSources(datasourceMap);
        routingDataSource.setDefaultTargetDataSource(writeDataSource);

        return routingDataSource;
    }
}
