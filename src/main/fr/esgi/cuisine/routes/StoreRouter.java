package main.fr.esgi.cuisine.routes;

import com.google.gson.Gson;
import main.fr.esgi.cuisine.helpers.CredentialsHelper;
import main.fr.esgi.cuisine.helpers.HttpHelper;
import main.fr.esgi.cuisine.models.*;
import org.apache.http.entity.StringEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class StoreRouter {

    public static String readyStatus = "a9586754-4f12-43f7-ad44-c27b47f36e68";
    public static String inPreparationStatus = "6a72390f-2542-42fd-9e5f-4b25a46dcd1c";

    public void login(String email, String password) throws IOException, ParseException {

        CredentialsHelper credentialsHelper = new CredentialsHelper();
        Properties routes = credentialsHelper.getRoutes();

        StoreLogin storeLogin = new StoreLogin(email, password);
        Gson gson = new Gson();
        StringEntity params;

        params = new StringEntity(gson.toJson(storeLogin));
        params.setContentType("application/json");

        JSONObject jsonObject = (JSONObject) HttpHelper.httpPostRequest(routes.getProperty("login"), params);

        Object jwt = jsonObject.get("jwt");
        if( jwt instanceof String){

            Object store = HttpHelper.httpGetRequest(routes.getProperty("me"), (String) jwt);

            String uuid = (String) ((JSONObject)store).get("uuid");
            StoreCredentials storeCredentials = new StoreCredentials((String) jwt, uuid);
            credentialsHelper.createCredentials(storeCredentials);
        }

    }

    public ArrayList<Order> getOrders() throws IOException, ParseException {

        ArrayList<Order> orders = new ArrayList<>();

        CredentialsHelper credentialsHelper = new CredentialsHelper();
        Properties routes = credentialsHelper.getRoutes();
        Properties config = credentialsHelper.getStoreCredentials();

        Object ordersObject = HttpHelper.httpGetRequest(routes.getProperty("orders"), config.getProperty("jwt"));

        if(ordersObject instanceof JSONArray){

           JSONArray ordersArray = (JSONArray) ordersObject;

            for (Object order : ordersArray) {



                if (order instanceof JSONObject){

                    String uuid = (String) ((JSONObject)order).get("uuid");
                    String reference = (String) ((JSONObject)order).get("reference");
                    boolean isTakeAway = (boolean) ((JSONObject)order).get("isTakeAway");
                    JSONArray history = (JSONArray) ((JSONObject) order).get("history");

                    Order orderModel = new Order(uuid, reference, isTakeAway);

                    Object detailsMeals = ((JSONObject) order).get("detailsMeals");
                    if(detailsMeals instanceof JSONArray){

                        ArrayList<MealDetail> mealDetails = new ArrayList<>();
                        JSONArray detailsMealsArray = (JSONArray) detailsMeals;

                        for (Object detailMeal : detailsMealsArray) {

                            if(detailMeal instanceof JSONObject){


                                long mealQuantity = (long) ((JSONObject) detailMeal).get("quantity");
                                JSONObject meal = (JSONObject) ((JSONObject) detailMeal).get("meal");
                                String mealName = (String) meal.get("name");

                                MealDetail mealDetail = new MealDetail(mealName, mealQuantity);

                                //TODO: Parse Options Products and Ingredients
                                mealDetails.add(mealDetail);
                            }


                        }

                        orderModel.setDetailsMeals(mealDetails);


                    }

                    orders.add(orderModel);

                }

            }

        }


        return orders;
    }

    public Store getStore() throws IOException, ParseException {

        CredentialsHelper credentialsHelper = new CredentialsHelper();
        Properties routes = credentialsHelper.getRoutes();
        Properties config = credentialsHelper.getStoreCredentials();

        String root = routes.getProperty("getStore");
        String storeUuid = config.getProperty("uuid");
        String route = root + storeUuid;

        Object store = HttpHelper.httpGetRequest(route);


        if(store instanceof JSONObject && ((JSONObject) store).get("sections") instanceof JSONArray){

            JSONObject storeJson = (JSONObject) store;

            String uuid = (String)storeJson.get("uuid");
            String name = (String) storeJson.get("name");
            String email = (String) storeJson.get("email");
            String phoneNumber = (String) storeJson.get("phoneNumber");
            String imageUrl = (String)storeJson.get("imageUrl");


            JSONArray sectionsJson = (JSONArray) storeJson.get("sections");


            for (Object section : sectionsJson) {

                if(section instanceof JSONObject){

                    if(((JSONObject) section).get("meals") instanceof JSONArray && ((JSONObject) section).get("products") instanceof JSONArray){

                        String sectionName = (String) ((JSONObject) section).get("name");
                        JSONArray mealsJson = (JSONArray) ((JSONObject) section).get("meals");

                        JSONArray productsJson = (JSONArray) ((JSONObject) section).get("products");



                    }

                }

            }

            return new Store(uuid,name,email,phoneNumber, imageUrl);


        }

        return null;
    }

    /* 1: In preparation action
     * 2: Ready Action
      * */
    public static void updateOrderStatus(String orderUuid, int type) throws IOException, ParseException {

        CredentialsHelper credentialsHelper = new CredentialsHelper();
        Properties routes = credentialsHelper.getRoutes();
        Properties config = credentialsHelper.getStoreCredentials();

        String root = routes.getProperty("getStore");
        String storeUuid = config.getProperty("uuid");
        String jwt = config.getProperty("jwt");
        String route = root+ orderUuid;
        Gson gson = new Gson();
        String orderStatusUuid;
        switch (type){
            case 1:
                orderStatusUuid = inPreparationStatus;
                break;
            case 2:
                orderStatusUuid = readyStatus;
                break;
            default:
                System.out.println("Invalid Type Of Operation");
                return;

        }
        UpdateStatus updateStatus = new UpdateStatus(orderStatusUuid);
        String toSend = gson.toJson(updateStatus);
        System.out.println(toSend);
        StringEntity params = new StringEntity(toSend);
        params.setContentType("application/json");
        HttpHelper.httpPatchRequest(root, params, jwt);



    }


}
