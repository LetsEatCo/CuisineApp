package main.fr.esgi.cuisine.helpers;

import main.fr.esgi.cuisine.models.StoreCredentials;

import java.io.*;
import java.util.Properties;

public class CredentialsHelper {

    private static String path ="src/main/fr/esgi/cuisine/config/";
    private static String config =path+"config.properties";
    private static String routes =path+"routes.properties";

    public void createCredentials(StoreCredentials storeCredentials) throws IOException {

        Properties properties = new Properties();
        OutputStream  outputStream = new FileOutputStream(config);

        // Conf properties
        properties.setProperty("uuid", storeCredentials.getUuid());
        properties.setProperty("jwt", storeCredentials.getJwt());

        // StoreRouter properties
        properties.store(outputStream, null);

        outputStream.close();

    }

    public Properties getStoreCredentials() throws IOException {

        return getProperties(config);
    }

    private Properties getProperties(String pathFile) throws IOException {

        Properties properties = new Properties();

        InputStream inputStream = new FileInputStream(pathFile);

        properties.load(inputStream);

        inputStream.close();

        return properties;
    }

    public Properties getRoutes() throws IOException {

        return getProperties(routes);

    }

}
