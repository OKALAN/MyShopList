package com.projetandroid.myshoplist.adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import com.projetandroid.myshoplist.R;
import com.projetandroid.myshoplist.dialog.AddArticle_dialog;
import com.projetandroid.myshoplist.dialog.Confirm_dialog;
import com.projetandroid.myshoplist.entities.Article;
import com.projetandroid.myshoplist.entities.Article_add;
import com.projetandroid.myshoplist.utils.Shared;

public class ArticleAddAdapter extends BaseAdapter {

    private List<Article_add> dtList = new ArrayList<Article_add>();
    private Activity context;
    private LayoutInflater inflater;

    public ArticleAddAdapter(List<Article_add> dtList, Activity context) {
        this.dtList = dtList;
        this.context = context;
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    public ArticleAddAdapter(Activity context) {
        this.context = context;
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    private class ViewHolder {
        TextView title;
        TextView quantity;
        TextView subtotal;
        ImageButton delete;
    }

    public void replace(Article_add data, int position)
    {
        dtList.set(position, data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dtList.size();
    }

    public List<Article_add> getData() {
        return dtList;
    }

    public void set(List<Article_add> list) {
        dtList = list;
        notifyDataSetChanged();
    }
    public void remove(Article data) {
        dtList.remove(data);
        notifyDataSetChanged();
    }

    public void removeAll() {
        dtList = new ArrayList<Article_add>();
        notifyDataSetChanged();
    }


    public void removeByID(long id) {
        for (int i = 0; i < dtList.size(); i++) {
            if(dtList.get(i).getId() == id)
                dtList.remove(i);
        };
        notifyDataSetChanged();
    }

    public void addMany(List<Article_add>  dt) {
        for (int i = 0; i < dt.size(); i++)
        {
            dtList.add(dt.get(i));
        }

        notifyDataSetChanged();
    }

    public void add(Article_add data) {
        dtList.add(data);
        notifyDataSetChanged();
    }

    public void insert(Article_add data,int index) {
        dtList.add(index, data);
        notifyDataSetChanged();
    }
    
    @Override
    public Object getItem(int position) {
        return dtList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;

        if (convertView == null) {
            vi = inflater.inflate(R.layout.article_add_item, null);
            holder = new ViewHolder();

            holder.title = (TextView) vi.findViewById(R.id.textView4);
            holder.quantity = (TextView) vi.findViewById(R.id.textView5);
            holder.subtotal = (TextView) vi.findViewById(R.id.textView6);
            holder.delete = (ImageButton) vi.findViewById(R.id.imageButton);

            vi.setTag(holder);
        } else {
            holder=(ViewHolder)vi.getTag();
        }

        final Article_add data = (Article_add) getItem(position);

        if(data.Article_name.length() >= 30)
            holder.title.setText(data.Article_name.substring(0,29)+"...");
        else
            holder.title.setText(data.Article_name);

        holder.quantity.setText(data.quantity + " x " + Shared.decimalformat.format(data.Article_price) );
        holder.subtotal.setText(Shared.decimalformat.format(data.Article_price * data.quantity));

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Confirm_dialog dialog = new Confirm_dialog(context,context.getString(R.string.success_del),context.getString(R.string.ok),context.getString(R.string.cancel));
                dialog.setConfirmListener(new Confirm_dialog.ConfirmListener() {
                    @Override
                    public void onOK() {
                        dtList.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(context,R.string.Ask_to_delete,Toast.LENGTH_SHORT).show();
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
