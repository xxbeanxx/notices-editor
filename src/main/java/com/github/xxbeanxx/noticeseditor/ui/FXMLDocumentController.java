package com.github.xxbeanxx.noticeseditor.ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.time.LocalDate;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Component;

import com.github.xxbeanxx.noticeseditor.bindings.CustomCharacterEscapeHandler;
import com.github.xxbeanxx.noticeseditor.bindings.Notices;
import com.github.xxbeanxx.noticeseditor.bindings.Notices.Notice;
import com.github.xxbeanxx.noticeseditor.bindings.Notices.Notice.Text;
import com.github.xxbeanxx.noticeseditor.bindings.Notices.Notice.Title;
import com.github.xxbeanxx.noticeseditor.bindings.NoticesHeader;
import com.github.xxbeanxx.noticeseditor.controls.NoticeListCell;
import com.sun.xml.bind.marshaller.CharacterEscapeHandler;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 * @author Greg Baker
 */
@Component
public class FXMLDocumentController extends AbstractFXMLController {

  private static final FileChooser.ExtensionFilter XML_EXTENSION_FILTER =
      new FileChooser.ExtensionFilter("Notices files (*.xml)", "*.xml");

  @FXML
  Node root;

  ////////////////////////////////////////////////////////////////////////////
  // File menu items
  ////////////////////////////////////////////////////////////////////////////

  @FXML
  MenuItem fileNewMenuItem;
  @FXML
  MenuItem fileOpenMenuItem;
  @FXML
  MenuItem fileSaveMenuItem;
  @FXML
  MenuItem fileSaveAsMenuItem;
  @FXML
  MenuItem fileCloseMenuItem;
  @FXML
  MenuItem fileRevertMenuItem;
  @FXML
  MenuItem fileQuitMenuItem;

  ////////////////////////////////////////////////////////////////////////////
  // Tools menu items
  ////////////////////////////////////////////////////////////////////////////

  @FXML
  MenuItem toolsOpenSampleNoticesMenuItem;
  @FXML
  MenuItem toolsPreviewXmlMenuItem;

  ////////////////////////////////////////////////////////////////////////////
  // Help menu items
  ////////////////////////////////////////////////////////////////////////////

  @FXML
  MenuItem helpAboutMenuItem;

  ////////////////////////////////////////////////////////////////////////////
  // Left pane items
  ////////////////////////////////////////////////////////////////////////////

  @FXML
  AnchorPane leftPane;
  @FXML
  ListView<Notice> noticesListView;
  @FXML
  Button addNoticeButton;

  ////////////////////////////////////////////////////////////////////////////
  // Right pane items
  ////////////////////////////////////////////////////////////////////////////

  @FXML
  VBox rightPane;
  @FXML
  TextField englishTitleTextField;
  @FXML
  TextField frenchTitleTextField;
  @FXML
  HTMLEditor englishTextHtmlEditor;
  @FXML
  HTMLEditor frenchTextHtmlEditor;
  @FXML
  DatePicker startDateDatePicker;
  @FXML
  DatePicker endDateDatePicker;
  @FXML
  DatePicker creationDateDatePicker;
  @FXML
  DatePicker displayedDateDatePicker;
  @FXML
  Button frenchTextEditSourceButton;
  @FXML
  Button englishTextEditSourceButton;
  @FXML
  Button englishTextCleanSourceButton;
  @FXML
  Button frenchTextCleanSourceButton;

  ////////////////////////////////////////////////////////////////////////////
  // Other class members
  ////////////////////////////////////////////////////////////////////////////

  private File currentlyOpenFile;

  private Notices currentlyLoadedNotices;

  private Notice currentlySelectedNotice;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.root.setOnDragOver(event -> {
      final Dragboard dragboard = event.getDragboard();
      if (dragboard.hasFiles() == true) {
        event.acceptTransferModes(TransferMode.COPY);
      }
      else {
        event.consume();
      }
    });
    
    this.root.setOnDragDropped(event -> {
      final Dragboard dragboard = event.getDragboard();

      if (dragboard.hasFiles() == true) {
        for (File file : dragboard.getFiles()) {
          openFile(file);
        }
      }

      event.setDropCompleted(true);
      event.consume();
    });
    
    this.noticesListView.setCellFactory((ListView<Notice> listView) -> {
      return new NoticeListCell();
    });

    this.noticesListView.getSelectionModel().selectedItemProperty().addListener(
        (ObservableValue<? extends Notice> observableValue, Notice oldNotice, Notice newNotice) -> {
          this.currentlySelectedNotice = newNotice;
          updateNoticeList(oldNotice);
          updateEditorPane(newNotice);
        });

    this.englishTitleTextField.addEventHandler(Event.ANY, event -> this.currentlySelectedNotice
        .getTitle().setEnglish(this.englishTitleTextField.getText()));
    this.frenchTitleTextField.addEventHandler(Event.ANY, event -> this.currentlySelectedNotice
        .getTitle().setFrench(this.frenchTitleTextField.getText()));
    this.englishTextHtmlEditor.addEventHandler(Event.ANY, event -> this.currentlySelectedNotice
        .getText().setEnglish(cleanHtml(this.englishTextHtmlEditor.getHtmlText())));
    this.frenchTextHtmlEditor.addEventHandler(Event.ANY, event -> this.currentlySelectedNotice
        .getText().setFrench(cleanHtml(this.frenchTextHtmlEditor.getHtmlText())));

    this.startDateDatePicker.addEventFilter(Event.ANY, event -> {
      try {
        this.currentlySelectedNotice
            .setEffectiveDate(localDateToXmlGregorianCalendar(this.startDateDatePicker.getValue()));
      } catch (final DatatypeConfigurationException datatypeConfigurationException) {
        handleException(datatypeConfigurationException);
      }
    });

    this.endDateDatePicker.addEventFilter(Event.ANY, event -> {
      try {
        this.currentlySelectedNotice
            .setExpiryDate(localDateToXmlGregorianCalendar(this.endDateDatePicker.getValue()));
      } catch (final DatatypeConfigurationException datatypeConfigurationException) {
        handleException(datatypeConfigurationException);
      }
    });

    this.displayedDateDatePicker.addEventFilter(Event.ANY, event -> {
      try {
        this.currentlySelectedNotice.setDisplayDate(
            localDateToXmlGregorianCalendar(this.displayedDateDatePicker.getValue()));
      } catch (final DatatypeConfigurationException datatypeConfigurationException) {
        handleException(datatypeConfigurationException);
      }
    });
  }

  @FXML
  public void deleteContextMenuItemAction(ActionEvent event) {
    new Alert(Alert.AlertType.ERROR, "Not yet implemented.").showAndWait();
  }

  ////////////////////////////////////////////////////////////////////////////
  // File menu action handlers
  ////////////////////////////////////////////////////////////////////////////

  @FXML
  public void fileNewMenuItemAction(ActionEvent event) {
    new Alert(Alert.AlertType.ERROR, "Not yet implemented.").showAndWait();
  }

  @FXML
  public void fileOpenMenuItemAction(ActionEvent event) {
    openExistingFile();
  }

  @FXML
  public void fileSaveMenuItemAction(ActionEvent event) {
    try {
      saveToFile(this.currentlyOpenFile, this.currentlyLoadedNotices);
    } catch (final UnsupportedEncodingException | FileNotFoundException | JAXBException exception) {
      handleException(exception);
    }
  }

  @FXML
  public void fileSaveAsMenuItemAction(ActionEvent event) {
    final FileChooser fileChooser = new FileChooser();
    final ObservableList<ExtensionFilter> observableExtensionFilters =
        fileChooser.getExtensionFilters();
    observableExtensionFilters.add(FXMLDocumentController.XML_EXTENSION_FILTER);

    final Scene scene = this.root.getScene();
    final Window window = scene.getWindow();
    final File file = fileChooser.showSaveDialog(window);

    if (file != null) {
      try {
        saveToFile(file, this.currentlyLoadedNotices);
      } catch (final UnsupportedEncodingException | FileNotFoundException
          | JAXBException exception) {
        handleException(exception);
      }
    }
  }

  @FXML
  public void fileCloseMenuItemAction(ActionEvent event) {
    closeOpenFile();
  }

  @FXML
  public void fileRevertMenuItemAction(ActionEvent event) throws JAXBException {
    new Alert(Alert.AlertType.ERROR, "Not yet implemented.").showAndWait();
  }

  @FXML
  public void fileQuitMenuItemAction(ActionEvent event) {
    new Alert(Alert.AlertType.ERROR, "Not yet implemented.").showAndWait();
  }

  ////////////////////////////////////////////////////////////////////////////
  // Tools menu action handlers
  ////////////////////////////////////////////////////////////////////////////

  @FXML
  public void toolsOpenSampleNoticesMenuItemAction(ActionEvent event) {
    openSampleFile();
  }

  ////////////////////////////////////////////////////////////////////////////
  // About menu action handlers
  ////////////////////////////////////////////////////////////////////////////

  @FXML
  public void toolsPreviewXmlMenuItemAction(ActionEvent event) throws IOException, JAXBException {
    final FXMLLoader fxmlLoader = getSourceEditorLoader();
    final Parent parent = fxmlLoader.load();
    final Stage stage = getSourceEditorStage(parent, "Preview XML...");
    final FXMLSourceEditorController fxmlSourceEditorController = fxmlLoader.getController();
    final String text = marshallNotices(this.currentlyLoadedNotices);
    fxmlSourceEditorController.setText(text);
    stage.showAndWait();
  }

  @FXML
  public void helpAboutMenuItemAction(ActionEvent event) {
    new Alert(Alert.AlertType.ERROR, "Not yet implemented.").showAndWait();
  }

  @FXML
  public void englishTextCleanSourceButtonAction(ActionEvent event) throws IOException {
    final String htmlText = this.englishTextHtmlEditor.getHtmlText();
    final String cleanHtmlText = cleanHtml(htmlText);
    this.englishTextHtmlEditor.setHtmlText(cleanHtmlText);
  }

  @FXML
  public void englishTextEditSourceButtonAction() {
    try {
      showEditSourceWindow("View Source: english", this.englishTextHtmlEditor);
    } catch (final IOException ioException) {
      handleException(ioException);
    }
  }

  @FXML
  public void frenchTextCleanSourceButtonAction() {
    final String htmlText = this.frenchTextHtmlEditor.getHtmlText();
    final String cleanHtmlText = cleanHtml(htmlText);
    this.frenchTextHtmlEditor.setHtmlText(cleanHtmlText);
  }

  @FXML
  public void frenchTextEditSourceButtonAction() {
    try {
      showEditSourceWindow("View Source: french", this.frenchTextHtmlEditor);
    } catch (final IOException ioException) {
      handleException(ioException);
    }
  }

  @FXML
  public void addNoticeButtonMouseClicked(ActionEvent event) {
    new Alert(Alert.AlertType.ERROR, "Not yet implemented.").showAndWait();
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
    this.currentlyOpenFile = null;

    clearNoticesListView();
    clearEditorPane();

    this.fileCloseMenuItem.setDisable(true);
    this.fileSaveMenuItem.setDisable(true);
    this.fileSaveAsMenuItem.setDisable(true);
    this.fileRevertMenuItem.setDisable(true);
    this.toolsPreviewXmlMenuItem.setDisable(true);
  }

  private FXMLLoader getSourceEditorLoader() {
    final Class<? extends FXMLDocumentController> clazz = getClass();
    final URL url = clazz.getResource(FXMLSourceEditorController.FXML_LOCATION);
    return new FXMLLoader(url);
  }

  private Stage getSourceEditorStage(Parent parent, String title) throws IOException {
    final Stage stage = new Stage(StageStyle.UTILITY);
    final Scene scene = new Scene(parent);

    stage.setScene(scene);
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setTitle(title);

    return stage;
  }

  private void handleException(Exception exception) {
    new Alert(Alert.AlertType.ERROR, exception.getMessage()).showAndWait();
  }

  private void loadNotices(List<Notice> notices) {
    final ObservableList<Notice> observableNotices = FXCollections.observableList(notices);
    this.noticesListView.setItems(observableNotices);
    this.leftPane.setDisable(false);
  }

  private XMLGregorianCalendar localDateToXmlGregorianCalendar(LocalDate localDate)
      throws DatatypeConfigurationException {
    return DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(
        localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth()));
  }

  private String marshallNotices(Notices notices)
      throws JAXBException, UnsupportedEncodingException {
    final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    final JAXBContext jaxbContext = JAXBContext.newInstance(Notices.class);
    final Marshaller marshaller = jaxbContext.createMarshaller();
    marshaller.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");
    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
    marshaller.setProperty(CharacterEscapeHandler.class.getName(),
        new CustomCharacterEscapeHandler());
    marshaller.marshal(notices, byteArrayOutputStream);
    return NoticesHeader.HEADER + byteArrayOutputStream.toString("ISO-8859-1");
  }

  private void openExistingFile() {
    final FileChooser fileChooser = new FileChooser();
    final ObservableList<ExtensionFilter> observableExtensionFilters =
        fileChooser.getExtensionFilters();
    observableExtensionFilters.add(FXMLDocumentController.XML_EXTENSION_FILTER);

    final Scene scene = this.root.getScene();
    final Window window = scene.getWindow();
    final File file = fileChooser.showOpenDialog(window);

    if (file != null) {
      openFile(file);
    }
  }

  private void openFile(final File file) {
    try {
      final FileInputStream inputStream = new FileInputStream(file);
      this.currentlyLoadedNotices = unmarshallNotices(inputStream);
      loadNotices(this.currentlyLoadedNotices.getNotice());

      this.fileCloseMenuItem.setDisable(false);
      this.fileSaveMenuItem.setDisable(false);
      this.fileSaveAsMenuItem.setDisable(false);
      this.fileRevertMenuItem.setDisable(false);
      this.toolsPreviewXmlMenuItem.setDisable(false);

      this.currentlyOpenFile = file;
    } catch (final FileNotFoundException | JAXBException exception) {
      handleException(exception);
    }
  }

  private void openSampleFile() {
    final InputStream inputStream = getClass().getResourceAsStream("/sample-notices.xml");

    try {
      this.currentlyLoadedNotices = unmarshallNotices(inputStream);
      loadNotices(this.currentlyLoadedNotices.getNotice());

      this.fileCloseMenuItem.setDisable(false);
      this.fileSaveMenuItem.setDisable(true);
      this.fileSaveAsMenuItem.setDisable(false);
      this.fileRevertMenuItem.setDisable(false);
      this.toolsPreviewXmlMenuItem.setDisable(false);

      this.currentlyOpenFile = null;
    } catch (final JAXBException exception) {
      handleException(exception);
    }
  }

  private void saveToFile(File file, Notices notices)
      throws JAXBException, UnsupportedEncodingException, FileNotFoundException {
    final FileOutputStream fileOutputStream = new FileOutputStream(file, false);
    final String text = marshallNotices(notices);
    try (final PrintWriter printWriter = new PrintWriter(fileOutputStream)) {
      printWriter.println(text);
    }
  }

  private void showEditSourceWindow(String title, HTMLEditor htmlEditor) throws IOException {
    super.springController.load(FXMLSourceEditorController.FXML_LOCATION);
//    final FXMLLoader fxmlLoader = getSourceEditorLoader();
//    final Parent parent = fxmlLoader.load();
//    final Stage stage = getSourceEditorStage(parent, title);
//    final FXMLSourceEditorController fxmlSourceEditorController = fxmlLoader.getController();
//    final String htmlText = htmlEditor.getHtmlText();
//    String cleanHtmlText = cleanHtml(htmlText);
//    fxmlSourceEditorController.setText(cleanHtmlText);
//
//    stage.showAndWait();
//
//    final FXMLSourceEditorController.ReturnValue returnValue =
//        fxmlSourceEditorController.getReturnValue();
//    if (returnValue == FXMLSourceEditorController.ReturnValue.OK) {
//      final String text = fxmlSourceEditorController.getText();
//      cleanHtmlText = cleanHtml(text);
//      htmlEditor.setHtmlText(cleanHtmlText);
//    }
  }

  private Notices unmarshallNotices(InputStream inputStream) throws JAXBException {
    final JAXBContext jaxbContext = JAXBContext.newInstance(Notices.class);
    final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
    final Notices noticesRoot = (Notices) unmarshaller.unmarshal(inputStream);
    return noticesRoot;
  }

  private void updateEditorPane(Notice notice) {
    if (notice == null) {
      clearEditorPane();
    } else {
      this.rightPane.setDisable(false);

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
      final LocalDate effectiveLocalDate =
          LocalDate.of(effectiveDate.getYear(), effectiveDate.getMonth(), effectiveDate.getDay());
      this.startDateDatePicker.setValue(effectiveLocalDate);

      final XMLGregorianCalendar expiryDate = notice.getExpiryDate();
      final LocalDate expiryLocalDate =
          LocalDate.of(expiryDate.getYear(), expiryDate.getMonth(), expiryDate.getDay());
      this.endDateDatePicker.setValue(expiryLocalDate);

      final XMLGregorianCalendar displayDate = notice.getDisplayDate();
      final LocalDate displayLocaleDate =
          LocalDate.of(displayDate.getYear(), displayDate.getMonth(), displayDate.getDay());
      this.displayedDateDatePicker.setValue(displayLocaleDate);

      final XMLGregorianCalendar dateCreated = notice.getDateCreated();
      final LocalDate createdLocalDate =
          LocalDate.of(dateCreated.getYear(), dateCreated.getMonth(), dateCreated.getDay());
      this.creationDateDatePicker.setValue(createdLocalDate);
    }
  }

  private void updateNoticeList(Notice oldNotice) {
    // TODO Auto-generated method stub
  }

}
