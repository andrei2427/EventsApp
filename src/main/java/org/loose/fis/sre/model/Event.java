package org.loose.fis.sre.model;

public class Event {
    private String name;
    private String loc;
    private String data;
    private Integer nrTickets;

    public Event(String name,String loc, String data, Integer nr){
        this. name = name;
        this. loc = loc ;
        this. data = data;
        nrTickets =nr ;
    }

    public String getData() {
        return data;
    }

    public String getLoc() {
        return loc;
    }

    public String getName() {
        return name;
    }

    public Integer getNrTickets() {
        return nrTickets;
    }
    @Override
    public String toString(){
        return name + " | " + loc + " | " + data;
    }
}
