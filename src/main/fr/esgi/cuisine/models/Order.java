package main.fr.esgi.cuisine.models;

import com.google.gson.Gson;
import java.util.ArrayList;

public class Order extends Ressource{

    private String reference;
    private boolean isTakeAway;
    private ArrayList<MealDetail> detailsMeals;
    private ArrayList<MealDetail> detailsProducts;

    public Order(){

    }

    public Order(String uuid, String reference, boolean isTakeAway) {

        this.uuid = uuid;
        this.reference = reference;
        this.isTakeAway = isTakeAway;
    }

    public void setDetailsMeals(ArrayList<MealDetail> detailsMeals) {
        this.detailsMeals = detailsMeals;
    }

    @Override
    public String toString() {

        Gson gson = new Gson();

        return gson.toJson(this);

    }

    public String getReference() {
        return reference;
    }

    public boolean isTakeAway() {
        return isTakeAway;
    }
}
