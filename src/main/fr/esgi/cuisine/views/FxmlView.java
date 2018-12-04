package main.fr.esgi.cuisine.views;

import java.util.ResourceBundle;

public enum FxmlView {

    SPLASH_SCREEN {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("home.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/SplashScreen.fxml";
        }
    },HOME {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("home.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Home.fxml";
        }
    },
    ADMIN_LOGIN{
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("adminLogin.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/AdminLogin.fxml";
        }
    },
    ORDER_HOME{
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("orderHome.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/OrderHome.fxml";
        }
    };

    public abstract String getTitle();
    public abstract String getFxmlFile();

    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("Bundle").getString(key);
    }
}
