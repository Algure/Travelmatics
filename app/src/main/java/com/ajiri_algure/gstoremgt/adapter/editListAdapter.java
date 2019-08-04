package com.ajiri_algure.gstoremgt.adapter;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.ajiri_algure.gstoremgt.Activities.editItem;
import com.ajiri_algure.gstoremgt.R;
import com.ajiri_algure.gstoremgt.market_data.maeketItems;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 19/01/2019.
 */

public class editListAdapter extends ArrayAdapter {
    private List<String> imagebit;
    String[] pics;
    Context context;
    maeketItems Items;
   public static ArrayList<String> allpics,removed;
    public editListAdapter(@NonNull Context context, int resource,String pics) {
        super(context, resource);
        this.context=context;

        allpics=new ArrayList<String>();
        removed=new ArrayList<String>();
       this.pics=pics.split(",");
       for (String p:this.pics){
           allpics.add(p);
       }
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myinflater = LayoutInflater.from(getContext());
        final View customView = (View) myinflater.inflate(R.layout.selpic2, parent, false);
        final ImageView imv=customView.findViewById(R.id.imv);
        Picasso.with(context).load(Uri.parse(pics[position]))
                .placeholder(R.drawable.img)
                .into(imv);
        final ImageButton imb=customView.findViewById(R.id.imageButton);
        imb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                editItem.picChange.replace(pics[position],"").replace(",,",",");
                if (!removed.contains(pics[position])) {
//                    imb.setImageResource((R.drawable.clearx));
                    imv.setVisibility(View.GONE);
                    rem3cloud r3=new rem3cloud(customView,allpics.get(position));
                    r3.execute();
                    removed.add(allpics.get(position));
                }else{
                    imb.setImageResource(R.drawable.clear);
                    imv.setVisibility(View.VISIBLE);
//                    removed.remove(removed.indexOf(pics[position]));

                }

//                editItem.picChange = "";
//                for (String p : allpics) {
//                    if (!removed.contains(p)) {
//                        editItem.picChange += "," + p;
//                    }
//                }
//                Log.i("iu9es90j",editItem.picChange);
            }
        });
        return customView;
    }

    @Override
    public int getCount() {
        return pics.length;
    }


    public class rem3cloud extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        String cloudSave;
        View cview;
        String pos;
        public rem3cloud(View view, String position){
            cview=view;
            pos=position;
        }
        String task;
        int pcenat;
        @Override
        protected Void doInBackground(Void... voids) {

            try {

                task="i";
                publishProgress();
                StorageReference photoref= FirebaseStorage.getInstance().getReferenceFromUrl(pos);
                photoref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        task="d";
                        publishProgress();
                    }
                });
            }catch (Exception e){
                Log.i("kjiodzsghgf",e.toString());

            }
            return null;
        }

        String updateType;
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            ProgressBar pp=cview.findViewById(R.id.pba);
            if (task.matches("s")){
//                TextView stat=cview.findViewById(R.id.status);
//                if(pcenat==100){
//                    stat.setText("");
//                }
//                pp.setProgress(pcenat);

//                Toast.makeText(context,String.valueOf(pcenat),Toast.LENGTH_SHORT).show();
            }else if(task.matches("d")){

                final ImageButton imb=cview.findViewById(R.id.imageButton);
                TextView stat=cview.findViewById(R.id.status);
                pp.setProgress(100);
            }else if(task.matches("i")){
                Toast.makeText(context,"deleting",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);
        }
    }
}
