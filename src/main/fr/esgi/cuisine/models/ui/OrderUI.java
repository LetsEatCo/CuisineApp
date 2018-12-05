package main.fr.esgi.cuisine.models.ui;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.fr.esgi.cuisine.controllers.MainController;
import main.fr.esgi.cuisine.models.Order;

public class OrderUI extends Pane {

    private boolean inPreparation = false;
    private String uuid;
    private VBox root = new VBox();

    public OrderUI(Order order) {

        this.uuid = order.getUuid();

        HBox head = new HBox();
        head.setAlignment(Pos.CENTER);
        root.setAlignment(Pos.CENTER_LEFT);
        root.setSpacing(15);
        root.setPrefHeight(400);
        root.setMinHeight(root.getPrefHeight());

        this.setOnDragDetected(event -> {
            Dragboard db = startDragAndDrop(TransferMode.ANY);

            ClipboardContent content = new ClipboardContent();
            content.putString(this.toString());
            db.setContent(content);
            event.consume();
        });


        // TODO : Iterate on details

        Label reference = new Label("Reference : "+order.getReference());
        String place = order.isTakeAway()? "Take Away":"On The Spot";
        Label placeToEat = new Label("Place : "+place);
        head.getChildren().add(reference);
        root.getChildren().addAll(head, placeToEat);

        root.getStyleClass().add("order-coming");
        this.getChildren().removeAll();
        this.getChildren().add(root);


    }

    public void setInPreparation(boolean inPreparation) {
        root.getStyleClass().clear();
        if(inPreparation){
            root.getStyleClass().add("in-preparation-on-set");
        }
        else root.getStyleClass().add("order-coming");
        this.inPreparation = inPreparation;
    }

    public boolean isInPreparation() {
        return inPreparation;
    }

    public String getUuid() {
        return uuid;
    }

}
