package com.homihq.db2rest.jdbc.core.schema;

import com.homihq.db2rest.access.DbTableAccess;
import com.homihq.db2rest.access.Operation;


public interface SchemaCache {
    DbTableAccess getTable(String tableName, Operation operation);



}
