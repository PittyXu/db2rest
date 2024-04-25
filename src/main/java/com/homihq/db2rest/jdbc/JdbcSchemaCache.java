package com.homihq.db2rest.jdbc;

import com.homihq.db2rest.access.Access;
import com.homihq.db2rest.access.DbTableAccess;
import com.homihq.db2rest.access.Operation;
import com.homihq.db2rest.jdbc.dialect.*;
import com.homihq.db2rest.core.config.Db2RestConfigProperties;
import com.homihq.db2rest.core.exception.InvalidTableException;
import com.homihq.db2rest.jdbc.core.model.DbTable;

import com.homihq.db2rest.jdbc.sql.DbMeta;
import com.homihq.db2rest.jdbc.sql.JdbcMetaDataProvider;
import com.homihq.db2rest.jdbc.core.schema.SchemaCache;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.MetaDataAccessException;


import javax.sql.DataSource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public final class JdbcSchemaCache implements SchemaCache {

    private final DataSource dataSource;
    private final Db2RestConfigProperties db2RestConfigProperties;
    private Map<String, DbTableAccess> dbTableMap;

    @Getter
    @Deprecated
    private String productName;

    @Getter
    @Deprecated
    private int productVersion;

    @Getter
    private Dialect dialect;

    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void reload() {

        this.dbTableMap = new ConcurrentHashMap<>();
        loadMetaData();
    }

    public List<DbTableAccess> getTables(Operation operation) {
        return this.dbTableMap.values()
                .stream()
                .filter(it -> it.access().operation().equals(operation))
                .toList();
    }

    private void loadMetaData() {
        log.info("Loading meta data");
        try {

            DbMeta dbMeta = JdbcUtils.extractDatabaseMetaData(dataSource, new JdbcMetaDataProvider(db2RestConfigProperties));

            Map<String, DbTable> tmp = dbMeta.dbTables().stream()
                    .collect(Collectors.toMap((it) -> it.schema() + "." + it.name(), it -> it));

            List<Access> accesses = loadAccess();
            accesses.forEach(it -> {
                String table = it.schema() + "." + it.table();
                DbTable dbTable = tmp.get(table);
                if (!Objects.isNull(dbTable)) {
                    dbTableMap.put(it.name() + "_" + it.operation(), new DbTableAccess(it, dbTable));
                }
            });

            this.productName = dbMeta.productName();
            this.productVersion = dbMeta.majorVersion();

            dialect = DialectFactory.getDialect(this.productName, this.productVersion);



        } catch (MetaDataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Access> loadAccess() {
        return jdbcTemplate.query("SELECT * FROM " + db2RestConfigProperties.getAccess().getTableName(), new DataClassRowMapper<>(Access.class));
    }

    @Override
    public DbTableAccess getTable(String tableName, Operation operation) {

        DbTableAccess table = this.dbTableMap.get(tableName + "_" + operation);

        if(Objects.isNull(table)) throw new InvalidTableException(tableName);

        return table;
    }


}
