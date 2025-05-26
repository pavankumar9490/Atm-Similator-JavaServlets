package service1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/balance")
public class BalanceServlet extends HttpServlet {
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sessionId = getSessionId(request);
        if (sessionId == null || !SessionManager.balances.containsKey(sessionId)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        double balance = SessionManager.balances.get(sessionId);
        response.setContentType("application/json");
        response.getWriter().write("{\"balance\": " + balance + "}");
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
