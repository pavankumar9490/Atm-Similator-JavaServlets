package service1;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final String CORRECT_PIN = "1234";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pin = request.getParameter("pin");
            if (CORRECT_PIN.equals(pin)) {
            String sessionId = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("sessionId", sessionId);
            cookie.setMaxAge(60 * 30); // 30 mins
            response.addCookie(cookie);
            SessionManager.initSession(sessionId);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
