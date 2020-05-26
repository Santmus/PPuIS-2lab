package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import sample.Parsers.Controller;
import sample.Parsers.DOMparser;
import sample.Product.AddProductWindow;
import sample.View.MainWindowTable;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;


public class Main extends Application {

    private FlowPane root;
    private Pane pane;
    private Controller controller = new Controller();
    private MainWindowTable insertTableElements= new MainWindowTable(controller);
    private Stage stage;
    private SAXParser saxParser;
    private DOMparser domParser = new DOMparser();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        pane = new VBox();
        pane.getChildren().addAll(createMenu());

        root = new FlowPane(15,0);
        root.getChildren().addAll(pane,insertTableElements.getAligner());

        Scene scene = new Scene(root,960,735);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public MenuBar createMenu(){

       MenuBar menuBar = new MenuBar();

        Menu fileData = new javafx.scene.control.Menu("Файл");
        Menu editData = new javafx.scene.control.Menu("Редактировать");

        MenuItem openFile = new javafx.scene.control.MenuItem("Открыть файл");
        MenuItem  saveFile = new javafx.scene.control.MenuItem("Сохранить файл");
        MenuItem searchLine = new javafx.scene.control.MenuItem("Поиск строки");
        MenuItem deleteLine = new javafx.scene.control.MenuItem("Удалить");
        MenuItem getLine = new javafx.scene.control.MenuItem("Добавить");
        MenuItem exitProgram = new MenuItem("Выйти из программы");

        fileData.getItems().addAll(openFile, saveFile, exitProgram);
        editData.getItems().addAll(getLine, searchLine, deleteLine);

        menuBar.getMenus().addAll(fileData, editData);

        openFile.setAccelerator(KeyCombination.keyCombination("CTRL+X"));
        openFile.setOnAction(actionEvent ->{
            try {
            getTableDataFromFile();
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        });

        saveFile.setAccelerator(KeyCombination.keyCombination("CTRL+S"));
        saveFile.setOnAction(actionEvent ->{
            try {
            saveTableData();
        } catch (TransformerException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        });

        exitProgram.setAccelerator(KeyCombination.keyCombination("CTRL+R"));
        exitProgram.setOnAction(event -> controller.exit());

        getLine.setAccelerator(KeyCombination.keyCombination("CTRL+W"));
        getLine.setOnAction(event -> {
            new AddProductWindow(insertTableElements,stage, controller);
        });

        return menuBar;
    }

    public void getTableDataFromFile() throws IOException, SAXException, ParserConfigurationException{

        FileChooser openFileChooser = new FileChooser();
        openFileChooser.setTitle("Открытие файла");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("XML files (*.xml)","*.xml");
        openFileChooser.setInitialDirectory(new File(("C:/Users/Евгений/Desktop/Study/2 курс/2 курс(2 сем)")));
        openFileChooser.getExtensionFilters().add(extensionFilter);
        File file = openFileChooser.showOpenDialog(stage);
        if (file != null) {
            controller.insertTableData(file, saxParser);
            insertTableElements.updateTable();
            insertTableElements.setPageNumber(1);
        }
    }

    public void saveTableData() throws TransformerException, ParserConfigurationException
    {
        FileChooser saveFileChooser = new FileChooser();
        saveFileChooser.setTitle("Cохранение файла");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("XML files (*.xml)","*.xml");
        saveFileChooser.setInitialDirectory(new File(("C:/Users/Евгений/Desktop/Study/2 курс/2 курс(2 сем)")));
        saveFileChooser.getExtensionFilters().add(filter);
        File file = saveFileChooser.showSaveDialog(stage);
        if (file != null) {
            controller.saveTableData(file, domParser);
        }
    }



}
