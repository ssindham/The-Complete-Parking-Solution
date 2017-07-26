package com.nick.finalyearproject;

/**
 * Created by CompuCareInfotech on 3/21/2017.
 */

public class CustomMenu3 implements Comparable<CustomMenu3> {

    private int id;

    private  int place_id;
    private double rent_lati;
    private double rent_longi;

    private String place_name;
    private double dist;


    private int capacity;
    private int current_capacity;
    private String attendant_name;






    //Constructor
    public CustomMenu3()
    {}
    public CustomMenu3(int id,int place_id,double rent_lati,double rent_longi,String place_name,double dist,int capacity,int current_capacity,String attendant_name)
    {
        this.id = id;
        this.place_id=place_id;
        this.rent_lati=rent_lati;
        this.rent_longi=rent_longi;
        this.place_name=place_name;
        this.dist=dist;
        this.capacity=capacity;
        this.current_capacity=current_capacity;
        this.attendant_name=attendant_name;

    }

    public int compareTo(CustomMenu3 f) {

        if (dist > f.dist) {
            return 1;
        }
        else if (dist <  f.dist) {
            return -1;
        }
        else {
            return 0;
        }

    }


    //setter,getter methods


    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public int getCurrent_capacity() {
        return current_capacity;
    }

    public void setCurrent_capacity(int current_capacity) {
        this.current_capacity = current_capacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlace_id() {
        return place_id;
    }

    public void setPlace_id(int place_id) {
        this.place_id = place_id;
    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public double getRent_lati() {
        return rent_lati;
    }

    public void setRent_lati(double rent_lati) {
        this.rent_lati = rent_lati;
    }

    public double getRent_longi() {
        return rent_longi;
    }

    public void setRent_longi(double rent_longi) {
        this.rent_longi = rent_longi;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getAttendant_name() {
        return attendant_name;
    }

    public void setAttendant_name(String attendant_name) {
        this.attendant_name = attendant_name;
    }
}

