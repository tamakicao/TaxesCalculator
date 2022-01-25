import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


import java.io.IOException;


public class SceneController {
    @FXML
    private TextArea receiptText;

    public void showReceipt(String s){
            receiptText.setText(s);
            //show receipt
    }

    public void toShoppingCart(ActionEvent e) throws IOException {
        //switch to shopping cart
        Stage stage;
        Scene scene;
        Parent root;
        FXMLLoader loader=new FXMLLoader(Main.class.getResource("/shopping.fxml"));
        root=loader.load();
        ShoppingController shoppingController=loader.getController();
        shoppingController.moreItems();
        stage=(Stage)(((Node)e.getSource()).getScene().getWindow());
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
