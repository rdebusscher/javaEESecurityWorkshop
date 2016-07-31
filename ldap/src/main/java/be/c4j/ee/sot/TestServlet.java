package be.c4j.ee.sot;

import javax.security.identitystore.annotation.LdapIdentityStoreDefinition;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
@WebServlet("/test")
@LdapIdentityStoreDefinition(
        url = "ldap://www.zflexldap.com/",
        baseDn = "cn=ro_admin,ou=sysadmins,dc=zflexsoftware,dc=com",
        password = "zflexpass",
        searchBase = "ou=developers,dc=zflexsoftware,dc=com",
        searchExpression = "(&(uid=%s)(objectClass=person))",
        groupBaseDn = "ou=groups,ou=developers,dc=zflexsoftware,dc=com"
)
public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("This is a servlet \n");
        String webName = null;
        if (req.getUserPrincipal() != null) {
            webName = req.getUserPrincipal().getName();
        }
        resp.getWriter().write("web username: " + webName + "\n");
        resp.getWriter().write("web user has role \"devgroup1\":" + req.isUserInRole("devgroup1") + "\n");
        resp.getWriter().write("web user has role \"devgroup2 \":" + req.isUserInRole("devgroup2") + "\n");
        resp.getWriter().write("web user has role \"devgroup3 \":" + req.isUserInRole("devgroup3") + "\n");
        resp.getWriter().write("web user has role \"devgroup4 \":" + req.isUserInRole("devgroup4") + "\n");
    }
}
