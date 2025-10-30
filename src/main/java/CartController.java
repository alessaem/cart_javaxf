import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class CartController {
    @FXML private Label languageSet;
    @FXML private ComboBox<String> comboBox;
    @FXML private Label itemCount;
    @FXML private TextField numberField;
    @FXML private Button itemBtn;
    @FXML private VBox itemBox;
    @FXML private HBox totalBox;
    @FXML private Label totalLabel;
    @FXML private Button calculateBtn;
    @FXML private TextField validationField;

    private ResourceBundle rb;
    private Cart cart = new Cart();
    private List<TextField> priceFields = new ArrayList<TextField>();
    private List<TextField> quantityFields = new ArrayList<TextField>();

    public void initialize(){
        comboBox.getItems().setAll("en","fi","ja","sv");
        comboBox.getSelectionModel().selectFirst();

        loadLanguageFor(comboBox.getValue());

        comboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                loadLanguageFor(newVal);
            }
        });
    }

    public void loadLanguageFor(String code){
        switch (code) {
            case "en":
                loadLanguage("en","US");
                break;
            case "fi":
                loadLanguage("fi","FI");
                break;
            case "ja":
                loadLanguage("ja","JP");
                break;
            case "sv":
                loadLanguage("sv","SE");
                break;
            default:
                loadLanguage("en","US");
                break;
        }
    }

    private void loadLanguage(String langCode, String country) {
        Locale locale = new Locale(langCode, country);
        rb = ResourceBundle.getBundle("messages", locale);
        try {
            rb = ResourceBundle.getBundle("messages", locale);

            languageSet.setText(rb.getString("languageSet.text"));
            itemCount.setText(rb.getString("itemCount.text"));
            //numberField.setPromptText(rb.getString("quantity.text"));
            itemBtn.setText(rb.getString("itemBtn.text"));
            totalLabel.setText(rb.getString("totalLabel.text"));
            calculateBtn.setText(rb.getString("calculateBtn.text"));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    protected void enterItems(){
        String txt = numberField.getText();
        if (txt == null || txt.isBlank()) {
            validationField.setText(rb != null && rb.containsKey("error.invalidNumber")
                    ? rb.getString("error.invalidNumber") : "Enter number of items");
            return;
        }
        int itemnum;
        try {
            itemnum = Integer.parseInt(txt.trim());
        } catch (NumberFormatException ex) {
            validationField.setText(rb != null && rb.containsKey("error.invalidNumber")
                    ? rb.getString("error.invalidNumber") : "Invalid number");
            return;
        }
        for(int i = 0; i < itemnum; i++){
            VBox vBox = new VBox();
            Label price =  new Label();
            price.setText(rb.getString("price.text"));
            TextField priceField = new TextField();
            Label quantity =  new Label();
            quantity.setText(rb.getString("quantity.text"));
            TextField quantityField = new TextField();
            vBox.getChildren().addAll(price,priceField,quantity,quantityField);
            itemBox.getChildren().add(vBox);
            priceFields.add(priceField);
            quantityFields.add(quantityField);
        }
        validationField.setText("");

    }

    private boolean validateFields(){
        for (int i = 0; i < itemBox.getChildren().size(); i++) {
            String p = priceFields.get(i).getText();
            String q = quantityFields.get(i).getText();
            if (p == null || p.trim().isEmpty() || q == null || q.trim().isEmpty()) {
                validationField.setText(rb != null && rb.containsKey("emptyNumber.text")
                        ? rb.getString("emptyNumber.text") : "Fill all price and quantity fields");
                return false;
            }try {
                double price = Double.parseDouble(p.trim());
                int qty = Integer.parseInt(q.trim());
                if (price < 0 || qty <= 0) {
                    validationField.setText(rb != null && rb.containsKey("invalidNumber.text")
                            ? rb.getString("invalidNumber.text") : "Price must be >= 0 and quantity > 0");
                    return false;
                }
            } catch (NumberFormatException ex) {
                validationField.setText(rb != null && rb.containsKey("notNumber.text")
                        ? rb.getString("notNumber.text") : "Invalid number format in fields");
                return false;
            }
        }
        validationField.setText("");
        return true;
    }

    @FXML
    protected void calculateTotal(){
        if(!validateFields()){
            return;
        }


        for( int i = 0; i < itemBox.getChildren().size(); i++){
            double price = Double.parseDouble(priceFields.get(i).getText());
            int quantity = Integer.parseInt(quantityFields.get(i).getText());
            Item item = new Item(price,quantity);
            cart.addItem(item);
        }
        VBox vBox = new VBox();
        Text total = new Text();
        total.setText(rb.getString("total.text")+cart.getTotalCost());
        vBox.getChildren().clear();
        vBox.getChildren().addAll(total);
        totalBox.getChildren().add(vBox);
    }
}
