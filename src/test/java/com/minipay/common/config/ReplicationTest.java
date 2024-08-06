package com.minipay.common.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ReplicationTest {

    private static final String DETERMINE_CURRENT_LOOKUP_KEY = "determineCurrentLookupKey";

    @Transactional(readOnly = true)
    @DisplayName("MasterDataSource Replication 설정 테스트")
    @Test
    void testMasterDataSourceReplication() throws Exception {

        // Given
        RoutingDataSource routingDataSource = new RoutingDataSource();

        // When
        Method declaredMethod = RoutingDataSource.class.getDeclaredMethod(DETERMINE_CURRENT_LOOKUP_KEY);
        declaredMethod.setAccessible(true);

        Object object = declaredMethod.invoke(routingDataSource);

        // Then
        assertEquals(DataSourceType.slave.toString(), object.toString());
    }

    static enum DataSourceType {
        master, slave
    }
}
