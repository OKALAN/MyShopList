package com.projetandroid.myshoplist.dialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.projetandroid.myshoplist.R;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.projetandroid.myshoplist.adapter.Article_adapter;
import com.projetandroid.myshoplist.entities.Article;

import java.util.List;

public class List_articles extends  Dialog implements AdapterView.OnItemClickListener {

    private Context context;
    private List_articlesListener listener;
    private ListView lv;
    private Article_adapter adapter;
    public List_articles(Context context) {
        super(context);
        this.context = context;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.activity_list_articles);

        lv = (ListView) findViewById(R.id.listview);

        List<Article> article = Article.listAll(Article.class);
            adapter = new Article_adapter(context,article);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(this);

        TextView t7 = (TextView)findViewById(R.id.textView7);

        if(adapter.getCount() == 0 )
            t7.setVisibility(View.VISIBLE);
        else
            t7.setVisibility(View.GONE);
    }

    public void setList_articlesListener( List_articlesListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(listener != null)
            listener.onFinish((Article) adapter.getItem(position));

        dismiss();
    }

    public interface  List_articlesListener {
        public void onFinish(Article data);
    }

}
