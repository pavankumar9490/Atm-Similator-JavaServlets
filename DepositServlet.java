package service1;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deposit")
public class DepositServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sessionId = getSessionId(request);
        if (sessionId == null || !SessionManager.balances.containsKey(sessionId)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        double amount = Double.parseDouble(request.getParameter("amount"));
        double newBalance = SessionManager.balances.get(sessionId) + amount;
        SessionManager.balances.put(sessionId, newBalance);
        SessionManager.addStatement(sessionId, "Deposited ₹" + amount);

        response.setContentType("application/json");
        response.getWriter().write("{\"balance\": " + newBalance + "}");
    }
        private String getSessionId(HttpServletRequest request) {
        for (Cookie c : request.getCookies()) {
            if (c.getName().equals("sessionId")) {
                return c.getValue();
            }
        }
        return null;
    }
}
