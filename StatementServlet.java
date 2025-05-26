package service1;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/statement")
public class StatementServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sessionId = getSessionId(request);
        if (sessionId == null || !SessionManager.statements.containsKey(sessionId)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        List<String> history = SessionManager.statements.get(sessionId);
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String json = "[" + history.stream()
            .map(s -> "\"" + s.replace("\"", "\\\"") + "\"")
            .reduce((s1, s2) -> s1 + "," + s2)
            .orElse("") + "]";
        response.getWriter().write(json);
    }
        private String getSessionId(HttpServletRequest request) {
        if (request.getCookies() == null) return null;
        for (Cookie c : request.getCookies()) {
            if (c.getName().equals("sessionId")) {
                return c.getValue();
            }
        }
        return null;
    }
}
