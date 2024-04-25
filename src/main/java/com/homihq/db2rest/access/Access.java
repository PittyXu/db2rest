package com.homihq.db2rest.access;

/**
 * Request access control
 * @param id
 * @param name rest path name
 * @param operation rest operation
 * @param schema real database schema
 * @param table real database table
 * @param columns real database columns // TODO support alias
 * @param verifier // TODO for to verify params UNKNOWN, LOGIN, CONTACT, CIRCLE, OWNER, ADMIN
 * @param permissions // TODO for to user permission filter UNKNOWN, LOGIN, OWNER, ADMIN, CUSTOMER
 */
public record Access(Long id, String name, Operation operation, String schema, String table, String columns, String verifier, String permissions) {
}
