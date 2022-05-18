package org.loose.fis.sre.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.sre.model.Event;
import org.loose.fis.sre.model.User;
import org.loose.fis.sre.services.UserService;

public class UserController {
    @FXML
    private Label select;
    @FXML
    private TableView<Event> Tabel;
    @FXML
    private TableColumn<Event, String> Data;
    @FXML
    private TableColumn<Event, Integer> NOT;
    @FXML
    private TableColumn<Event, String> Name;
    @FXML
    private TableColumn<Event, String> Place;
    private static ObjectRepository<User> repository;
    @FXML
    public void initialize(){
        Tabel.setVisible(false);
        Name.setCellValueFactory(new PropertyValueFactory<Event, String>("name"));
        Place.setCellValueFactory(new PropertyValueFactory<Event, String>("loc"));
        Data.setCellValueFactory(new PropertyValueFactory<Event, String>("data"));
        NOT.setCellValueFactory(new PropertyValueFactory<Event, Integer>("nrTickets"));
    }

    public void Search(){
    repository = UserService.getDatabase();
    ObservableList<Event> records= FXCollections.observableArrayList();
        for(User user : repository.find()){
            if(user.getEvents()!=null){
                Event[] events = user.getEvents();
                int contor = user.getContor();
                for(int i=0;i<contor;i++){
                    records.add(events[i]);
                }
            }
        }
        records.add(new Event("articol","MH","22.02.2002",10));
        Tabel.setItems(records);
        Tabel.setVisible(true);
    }
    @FXML
    public void getSelection(){
        Event ev = Tabel.getSelectionModel().getSelectedItem();
        String txt = "Selected: " + ev.toString();
        select.setText(txt);
    }

}