package com.projetandroid.myshoplist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.projetandroid.myshoplist.entities.User;
import com.projetandroid.myshoplist.loginsuscribe.suscribe;

import java.util.*;

import static java.lang.String.valueOf;

public class Login extends AppCompatActivity implements List<User> {
    private List<User> users = new ArrayList<User>();
    private EditText pseudo;
    private EditText password;
    private String pseu,  pwd;
    private Button btnSuscribe, btnLogin;
    private Adapter adapter;
    public static final String COL_3 ="pseudo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pseudo = (EditText)findViewById(R.id.editPseudo);
        password = (EditText)findViewById(R.id.editPassword);
        btnLogin = (Button)findViewById(R.id.register_button);
        btnSuscribe = (Button)findViewById(R.id.suscribe_button);




        btnSuscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, suscribe.class);
                startActivity(intent);

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pseu = pseudo.getText().toString().trim() ;
                pwd = password.getText().toString().trim();

                Select theUserQuery = Select.from(User.class).where(Condition.prop("pseudo").eq(pseu), Condition.prop("password").eq(pwd));
               User user = (User) theUserQuery.first();

               Log.d(String.valueOf(user), "user");
                if ( user == null ){
                    Toast.makeText(Login.this, "pseudo or password is unright !!!", Toast.LENGTH_SHORT).show();
                 }

                 else
                 {

                     Intent intent = new Intent(Login.this, MainActivity.class);
                     startActivity(intent);
                     finish();

                 }



            }
        });

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
    public Iterator<User> iterator() {
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
    public boolean add(User user) {
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
    public boolean addAll(@NonNull Collection<? extends User> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends User> c) {
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
    public User get(int index) {
        return null;
    }

    @Override
    public User set(int index, User element) {
        return null;
    }

    @Override
    public void add(int index, User element) {

    }

    @Override
    public User remove(int index) {
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
    public ListIterator<User> listIterator() {
        return null;
    }

    @NonNull
    @Override
    public ListIterator<User> listIterator(int index) {
        return null;
    }

    @NonNull
    @Override
    public List<User> subList(int fromIndex, int toIndex) {
        return null;
    }
}
