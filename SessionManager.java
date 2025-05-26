package service1;

import java.util.*;
public class SessionManager {
    public static Map<String, Double> balances = new HashMap<>();
    public static Map<String, List<String>> statements = new HashMap<>();
    public static void initSession(String sessionId) {
        balances.put(sessionId, 0.0);
        statements.put(sessionId, new ArrayList<>());
    }
    public static void addStatement(String sessionId, String entry) {
        statements.computeIfAbsent(sessionId, k -> new ArrayList<>()).add(entry);
    }
}
