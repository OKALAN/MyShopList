package com.projetandroid.myshoplist.entities;
import com.orm.SugarRecord;


public class Article extends SugarRecord<Article> {

    public String Article_name;
    public Double Article_price;

    public Article(){
    }
    public Article(String Article_name, Double Article_price){
       this.Article_name = Article_name;
       this.Article_price = Article_price;

     }
}


