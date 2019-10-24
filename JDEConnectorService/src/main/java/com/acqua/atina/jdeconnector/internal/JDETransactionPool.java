/**
 * Copyright (C) 2019 ModusBox, Inc. All rights reserved.
 */
package com.acqua.atina.jdeconnector.internal;

import java.util.HashMap;
import java.util.Map;

import com.jdedwards.system.connector.dynamic.OneworldTransaction;

public class JDETransactionPool {

    private Map<Integer, OneworldTransaction> transactionList;

    public JDETransactionPool() {
        super();
        transactionList = new HashMap<Integer, OneworldTransaction>();
    }

    public static JDETransactionPool getInstance() {
        return JDETransactionListHolder.INSTANCE;
    }

    private static class JDETransactionListHolder {

        private JDETransactionListHolder() {
        }

        private static final JDETransactionPool INSTANCE = new JDETransactionPool();
    }

    public Integer addTransaction(OneworldTransaction owTransaction) {
        Integer nextId = returnNextTransactionId();

        transactionList.put(nextId, owTransaction);

        return nextId;

    }

    public OneworldTransaction getTransaction(Integer id) {
        if (transactionList.containsKey(id)) {
            return transactionList.get(id);
        } else {
            return null;
        }

    }

    public void removeTransaction(Integer id) {
        transactionList.remove(id);

    }

    public boolean existAPendingTransaction() {
        return !transactionList.isEmpty();

    }

    private Integer returnNextTransactionId() {

        Integer maxValue = Integer.valueOf(0);

        if (transactionList.isEmpty()) {
            maxValue = Integer.valueOf(1);
        } else {
            for (Integer value : transactionList.keySet()) {

                if (value > maxValue) {
                    maxValue = value;
                }

            }

            maxValue = maxValue + 1;

        }

        return maxValue;
    }
}
