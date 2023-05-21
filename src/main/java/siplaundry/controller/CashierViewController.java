package siplaundry.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import siplaundry.data.SessionData;
import siplaundry.view.cashier.DashboardView;
import siplaundry.view.cashier.ReportView;
import siplaundry.view.cashier.RetardView;
import siplaundry.view.cashier.TransactionView;

public class CashierViewController {
    
    @FXML
    private HBox btn_sb_dashboard;

    @FXML
    private HBox btn_sb_transaction;

    @FXML
    private HBox btn_sb_retard;

    @FXML
    private HBox btn_sb_report;

    @FXML
    private BorderPane shadow;

    @FXML
    private BorderPane parent_root;

    @FXML
    private Text topbar_name;

    @FXML
    void initialize() throws IOException{
        parent_root.setCenter(new DashboardView());
        topbar_name.setText(SessionData.user.getFullname());
        shadow.setVisible(false);
    }

    @FXML
    void showDashboard(MouseEvent event) throws IOException{
        removeAllStates();
        btn_sb_dashboard.getStyleClass().add("active");

        parent_root.setCenter(new DashboardView());
    }

    @FXML
    void showRetard(MouseEvent event) throws IOException{
        removeAllStates();
        btn_sb_retard.getStyleClass().add("active");

        parent_root.setCenter(new RetardView());
    }

    @FXML
    void showTransaction(MouseEvent event) throws IOException{
        removeAllStates();
        btn_sb_transaction.getStyleClass().add("active");

        parent_root.setCenter(new TransactionView());
    }

    @FXML
    void showReport(MouseEvent event) throws IOException{
        removeAllStates();
        btn_sb_report.getStyleClass().add("active");

        parent_root.setCenter(new ReportView());
    }

    private void removeAllStates(){
        btn_sb_dashboard.getStyleClass().remove("active");
        btn_sb_report.getStyleClass().remove("active");
        btn_sb_retard.getStyleClass().remove("active");
        btn_sb_transaction.getStyleClass().remove("active");
    }

}