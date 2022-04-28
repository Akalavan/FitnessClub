package sample.controllers;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import sample.controllers.chart.ChartsController;
import sample.controllers.chart.ChoiceChartController;
import sample.controllers.edits.EditAddingForm;
import sample.controllers.edits.EditAddingFormStock;
import sample.controllers.edits.EditAddingTrainerForm;
import sample.controllers.edits.EditPersonalInfo;
import sample.email.EmailSession;
import sample.interfaces.impls.CollectionControlScheduleGroup;
import sample.interfaces.impls.CollectionOperatingHall;
import sample.interfaces.impls.CollectionPersonBase;
import sample.objects.Hall;
import sample.objects.Person;
import sample.objects.Trainer;
import sample.start.Main;

import javax.mail.MessagingException;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static sample.interfaces.impls.CollectionPersonBase.getPersonList;

public class Controller {
    @FXML
    public MenuItem openTab1, openTab2, openTab3, tabAllStock, tabStock, tabTrainers, itemGroup;
    @FXML
    public Tab tab, Hall, subscription, trainer, stock, allStock, group;

    CollectionPersonBase personBase = new CollectionPersonBase();

    CollectionOperatingHall collectionOperatingHall = new CollectionOperatingHall();

    Hall hall;

    public AnchorPane firstTabPage;

    @FXML
    public DatePicker dateStart;

    @FXML
    public DatePicker dateEnd;

    @FXML
    public Button btnAdd;

    @FXML
    public Button btnTrainer;

    @FXML
    public Button btnUpdate;

    @FXML
    public Button btnSportRep;

    @FXML
    public Button btnSendEmail;

    @FXML
    public Button btnBuildStock;

    @FXML
    public Button btnAddStock;

    @FXML
    public Button btnDelete;

    @FXML
    public Button btnScreech;

    @FXML
    public TextField textScreech;

    @FXML
    public Tab tab3;

    @FXML
    public TabPane tabPane;

    @FXML
    public TableView<Person> tableViewFirst;

    private EditAddingForm editAddingController;

    private EditAddingTrainerForm editAddingTrainerController;

    private EditPersonalInfo editAddingPersonController;

    private EditAddingFormStock editAddingFormStock;

    private ChartsController chartsController;

    private ChoiceChartController choiceChartController;

    private Parent fxmlEditTrainer;

    private Parent fxmlEditPerson;

    private Parent fxmlEditStock;

    private Parent fxmlSportRep;

    private Parent fxmlChoiceRep;

    private Parent fxmlEdit;

    private Stage editDialogStageTrainer;

    private Stage editDialogStage;

    private Stage editDialogStagePerson;

    private Stage editDialogStageStock;

    private Stage editReportStageSport;

    private Stage editReportStageChoice;

    private Stage mainStage;

    private FXMLLoader fxmlLoader = new FXMLLoader();

    private ThirdTabController thirdTabController = new ThirdTabController();

    TableScheduleController chg;

    Parent fxmlSh;

    static Stage schg;

    TableHistoryController chp;

    Parent fxmlCP;

    static Stage scHP;


    @FXML
    private void initialize() {
        // Подумать как лучше получать данные из таблиц
        try {
            mainStage = Main.getMain();
            fxmlLoader.setLocation(getClass().getResource("../fxml/addingForm.fxml"));
            fxmlEdit = fxmlLoader.load();
            editAddingController = fxmlLoader.getController();
            fxmlLoader = new FXMLLoader();

            fxmlLoader.setLocation(getClass().getResource("../fxml/addingFormTrainers.fxml"));
            fxmlEditTrainer = fxmlLoader.load();
            editAddingTrainerController = fxmlLoader.getController();
            fxmlLoader = new FXMLLoader();

            fxmlLoader.setLocation(getClass().getResource("../fxml/person.fxml"));
            fxmlEditPerson = fxmlLoader.load();
            editAddingPersonController = fxmlLoader.getController();
            fxmlLoader = new FXMLLoader();

            fxmlLoader.setLocation(getClass().getResource("../fxml/addFormStock.fxml"));
            fxmlEditStock = fxmlLoader.load();
            editAddingFormStock = fxmlLoader.getController();
            fxmlLoader = new FXMLLoader();

            fxmlLoader.setLocation(getClass().getResource("../fxml/choiceReport.fxml"));
            fxmlChoiceRep = fxmlLoader.load();
            choiceChartController = fxmlLoader.getController();
            fxmlLoader = new FXMLLoader();

            fxmlLoader.setLocation(getClass().getResource("../fxml/tabScheduleGroup.fxml"));
            fxmlSh = fxmlLoader.load();
            chg = fxmlLoader.getController();
            fxmlLoader = new FXMLLoader();

            fxmlLoader.setLocation(getClass().getResource("../fxml/purchaseHistory.fxml"));
            fxmlCP = fxmlLoader.load();
            chp = fxmlLoader.getController();


        } catch (IOException e) {
            e.printStackTrace();
        }


        //  tab3 = new Tab("Tab3");
        //   tab3.setContent(thirdTabController.getFxmlEdit());
        //  personBase = FirstTabController.getPersonBase();
        // System.out.println(personBase.getPersonList().size());
        initListeners();
        initLoader();
        editDialogStage = reg(editDialogStage, fxmlEdit, "Регистрация клиента", 300, 101);
        editDialogStageTrainer = reg(editDialogStageTrainer, fxmlEditTrainer, "Регистрация тренира", 517, 106);
        editDialogStagePerson = reg(editDialogStagePerson, fxmlEditPerson, "Персональные данные", 300, 101);
        editDialogStageStock = reg(editDialogStageStock, fxmlEditStock, "Добавить акцию", 441, 225);
        editReportStageChoice = reg(editReportStageChoice, fxmlChoiceRep, "Отчёт", 300, 101);

        schg = reg(schg, fxmlSh, "Расписание группы", 441, 225);
        scHP = reg(scHP, fxmlCP, "История покупок", 441, 225);

        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };

        Label label = new Label();

        // Create ContextMenu
        ContextMenu contextMenu = new ContextMenu();

        MenuItem item1 = new MenuItem("История покупок");

        // Add MenuItem to ContextMenu
        contextMenu.getItems().add(item1);

        // When user right-click on Circle
        FirstTabController.getTableView().setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

            @Override
            public void handle(ContextMenuEvent event) {

                contextMenu.show(FirstTabController.getTableView(), event.getScreenX(), event.getScreenY());
            }
        });

        contextMenu.setOnHidden(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event) {
                chp.setDate(FirstTabController.getSelectedItem().getTik_id());
                showDialog(scHP, fxmlCP, "История покупок", 300, 101);
            }
        });
//        dateStart.setConverter(converter);
//        dateStart.setPromptText("yyyy-MM-dd");
//        dateEnd.setConverter(converter);
//        dateEnd.setPromptText("yyyy-MM-dd");

//        FilteredList<Person> filteredList = new FilteredList<>(getPersonList(), p -> true);
//        filteredList.predicateProperty().addListener((observable, oldValue, newValue) -> {
//            filteredList.setPredicate(person -> {if (newValue == null || newValue.isEmpty()) {
//                return true;
//            }
//
//                // Compare first name and last name of every person with filter text.
//                String lowerCaseFilter = newValue.toLowerCase();
//
//                if (person.getName().toLowerCase().contains(lowerCaseFilter)) {
//                    return true; // Filter matches first name.
//                } else if (person.getSurname().toLowerCase().contains(lowerCaseFilter)) {
//                    return true; // Filter matches last name.
//                }
//                return false; // Does not match.
//            });
//        });
//
//        // 3. Wrap the FilteredList in a SortedList.
//        SortedList<Person> sortedData = new SortedList<>(filteredList);
//
//        // 4. Bind the SortedList comparator to the TableView comparator.
//        sortedData.comparatorProperty().bind(FirstTabController.getTableView().comparatorProperty());
//
//        // 5. Add sorted (and filtered) data to the table.
//        FirstTabController.getTableView().setItems(sortedData);
    }

    private void initLoader() {
        collectionOperatingHall.setHall();
        collectionOperatingHall.setMoneyProfits(ThirdTabController.getHallBaseSecondTable().getMoneyProfits());
    }


    private void initListeners() {
        FirstTabController.getTableView().setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                editAddingPersonController.setPerson(FirstTabController.getSelectedItem());
                showDialog(editDialogStagePerson, fxmlEditPerson, "Персональные данные", 300, 101);
                if (!editAddingPersonController.isB()) return;
                FirstTabController.getPersonBase().update(editAddingPersonController.getPerson());
                editAddingPersonController.setB(false);
            }
        });

        TableGroupController.getTableGroup().setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                System.out.println("ok");
                chg.setDate(TableGroupController.getSelectedItem().getId());
                showDialog(schg, fxmlSh, "Расписание группы", 300, 101);
            }
        });
    }

    private void showDialog(Stage stage, Parent parent, String title, int width, int height) {
        stage.showAndWait();
    }

    private Stage reg(Stage stage, Parent parent, String title, int width, int height) {
        if (stage == null) {
            stage = new Stage();
            stage.setTitle(title);
            stage.setMinWidth(width);
            stage.setMinHeight(height);
            stage.setResizable(false);
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(mainStage);
        }
        return stage;
    }


    public void openTab(ActionEvent actionEvent) {
        MenuItem m = (MenuItem) actionEvent.getSource();
        switch (m.getId()) {
            case "openTab1": {
                try {
//                    URL location = CollectionPersonBase.class.getResource("../../fxml/tab1.fxml");
//                    FXMLLoader fxmlLoader = new FXMLLoader();
//                    fxmlLoader.setLocation(location);
//                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
//                    Node node = (Node) fxmlLoader.load(location.openStream());
//
//                    Tab newTab = new Tab("Клиенты", node);
                    tabPane.getTabs().remove(tab);
                    tabPane.getTabs().add(tab);
                } catch (Exception exception) {
                    //no real error handling...just print the stacktrace...
                    exception.printStackTrace();
                }
                break;
            }
            case "openTab2": {
                tabPane.getTabs().remove(Hall);
                tabPane.getTabs().add(Hall);
                break;
            }
            case "openTab3": {
                tabPane.getTabs().remove(subscription);
                tabPane.getTabs().add(subscription);
                break;
            }
            case "tabAllStock": {
                tabPane.getTabs().remove(allStock);
                tabPane.getTabs().add(allStock);
                break;
            }
            case "tabStock": {
                tabPane.getTabs().remove(stock);
                tabPane.getTabs().add(stock);
                break;
            }
            case "tabTrainers": {
                tabPane.getTabs().remove(trainer);
                tabPane.getTabs().add(trainer);
                break;
            }
            case "tabGroup": {
                tabPane.getTabs().remove(group);
                tabPane.getTabs().add(group);
                break;
            }
        }

    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void actionButtonPressed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        if (!(source instanceof Button)) return;

        Button btnClick = (Button) source;

        switch (btnClick.getId()) {
            case "btnAdd":
                editAddingController.setPerson(new Person());
                showDialog(editDialogStage, fxmlEdit, "Регистрация клиента", 300, 101);
                if (!editAddingController.isB()) return;
                editAddingController.setB(false);
                if (editAddingController.getTextName().getText().equals("") ||
                        editAddingController.getTextSurname().getText().equals("")) return;
                // System.out.println(CollectionPersonBase.getPersonList().size());
                FirstTabController.getPersonBase().add(editAddingController.getPerson());
                collectionOperatingHall.sale(editAddingController.getPerson());
                //  System.out.println(CollectionPersonBase.getPersonList().size());
                break;
            case "btnTrainer":
                editAddingTrainerController.setTrainer(new Trainer());
                showDialog(editDialogStageTrainer, fxmlEditTrainer, "Регистрация тренира", 517, 106);
                if (!editAddingTrainerController.isB()) return;
                TableTrainersController.getControlTrainers().add(editAddingTrainerController.getTrainer());
                editAddingTrainerController.setB(false);
                break;
            case "btnDelete":
                FirstTabController.getPersonBase().delete(FirstTabController.getSelectedItem());
                collectionOperatingHall.deletePerson();
                break;
            case "btnSportRep":
                //  dateStart.set
                //     chartsController.setPersonBaseFirstTable(FirstTabController.getPersonBase());
                //   chartsController.setDate(dateStart.getEditor().getText(), dateEnd.getEditor().getText());
                choiceChartController.setMain(editReportStageChoice);
                showDialog(editReportStageChoice, fxmlEditTrainer, "Регистрация тренира", 517, 106);
                choiceChartController.setMain(mainStage);
                break;
            case "btnSendEmail":
                String[] args = new String[1];
                Thread myThready = new Thread(new Runnable() {
                    public void run() //Этот метод будет выполняться в побочном потоке
                    {

                        try {
                            new EmailSession().main(args);
                            JOptionPane.showMessageDialog(null, "Письма отправлены");
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }

                    }
                });
                myThready.start();
                break;
            case "btnBuildStock":
                TableStockController.goDate();
                break;
            case "btnAddStock":
                showDialog(editDialogStageStock, fxmlEditStock, "Добавить акцию", 441, 225);
                break;
            case "btnScreech":
                Person person = null;
                for (Person person1 :
                        getPersonList()) {
                    String nameSur = person1.getName() + " " + person1.getSurname();
                    if (String.valueOf(person1.getTik_id()).equals(textScreech.getText()) || person1.getName().equals(textScreech.getText())
                            || person1.getSurname().equals(textScreech.getText()) || nameSur.equals(textScreech.getText()))
                        person = person1;
                }
                if (person != null) {
                    FirstTabController.getTableView().getSelectionModel().select(person);
                    FirstTabController.getTableView().scrollTo(person);
                }
                break;
        }
    }

    public void actionTab(MouseEvent mouseEvent) {
        Tab tab = tabPane.getSelectionModel().getSelectedItem();

        //System.out.println(tab.getId());
        if (tab.getId().equals("stock") || tab.getId().equals("allStock")) {
            btnAdd.setDisable(true);
            btnAdd.setVisible(false);
            btnSportRep.setDisable(true);
            btnSportRep.setVisible(false);
            btnTrainer.setDisable(true);
            btnTrainer.setVisible(false);

            btnSendEmail.setDisable(false);
            btnSendEmail.setVisible(true);
            btnBuildStock.setDisable(false);
            btnBuildStock.setVisible(true);
            btnAddStock.setDisable(false);
            btnAddStock.setVisible(true);
        } else {
            btnSendEmail.setDisable(true);
            btnSendEmail.setVisible(false);
            btnBuildStock.setDisable(true);
            btnBuildStock.setVisible(false);
            btnAddStock.setDisable(true);
            btnAddStock.setVisible(false);

            btnAdd.setDisable(false);
            btnAdd.setVisible(true);
            btnSportRep.setDisable(false);
            btnSportRep.setVisible(true);
            btnTrainer.setDisable(false);
            btnTrainer.setVisible(true);
        }

    }

    public static Stage getSchg() {
        return schg;
    }
}
