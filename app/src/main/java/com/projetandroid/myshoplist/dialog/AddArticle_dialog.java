package com.projetandroid.myshoplist.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import com.projetandroid.myshoplist.R;
import com.projetandroid.myshoplist.entities.Article;
import com.projetandroid.myshoplist.entities.Article_add;
import com.projetandroid.myshoplist.entities.Shopping_list;
import com.projetandroid.myshoplist.utils.Shared;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Intent.*;


public class AddArticle_dialog extends Dialog  implements  View.OnClickListener {
        private Context context;

    public void setListener(AddArticle_dialogListener listener) {
        this.listener = listener;
    }

    private AddArticle_dialogListener listener;
        private EditText textName , textPrice ;
        private TextView  textQty ;
        private ImageButton btnPlus , btnMinus;
        private Article_add articleAdd;
        private int position = -1;
        private Button btnADD;
        private static  final int GALLERY_REQUEST_CODE = 123;
        private Uri imageURI;



      public   ImageView imageView;
      public   Button broswer;


    public AddArticle_dialog(@NonNull Context context) {
        super(context);
        this.context = context;

        articleAdd = new Article_add();
        articleAdd.quantity = 1;
    }

    public AddArticle_dialog(@NonNull Context context, Article_add articleAdd, int pos) {
        super(context);
        this.context = context;
        this.position = pos;
        this.articleAdd = articleAdd;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.activity_add_article_dialog);

        findViewById(R.id.btnADD).setOnClickListener(this);
        findViewById(R.id.btnClose).setOnClickListener(this);
        findViewById(R.id.btnList).setOnClickListener(this);

        Button button = (Button)findViewById(R.id.btnADD);
        Button button2 = (Button)findViewById(R.id.btnClose);
        button.setTypeface(Shared.appfontBold);
        button2.setTypeface(Shared.appfontBold);


        textName = (EditText)findViewById(R.id.editText);
        textPrice = (EditText)findViewById(R.id.editText2);
        textQty = (TextView)findViewById(R.id.textView4);
        btnPlus = (ImageButton)findViewById(R.id.imageButton1);
        btnMinus = (ImageButton)findViewById(R.id.imageButton2);

        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        btnMinus.setEnabled(false);

        btnADD = (Button)findViewById(R.id.btnADD);

        if(articleAdd.Article_name  != null)
        {
            textName.setText(articleAdd.Article_name);
            textPrice.setText(Shared.decimalformat2.format(articleAdd.Article_price));
            textQty.setText(String.valueOf(articleAdd.quantity));

            if(articleAdd.quantity <=1 ){

                btnMinus.setEnabled(false);

                btnADD.setText("UPDATE");
            }
            else
                btnMinus.setEnabled(false);

        }

     /*   imageView = findViewById(R.id.imageArticle);
        broswer = findViewById(R.id.gallery);

        broswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                      openGallery();

            }
        });*/


    }

  /*  private void openGallery() {
        Intent gallery = new Intent(ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        ActivityCompat.startActivityForResult(gallery, GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST_CODE){
            imageURI = data.getData();
            imageView.setImageURI(imageURI);

        }

    } */


    protected AddArticle_dialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnADD :

                if (textName.getText().toString().equals(""))
                {
                    Toast.makeText(context, "item can't be empty ", Toast.LENGTH_SHORT).show();
                     return;
                }
                if (textPrice.getText().toString().equals(""))
                {
                    Toast.makeText(context, "price can't be empty", Toast.LENGTH_SHORT).show();
                     return;
                }
                articleAdd.Article_name = textName.getText().toString().toLowerCase();
                articleAdd.quantity = Integer.valueOf(textQty.getText().toString());
                articleAdd.Article_price = Double.valueOf(textPrice.getText().toString());

                if (listener != null)

                listener.onFinish(articleAdd,position);

                List<Article> article = new ArrayList<Article>();

                try
                {
                    article = Article.find(Article.class, "Name = ?", articleAdd.Article_name.toLowerCase());

                }

                catch (Exception e) { }

                if (article.size() == 0)
                {
                    Article b = new Article();
                    b.Article_name = articleAdd.Article_name;
                    b.Article_price = articleAdd.Article_price;
                    b.save();
                }
                else
                {
                  Article b = article.get(0);
                  b.Article_price = articleAdd.Article_price;
                  b.save();
                }

                if (position != -1)
                {
                    dismiss();
                    Toast.makeText(context, "item successfully update",Toast.LENGTH_SHORT).show();

                }

                else
                {
                    articleAdd = new Article_add();
                    Toast.makeText(context, "item successfully saved", Toast.LENGTH_SHORT).show();
                    textQty.setText("1");
                    textPrice.setText("");
                    textName.setText("");
                    btnMinus.setEnabled(false);
                    textName.requestFocus();
                }

                 break;
                case R.id.btnCLOSE :
                        dismiss();
                        break;

            case R.id.imageButton1:
                articleAdd.quantity +=1;
                if (articleAdd.quantity > 1)
                      btnMinus.setEnabled(true);

                textQty.setText(String.valueOf(articleAdd.quantity));
                break;
            case R.id.imageButton2 :
                articleAdd.quantity -= 1;

                textQty.setText(String.valueOf(articleAdd.quantity));

                if (articleAdd.quantity == 1)
                {
                        btnMinus.setEnabled((false));
                }
                break;
            case R.id.btnList :
                List_articles dialog = new List_articles(context);
                dialog.setList_articlesListener(new List_articles.List_articlesListener() {
                    @Override
                    public void onFinish(Article data) {
                        textName.setText(data.Article_name);
                        textPrice.setText(Shared.decimalformat2.format(data.Article_price));
                    }
                });
                dialog.show();
                break;





        }

    }



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public interface AddArticle_dialogListener {
        public void onFinish(Article_add articleAdd, int position);
    }
}
