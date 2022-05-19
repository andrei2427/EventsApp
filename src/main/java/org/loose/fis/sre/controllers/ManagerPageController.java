package org.loose.fis.sre.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.sre.model.Event;
import org.loose.fis.sre.model.User;
import org.loose.fis.sre.services.UserService;

import java.io.IOException;
import java.util.Arrays;

public class ManagerPageController {

    @FXML
    private TableColumn<Event, String> Date;

    @FXML
    private TableColumn<Event, Integer> NOT;

    @FXML
    private TableColumn<Event, String> Name;

    @FXML
    private TableColumn<Event, String> Place;

    @FXML
    private TableView<Event> Table;

    @FXML
    private Label namelbl;
    public ObservableList<Event> managerEvents= FXCollections.observableArrayList();
    private static ObjectRepository<User> repository;
    public void initialize(){
        Table.setVisible(false);
        Name.setCellValueFactory(new PropertyValueFactory<Event, String>("name"));
        Place.setCellValueFactory(new PropertyValueFactory<Event, String>("loc"));
        Date.setCellValueFactory(new PropertyValueFactory<Event, String>("data"));
        NOT.setCellValueFactory(new PropertyValueFactory<Event, Integer>("nrTickets"));
        namelbl.setText(UserService.currentUser.getUsername());
    }
    public void SearchEvent(){
        repository = UserService.getDatabase();
        managerEvents.clear();
        for(User user : repository.find()){
            if(user.getEvents()!=null){
                Event[] events = user.getEvents();
                int contor = user.getContor();
                managerEvents.addAll(Arrays.asList(events).subList(0, contor));
            }
        }
        managerEvents.add(new Event("test","test","20.03.2008",1));
        Table.setItems(managerEvents);
        Table.setVisible(true);
    }
    public void Logoff(ActionEvent action) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml"));
        Stage stage = (Stage) ((Node) action.getSource()).getScene().getWindow();
        Scene scene2 = new Scene(root);
        stage.setScene(scene2);
        stage.setTitle("Events App");
        stage.show();
    }
}
