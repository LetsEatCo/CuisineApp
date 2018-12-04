package main.fr.esgi.cuisine.models;

public class StoreCredentials {

    private String jwt;

    private String uuid;

    public StoreCredentials(String jwt, String uuid) {
        this.jwt = jwt;
        this.uuid = uuid;
    }

    public String getJwt() {
        return jwt;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
