package com.homihq.db2rest.access;

import java.util.Arrays;
import java.util.List;

public enum Operation {
    /**
     * Rest for read
     */
    READ,

    /**
     * Rest for count
     */
    COUNT,

    /**
     * Rest for create
     */
    CREATE,

    /**
     * Rest for bulk create
     */
    BULK,

    /**
     * Rest for update
     */
    UPDATE,

    /**
     * Rest for delete
     */
    DELETE,

    /**
     * Rest for schema
     */
    SCHEMA,

    FUNCTION,

    PROCEDURE;
}
