package com.projetandroid.myshoplist.entities;
import com.orm.SugarRecord;

public class Article_details extends SugarRecord<Article_details> {

    public Long id_Article;
    public String Article_name;
    public double price;
    public int amount_unity;
    public int status;


}
