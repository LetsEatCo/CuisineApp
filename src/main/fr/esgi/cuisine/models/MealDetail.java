package main.fr.esgi.cuisine.models;

import java.util.ArrayList;

public class MealDetail extends Ressource {

    private ArrayList<ProductOption> productOptions;
    private ArrayList<IngredientOption> ingredientOptions;

    public MealDetail(String name, long quantity){
        this.name = name;
        this.quantity = quantity;
    }

}
