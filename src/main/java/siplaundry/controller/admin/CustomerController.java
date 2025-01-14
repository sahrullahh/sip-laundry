package siplaundry.controller.admin;

import java.util.*;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import siplaundry.data.SortingOrder;
import siplaundry.entity.CustomerEntity;
import siplaundry.repository.CustomerRepo;
import siplaundry.util.ViewUtil;
import siplaundry.view.admin.components.column.CustomerColumn;
import siplaundry.view.admin.components.modal.ConfirmDialog;
import siplaundry.view.admin.components.modal.CustomerModal;
import siplaundry.view.util.EmptyData;
import toast.Toast;
import toast.ToastType;

public class CustomerController {

    private BorderPane shadowRoot;

    @FXML
    private HBox btn_add_customer, btn_bulk_delete;
    @FXML
    private VBox customer_table;
    @FXML
    private Text total_text;
    @FXML
    private TextField txt_keyword;
    @FXML
    private FontIcon sort_icon;
    @FXML
    private ComboBox<String> CB_column;

    private CustomerRepo custRepo = new CustomerRepo();
    private Set<CustomerEntity> bulkItems = new HashSet<>();
    private ArrayList<CustomerColumn> accColumns = new ArrayList<>();

    public CustomerController(BorderPane shadow){ this.shadowRoot = shadow;}
    private SortingOrder sortOrder = SortingOrder.ASC;

    @FXML
    void initialize(){
        List<CustomerEntity> customer = custRepo.get();
        ObservableList<String> column = FXCollections.observableArrayList(
            "Nama",
            "Alamat"
        );

        CB_column.setItems(column);

        total_text.setText("Menampilkan total "+ customer.size());
        showTable(customer);
    }

    @FXML
    void showAddCustomer(){
        new CustomerModal(shadowRoot, this::showTable, null);
    }

    @FXML
    void searchAction(KeyEvent event){
        List<CustomerEntity> cust = custRepo.search(this.searchableValues());
        showTable(cust);
    }

    @FXML
    void sortAction(){
        String column = "name";

        this.sortOrder = ViewUtil.switchOrderIcon(this.sortOrder, this.sort_icon);

        if(CB_column.getValue() != null){
            if(CB_column.getValue().equals("Alamat")) column = " address";
        }

        List<CustomerEntity> cust = custRepo.search(this.searchableValues(), column, this.sortOrder);
        showTable(cust);
     }

     @FXML
     void selectBulkAll(){
        for(CustomerColumn column: accColumns){
            column.toggleBulk();
        }
     }

     @FXML
    void bulkDelete(){
        new ConfirmDialog(shadowRoot, () -> {
            for(CustomerEntity cust: this.bulkItems){
                custRepo.delete(cust.getid());
            }

            new Toast((AnchorPane) btn_bulk_delete.getScene().getRoot())
                .show(ToastType.SUCCESS, "Berhasil melakukan hapus semua", null);
            showTable(custRepo.get());
        });
    }

    void showTable(List<CustomerEntity> customer){
        customer_table.getChildren().clear();

        if(customer == null) customer = custRepo.get();
        if(customer.size() < 1) {
            customer_table.getChildren().add(new EmptyData(this::showAddCustomer, txt_keyword.getText()));
        }

        for(CustomerEntity cust : customer){
            CustomerColumn column = new CustomerColumn(shadowRoot, this::showTable, cust);
            column.setBulkAction(this::toggleBulkItem);

            customer_table.getChildren().add(column);
            accColumns.add(column);
        }
    }

    protected void toggleBulkItem(CustomerEntity cust){
        if(this.bulkItems.contains(cust)){
            this.bulkItems.remove(cust);
        } else {
            this.bulkItems.add(cust);
        }

        btn_bulk_delete.setDisable(this.bulkItems.size() < 1);
    }

    private HashMap<String, Object> searchableValues() {
        String keyword = txt_keyword.getText();
        return new HashMap<>() {{
            put("name", keyword);
            put("phone", keyword);
            put("address", keyword);
        }};
    }
}
