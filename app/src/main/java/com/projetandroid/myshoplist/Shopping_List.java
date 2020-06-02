package com.projetandroid.myshoplist;

import android.content.Intent;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.projetandroid.myshoplist.adapter.ArticleCheck_adapter;
import com.projetandroid.myshoplist.entities.Article_details;
import com.projetandroid.myshoplist.entities.Shopping_list;
import com.projetandroid.myshoplist.utils.Shared;

import java.util.Date;
import java.util.List;

public class Shopping_List extends AppCompatActivity {

    private Shopping_list shoppingList;
    private ListView lv;
    private ArticleCheck_adapter adapter;
    private TextView txtName;
    private TextView txtDate;
    private TextView txttotal;
    final long checkedDate = new Date().getTime();
    //private EditText txtBudget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping__list);

        Bundle extras = getIntent().getExtras();

        lv = (ListView)findViewById(R.id.listview);
        txtName = (TextView)findViewById(R.id.textView2);
        txtDate = (TextView)findViewById(R.id.textView);
        //txtBudget = (EditText) findViewById(R.id.editBudget);
        txttotal = (TextView)findViewById(R.id.textView8);

        txttotal.setTypeface(Shared.appfontBold);

        adapter = new ArticleCheck_adapter(this);

        lv.setAdapter(adapter);
        if(extras != null)
        {
           shoppingList = Shopping_list.findById(Shopping_list.class,extras.getLong("id"));
            txtName.setText( shoppingList.shopping_list_name);
            txtDate.setText(Shared.dateformatAdd.format(shoppingList.shopping_date));
            //txtBudget.setText(Shared.decimalformat2.format(shoppingList.budget));

            List<Article_details> detail = Article_details.find(Article_details.class, "idarticle = ?", shoppingList.getId().toString());
            adapter.set(detail);

        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Article_details article = (Article_details) adapter.getItem(position);
                    adapter.checked(position,article.status == 1 ? false : true);
                    total();
                }
            });

            adapter.setChangeListener(new ArticleCheck_adapter.changeListener() {
                @Override
                public void onChange() {

                    total();

                }
            });

            total();

           findViewById(R.id.imageButton3).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<Article_details> detail = adapter.getData();

                    String text = shoppingList.shopping_list_name + " \n" + Shared.dateformatAdd.format(shoppingList.shopping_date)+ "\n"
                            + Shared.decimalformat2.format(shoppingList.budget) + " \n" ;
                    double total = 0;
                    for(int i = 0; i < detail.size();i++)
                    {
                        text += "\n " + detail.get(i).Article_name;
                        text += "\n " + detail.get(i).amount_unity + " x " + Shared.decimalformat.format(detail.get(i).price);
                        total += detail.get(i).amount_unity* detail.get(i).price;



                    }

                    text +="\n\n "+getString(R.string.totalPrice)+": " + Shared.decimalformat.format(total);

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, text);
                    startActivity(Intent.createChooser(intent, getString(R.string.share_via)));
                }
            });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    private void total()
    {
        List<Article_details> detail = adapter.getData();
        int qty = 0;
        double total = 0;
        for(int i = 0; i < detail.size();i++)
        {
            if(detail.get(i).status == 1)
            {
                detail.get(i).datechecked = checkedDate;
                qty += detail.get(i).amount_unity;
                total += ( detail.get(i).price * detail.get(i).amount_unity);
            }
        }

        txttotal.setText("TotalPurchased" + ": " + qty + " " + "item"+ " "  + getString(R.string.totalPrice) + ": " + Shared.decimalformat.format(total));
    }




}



