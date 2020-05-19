package com.projetandroid.myshoplist.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import com.projetandroid.myshoplist.R;
import com.projetandroid.myshoplist.dialog.Modif_article_dialog;
import com.projetandroid.myshoplist.entities.Article_details;
import com.projetandroid.myshoplist.entities.Shopping_list;
import com.projetandroid.myshoplist.utils.Shared;

import java.util.ArrayList;
import java.util.List;



public class ArticleCheck_adapter extends BaseAdapter {

    private List<Article_details> dtList = new ArrayList<Article_details>();
    private Activity context;
    private LayoutInflater inflater;

    public ArticleCheck_adapter(Activity context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ArticleCheck_adapter(List<Article_details> dtList, Activity context) {
        this.dtList = dtList;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    private class ViewHolder {
        TextView title;
        TextView qty;
        TextView subtotal;
        ImageButton action;
        CheckBox checkBox;
    }

    public void replace(Article_details data,int position) {
        dtList.set(position, data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dtList.size();
    }

    public List<Article_details> getData() {
        return dtList;
    }

    public void set(List<Article_details> list) {
        dtList = list;
        notifyDataSetChanged();
    }

    public void remove(Article_details data) {
        dtList.remove(data);
        notifyDataSetChanged();
    }
    public void removeAll() {
        dtList = new ArrayList<Article_details>();
        notifyDataSetChanged();
    }



    public void removeByID(long id) {
        for (int i = 0; i < dtList.size(); i++) {
            if(dtList.get(i).getId() == id)
                dtList.remove(i);
        };
        notifyDataSetChanged();
    }


    public void checked(int position,boolean check) {
       Article_details b = dtList.get(position);
        b.status = check ? 1 : 0;
        dtList.set(position,b);
        b.save();
        notifyDataSetChanged();
    }



    public void addMany(List<Article_details>  dt) {
        for (int i = 0; i < dt.size(); i++)
        {
            dtList.add(dt.get(i));
        }

        notifyDataSetChanged();
    }

    public void add(Article_details data) {
        dtList.add(data);
        notifyDataSetChanged();
    }

    public void insert(Article_details data,int index) {
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
            vi = inflater.inflate(R.layout.article_check_item, null);
            holder = new ViewHolder();

            holder.title = (TextView) vi.findViewById(R.id.textView4);
            holder.qty = (TextView) vi.findViewById(R.id.textView5);
            holder.subtotal = (TextView) vi.findViewById(R.id.textView6);
            holder.action = (ImageButton) vi.findViewById(R.id.imageButton);
            holder.checkBox = (CheckBox)vi.findViewById(R.id.checkBox);

            vi.setTag(holder);
        } else {
            holder=(ViewHolder)vi.getTag();
        }

        final Article_details data = (Article_details) getItem(position);

        if(data.Article_name.length() >= 30)
            holder.title.setText(data.Article_name.substring(0,29)+"...");
        else
            holder.title.setText(data.Article_name);

        holder.qty.setText(data.amount_unity + " x " + Shared.decimalformat.format(data.price) );
        holder.subtotal.setText(Shared.decimalformat.format(data.price * data.amount_unity));
        holder.checkBox.setChecked(data.status == 1 ? true  : false);

       holder.action.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Modif_article_dialog dialog  =  new Modif_article_dialog(context,data);
               dialog.setListener(new Modif_article_dialog.Modif_article_dialogListener() {
                   @Override
                   public void onFinish(Article_details articleDetails) {

                       dtList.set(position, articleDetails);

                       List<Article_details> details = Article_details.find(Article_details.class,"idArticle",articleDetails.id_Article.toString() );
                       int qty = 0;
                       double total = 0;
                       for(int i = 0; i < details.size();i++)
                       {
                           qty += details.get(i).amount_unity;
                           total += ( details.get(i).price * details.get(i).amount_unity);
                       }
                       Shopping_list shoplist = Shopping_list.findById(Shopping_list.class, articleDetails.id_Article);
                      shoplist.totalitem = qty;
                      shoplist.totalprices = total;
                      shoplist.save();

                       context.setResult(Activity.RESULT_OK);
                       notifyDataSetChanged();

                   }


               });
               dialog.show();
           }
       });
        holder.checkBox.setClickable(false);

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