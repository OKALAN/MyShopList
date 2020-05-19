package com.projetandroid.myshoplist.adapter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.projetandroid.myshoplist.Add_shopping_list;
import com.projetandroid.myshoplist.R;
import com.projetandroid.myshoplist.Shopping_List;
import com.projetandroid.myshoplist.dialog.Confirm_dialog;
import com.projetandroid.myshoplist.entities.Article_details;
import com.projetandroid.myshoplist.entities.Shopping_list;
import com.projetandroid.myshoplist.utils.Shared;
import me.piruin.quickaction.ActionItem;
import me.piruin.quickaction.QuickAction;


public class Shopping_adapter extends BaseAdapter {


    private List<Shopping_list> dtList = new ArrayList<Shopping_list>();
    private Activity context;
    private LayoutInflater inflater;

    public Shopping_adapter(Activity context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public Shopping_adapter(List<Shopping_list> dtList, Activity context) {
        this.dtList = dtList;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private class ViewHolder {
        TextView title;
        TextView qty;

        TextView AddDate;
        TextView budget;
        ImageView icon;
        ImageView action;

    }

    @Override
    public int getCount() {
        return dtList.size();
    }


    public List<Shopping_list> getData() {
        return dtList;
    }

    public void set(List<Shopping_list> list) {
        dtList = list;
        notifyDataSetChanged();
    }
    public void remove(Shopping_list data) {
        dtList.remove(data);
        notifyDataSetChanged();
    }
    public void removeAll() {
        dtList = new ArrayList<Shopping_list>();
        notifyDataSetChanged();
    }

    public void removeByID(long id) {
        for (int i = 0; i < dtList.size(); i++) {
            if(dtList.get(i).getId() == id)
                dtList.remove(i);
        };
        notifyDataSetChanged();
    }

    public void addMany(List<Shopping_list>  dt) {
        for (int i = 0; i < dt.size(); i++)
        {
            dtList.add(dt.get(i));
        }

        notifyDataSetChanged();
    }

    public void add(Shopping_list data) {
        dtList.add(data);
        notifyDataSetChanged();
    }

    public void insert(Shopping_list data,int index) {
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
            vi = inflater.inflate(R.layout.shopping_list_item, null);
            holder = new ViewHolder();

            holder.AddDate = (TextView) vi.findViewById(R.id.textView2);
            holder.title = (TextView) vi.findViewById(R.id.textView4);
            holder.qty = (TextView) vi.findViewById(R.id.textView5);
            holder.budget = (TextView) vi.findViewById(R.id.textView6);
            holder.icon = (ImageView) vi.findViewById(R.id.imageView2);
            holder.action = (ImageButton) vi.findViewById(R.id.imageButton);

            vi.setTag(holder);
        } else {
            holder=(ViewHolder)vi.getTag();
        }

        final Shopping_list data = (Shopping_list) getItem(position);

        if(data.shopping_list_name.length() >= 30)
            holder.title.setText(data.shopping_list_name.substring(0,29)+"...");
        else
            holder.title.setText(data.shopping_list_name);

        holder.AddDate.setText(dateFromLong(data.Date));
        holder.qty.setText("Total item" + ": " + data.totalitem + "  "+ "TotalPrice"+": " +  Shared.decimalformat.format(data.totalprices));
        holder.budget.setText("Budget " + ": " + data.budget);

        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(data.shopping_list_name.substring(0,1));
        TextDrawable drawable = TextDrawable.builder().buildRound(data.shopping_list_name.substring(0,1).toUpperCase(), color);
        holder.icon.setImageDrawable(drawable);

        QuickAction.setDefaultColor(ResourcesCompat.getColor(context.getResources(), R.color.light_brown, null));
        ActionItem share = new ActionItem(4, "Share");
        ActionItem copy = new ActionItem(1, "Copy");
        ActionItem edit = new ActionItem(2, "Edit");
        ActionItem delete = new ActionItem(3, "Delete");

        final QuickAction quickAction = new QuickAction(context, QuickAction.HORIZONTAL);
        quickAction.setColorRes(R.color.light_brown);
        quickAction.setTextColorRes(R.color.deep_grey);

        quickAction.addActionItem(share);
        quickAction.addActionItem(copy);
        quickAction.addActionItem(edit);
        quickAction.addActionItem(delete);

        quickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
            @Override public void onItemClick(ActionItem item) {
                if(item.getActionId() == 1)
                {
                    Shopping_list d = new Shopping_list();
                    d.shopping_list_name = data.shopping_list_name;
                    d.shopping_date = new Date();
                    d.status = 0;
                    d.totalitem = data.totalitem;
                    d.totalprices = data.totalprices;
                    d.save();

                    List<Article_details> detail = Article_details.find(Article_details.class, "idarticle = ?", String.valueOf(data.getId()));
                    for(int i = 0; i < detail.size();i++)
                    {
                        Article_details dt = new Article_details();
                        dt.price = detail.get(i).price;
                        dt.status = 0;
                        dt.amount_unity = detail.get(i).amount_unity;
                        dt.id_Article = d.getId();
                        dt.Article_name = detail.get(i).Article_name;
                        dt.save();
                    }

                    dtList.add(d);
                    notifyDataSetChanged();

                }
                else if(item.getActionId() == 2)
                {
                    Intent intent = new Intent(context, Add_shopping_list.class);
                    intent.putExtra("id",data.getId());
                    context.startActivityForResult(intent,10001);
                    context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
                else if(item.getActionId() == 3)
                {
                    Confirm_dialog dialog = new Confirm_dialog(context,context.getString(R.string.Ask_to_delete),context.getString(R.string.ok),context.getString(R.string.cancel));
                    dialog.setConfirmListener(new Confirm_dialog.ConfirmListener() {
                        @Override
                        public void onOK() {
                           Article_details.deleteAll(Article_details.class, "idarticle = ?", String.valueOf(data.getId()));
                            data.delete();
                            dtList.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(context,R.string.sucess_del_shopping,Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancel() {

                        }
                    });

                    dialog.show();
                }
                else if(item.getActionId() == 4)
                {
                    List<Article_details> detail = Article_details.find(Article_details.class, "idarticle = ?", String.valueOf(data.getId()));

                    String text = data.shopping_list_name + " \n" + Shared.dateformatAdd.format(data.shopping_date)+ "\n";
                    double total = 0;
                    for(int i = 0; i < detail.size();i++)
                    {
                        text += "\n " + detail.get(i).Article_name;
                        text += "\n " + detail.get(i).amount_unity + " x " + Shared.decimalformat.format(detail.get(i).price);
                        total += detail.get(i).amount_unity * detail.get(i).price ;
                    }

                    text +="\n\n "+context.getString(R.string.totalPrice)+": " + Shared.decimalformat.format(total);

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, text);
                    context.startActivity(Intent.createChooser(intent, context.getString(R.string.share_via)));
                }

            }
        });

        holder.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quickAction.show(v);
            }
        });

        return vi;
    }


    private  ArticleAddAdapter.changeListener listener;
    public void setChangeListener(ArticleAddAdapter.changeListener listener ){ this.listener = listener;}

    public interface changeListener {
        public void onChange();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if(listener != null)
            listener.onChange();
    }

    public static String dateFromLong(long time) {
        DateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy 'at' hh:mm aaa", Locale.US);
        return format.format(new Date(time));
    }


}

