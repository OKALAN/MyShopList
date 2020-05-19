package com.projetandroid.myshoplist.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.projetandroid.myshoplist.R;
import com.projetandroid.myshoplist.entities.Article;
import com.projetandroid.myshoplist.entities.Article_details;
import com.projetandroid.myshoplist.utils.Shared;

import java.util.ArrayList;
import java.util.List;

public class Modif_article_dialog extends Dialog  {

    private Context context;
    private  Modif_article_dialogListener  listener;
    private EditText txtPrice;
    private TextView txtqty;
    private ImageButton btnPlus;
    private ImageButton btnMinus;
    private Article_details articleDetails;
    private int position = -1;
    private Button btnADD;


    public void setListener(Modif_article_dialogListener listener) {
        this.listener = listener;
    }

    public Modif_article_dialog(@NonNull Context context, Article_details articleDetails ) {
        super(context);
        this.context = context;
        this.articleDetails = articleDetails;
    }

    protected Modif_article_dialog(@NonNull Context context, Article_details articleDetails, int pos) {
        super(context);
        this.context = context;
        this.articleDetails = articleDetails;
        this.position = pos;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.activity_modif_article_dialog);

        findViewById(R.id.btnSAVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtPrice.getText().toString().equals(""))
                {
                    Toast.makeText(context, "price can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                articleDetails.amount_unity = Integer.valueOf(txtqty.getText().toString());
                articleDetails.price = Double.valueOf(txtqty.getText().toString());
                articleDetails.save();

                if (listener != null)
                    listener.onFinish(articleDetails);

                List<Article> articles = new ArrayList<Article>();
                try
                {
                    articles = Article.find(Article.class, "Name = ?", articleDetails.Article_name.toLowerCase());
                }
                catch (Exception e) { }

                if (articles.size() == 0)
                {
                    Article a = new Article();
                    a.Article_name = articleDetails.Article_name;
                    a.Article_price = articleDetails.price;
                    a.save();

                }
                else
                {
                    Article a = articles.get(0);
                    a.Article_price = articleDetails.price;
                    a.save();
                }

                Toast.makeText(context, "item successfully updated", Toast.LENGTH_SHORT).show();
                dismiss();


            }
        });
        Button button = (Button)findViewById(R.id.btnADD);

        findViewById(R.id.btnCLOSE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }
        });

        Button button2 = (Button)findViewById(R.id.btnCLOSE);

        txtPrice = (EditText)findViewById(R.id.editText2);
        txtqty = (TextView)findViewById(R.id.textView4);
        btnPlus = (ImageButton) findViewById(R.id.imageButton1);
        btnMinus = (ImageButton)findViewById(R.id.imageButton2);

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                articleDetails.amount_unity +=1;
                if (articleDetails.amount_unity > 1)
                {
                    btnMinus.setEnabled(true);
                }
                txtqty.setText(String.valueOf(articleDetails.amount_unity));

            }
        });
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                articleDetails.amount_unity -= 1;
                txtqty.setText(String.valueOf(articleDetails.amount_unity));

                if (articleDetails.amount_unity == 1)
                    btnMinus.setEnabled(false);


            }
        });

        btnMinus.setEnabled(true);

        btnADD = (Button)findViewById(R.id.btnADD);

        if (articleDetails != null)

        {
            txtPrice.setText(Shared.decimalformat2.format(articleDetails.price));
            txtqty.setText(String.valueOf(articleDetails.amount_unity));
            if (articleDetails.amount_unity <= 1)
                btnMinus.setEnabled(false);
        }
        else
            btnMinus.setEnabled(false);





      /*  findViewById(R.id.btnADD).setOnClickListener(this);
        findViewById(R.id.btnCLOSE).setOnClickListener(this);

        Button button = (Button)findViewById(R.id.btnADD);
        Button button2 = (Button)findViewById(R.id.btnCLOSE);

        button.setTypeface(Shared.appfontBold);
        button2.setTypeface(Shared.appfontBold);


        txtPrice = (EditText)findViewById(R.id.editText2);
        txtqty = (TextView)findViewById(R.id.textView4);
        btnPlus = (ImageButton) findViewById(R.id.imageButton1);
        btnMinus = (ImageButton)findViewById(R.id.imageButton2);

        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);

        btnMinus.setEnabled(true);

        btnADD = (Button)findViewById(R.id.btnADD);

        if (articleDetails != null)

        {
            txtPrice.setText(Shared.decimalformat2.format(articleDetails.price));
            txtqty.setText(String.valueOf(articleDetails.amount_unity));
            if (articleDetails.amount_unity <= 1)
                btnMinus.setEnabled(false);
        }
          else
              btnMinus.setEnabled(false);*/

    }

   /* @Override
    public void onClick(View v) {
        switch (v.getId())
        { 
            case R.id.btnADD:
               if (txtPrice.getText().toString().equals(""))
               { 
                   Toast.makeText(context, "price can't be empty", Toast.LENGTH_SHORT).show();
                   return;
               }
               
               articleDetails.amount_unity = Integer.valueOf(txtqty.getText().toString());
               articleDetails.price = Double.valueOf(txtqty.getText().toString());
               articleDetails.save();
               
               if (listener != null)
                   listener.onFinish(articleDetails);
               
               List<Article> articles = new ArrayList<Article>();
               try
               {
                   articles = Article.find(Article.class, "Name = ?", articleDetails.Article_name.toLowerCase());
               } 
               catch (Exception e) { }
               
               if (articles.size() == 0)
               { 
                   Article a = new Article();
                   a.Article_name = articleDetails.Article_name;
                   a.Article_price = articleDetails.price;
                   a.save();
                   
               }
               else 
               { 
                   Article a = articles.get(0);
                   a.Article_price = articleDetails.price;
                   a.save();
               }
               
           Toast.makeText(context, "item successfully updated", Toast.LENGTH_SHORT).show();       
               dismiss();
               break;
            case R.id.btnClose:
                dismiss();
                break;
            case R.id.imageButton1:
                articleDetails.amount_unity +=1;
                if (articleDetails.amount_unity > 1)
                {
                    btnMinus.setEnabled(true);
                }
                txtqty.setText(String.valueOf(articleDetails.amount_unity));
                break;

            case R.id.imageButton2:
                articleDetails.amount_unity -= 1;
                txtqty.setText(String.valueOf(articleDetails.amount_unity));

                if (articleDetails.amount_unity == 1)
                    btnMinus.setEnabled(false);

                break;
        }
            
            

    }*/

    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, @Nullable Menu menu, int deviceId) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public interface Modif_article_dialogListener {
      public   void onFinish(Article_details articleDetails);
    }
}
