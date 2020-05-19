package com.projetandroid.myshoplist.entities;

import com.orm.SugarRecord;

import java.util.Date;

public class Shopping_list extends SugarRecord<Shopping_list> {

    public  String shopping_list_name;
    public Date shopping_date;
    public long Date;
    public int status;
    public int totalitem;
    public double totalprices;
    public Double budget;

}
