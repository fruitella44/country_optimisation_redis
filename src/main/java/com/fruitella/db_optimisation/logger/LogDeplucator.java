package com.fruitella.db_optimisation.logger;

import java.util.HashSet;
import java.util.Set;

public class LogDeplucator {
    private Set<String> loggedMessages = new HashSet<>();

    public boolean isDuplicate(String logMessage) {
        synchronized (loggedMessages) {
            if (loggedMessages.contains(logMessage)) {
                return true; // Log message is a duplicate
            } else {
                loggedMessages.add(logMessage);
                return false; // Log message is not a duplicate
            }
        }
    }
}
