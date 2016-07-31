package be.c4j.ee.sot;

public class Oauth2User {
    private String name;
    private String email;

    public Oauth2User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "OAuth2User{" +
                "name='" + name + '\'' + ", email='" + email + '\'' + '}';
    }
}


