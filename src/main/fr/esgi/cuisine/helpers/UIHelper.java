package main.fr.esgi.cuisine.helpers;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import main.fr.esgi.cuisine.views.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class UIHelper {


    public static void makeFadeInTransition(Parent root) {
        FadeTransition fadeTransition = new FadeTransition();

        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(root);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    public static void makeFadeOutTransition(Parent root, StageManagerHelper stageManagerHelper, FxmlView view) {
        FadeTransition fadeTransition = new FadeTransition();

        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(root);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(event1 -> stageManagerHelper.switchScene(view));
        fadeTransition.play();
    }

    public static Parent loadFxml(String path) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(UIHelper.class.getResource(path));
        return fxmlLoader.load();
    }

    public static <T> void loadUIContent(Pane origin, Pane content) {

        content.getChildren().removeAll();
        content.getChildren().add(origin);

    }

}
