import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;



public class ShoppingController {

    @FXML
    private Button doneButton;
    @FXML
    private Button addButton;
    @FXML
    private VBox itemBox;
    @FXML
    private Label notice;

    public void moreItems(){
        HBox h = new HBox();
        h.setPrefSize(440,60);
        h.setSpacing(2);
        VBox v1 = new VBox();
        v1.setPrefSize(100,60);
        Label nameLabel = new Label("Name:");
        TextField itemName = new TextField();
        itemName.setPromptText("e.g. apple");
        v1.getChildren().addAll(nameLabel,itemName);
        VBox v2 = new VBox();
        v2.setPrefSize(100,60);
        Label quantityLabel = new Label("Quantity:");
        TextField itemQuantity = new TextField();
        itemQuantity.setPromptText("e.g. 1");
        validQuantity(itemQuantity);
        v2.getChildren().addAll(quantityLabel,itemQuantity);
        VBox v3 = new VBox();
        v3.setPrefSize(100,60);
        Label unitPriceLabel = new Label("Unit-Price:");
        TextField unitPrice = new TextField();
        unitPrice.setPromptText("e.g. 1.99");
        validUnitPrice(unitPrice);
        v3.getChildren().addAll(unitPriceLabel,unitPrice);
        VBox v4 = new VBox();
        v4.setPrefSize(80,60);
        v4.setSpacing(5);
        RadioButton importedRB = new RadioButton("imported");
        RadioButton exemptRB = new RadioButton("exempt");
        v4.getChildren().addAll(importedRB,exemptRB);
        Button deleteButton = new Button();
        deleteButton.setPrefSize(30,30);
        ImageView del = new ImageView(new Image("delete.png"));
        del.setFitHeight(25);
        del.setFitWidth(25);
        deleteButton.setGraphic(del);
        deleteItem(deleteButton);
        h.getChildren().addAll(v1,v2,v3,v4,deleteButton);
        itemBox.getChildren().add(h);

        //change button text from add to more items
        if(itemBox.getChildren().size()>=1){
            addButton.setText("More Items");
            doneButton.setDisable(false);
        }

        notice.setVisible(false);

        //enable check out
        doneButton.setDisable(false);
    }



    public void deleteItem (Button b){
        //delete all items details (HBox) when delete button clicked
        b.setOnAction(event -> {
            itemBox.getChildren().remove(b.getParent());
            notice.setVisible(false);
            //change button text from more to add items
            if(itemBox.getChildren().size()<1){
                addButton.setText("Add Items");
                doneButton.setDisable(true);
            }
        });
    }

    public void validQuantity(TextField t) {
        //user can only enter a positiver integer as item quantity
        t.setTextFormatter(new TextFormatter<String>(change -> {
            String inputQuantity = change.getControlNewText();
            if (!inputQuantity.matches("[1-9][0-9]*")) {
                change.setText("");
            }
            return change;
        }));
    }

    public void validUnitPrice(TextField t) {
        //user can only enter a non-negative number with maximal two decimal places as unit price
        //allow unit price=0 in case of free items
        t.setTextFormatter(new TextFormatter<String>(change -> {
            String inputPrice = change.getControlNewText();
            if (inputPrice.matches("^0\\.?([0-9]{1,2})?"))
                return change;
            else if (inputPrice.matches("[1-9][0-9]*\\.?([0-9]{1,2})?"))
                return change;
            else {
                change.setText("");
                return change;
            }
        }));
    }


    public boolean checkEmptyFields(){
        boolean noEmptyFields = true;
        for(int i=0; i<itemBox.getChildren().size(); i++){
            HBox item = (HBox) itemBox.getChildren().get(i);
            TextField nText = (TextField) ((VBox) item.getChildren().get(0)).getChildren().get(1);
            TextField qText = (TextField) ((VBox) item.getChildren().get(1)).getChildren().get(1);
            TextField uText = (TextField) ((VBox) item.getChildren().get(2)).getChildren().get(1);
            if (nText.getText().trim().length() == 0 || qText.getText().trim().length() == 0 || uText.getText().trim().length() == 0){
                noEmptyFields = false;
            }
        }
        return noEmptyFields;
    }

    public void done(ActionEvent e) throws IOException {
        if (checkEmptyFields()) {
            for (int i = 0; i < itemBox.getChildren().size(); i++) {
                HBox item = (HBox) itemBox.getChildren().get(i);
                TextField nText = (TextField) ((VBox) item.getChildren().get(0)).getChildren().get(1);
                TextField qText = (TextField) ((VBox) item.getChildren().get(1)).getChildren().get(1);
                TextField uText = (TextField) ((VBox) item.getChildren().get(2)).getChildren().get(1);
                RadioButton iRB = (RadioButton) ((VBox) item.getChildren().get(3)).getChildren().get(0);
                RadioButton eRB = (RadioButton) ((VBox) item.getChildren().get(3)).getChildren().get(1);

                String n = nText.getText();
                int q = Integer.parseInt(qText.getText());
                double u = Double.parseDouble(uText.getText());
                boolean imp = false;
                boolean ex = false;

                if (iRB.isSelected()) {
                    imp = true;
                }
                if (eRB.isSelected()) {
                    ex = true;
                }
                ItemDetails details = new ItemDetails(n, q, u, imp, ex);
                details.calculate();
                if (i == itemBox.getChildren().size() - 1) {
                    Stage stage;
                    Scene scene;
                    Parent root;
                    FXMLLoader loader = new FXMLLoader(Main.class.getResource("/receipt.fxml"));
                    root = loader.load();
                    SceneController sceneController = loader.getController();
                    sceneController.showReceipt(details.updateReceipt());
                    stage = (Stage) (((Node) e.getSource()).getScene().getWindow());
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    //initialising receipt before restart
                    notice.setVisible(false);
                    details.initialSumTax();
                    details.initialSumPrice();
                    details.initialReceipt();
                }


            }


        } else {
            notice.setVisible(true);
        }
    }
}


