package com.projetandroid.myshoplist;

import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.projetandroid.myshoplist.adapter.ArticleCheck_adapter;
import com.projetandroid.myshoplist.adapter.Journal_adapter;
import com.projetandroid.myshoplist.adapter.Shopping_adapter;
import com.projetandroid.myshoplist.entities.Article_details;
import com.projetandroid.myshoplist.entities.Shopping_list;

import java.util.ArrayList;
import java.util.List;

public class shopping_journal extends AppCompatActivity  {
    private List<Article_details> articleDetails = new ArrayList<Article_details>();
    private ListView lv;
    private Journal_adapter journalAdapter;
    private Shopping_list shopping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_journal);

        lv = findViewById(R.id.listview);

        Select ArticleBought =  Select.from(Article_details.class).where(Condition.prop("status").eq("1"));
            articleDetails = ArticleBought.list();
            journalAdapter = new Journal_adapter(articleDetails, shopping_journal.this);
            lv.setAdapter(journalAdapter);





    }
}
