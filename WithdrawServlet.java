package service1;

import java.io.IOException;
import java.util.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/withdraw")
public class WithdrawServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sessionId = getSessionId(request);
        if (sessionId == null || !SessionManager.balances.containsKey(sessionId)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
            double amount = Double.parseDouble(request.getParameter("amount"));
            double balance = SessionManager.balances.get(sessionId);

            if (amount > balance) {
               response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Insufficient balance");
                return;
            }
            double newBalance = balance - amount;
            SessionManager.balances.put(sessionId, newBalance);
            SessionManager.addStatement(sessionId, "Withdrawn ₹" + amount);
            response.setContentType("application/json");
            response.getWriter().write("{\"balance\": " + newBalance + "}");
        }
       

//        } catch (NumberFormatException e) {
//          response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            response.getWriter().write("Invalid amount format");
//        }

    private String getSessionId(HttpServletRequest request) {
        for (Cookie c : request.getCookies()) {
            if (c.getName().equals("sessionId")) {
                return c.getValue();
            }
        }
        return null;
    }
}
