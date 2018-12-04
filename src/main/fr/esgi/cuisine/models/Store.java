package main.fr.esgi.cuisine.models;


public class Store extends Ressource {

    private String name;
    private String email;
    private String phoneNumber;
    private String imageUrl;


    public Store(String uuid, String name, String email, String phoneNumber, String imageUrl) {
        this.uuid = uuid;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
