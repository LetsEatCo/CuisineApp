package main.fr.esgi.cuisine.helpers;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.fr.esgi.cuisine.spring.config.SpringFXMLLoader;
import main.fr.esgi.cuisine.views.FxmlView;
import org.slf4j.Logger;

import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class StageManagerHelper {

    private static final Logger LOG = getLogger(StageManagerHelper.class);
    private Stage primaryStage;
    private final SpringFXMLLoader springFXMLLoader;

    public StageManagerHelper(SpringFXMLLoader springFXMLLoader, Stage stage) {
        this.springFXMLLoader = springFXMLLoader;
        this.primaryStage = stage;
    }

    public void switchScene(final FxmlView view) {
        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
        viewRootNodeHierarchy.minWidth(primaryStage.getMinWidth());
        viewRootNodeHierarchy.minHeight(primaryStage.getMinHeight());
        viewRootNodeHierarchy.maxWidth(primaryStage.getMinWidth());
        viewRootNodeHierarchy.maxHeight(primaryStage.getMaxHeight());
        show(viewRootNodeHierarchy, view.getTitle());
    }

    /**
     * Loads the object hierarchy from a FXML document and returns to root node
     * of that hierarchy.
     *
     * @return Parent root node of the FXML document hierarchy
     */
    private Parent loadViewNodeHierarchy(String fxmlFilePath) {
        Parent rootNode = null;
        try {
            rootNode = springFXMLLoader.load(fxmlFilePath);
            Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
        } catch (Exception exception) {
            logAndExit("Unable to load FXML view" + fxmlFilePath, exception);
        }
        return rootNode;
    }

    private void logAndExit(String errorMsg, Exception exception) {
        LOG.error(errorMsg, exception, exception.getCause());
        Platform.exit();
    }

    private void show(final Parent rootNode, String title) {
        Scene scene = prepareScene(rootNode);

        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();

        try {
            primaryStage.show();
        } catch (Exception exception) {
            logAndExit ("Unable to show scene for title" + title,  exception);
        }
    }

    private Scene prepareScene(Parent rootNode){
        Scene scene = primaryStage.getScene();

        if (scene == null) {
            scene = new Scene(rootNode);
        }
        scene.setRoot(rootNode);
        return scene;
    }
}
