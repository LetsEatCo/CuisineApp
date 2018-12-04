package main.fr.esgi.cuisine.helpers;


import org.apache.http.HttpResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonHelper {

    private static Object parseJsonData(String json) throws ParseException {

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(json);

        return jsonObject.get("data");
    }

    public static Object getResponseBody(HttpResponse httpResponse) throws IOException, ParseException {


        InputStream inputStream = httpResponse.getEntity().getContent();

        InputStreamReader bufferedInputStream = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(bufferedInputStream);

        StringBuilder stringBuilder = new StringBuilder();
        String tmp;
        while ((tmp = bufferedReader.readLine()) != null) {

            stringBuilder.append(tmp);
        }

        return parseJsonData(stringBuilder.toString());
    }


}
