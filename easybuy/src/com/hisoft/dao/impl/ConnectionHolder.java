package com.hisoft.dao.impl;

import com.hisoft.dao.BaseDao;

import java.sql.Connection;

public class ConnectionHolder  {

    protected ThreadLocal<Connection> threadLocal;

    public ConnectionHolder(ThreadLocal<Connection> threadLocal) {

        this.threadLocal = threadLocal;
    }

    public ThreadLocal<Connection> getThreadLocal() {
        return threadLocal;
    }

    public void setThreadLocal(ThreadLocal<Connection> threadLocal) {
        this.threadLocal = threadLocal;
    }
}
