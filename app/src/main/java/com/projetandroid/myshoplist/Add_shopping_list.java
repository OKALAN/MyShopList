package com.projetandroid.myshoplist;

import android.app.DatePickerDialog;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.projetandroid.myshoplist.adapter.ArticleAddAdapter;
import com.projetandroid.myshoplist.dialog.AddArticle_dialog;
import com.projetandroid.myshoplist.entities.Article_add;
import com.projetandroid.myshoplist.entities.Article_details;
import com.projetandroid.myshoplist.entities.Shopping_list;
import com.projetandroid.myshoplist.utils.Shared;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Add_shopping_list extends AppCompatActivity implements View.OnClickListener {

    private DatePickerDialog datePickerDialog;
    private EditText txtName;
    private EditText txtDate;
    private EditText txtBudget;
    private TextView txthint;
    private ListView lv;
    private ArticleAddAdapter adapter;
    private Date selectedDate ;
    private long SavedDate = new Date().getTime() ;

    private boolean isEditMode = false;
    private Long id = -1l;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shopping_list);


        txtName = (EditText)findViewById(R.id.editText) ;
        txtDate = (EditText) findViewById(R.id.editText2);
        txtBudget = (EditText)findViewById(R.id.editBudget);
        txtDate.setOnClickListener(this);


        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);


        Button button = (Button)findViewById(R.id.button);
        Button button2 = (Button)findViewById(R.id.button2);
        button.setTypeface(Shared.appfontBold);
        button2.setTypeface(Shared.appfontBold);

        Calendar c = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, dateListener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        selectedDate  = c.getTime();

        lv = (ListView)findViewById(R.id.listview);
        adapter = new ArticleAddAdapter(this);

        lv.setAdapter(adapter);

        txthint = (TextView)findViewById(R.id.textView3);

        if(adapter.getCount() == 0 )
            txthint.setVisibility(View.VISIBLE);
        else
            txthint.setVisibility(View.GONE);

        adapter.setChangeListener(new ArticleAddAdapter.changeListener() {
            @Override
            public void onChange() {
                if(adapter.getCount() == 0 )
                    txthint.setVisibility(View.VISIBLE);
                else
                    txthint.setVisibility(View.GONE);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AddArticle_dialog dialog = new AddArticle_dialog(Add_shopping_list.this, (Article_add) adapter.getItem(position),position);
                dialog.setListener(new AddArticle_dialog.AddArticle_dialogListener() {
                    @Override
                    public void onFinish(Article_add data, int position) {

                        if (position == 1)
                            adapter.add(data);
                        else
                            adapter.replace(data, position);

                       txthint.setVisibility(View.GONE);
                    }
                });

                dialog.show();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null){

            id = extras.getLong("id");
            Shopping_list d = Shopping_list.findById(Shopping_list.class,id);
            txtName.setText(d.shopping_list_name);
            txtDate.setText(Shared.dateformatAdd.format(d.shopping_date));
            txtBudget.setText(Shared.decimalformat2.format(d.budget));
            txthint.setVisibility(View.GONE);
            isEditMode  = true;

            List<Article_details> detail = Article_details.find(Article_details.class, "idarticle = ?", String.valueOf(id));
            for(int i = 0; i < detail.size();i++)
            {
               Article_add b = new Article_add();
                b.Article_name = detail.get(i).Article_name;
                b.Article_price =  detail.get(i).price;
                b.quantity =  detail.get(i).amount_unity;
                adapter.add(b);
            }

            selectedDate = d.shopping_date;

        }



    }

    private DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            datePickerDialog.updateDate(year, monthOfYear, dayOfMonth);
            txtDate.setText(Shared.dateformatAdd.format(calendar.getTime()));
            selectedDate  = calendar.getTime();
        }
    };

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.editText2:
                datePickerDialog.show();
                break;
            case R.id.button:
                AddArticle_dialog dialog = new AddArticle_dialog(this);
                dialog.setListener(new AddArticle_dialog.AddArticle_dialogListener() {
                    @Override
                    public void onFinish(Article_add data, int pos) {
                        
                   
                        if(pos == -1)
                            adapter.add(data);
                        else
                            adapter.replace(data,pos);

                        txthint.setVisibility(View.GONE);
                    }
                });
                dialog.show();
                break;
            case R.id.button2:
                save();
                break;
        }

    }

    private void save()
    {
        if(txtName.getText().toString().equals(""))
        {
            Toast.makeText(this, "list name can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(txtDate.getText().toString().equals(""))
        {
            Toast.makeText(this, "date can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (txtBudget.getText().toString().equals(""))
        {
            Toast.makeText(this, "budget can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(adapter.getCount() == 0)
        {
            Toast.makeText(this, "add at least 1 item", Toast.LENGTH_SHORT).show();
            return;
        }

       Shopping_list shopping_list = new Shopping_list();
        if(isEditMode)
            shopping_list = Shopping_list.findById(Shopping_list.class,id);

        shopping_list.shopping_list_name = txtName.getText().toString();
        shopping_list.shopping_date = selectedDate;
        shopping_list.Date = SavedDate ;
        shopping_list.budget = Double.valueOf(txtBudget.getText().toString());
        shopping_list.status = 0;
        shopping_list.save();

        if(isEditMode)
            Article_details.deleteAll(Article_details.class, "idarticle = ?", String.valueOf(id));

        List<Article_add> articleAdd = adapter.getData();
        int qty = 0;
        double total = 0;
        for(int i = 0; i < articleAdd.size();i++)
        {

           if (shopping_list.budget > total) {
               Article_add ba = articleAdd.get(i);
               Article_details d = new Article_details();
               d.Article_name = ba.Article_name;
               d.price = ba.Article_price;
               d.amount_unity = ba.quantity;
               d.status = 0;
               d.id_Article = shopping_list.getId();
               d.save();

               qty += ba.quantity;
               total += ( ba.Article_price * ba.quantity);
           }
           else
           {

               Toast.makeText(this, "budget not enough!",Toast.LENGTH_SHORT).show();
           }

        }

        shopping_list.totalitem = qty;
        shopping_list.totalprices = total;
        shopping_list.Date = SavedDate ;
        shopping_list.save();

        setResult(RESULT_OK);
        Toast.makeText(this, "shopping successfully saved", Toast.LENGTH_SHORT).show();
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
