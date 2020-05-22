package com.projetandroid.myshoplist;

import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import com.projetandroid.myshoplist.adapter.ArticleAddAdapter;
import com.projetandroid.myshoplist.adapter.Shopping_adapter;
import com.projetandroid.myshoplist.entities.Shopping_list;
import com.projetandroid.myshoplist.utils.Shared;

import java.util.*;


public class MainActivity extends AppCompatActivity implements List<Shopping_list> {

    private ListView lv;
    private TextView txthint;
    private Shopping_adapter adapter;
    private ImageButton imageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Shared.initialize(getBaseContext());

        setContentView(R.layout.activity_main);

        findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, shopping_journal.class);
                startActivity(intent);

            }
        });

        lv = (ListView)findViewById(R.id.listview);
        txthint = (TextView)findViewById(R.id.textView2);
        List<Shopping_list> shopping = Shopping_list.findWithQuery(Shopping_list.class,"SELECT * FROM shoppinglist order by shoppingdate desc ");
        Log.d(String.valueOf(shopping), "shopping ");
        adapter = new Shopping_adapter(shopping, this);

        lv.setAdapter(adapter);
        if(shopping.size() == 0 )
            txthint.setVisibility(View.VISIBLE);
        else
            txthint.setVisibility(View.GONE);

        adapter.setChangeListener(new ArticleAddAdapter.changeListener() {
            @Override
            public void onChange() {
                if (adapter.getCount() == 0)
                    txthint.setVisibility(View.VISIBLE);
                else
                    txthint.setVisibility(View.GONE);
            }
        });

       // EditText search = (EditText)findViewById(R.id.editText);
       // search.addTextChangedListener((TextWatcher) this);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Shopping_list data = (Shopping_list) adapter.getItem(position);
                Intent intent = new Intent(MainActivity.this, Shopping_List.class);
                intent.putExtra("id",data.getId());
                startActivityForResult(intent,10002);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            }
        });

        TextView title = (TextView)findViewById(R.id.textView);
        title.setTypeface(Shared.appfontTitle);

        Button ADD = (Button)findViewById(R.id.btnCreate);
        ADD.setTypeface(Shared.appfontBold);


        ADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId())
                {
                    case R.id.btnCreate:
                        Intent intent = new Intent(MainActivity.this, Add_shopping_list.class);
                        startActivityForResult(intent,10001);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;
                }

            }
        });


    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
        {
            if(requestCode == 10001 || requestCode == 10002)
            {
                List<Shopping_list> shopping = Shopping_list.findWithQuery(Shopping_list.class,"SELECT * FROM shoppinglist order by shoppingdate desc ");
                adapter.set(shopping);
                if(shopping.size() == 0 )
                    txthint.setVisibility(View.VISIBLE);
                else
                    txthint.setVisibility(View.GONE);
            }
        }
    }


    public void onTextChanged(CharSequence s, int start, int before, int count) {
        List<Shopping_list> shopping = Shopping_list.findWithQuery(Shopping_list.class,"SELECT * FROM shoppinglist WHERE LOWER(shoppinglistname) like '%"+s.toString().toLowerCase()+"%' order by shopping_date desc ");
        adapter.set(shopping);
    }




    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(@Nullable Object o) {
        return false;
    }

    @NonNull
    @Override
    public Iterator<Shopping_list> iterator() {
        return null;
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NonNull
    @Override
    public <T> T[] toArray(@NonNull T[] a) {
        return null;
    }

    @Override
    public boolean add(Shopping_list shopping_list) {
        return false;
    }

    @Override
    public boolean remove(@Nullable Object o) {
        return false;
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends Shopping_list> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends Shopping_list> c) {
        return false;
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Shopping_list get(int index) {
        return null;
    }

    @Override
    public Shopping_list set(int index, Shopping_list element) {
        return null;
    }

    @Override
    public void add(int index, Shopping_list element) {

    }

    @Override
    public Shopping_list remove(int index) {
        return null;
    }

    @Override
    public int indexOf(@Nullable Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(@Nullable Object o) {
        return 0;
    }

    @NonNull
    @Override
    public ListIterator<Shopping_list> listIterator() {
        return null;
    }

    @NonNull
    @Override
    public ListIterator<Shopping_list> listIterator(int index) {
        return null;
    }

    @NonNull
    @Override
    public List<Shopping_list> subList(int fromIndex, int toIndex) {
        return null;
    }
}
