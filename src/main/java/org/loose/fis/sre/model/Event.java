package org.loose.fis.sre.model;

public class Event {
    private String name;
    private String loc;
    private String data;
    private Integer nrTickets;
    private int request = 0;

    public Event(String name,String loc, String data, Integer nr){
        this. name = name;
        this. loc = loc ;
        this. data = data;
        nrTickets =nr ;
    }
    public Event(){}
    public String getData() {
        return data;
    }

    public void setRequest(int i){ this.request=i; }

    public int getRequest(){ return request; }
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
        return "   " + name + "   |   " + loc + "   |   " + data + "   |   " + nrTickets;
    }
}
