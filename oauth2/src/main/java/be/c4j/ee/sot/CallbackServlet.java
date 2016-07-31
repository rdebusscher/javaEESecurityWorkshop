package be.c4j.ee.sot;

import com.github.scribejava.core.model.*;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 */
@WebServlet("oauth2callback")
public class CallbackServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String csrfToken = request.getParameter("state");
        // We need to verify this with the value we submitted !!

        String code = request.getParameter("code"); // The Authenticationcode
        OAuth20Service authService = AuthenticationFilter.createOAuthService(null);
        Token token = authService.getAccessToken(new Verifier(code));

        OAuthRequest oReq = new OAuthRequest(Verb.GET, "https://www.googleapis.com/oauth2/v3/userinfo", authService);
        authService.signRequest(token, oReq);
        Response oResp = oReq.send();
        String userInfo = oResp.getBody();
        //System.out.println(userInfo);
        try {
            JSONObject jsonObject = new JSONObject(new JSONTokener(userInfo));
            Oauth2User user = new Oauth2User(jsonObject.getString("name"), jsonObject.getString("email"));
            HttpSession session = request.getSession();
            session.setAttribute(AuthenticationFilter.USER, user);
            // Redirect to original called URL.
            response.sendRedirect(session.getAttribute(AuthenticationFilter.ORIGINAL_URL).toString());
        } catch (JSONException e) {
            // FIXME
            e.printStackTrace();
        }
    }
}
