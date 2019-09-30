package org.chance.example.starter.distributelock.trigger;


import lombok.extern.slf4j.Slf4j;
import org.h2.api.Trigger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * LoggingDatabaseTrigger
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/29
 */
@Slf4j
public class LoggingDatabaseTrigger implements Trigger {

    @Override
    public void init(Connection conn, String schemaName, String triggerName, String tableName, boolean before, int type) throws SQLException {

    }

    @Override
    public void fire(Connection conn, Object[] oldRow, Object[] newRow) throws SQLException {
        log.info("Inserting new lock table entry: {}", Arrays.toString(newRow));
    }

    @Override
    public void close() throws SQLException {

    }

    @Override
    public void remove() throws SQLException {

    }
}
