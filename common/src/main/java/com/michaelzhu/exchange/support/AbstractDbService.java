package com.michaelzhu.exchange.support;

import com.michaelzhu.exchange.db.DbTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDbService extends LoggerSupport {
    @Autowired
    protected DbTemplate db;
}
