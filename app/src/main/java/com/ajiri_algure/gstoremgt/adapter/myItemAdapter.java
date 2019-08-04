package com.ajiri_algure.gstoremgt.adapter;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajiri_algure.gstoremgt.Activities.EditMartItems;
import com.ajiri_algure.gstoremgt.R;
import com.ajiri_algure.gstoremgt.favorites.favoriteClass;
import com.ajiri_algure.gstoremgt.market_data.maeketItems;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

//import com.algure.gmart.Activities.Itemdescription;

//import com.algure.gmart.Activities.itemdescription;
//import com.bumptech.glide.Glide;

/**
 * Created by HP on 01/01/2019.
 */

public class myItemAdapter extends RecyclerView.Adapter<myItemAdapter.ViewHolder> {
    public ArrayList<String> imagebit= new ArrayList<String>();
    public ArrayList<maeketItems> items=new ArrayList<maeketItems>();
    public Context context;
    public ArrayList<favoriteClass> favItems;
    private SharedPreferences sp;

    public myItemAdapter(ArrayList<maeketItems> items, @Nullable ArrayList<favoriteClass> favItems, Context context){
        this.favItems=favItems;
        this.items=items;
        this.context=context;
        sp=context.getSharedPreferences("user_prefs",MODE_PRIVATE);
        for (maeketItems mt:items){
            imagebit.add(mt.getPics().split(",")[0]);
            Log.i("dmpoerf",mt.getTitle());
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);

        View itemView=inflater.inflate(R.layout.marketitems,parent,false);
        ViewHolder viewHolder=new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        try {
            Picasso.with(context).load(Uri.parse(imagebit.get(position)))
                    .placeholder(R.drawable.img)
                    .into(holder.imv);
            final String t=items.get(position).getTitle();//.split(",");
            String[] price=items.get(position).getPrice().contains(",")?items.get(position).getPrice().split(","): new String[]{items.get(position).getPrice()};
            String rprice="\u20a6 "+price[0];
            if (price.length>1 && !(price[1].matches("")||price[1].matches("0"))){
                rprice+="+"+price[1];
            }
            holder.name.setText(t);
            holder.price.setText(rprice);

            holder.availability.setText(items.get(position).getDescription());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    maeketItems it = items.get(position);
                    Intent i = new Intent(context, EditMartItems.class);
                    i.putExtra("title", it.getTitle());
                    i.putExtra("description", it.getDescription());
                    i.putExtra("price", it.getPrice());
                    context.startActivity(i);
                }
            });
            // to set favorite if it is contained in favorite class
        }catch (Exception e){
            Log.i("jugiu",e.toString());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imv;
        TextView price;
        TextView name;
        ImageButton ib;
        TextView availability;
        public ViewHolder(View itemView) {
            super(itemView);
            imv=itemView.findViewById(R.id.imv);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
            ib=itemView.findViewById(R.id.ib);
            availability=itemView.findViewById(R.id.textView2);
        }
    }

    maeketItems favm;
    String favid;

}
