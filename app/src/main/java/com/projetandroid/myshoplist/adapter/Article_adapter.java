package com.projetandroid.myshoplist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.projetandroid.myshoplist.*;
import com.projetandroid.myshoplist.dialog.Confirm_dialog;
import com.projetandroid.myshoplist.entities.Article;
import com.projetandroid.myshoplist.utils.Shared;

import java.util.ArrayList;
import java.util.List;

public class Article_adapter extends BaseAdapter {


    private List<Article> dtList = new ArrayList<Article>();
    private Context context;
    private LayoutInflater inflater;
    public Article_adapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public Article_adapter(Context context, List<Article> data) {

        this.context = context;
        this.dtList = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private class ViewHolder {
        TextView title;
        TextView price;
        ImageButton delete;
    }

    public void replace(Article data,int position) {
        dtList.set(position, data);
        notifyDataSetChanged();
    }

    public int getCount() {
        return dtList.size();
    }

    public List<Article> getData() {
        return dtList;
    }

    public void set(List<Article> list) {
        dtList = list;
        notifyDataSetChanged();
    }
    public void remove(Article data) {
        dtList.remove(data);
        notifyDataSetChanged();
    }
    public void removeAll() {
        dtList = new ArrayList<Article>();
        notifyDataSetChanged();
    }

    public void removeByID(long id) {
        for (int i = 0; i < dtList.size(); i++) {
            if(dtList.get(i).getId() == id)
                dtList.remove(i);
        };
        notifyDataSetChanged();
    }

    public void addMany(List<Article>  dt) {
        for (int i = 0; i < dt.size(); i++)
        {
            dtList.add(dt.get(i));
        }

        notifyDataSetChanged();
    }

    public void add(Article data) {
        dtList.add(data);
        notifyDataSetChanged();
    }

    public void insert(Article data,int index) {
        dtList.add(index, data);
        notifyDataSetChanged();
    }

    public Object getItem(int position) {
        return dtList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;

        if (convertView == null) {
            vi = inflater.inflate(R.layout.article_list_item, null);
            holder = new ViewHolder();

            holder.title = (TextView) vi.findViewById(R.id.textView4);
            holder.price = (TextView) vi.findViewById(R.id.textView5);
            holder.delete = (ImageButton) vi.findViewById(R.id.imageButton);

            vi.setTag(holder);
        } else {
            holder=(ViewHolder)vi.getTag();
        }

        final Article data = (Article) getItem(position);

        if(data.Article_name.length() >= 30)
            holder.title.setText(data.Article_name.substring(0,29)+"...");
        else
            holder.title.setText(data.Article_name);

        holder.price.setText(Shared.decimalformat.format(data.Article_price) );

        holder.delete.setOnClickListener(new View.OnClickListener() {
            private Object Confirm_dialog;

            @Override
            public void onClick(View v) {
                Confirm_dialog dialog= new Confirm_dialog(context,context.getString(R.string.dialog_to_del),context.getString(R.string.ok),context.getString(R.string.cancel));
                dialog.setConfirmListener(new Confirm_dialog.ConfirmListener() {
                    @Override
                    public void onOK() {
                        dtList.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(context,R.string.item_successfully_saved,Toast.LENGTH_SHORT).show();
                        data.delete();
                    }

                    @Override
                    public void onCancel() {

                    }
                });

                dialog.show();
            }
        });

        return vi;
    }

    private changeListener listener;
    public void setChangeListener(changeListener listener)
    {
        this.listener = listener;
    }

    public interface changeListener {
        public void onChange();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if(listener != null)
            listener.onChange();
    }



}
