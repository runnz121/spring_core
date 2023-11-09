package kuku.mainApi.common.config.rds;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class DataSourceRouter extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        boolean readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
        return readOnly ? DBType.READ : DBType.WRITE;
    }

    public static DataSourceRouter of(final DataSource readDataSource,
                                      final DataSource writeDataSource) {

        Map<Object, Object> dataSoruceMap = new HashMap<>();
        dataSoruceMap.put(DBType.READ, readDataSource);
        dataSoruceMap.put(DBType.WRITE, writeDataSource);

        DataSourceRouter dataSourceRouter = new DataSourceRouter();
        dataSourceRouter.setTargetDataSources(dataSoruceMap);
        dataSourceRouter.setDefaultTargetDataSource(writeDataSource);

        return dataSourceRouter;
    }
}
