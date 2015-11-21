package com.github.xxbeanxx.noticeseditor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.github.xxbeanxx.noticeseditor.bindings.Notices;
import com.github.xxbeanxx.noticeseditor.bindings.Notices.Notice;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXMLDocumentController {

    private static final FileChooser.ExtensionFilter XML_EXTENSION_FILTER = new FileChooser.ExtensionFilter("Notices files (*.xml)", "*.xml");
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Node root;
    
    @FXML
    private MenuItem fileOpenMenuItem;

    @FXML
    private Button addNoticeButton;

    @FXML
    private MenuItem fileNewMenuItem;

    @FXML
    private MenuItem fileQuitMenuItem;

    @FXML
    private DatePicker displayedDateDatePicker;

    @FXML
    private MenuItem fileSaveAsMenuItem;

    @FXML
    private TextField frenchTitleTextField;

    @FXML
    private HTMLEditor frenchTextHtmlEditor;

    @FXML
    private DatePicker endDateDatePicker;

    @FXML
    private DatePicker creationDateDatePicker;

    @FXML
    private Button frenchTextEditSourceButton;

    @FXML
    private ListView<Notice> noticesListView;

    @FXML
    private MenuItem fileSaveMenuItem;

    @FXML
    private HTMLEditor englishTextHtmlEditor;

    @FXML
    private TextField englishTitleTextField;

    @FXML
    private MenuItem fileRevertMenuItem;

    @FXML
    private MenuItem helpAboutMenuItem;

    @FXML
    private MenuItem toolsOpenSampleNoticesMenuItem;
    
    @FXML
    private MenuItem fileCloseMenuItem;

    @FXML
    private Button englishTextEditSourceButton;

    @FXML
    private Button englishTextCleanSourceButton;
    
    @FXML
    private Button frenchTextCleanSourceButton;
    
    @FXML
    private DatePicker startDateDatePicker;

    @FXML
    private AnchorPane leftPane;
    
    @FXML
    private VBox rightPane;

    private File openFile;
    
    private boolean isDitry;
    
    @FXML
    void initialize() {
        this.noticesListView.setCellFactory((ListView<Notice> listView) -> {
            return new NoticeListCell();
        });
        
        this.noticesListView.getSelectionModel().selectedItemProperty().addListener(
            (ObservableValue<? extends Notice> observableValue, Notice oldNotice, Notice newNotice) -> {
                updateEditorPane(newNotice);
            }
        );
    }

    @FXML
    void fileNewMenuItemAction(ActionEvent event) {
        final Alert alert = new Alert(Alert.AlertType.ERROR, "Not yet implemented.");
        alert.showAndWait();
    }

    @FXML
    void fileOpenMenuItemAction(ActionEvent event) throws JAXBException, FileNotFoundException {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(XML_EXTENSION_FILTER);

        final File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        
        if (file != null) {
            this.openFile = file;
            final List<Notice> notices = loadNotices(new FileInputStream(file));
            
            leftPane.setDisable(false);
            noticesListView.setItems(FXCollections.observableList(notices));
        }
    }

    @FXML
    void fileCloseMenuItemAction(ActionEvent event) {
        final Alert alert = new Alert(Alert.AlertType.ERROR, "Not yet implemented.");
        alert.showAndWait();
    }

    @FXML
    void fileSaveMenuItemAction(ActionEvent event) {
        final Alert alert = new Alert(Alert.AlertType.ERROR, "Not yet implemented.");
        alert.showAndWait();
    }

    @FXML
    void fileSaveAsMenuItemAction(ActionEvent event) {
        final Alert alert = new Alert(Alert.AlertType.ERROR, "Not yet implemented.");
        alert.showAndWait();
    }

    @FXML
    void fileRevertMenuItemAction(ActionEvent event) throws JAXBException {
        final Alert alert = new Alert(Alert.AlertType.ERROR, "Not yet implemented.");
        alert.showAndWait();
    }

    @FXML
    void fileQuitMenuItemAction(ActionEvent event) {
        final Alert alert = new Alert(Alert.AlertType.ERROR, "Not yet implemented.");
        alert.showAndWait();
    }

    @FXML
    void helpAboutMenuItemAction(ActionEvent event) {
        final Alert alert = new Alert(Alert.AlertType.ERROR, "Not yet implemented.");
        alert.showAndWait();
    }

    @FXML
    void toolsOpenSampleNoticesMenuItemAction(ActionEvent event) throws JAXBException {
    	final InputStream inputStream = getClass().getResourceAsStream("/sample-notices.xml");
    	final List<Notice> notices = loadNotices(inputStream);
    	
        leftPane.setDisable(false);
        noticesListView.setItems(FXCollections.observableList(notices));
    }
    
    @FXML
    void addNoticeButtonMouseClicked(ActionEvent event) {
        final Alert alert = new Alert(Alert.AlertType.ERROR, "Not yet implemented.");
        alert.showAndWait();
    }

    @FXML
    void englishTextCleanSourceButtonAction(ActionEvent event) throws IOException {
        englishTextHtmlEditor.setHtmlText(Jsoup.clean(englishTextHtmlEditor.getHtmlText(), Whitelist.basic()));
    }

    @FXML
    void frenchTextCleanSourceButtonAction(ActionEvent event) throws IOException {
        frenchTextHtmlEditor.setHtmlText(Jsoup.clean(frenchTextHtmlEditor.getHtmlText(), Whitelist.basic()));
    }

    @FXML
    void englishTextEditSourceButtonAction(ActionEvent event) throws IOException {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/github/xxbeanxx/noticeseditor/FXMLSourceEditor.fxml"));
        final Stage stage = new Stage(StageStyle.UTILITY);
        
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.setTitle("View Source: english");

        final FXMLSourceEditorController fxmlSourceEditorController = fxmlLoader.<FXMLSourceEditorController>getController();
        final String htmlText = englishTextHtmlEditor.getHtmlText();
        fxmlSourceEditorController.setText(cleanHtml(htmlText));

        stage.showAndWait();
        
        final FXMLSourceEditorController.ReturnValue returnValue = fxmlSourceEditorController.getReturnValue();
        if (returnValue == FXMLSourceEditorController.ReturnValue.OK) {
            final String text = fxmlSourceEditorController.getText();
            englishTextHtmlEditor.setHtmlText(cleanHtml(text));
        }
    }

    @FXML
    void frenchTextEditSourceButtonAction(ActionEvent event) throws IOException {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/github/xxbeanxx/noticeseditor/FXMLSourceEditor.fxml"));
        final Stage stage = new Stage(StageStyle.UTILITY);
        
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.setTitle("View Source: french");

        final FXMLSourceEditorController fxmlSourceEditorController = fxmlLoader.<FXMLSourceEditorController>getController();
        final String htmlText = frenchTextHtmlEditor.getHtmlText();
        fxmlSourceEditorController.setText(cleanHtml(htmlText));

        stage.showAndWait();
        
        final FXMLSourceEditorController.ReturnValue returnValue = fxmlSourceEditorController.getReturnValue();
        if (returnValue == FXMLSourceEditorController.ReturnValue.OK) {
            final String text = fxmlSourceEditorController.getText();
            frenchTextHtmlEditor.setHtmlText(cleanHtml(text));
        }
    }

    private List<Notice> loadNotices(InputStream inputStream) throws JAXBException {
        final JAXBContext jaxbContext = JAXBContext.newInstance(Notices.class);
        final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        final Notices noticesRoot = (Notices) unmarshaller.unmarshal(inputStream);
        return noticesRoot.getNotice();
    }
    
    private String cleanHtml(String html) {
        return Jsoup.clean(html, Whitelist.basic());
    }

    private void updateEditorPane(Notice notice) {
        if (notice == null) {
            clearEditorPane();
        }
        else {
            rightPane.setDisable(false);

            this.englishTitleTextField.setText(notice.getTitle().getEnglish());
            this.frenchTitleTextField.setText(notice.getTitle().getFrench());

            this.englishTextHtmlEditor.setHtmlText(notice.getText().getEnglish());
            this.frenchTextHtmlEditor.setHtmlText(notice.getText().getFrench());

            final XMLGregorianCalendar effectiveDate = notice.getEffectiveDate();
            this.startDateDatePicker.setValue(LocalDate.of(effectiveDate.getYear(), effectiveDate.getMonth(), effectiveDate.getDay()));

            final XMLGregorianCalendar expiryDate = notice.getExpiryDate();
            this.endDateDatePicker.setValue(LocalDate.of(expiryDate.getYear(), expiryDate.getMonth(), expiryDate.getDay()));

            final XMLGregorianCalendar displayDate = notice.getDisplayDate();
            this.displayedDateDatePicker.setValue(LocalDate.of(displayDate.getYear(), displayDate.getMonth(), displayDate.getDay()));

            final XMLGregorianCalendar dateCreated = notice.getDateCreated();
            this.creationDateDatePicker.setValue(LocalDate.of(dateCreated.getYear(), dateCreated.getMonth(), dateCreated.getDay()));
        }
    }

    private void clearEditorPane() {
        this.rightPane.setDisable(true);
        this.englishTitleTextField.setText(null);
        this.frenchTitleTextField.setText(null);
        this.englishTextHtmlEditor.setHtmlText("");
        this.frenchTextHtmlEditor.setHtmlText("");
        this.startDateDatePicker.setValue(null);
        this.endDateDatePicker.setValue(null);
        this.displayedDateDatePicker.setValue(null);
        this.creationDateDatePicker.setValue(null);
    }
    
    private static class NoticeListCell extends ListCell<Notice> {

        @Override
        protected void updateItem(Notice notice, boolean empty) {
            super.updateItem(notice, empty);
            if (notice != null) {
                super.setText(notice.getDateCreated().toString());

                final WebView webView = new WebView();
                webView.getEngine().loadContent(notice.getText().getEnglish());

                final Tooltip tooltip = new Tooltip();
                tooltip.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                tooltip.setGraphic(webView);

                super.setTooltip(tooltip);
            }
        }
        
    }
	
}
