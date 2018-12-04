package main.fr.esgi.cuisine.spring.config;


import javafx.stage.Stage;
import main.fr.esgi.cuisine.helpers.StageManagerHelper;
import main.fr.esgi.cuisine.models.*;
import main.fr.esgi.cuisine.routes.StoreRouter;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

@Configuration
public class AppJavaConfig {
    @Autowired
    SpringFXMLLoader springFXMLLoader;

    /**
     * Useful when dumping stack trace to a string for logging.
     * @return ExceptionWriter contains logging utility methods
     */
//    @Bean
//    @Scope("prototype")
//    public ExceptionWriter exceptionWriter() {
//        return new ExceptionWriter(new StringWriter());
//    }

    @Bean
    public ResourceBundle resourceBundle() {
        return ResourceBundle.getBundle("Bundle");
    }

    @Bean
    @Lazy //Stage only created after Spring context bootstrap
    public StageManagerHelper stageManagerHelper(Stage stage) {
        return new StageManagerHelper(springFXMLLoader, stage);
    }
    @Bean
    public Store store() throws IOException, ParseException {
        StoreRouter storeRouter = new StoreRouter();
        return storeRouter.getStore();
    }

    @Bean
    public ArrayList<Order> orders() throws IOException, ParseException {
        StoreRouter storeRouter = new StoreRouter();
        return storeRouter.getOrders();

    }

}
