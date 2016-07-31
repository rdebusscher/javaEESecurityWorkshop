package be.c4j.ee.sot;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.util.Map;

/**
 *
 */

@RequestScoped
@Named
public class UserBean {
    private Oauth2User user;

    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        user = (Oauth2User) sessionMap.get(AuthenticationFilter.USER);
    }

    public Oauth2User getUser() {
        return user;
    }
}

