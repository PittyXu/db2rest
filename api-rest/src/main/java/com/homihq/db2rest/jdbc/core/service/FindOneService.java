package com.homihq.db2rest.jdbc.core.service;

import com.homihq.db2rest.jdbc.rest.read.dto.ReadContext;

import java.util.Map;

public interface FindOneService {
    Map<String, Object> findOne(ReadContext readContext);
}
