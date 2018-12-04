package main.fr.esgi.cuisine.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import main.fr.esgi.cuisine.helpers.StageManagerHelper;
import main.fr.esgi.cuisine.helpers.UIHelper;
import main.fr.esgi.cuisine.views.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class SplashScreenController implements FxmlController {

    @FXML
    private VBox splashRoot;

    private StageManagerHelper stageManagerHelper;
    @Autowired
    @Lazy
    public SplashScreenController(StageManagerHelper stageManagerHelper) {
        this.stageManagerHelper = stageManagerHelper;
    }

    @Override
    public void initialize() {
        new SplashScreen().start();
    }

    class SplashScreen extends Thread{

        @Override
        public void run() {
            try {
                Thread.sleep(2000);

                UIHelper.makeFadeOutTransition(splashRoot, stageManagerHelper, FxmlView.ORDER_HOME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
