package sample.View;

import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import sample.Parsers.Controller;
import sample.Parsers.DOMparser;
import sample.Product.AddProductWindow;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MenuEvent {

    private Desktop desktop = Desktop.getDesktop();

    private javafx.scene.control.MenuBar menuBar;
    private javafx.scene.control.Menu editData,fileData;
    private javafx.scene.control.MenuItem openFile,saveFile,getLine,searchLine,deleteLine,exitProgram;

    private Pane aligner;
    private SAXParser saxParser;
    private DOMparser domParser = new DOMparser();
    private Controller controller = new Controller();
    private MainWindowTable mainWindowTable;


    public MenuEvent (MainWindowTable mainWindowTable, Stage stage, Controller controller) {

        menuBar = new MenuBar();

        fileData = new javafx.scene.control.Menu("Файл");
        editData = new javafx.scene.control.Menu("Редактировать");

        openFile = new javafx.scene.control.MenuItem("Открыть файл");
        saveFile = new javafx.scene.control.MenuItem("Сохранить файл");
        searchLine = new javafx.scene.control.MenuItem("Поиск строки");
        deleteLine = new javafx.scene.control.MenuItem("Удалить");
        getLine = new javafx.scene.control.MenuItem("Добавить");
        exitProgram = new MenuItem("Выйти из программы");

        fileData.getItems().addAll(openFile, saveFile, exitProgram);
        editData.getItems().addAll(getLine, searchLine, deleteLine);

        menuBar.getMenus().addAll(fileData, editData);

        aligner = new VBox();
        aligner.getChildren().addAll(menuBar);

        exitProgram.setAccelerator(KeyCombination.keyCombination("CTRL+R"));
        exitProgram.setOnAction(event -> controller.exit());


        getLine.setAccelerator(KeyCombination.keyCombination("CTRL+W"));
        getLine.setOnAction(event -> {
            new AddProductWindow(mainWindowTable, stage, controller);
        });

        openFile.setAccelerator(KeyCombination.keyCombination("CTRL+X"));
        openFile.setOnAction(actionEvent ->{
            try {
            getTableDataFromFile(stage);
            } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        });

        saveFile.setAccelerator(KeyCombination.keyCombination("CTRL+S"));
        saveFile.setOnAction(actionEvent ->{try {
            saveTableData(stage);
        } catch (TransformerException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        });
    }

    public void getTableDataFromFile (Stage stage ) throws IOException, SAXException, ParserConfigurationException {
        FileChooser openFileChooser = new FileChooser();
        openFileChooser.setTitle("Открытие файла");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("XML files (*.xml)","*.xml");
        openFileChooser.setInitialDirectory(new File(("C:/Users/Евгений/Desktop/Study/2 курс/2 курс(2 сем)")));
        openFileChooser.getExtensionFilters().add(extensionFilter);
        File file = openFileChooser.showOpenDialog(stage);
        if (file != null) {
            controller.insertTableData(file, saxParser);
            mainWindowTable.updateTable();
            mainWindowTable.setPageNumber(1);
        }
    }

    public void saveTableData(Stage primaryStage) throws TransformerException, ParserConfigurationException {

        FileChooser saveFileChooser = new FileChooser();
        saveFileChooser.setTitle("Cохранение файла");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("XML files (*.xml)","*.xml");
        saveFileChooser.setInitialDirectory(new File(("C:/Users/Евгений/Desktop/Study/2 курс/2 курс(2 сем)")));
        saveFileChooser.getExtensionFilters().add(filter);
        File file = saveFileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            controller.saveTableData(file, domParser);
        }
    }

    public Pane getAligner() {
        return aligner;
    }
}
