package com.homihq.db2rest.jdbc.core.service;

import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import java.util.Map;

public interface ProcedureService extends SubRoutine {
    SimpleJdbcCall getSimpleJdbcCall(String subRoutineName);

    Map<String, Object> execute(String subRoutineName, Map<String, Object> inParams);
}
