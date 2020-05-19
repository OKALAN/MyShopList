package com.projetandroid.myshoplist.loginsuscribe;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.projetandroid.myshoplist.Login;
import com.projetandroid.myshoplist.R;
import com.projetandroid.myshoplist.entities.User;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class suscribe extends AppCompatActivity  implements List<User> {
    private EditText editName, editFamNme, editPseudo, editPassword;
    private User user = new User();
    private Button btnSuscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suscribe);

        editName = (EditText)findViewById(R.id.editName);
        editFamNme = (EditText)findViewById(R.id.editFamily);
        editPseudo = (EditText)findViewById(R.id.editPseudo2);
        editPassword = (EditText)findViewById(R.id.editPassword2);
        btnSuscribe = (Button)findViewById(R.id.suscribe_button2);

        btnSuscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save();
                Intent intent = new Intent(suscribe.this, Login.class);
                startActivity(intent);

            }
        });


    }

    private void Save() {
         if ( editPseudo.getText().toString() != null &&  editPassword.getText().toString() != null )
        user.UserName = editName.getText().toString();
        user.UserFamilyName = editFamNme.getText().toString();
        user.pseudo = editPseudo.getText().toString();
        user.password = editPassword.getText().toString();
        user.save();
        setResult(RESULT_OK);
        Toast.makeText(this, "Suscribe successful !!!", Toast.LENGTH_SHORT).show();
         finish();
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
