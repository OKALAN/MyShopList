package com.projetandroid.myshoplist.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.projetandroid.myshoplist.R;
import com.projetandroid.myshoplist.entities.Article_details;
import com.projetandroid.myshoplist.utils.Shared;

import java.util.ArrayList;
import java.util.List;

import static com.projetandroid.myshoplist.adapter.Shopping_adapter.dateFromLong;

public class Journal_adapter extends BaseAdapter {

    private List<Article_details> dtListe = new ArrayList<Article_details>();
    private Activity context;
    private LayoutInflater inflater;

    public Journal_adapter(Activity context1) {
        this.context = context1;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public Journal_adapter(List<Article_details> dtListe1, Activity context1) {
        this.dtListe = dtListe1;
        this.context = context1;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void setDtListe(List<Article_details> dtListe) {
        this.dtListe = dtListe;
        notifyDataSetChanged();
    }

    public List<Article_details> getDtListe() {
        return dtListe;
    }

    public class ViewHolder{
       public TextView checkedDate;
       public TextView title;
       public TextView qty;
       public TextView subtotal;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if (convertView == null){
            holder=(ViewHolder)vi.getTag();
        }
        else{
            vi = inflater.inflate(R.layout.activity_article_bought, null);
            holder = new ViewHolder();

            holder.checkedDate = (TextView)vi.findViewById(R.id.textView);
            holder.title = (TextView)vi.findViewById(R.id.textView1);
            holder.qty = (TextView)vi.findViewById(R.id.textView2);
            holder.subtotal = (TextView)vi.findViewById(R.id.textView3);
            vi.setTag(holder);

        }
        final Article_details data = (Article_details)getItem(position);

        if(data.Article_name.length() >=30)
            holder.title.setText(data.Article_name.substring(0,29)+"...");
        else
            holder.title.setText(data.Article_name);

        holder.qty.setText(data.amount_unity+" x " + Shared.decimalformat.format(data.price) );
        holder.subtotal.setText(Shared.decimalformat.format(data.price * data.amount_unity));
        holder.checkedDate.setText(dateFromLong(data.datechecked));

        return vi;

    }



   public ArticleCheck_adapter.changeListener listener;
    public void setChangeListener(ArticleCheck_adapter.changeListener listener)
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
