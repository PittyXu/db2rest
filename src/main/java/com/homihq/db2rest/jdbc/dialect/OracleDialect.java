package com.homihq.db2rest.jdbc.dialect;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.homihq.db2rest.core.Dialect;
import com.homihq.db2rest.core.model.DbTable;
import com.homihq.db2rest.exception.GenericDataAccessException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

@Slf4j
public class OracleDialect implements Dialect {

    private final ObjectMapper objectMapper;
    @Getter
    private final String productName;

    @Getter
    private final int productVersion;


    public OracleDialect(ObjectMapper objectMapper, String productName, int productVersion) {
        this.objectMapper = objectMapper;
        this.productName = productName;
        this.productVersion = productVersion;

    }

    @Override
    public String getProductFamily() {
        return "Oracle";
    }

    @Override
    public String getProductName() {
        return this.productName;
    }

    @Override
    public int getMajorVersion() {
        return productVersion;
    }

    @Override
    public boolean supportBatchReturnKeys() {
        return false;
    }

    @Override
    public void processTypes(DbTable table, List<String> insertableColumns, Map<String, Object> data) {

        try {
            for (String columnName : insertableColumns) {
                Object value = data.get(columnName);

                String columnDataTypeName = table.getColumnDataTypeName(columnName);

                log.debug("columnDataTypeName - {}", columnDataTypeName);

                if (StringUtils.equalsAnyIgnoreCase(columnDataTypeName, "json")) {

                    data.put(columnName, objectMapper.writeValueAsString(value));
                }

            }
        }
        catch (Exception exception) {
            throw new GenericDataAccessException(exception.getMessage());
        }

    }

}
