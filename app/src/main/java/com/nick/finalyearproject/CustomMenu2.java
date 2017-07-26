package com.nick.finalyearproject;

/**
 * Created by CompuCareInfotech on 3/21/2017.
 */

public class CustomMenu2 {

    private int id;

    private  int r_id;
    private double rent_lati;
    private double rent_longi;



    private int two_wheel;
    private int four_wheel;
    private String area;
    private String week_days;
    private String start_time;
    private String end_time;
    private int charge;



    //Constructor
    public CustomMenu2()
    {}
    public CustomMenu2(int id,double rent_lati,double rent_longi,int two_wheel,int four_wheel,String area,String week_days,String start_time,String end_time,int charge,int r_id) {
        this.id = id;
        this.rent_lati=rent_lati;
        this.rent_longi=rent_longi;
        this.two_wheel=two_wheel;
        this.four_wheel=four_wheel;
        this.area = area;
        this.week_days=week_days;
        this.start_time=start_time;
        this.end_time=end_time;
        this.charge = charge;
        this.r_id=r_id;


    }

    //setter,getter methods

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
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

    public  void setTwo_wheel(int two_wheel){this.two_wheel=two_wheel;}

    public int getTwo_wheel(){return  two_wheel;}

    public  void setFour_wheel(int four_wheel){this.four_wheel=four_wheel;}

    public int getFour_wheel(){return  four_wheel;}

    public String getArea() {
        return area;
    }


    public void setArea(String area) {
        this.area = area;
    }

    public String getStart_time(){return  start_time;}

    public void setStart_time(String start_time){this.start_time=start_time;}

    public String getEnd_time(){return  end_time;}
    public void setEnd_time(String end_time_time){this.end_time=end_time;}

    public  String getWeek_days(){return  week_days;}
    public void setWeek_days(String  week_days){this.week_days=week_days;}
    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }


}

