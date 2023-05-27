package siplaundry.view.cashier;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import siplaundry.controller.cashier.ExpenseController;

public class ExpenseView extends AnchorPane{
    public ExpenseView(BorderPane shadow) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/cashier/expense.fxml"));

        loader.setRoot(this);
        loader.setController(new ExpenseController(shadow));
        loader.load();
    }
}
