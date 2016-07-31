package be.c4j.ee.sot;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@WebServlet("/protected")
public class TestServlet extends HttpServlet {

    private Map<String, String> users;

    @Override
    public void init() throws ServletException {
        users = new HashMap<>();
        users.put("sot", "sot");
        users.put("rudy", "rudy");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String authorizationHeader = req.getHeader("Authorization");
        if (!authorized(authorizationHeader)) {
            // Not allowed, so report he's unauthorized
            resp.setHeader("WWW-Authenticate", "BASIC realm=\"SOTtest\"");
            resp.sendError(resp.SC_UNAUTHORIZED);
        } else {
            resp.getWriter().println("Highly sensitive content");
        }
    }

    private boolean authorized(String authorizationHeader) {
        if (authorizationHeader == null) {
            return false;
        }
        if (!authorizationHeader.toUpperCase().startsWith("BASIC")) {
            return false;
        }
        // Get encoded user and password, comes after "BASIC"
        String userPwEncoded = authorizationHeader.substring(6);
        byte[] decoded = Base64.getDecoder().decode(userPwEncoded);
        String[] parts = new String(decoded).split(":");
        String pw = users.get(parts[0]);
        return pw != null && pw.equals(parts[1]);
    }

}
