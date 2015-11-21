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
import com.github.xxbeanxx.noticeseditor.bindings.Notices.Notice.Text;
import com.github.xxbeanxx.noticeseditor.bindings.Notices.Notice.Title;
import com.github.xxbeanxx.noticeseditor.controls.NoticeListCell;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class FXMLDocumentController {

    private static final String SOURCE_EDITOR_FXML = "/com/github/xxbeanxx/noticeseditor/FXMLSourceEditor.fxml";

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
    void deleteContextMenuItemAction(ActionEvent event) {
        new Alert(Alert.AlertType.ERROR, "Not yet implemented.").showAndWait();
    }
    
    @FXML
    void fileNewMenuItemAction(ActionEvent event) {
        new Alert(Alert.AlertType.ERROR, "Not yet implemented.").showAndWait();
    }

    @FXML
    void fileOpenMenuItemAction(ActionEvent event) {
        openExistingFile();
    }

	@FXML
    void fileCloseMenuItemAction(ActionEvent event) {
    	closeOpenFile();
    }

	@FXML
    void fileSaveMenuItemAction(ActionEvent event) {
        new Alert(Alert.AlertType.ERROR, "Not yet implemented.").showAndWait();
    }

    @FXML
    void fileSaveAsMenuItemAction(ActionEvent event) {
        new Alert(Alert.AlertType.ERROR, "Not yet implemented.").showAndWait();
    }

    @FXML
    void fileRevertMenuItemAction(ActionEvent event) throws JAXBException {
        new Alert(Alert.AlertType.ERROR, "Not yet implemented.").showAndWait();
    }

    @FXML
    void fileQuitMenuItemAction(ActionEvent event) {
        new Alert(Alert.AlertType.ERROR, "Not yet implemented.").showAndWait();
    }

    @FXML
    void helpAboutMenuItemAction(ActionEvent event) {
        new Alert(Alert.AlertType.ERROR, "Not yet implemented.").showAndWait();
    }

    @FXML
    void toolsOpenSampleNoticesMenuItemAction(ActionEvent event) {
    	openSampleFile();
    }

	@FXML
    void englishTextCleanSourceButtonAction(ActionEvent event) throws IOException {
        final String htmlText = englishTextHtmlEditor.getHtmlText();
		final String cleanHtmlText = cleanHtml(htmlText);
		englishTextHtmlEditor.setHtmlText(cleanHtmlText);
    }

    @FXML
    void frenchTextCleanSourceButtonAction(ActionEvent event) {
        final String htmlText = frenchTextHtmlEditor.getHtmlText();
		String cleanHtmlText = cleanHtml(htmlText);
		frenchTextHtmlEditor.setHtmlText(cleanHtmlText);
    }

    @FXML // TODO remove duplicate code
    void englishTextEditSourceButtonAction(ActionEvent event) {
        final Class<? extends FXMLDocumentController> clazz = getClass();
		final URL url = clazz.getResource(SOURCE_EDITOR_FXML);
		final FXMLLoader fxmlLoader = new FXMLLoader(url);
        final Stage stage = new Stage(StageStyle.UTILITY);
        
        try {
        	final Parent parent = fxmlLoader.load();
        	final Scene scene = new Scene(parent);
        	stage.setScene(scene);
        }
        catch (final IOException ioException) {
        	alertException(ioException);
        	return;
        }
        
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("View Source: english");

        final FXMLSourceEditorController fxmlSourceEditorController = fxmlLoader.<FXMLSourceEditorController>getController();
        final String htmlText = englishTextHtmlEditor.getHtmlText();
        
        String cleanHtmlText = cleanHtml(htmlText);
		fxmlSourceEditorController.setText(cleanHtmlText);

        stage.showAndWait();
        
        final FXMLSourceEditorController.ReturnValue returnValue = fxmlSourceEditorController.getReturnValue();
        if (returnValue == FXMLSourceEditorController.ReturnValue.OK) {
            final String text = fxmlSourceEditorController.getText();
            cleanHtmlText = cleanHtml(text);
			englishTextHtmlEditor.setHtmlText(cleanHtmlText);
        }
    }

    @FXML // TODO remove duplicate code
    void frenchTextEditSourceButtonAction(ActionEvent event) {
        final Class<? extends FXMLDocumentController> clazz = getClass();
		final URL url = clazz.getResource(SOURCE_EDITOR_FXML);
		final FXMLLoader fxmlLoader = new FXMLLoader(url);
        final Stage stage = new Stage(StageStyle.UTILITY);

        try {
        	final Parent parent = fxmlLoader.load();
        	final Scene scene = new Scene(parent);
        	stage.setScene(scene);
        }
        catch (final IOException ioException) {
        	alertException(ioException);
        	return;
        }
        
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("View Source: french");

        final FXMLSourceEditorController fxmlSourceEditorController = fxmlLoader.<FXMLSourceEditorController>getController();
        final String htmlText = frenchTextHtmlEditor.getHtmlText();
        
        String cleanHtmlText = cleanHtml(htmlText);
		fxmlSourceEditorController.setText(cleanHtmlText);

        stage.showAndWait();
        
        final FXMLSourceEditorController.ReturnValue returnValue = fxmlSourceEditorController.getReturnValue();
        if (returnValue == FXMLSourceEditorController.ReturnValue.OK) {
            final String text = fxmlSourceEditorController.getText();
            cleanHtmlText = cleanHtml(text);
			frenchTextHtmlEditor.setHtmlText(cleanHtmlText);
        }
    }

    @FXML
	void addNoticeButtonMouseClicked(ActionEvent event) {
	    new Alert(Alert.AlertType.ERROR, "Not yet implemented.").showAndWait();
	}

	private void alertException(Exception exception) {
		new Alert(Alert.AlertType.ERROR, exception.getMessage()).showAndWait();
	}

	private String cleanHtml(String html) {
        final Whitelist whitelist = Whitelist.basic();
		return Jsoup.clean(html, whitelist);
    }

    private void clearNoticesListView() {
    	this.leftPane.setDisable(true);
    	this.noticesListView.setItems(null);
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

	private void closeOpenFile() {
		openFile = null;
		
		clearNoticesListView();
		clearEditorPane();
		
		fileCloseMenuItem.setDisable(true);
		fileSaveMenuItem.setDisable(true);
		fileSaveAsMenuItem.setDisable(true);
		fileRevertMenuItem.setDisable(true);
	}

	private List<Notice> getNotices(InputStream inputStream) throws JAXBException {
	    final JAXBContext jaxbContext = JAXBContext.newInstance(Notices.class);
	    final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	    final Notices noticesRoot = (Notices) unmarshaller.unmarshal(inputStream);
	    return noticesRoot.getNotice();
	}

	private void loadNotices(InputStream inputStream) throws JAXBException {
	    final List<Notice> notices = getNotices(inputStream);
		final ObservableList<Notice> observableNotices = FXCollections.observableList(notices);
		noticesListView.setItems(observableNotices);
	    leftPane.setDisable(false);
	}

	private void openExistingFile() {
		final FileChooser fileChooser = new FileChooser();
	    final ObservableList<ExtensionFilter> observableExtensionFilters = fileChooser.getExtensionFilters();
		observableExtensionFilters.add(XML_EXTENSION_FILTER);
	
	    final Scene scene = root.getScene();
		final Window window = scene.getWindow();
		final File file = fileChooser.showOpenDialog(window);
	    
	    if (file != null) {
	        try {
				final FileInputStream inputStream = new FileInputStream(file);
				loadNotices(inputStream);
				this.openFile = file;
				fileCloseMenuItem.setDisable(false);
				fileSaveMenuItem.setDisable(false);
				fileSaveAsMenuItem.setDisable(false);
				fileRevertMenuItem.setDisable(false);
			}
	        catch (final FileNotFoundException | JAXBException exception) {
	        	alertException(exception);
			}
	    }
	}

	private void openSampleFile() {
		final InputStream inputStream = getClass().getResourceAsStream("/sample-notices.xml");
		
		try {
			loadNotices(inputStream);
			fileCloseMenuItem.setDisable(false);
			fileSaveMenuItem.setDisable(true);
			fileSaveAsMenuItem.setDisable(false);
			fileRevertMenuItem.setDisable(false);
		}
		catch (final JAXBException exception) {
	    	alertException(exception);
		}
	}

	private void updateEditorPane(Notice notice) {
        if (notice == null) {
            clearEditorPane();
        }
        else {
            rightPane.setDisable(false);

            final Title title = notice.getTitle();
			final String englishTitle = title.getEnglish();
			final String frenchTitle = title.getFrench();
			this.englishTitleTextField.setText(englishTitle);
			this.frenchTitleTextField.setText(frenchTitle);

            final Text text = notice.getText();
			final String englishText = text.getEnglish();
			final String frenchText = text.getFrench();
			this.englishTextHtmlEditor.setHtmlText(englishText);
			this.frenchTextHtmlEditor.setHtmlText(frenchText);

            final XMLGregorianCalendar effectiveDate = notice.getEffectiveDate();
            final LocalDate effectiveLocalDate = LocalDate.of(effectiveDate.getYear(), effectiveDate.getMonth(), effectiveDate.getDay());
			this.startDateDatePicker.setValue(effectiveLocalDate);

            final XMLGregorianCalendar expiryDate = notice.getExpiryDate();
            final LocalDate expiryLocalDate = LocalDate.of(expiryDate.getYear(), expiryDate.getMonth(), expiryDate.getDay());
			this.endDateDatePicker.setValue(expiryLocalDate);

            final XMLGregorianCalendar displayDate = notice.getDisplayDate();
            final LocalDate displayLocaleDate = LocalDate.of(displayDate.getYear(), displayDate.getMonth(), displayDate.getDay());
			this.displayedDateDatePicker.setValue(displayLocaleDate);

            final XMLGregorianCalendar dateCreated = notice.getDateCreated();
            final LocalDate createdLocalDate = LocalDate.of(dateCreated.getYear(), dateCreated.getMonth(), dateCreated.getDay());
			this.creationDateDatePicker.setValue(createdLocalDate);
        }
    }

}
