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
import javafx.scene.shape.Line;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.sre.model.Event;
import org.loose.fis.sre.model.User;
import org.loose.fis.sre.services.UserService;

import java.util.Arrays;

public class UserController {
    @FXML
    private Line l1;
    @FXML
    private Line l2;
    @FXML
    private Line l3;
    @FXML
    private Line l4;
    @FXML
    private Label select;
    @FXML
    private Label namelbl;
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
        l1.setVisible(false);l2.setVisible(false);l3.setVisible(false);l4.setVisible(false);
        Name.setCellValueFactory(new PropertyValueFactory<Event, String>("name"));
        Place.setCellValueFactory(new PropertyValueFactory<Event, String>("loc"));
        Data.setCellValueFactory(new PropertyValueFactory<Event, String>("data"));
        NOT.setCellValueFactory(new PropertyValueFactory<Event, Integer>("nrTickets"));
        namelbl.setText(UserService.currentUser.getUsername());
    }
    public void SearchEvents(){
    repository = UserService.getDatabase();
    ObservableList<Event> records= FXCollections.observableArrayList();
        for(User user : repository.find()){
            if(user.getEvents()!=null){
                Event[] events = user.getEvents();
                int contor = user.getContor();
                records.addAll(Arrays.asList(events).subList(0, contor));
            }
        }
        records.add(new Event("articol","MH","22.02.2002",10));
        Tabel.setItems(records);
        Tabel.setVisible(true);
        l1.setVisible(true);l2.setVisible(true);l3.setVisible(true);l4.setVisible(true);
    }
    @FXML
    public void getSelection(){
        Event ev = Tabel.getSelectionModel().getSelectedItem();
        String txt = "Selected: " + ev.toString();
        select.setText(txt);
    }
    public void MakeReservation(){
        Event ev = Tabel.getSelectionModel().getSelectedItem();
        if(UserService.currentUser!=null){
            UserService.currentUser.addEvent(ev);
        }
    }
    public void searchName(){
    }
}