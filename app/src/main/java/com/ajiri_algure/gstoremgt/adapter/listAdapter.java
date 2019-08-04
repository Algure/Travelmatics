package com.ajiri_algure.gstoremgt.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
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

import com.ajiri_algure.gstoremgt.Activities.Main2Activity;
import com.ajiri_algure.gstoremgt.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

/**
 * Created by HP on 29/12/2018.
 */

public class listAdapter extends ArrayAdapter {
    private final StorageReference mStorageRef;
    ArrayList<Bitmap> list;
    Context context;
    ArrayList<Uri> files;
    boolean[] aBoolean;
    public listAdapter(@NonNull Context context, int resource, ArrayList<Bitmap> list,ArrayList<Uri> fileloc) {
        super(context, resource);
        this.list=list;
        files=fileloc;
        this.context=context;
        aBoolean=new boolean[list.size()];
        for (int j=0;j<aBoolean.length;j++){
            aBoolean[j]=false;
        }
        mStorageRef = FirebaseStorage.getInstance().getReference("market");
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater myinflater = LayoutInflater.from(getContext());
        final View customView = (View) myinflater.inflate(R.layout.selpics, parent, false);

        final TextView stat=customView.findViewById(R.id.status);
        Bitmap b=(list.get(position));
        ImageView imv=customView.findViewById(R.id.imv);
        final ProgressBar pb=customView.findViewById(R.id.pba);
        imv.setImageBitmap(b);
        final ImageButton imb=customView.findViewById(R.id.imageButton);
        imb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(aBoolean[position])) {
                    save3cloud sv = new save3cloud(customView, position);
                    sv.execute();
                    imb.setBackground(context.getDrawable( R.drawable.yelback));
                    aBoolean[position]=true;
                }
            }
        });
        return customView;
    }
    private String getTime() {
        String id= String.valueOf(FirebaseDatabase.getInstance().getReference().push());
        id.replaceAll("\\-","")
                .replaceAll("\\#","")
                .replaceAll("\\.","")
                .replaceAll("\\$","")
                .replaceAll("\\[","")
                .replaceAll("\\]","");
        return  id;
    }

    public class save3cloud extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        String cloudSave;
        View cview;
        int pos;
        public save3cloud(View view, int position){
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
                UploadTask uploadtask = mStorageRef.child(getTime()).putFile((files.get(pos)));
                uploadtask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        long state=taskSnapshot.getBytesTransferred();
                        long tot=taskSnapshot.getTotalByteCount();
                        double pcent=100.0*(state/tot);
                        Log.i("kuhie",String.valueOf(pcent));
                        pcenat= (int) pcent;
                        task="s";
                        publishProgress();
                    }});

                uploadtask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Main2Activity.downloadurls.add((taskSnapshot.getDownloadUrl().toString()));
                        task="d";

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
                TextView stat=cview.findViewById(R.id.status);
                if(pcenat==100){
                    stat.setText("");
                }
                pp.setProgress(pcenat);

//                Toast.makeText(context,String.valueOf(pcenat),Toast.LENGTH_SHORT).show();
            }else if(task.matches("d")){

                final ImageButton imb=cview.findViewById(R.id.imageButton);
                TextView stat=cview.findViewById(R.id.status);
                pp.setProgress(100);
                imb.setBackgroundColor(Color.GREEN);
                stat.setVisibility(View.VISIBLE);
            }else if(task.matches("i")){
                Toast.makeText(context,"uploading",Toast.LENGTH_SHORT).show();
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
