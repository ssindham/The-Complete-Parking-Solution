package com.nick.finalyearproject;

/**
 * Created by CompuCareInfotech on 2/23/2017.
 */

public class CustomMenu implements Comparable<CustomMenu> {

    private int id;
    private String area;
    private int charge;
    private double dist;
    private String u_id;
    private String r_id;
    private double rent_lati;
    private double rent_longi;

    //Constructor
    public CustomMenu()
    {}

    public CustomMenu(int id, String area, int charge, double dist,String u_id,String r_id,double rent_lati,double rent_longi) {
        this.id = id;
        this.area = area;
        this.charge = charge;
        this.dist = dist;
        this.u_id=u_id;
        this.r_id=r_id;
        this.rent_lati=rent_lati;
        this.rent_longi=rent_longi;
    }

    public int compareTo(CustomMenu f) {

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }
    public String getUid() {
        return u_id;
    }

    public void setUid(String u_id) {
        this.u_id = u_id;
    }
    public String getRid()
    {
        return r_id;
    }

    public void setRid(String r_id) {
        this.r_id = r_id;
    }
    public void setLati(double rent_lati)
    {
        this.rent_lati=rent_lati;
    }
    public double getLati()
    {
        return  rent_lati;
    }
    public void setLongi(double rent_longi)
    {
        this.rent_longi=rent_longi;
    }
    public double getLongi()
    {
        return  rent_longi;
    }
}

