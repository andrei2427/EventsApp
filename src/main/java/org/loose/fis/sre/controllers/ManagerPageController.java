package org.loose.fis.sre.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.sre.model.EnhancedEvent;
import org.loose.fis.sre.model.Event;
import org.loose.fis.sre.model.User;
import org.loose.fis.sre.services.UserService;

import java.io.IOException;
import java.util.Arrays;

public class ManagerPageController {
    @FXML
    private TextField srctxt;
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
    @FXML
    private ListView<String> list = new ListView<String>();

    public ObservableList<Event> managerEvents= FXCollections.observableArrayList();
    public ObservableList<EnhancedEvent> reservations= FXCollections.observableArrayList();

    private static ObjectRepository<User> repository = UserService.getDatabase();

    private static User manager = UserService.currentUser;
    public static Event edit;
    public static Event add =null;
    public void initialize(){
        Table.setVisible(false);
        list.setVisible(false);
        Name.setCellValueFactory(new PropertyValueFactory<Event, String>("name"));
        Place.setCellValueFactory(new PropertyValueFactory<Event, String>("loc"));
        Date.setCellValueFactory(new PropertyValueFactory<Event, String>("data"));
        NOT.setCellValueFactory(new PropertyValueFactory<Event, Integer>("nrTickets"));
        namelbl.setText(UserService.currentUser.getUsername());
    }
    public void SearchEvent(ActionEvent a)throws IOException{
        managerEvents.clear();
        if(add!=null){
            add(a);
            SearchEvent(a);
        }
        if (manager.events != null) {
            for( int i=0; i<manager.getContor();i++ ) {
                {
                    managerEvents.add(manager.events[i]);
                }
            }
        }
        //managerEvents.add(new Event("test", "test", "20.03.2008", 1));
        Table.setItems(managerEvents);
        Table.setVisible(true);
        list.setVisible(false);
    }
    public void History(){
        reservations.clear();
        list.getItems().clear();
        for(User user: repository.find()){
            if ("User".equals(user.getRole())){
                for(int i=0; i< manager.getContor();i++){
                    for(int j=0; j<user.getContor();j++){
                        if(manager.events[i].getName()!=null){
                            if(manager.events[i].getName().equals(user.events[j].getName())){
                                reservations.add(new EnhancedEvent(user.events[i],user.getUsername()));
                            }
                        }
                    }
                }
            }
        }
        for(int k=0; k< reservations.size();k++){
            String show = "  < " + (k+1) + " >  " + reservations.get(k).toString() ;
            list.getItems().add(show);
        }
        //String show = "  < 1 > aaa  --->  articol    |    MH    |    22.02.2022";
        list.getItems().add(show);
        list.setVisible(true);
    }
    public void doSearch(){
        Table.setVisible(true);
        list.setVisible(false);
        String search = srctxt.getText();
        Table.getItems().stream()
                .filter(item -> search.equals(item.getName()))
                .findAny()
                .ifPresent(item -> {
                    Table.getSelectionModel().select(item);
                    Table.scrollTo(item);
                });
    }
    public void Edit(ActionEvent event) throws IOException{
        Table.setVisible(true);
        list.setVisible(false);
        Event ev = Table.getSelectionModel().getSelectedItem();
        if(ev!=null){
            edit = new Event(ev);
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("EditEvent.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            User user = new User(manager);
            for(int i=0;i<manager.getContor();i++){
                if( edit != null ){
                    if(user.events[i].getName()!=null){
                        if(user.events[i].getName().equals(edit.getName())) {
                            user.events[i]=edit;
                        }
                    }
                }
            }
            repository.remove(manager);
            repository.insert(user);
            manager = user;
            UserService.currentUser = user;
        }
    }
    public void addd() {

        if(add != null){
            User usera = new User(manager);
            usera.addEvent(add);
            repository.remove(manager);
            repository.insert(usera);
            manager = usera;
            UserService.currentUser = usera;
            System.out.println("merge add");
        }
    }
    public void add(ActionEvent event ) throws IOException{
        if(add ==null){
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("AddEvent.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        addd();}
        if(add != null){
            User usera = new User(manager);
            usera.addEvent(add);
            repository.remove(manager);
            repository.insert(usera);
            manager = usera;
            UserService.currentUser = usera;
            System.out.println("merge add");
            add =null;
        }
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
