/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acqua.atina.jdeconnector.internal;

import java.util.HashMap;
import java.util.Map;

import com.jdedwards.system.connector.dynamic.OneworldTransaction;

/**
 *
 * @author jgodi
 */
public class JDETransactions {

    private Map<Integer, OneworldTransaction> transactionPendingProcess;

    public JDETransactions() { 
        transactionPendingProcess = new HashMap<Integer, OneworldTransaction>();
    }

    public static JDETransactions getInstance() {
        return JDETransactionsHolder.INSTANCE;
    }

    private static class JDETransactionsHolder {

        private JDETransactionsHolder() {
        }

        private static final JDETransactions INSTANCE = new JDETransactions();
    }

    public Integer addTransaction(OneworldTransaction owTransaction) {
        Integer nextId = returnNextTransactionId();

        transactionPendingProcess.put(nextId, owTransaction);

        return nextId;

    }

    public OneworldTransaction getTransaction(Integer id) {
        if (transactionPendingProcess.containsKey(id)) {
            return transactionPendingProcess.get(id);
        } else {
            return null;
        }

    }

    public void removeTransaction(Integer id) {
        transactionPendingProcess.remove(id);

    }

    public boolean existAPendingTransaction() {
        return !transactionPendingProcess.isEmpty();

    }

    private Integer returnNextTransactionId() {

        Integer maxValue = Integer.valueOf(0);

        if (transactionPendingProcess.isEmpty()) {
            maxValue = Integer.valueOf(1);
        } else {
            for (Integer value : transactionPendingProcess.keySet()) {

                if (value > maxValue) {
                    maxValue = value;
                }

            }

            maxValue = maxValue + 1;

        }

        return maxValue;
    }

}
