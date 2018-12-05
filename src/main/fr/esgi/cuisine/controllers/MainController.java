package main.fr.esgi.cuisine.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.fr.esgi.cuisine.helpers.StageManagerHelper;
import main.fr.esgi.cuisine.helpers.UIHelper;
import main.fr.esgi.cuisine.models.Order;
import main.fr.esgi.cuisine.models.ui.OrderUI;
import main.fr.esgi.cuisine.routes.StoreRouter;
import main.fr.esgi.cuisine.views.FxmlView;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Component
public class MainController implements FxmlController{

    @FXML
    private VBox root;

    @FXML
    private HBox rootAnchor;

    @FXML
    private HBox ordersContainer;

    @FXML
    private VBox inPreparationBox;

    @FXML
    private HBox toMarkReadyContainer;

    @FXML
    private VBox readyBox;

    @FXML
    private HBox adminRoot;

    @FXML
    private JFXTextField emailInput;

    @FXML
    private JFXPasswordField passwordInput;

    private int adminCounter = 0;
    private final StageManagerHelper stageManagerHelper;
    private ArrayList<Order> orders;

    @Autowired @Lazy
    public MainController(StageManagerHelper stageManagerHelper, ArrayList<Order> orders) {

        this.stageManagerHelper = stageManagerHelper;
        this.orders = orders;
    }

    @FXML
    void login() throws IOException, ParseException {

        String email = emailInput.getText();
        String password = passwordInput.getText();
        StoreRouter storeRouter = new StoreRouter();
        storeRouter.login(email, password);

    }


    @FXML
    void adminRegistration() {

        adminCounter +=1;

        if(adminCounter == 10) {

            adminCounter=0;
            UIHelper.makeFadeOutTransition(rootAnchor,stageManagerHelper,FxmlView.ADMIN_LOGIN);
        }
    }

    @FXML
    void home(ActionEvent event){

        UIHelper.makeFadeOutTransition(adminRoot, stageManagerHelper, FxmlView.ORDER_HOME);
    }

    @Override
    public void initialize() {
        UIHelper.makeFadeInTransition(rootAnchor);
        Thread dummyOrdersThread = new Thread(this::dummyOrders);
        dummyOrdersThread.start();

    }

    @FXML
    public void handleDragOverEvent(DragEvent event) {

        if(event.getDragboard().hasString()){
            event.acceptTransferModes(TransferMode.ANY);
        }

    }

    @FXML
    public void handleOrderDropInPreparation(DragEvent event) {

        String reference = event.getDragboard().getString();

        setInPreparation(reference);

    }

    private void setInPreparation(String s) {

        OrderUI toUpdate = null;
        OrderUI isBack = null;
        for (Node child : ordersContainer.getChildren()) {

            if(Objects.equals(child.toString(), s)){
                toUpdate = (OrderUI) child;
            }
        }

        for (Node child : toMarkReadyContainer.getChildren()) {

            if(Objects.equals(child.toString(), s)){
                isBack = (OrderUI) child;
            }
        }

        if(isBack!=null){

            isBack.setInPreparation(false);
            toMarkReadyContainer.getChildren().remove(isBack);
            ordersContainer.getChildren().add(isBack);
        }

        if(toUpdate != null && !toUpdate.isInPreparation()){

            toUpdate.setInPreparation(true);
            toMarkReadyContainer.getChildren().add(toUpdate);
            ordersContainer.getChildren().remove(toUpdate);

        }
    }

    @FXML
    void handleOrderDropInReady(DragEvent event) throws IOException, ParseException {

        String reference = event.getDragboard().getString();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Mark As Ready");
        alert.setHeaderText(null);
        alert.setContentText("Order Ready ?");

        Optional<ButtonType> action = alert.showAndWait();

        if(action.get() == ButtonType.OK){

            removeObject(reference);

        }


    }

    private void removeObject(String reference) throws IOException, ParseException {

        OrderUI toDelete = null;
        for (Node child : toMarkReadyContainer.getChildren()) {

            System.out.println(child.toString());
            if(Objects.equals(child.toString(), reference)){
                toDelete = (OrderUI) child;
            }
        }

        if(toDelete != null){

            StoreRouter.updateOrderStatus(toDelete.getUuid(), 2);
            toMarkReadyContainer.getChildren().remove(toDelete);
        }

    }

    private void dummyOrders() {

            try {

                final int[] dummyCounter = {0};
                while (true){

                    Thread.sleep(10000);

                    new Thread(() -> Platform.runLater(() -> {

                        Order dummy = new Order("uuid-"+ dummyCounter[0],"Super-Reference-"+ dummyCounter[0], true);
                        OrderUI orderUI = new OrderUI(dummy);
                        ordersContainer.getChildren().add(orderUI);
                        ++dummyCounter[0];

                    })).start();

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

    }

}

