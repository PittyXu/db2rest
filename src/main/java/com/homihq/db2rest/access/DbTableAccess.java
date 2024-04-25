package com.homihq.db2rest.access;

import com.homihq.db2rest.jdbc.core.model.DbTable;

public record DbTableAccess(Access access, DbTable dbTable) {
}
