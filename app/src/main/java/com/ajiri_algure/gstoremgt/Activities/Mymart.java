package com.ajiri_algure.gstoremgt.Activities;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ajiri_algure.gstoremgt.R;
import com.ajiri_algure.gstoremgt.adapter.myItemAdapter;
import com.ajiri_algure.gstoremgt.market_data.itemDao;
import com.ajiri_algure.gstoremgt.market_data.maeketItems;
import com.ajiri_algure.gstoremgt.market_data.marketRoom;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Mymart extends AppCompatActivity {

    private Toolbar toolbar;
    private DatabaseReference dbref;
    private DataSnapshot snapshot;
    private marketRoom mroom;
    private ArrayList<maeketItems> Items;
    private RecyclerView rview;
    private ProgressBar pbar;


//    public void orderMgt(View view) {
//        startActivity(new Intent(this,Orders.class));
//    }
//        public void openprofile(View view){
//        startActivity(new Intent(this,Profile.class));
//    }
    public void openMarket(View view){
        startActivity(new Intent(this,Mymart.class));
    }
    public void switchUser(View view){
//        startActivity(new Intent(this,LoginActivity.class));
    }
    public void UploadI(View view){
        startActivity(new Intent(
                this,Main2Activity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymart);
        try {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            toolbar = findViewById(R.id.tb);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Travelmatics");
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            Items=new ArrayList<maeketItems>();
            rview = findViewById(R.id.recyclerView);
            pbar = findViewById(R.id.pbar);
            mroom = Room.databaseBuilder(getApplicationContext(), marketRoom.class, "myitems").build();
            dbref = FirebaseDatabase.getInstance().getReference().child("Gstore").child("https:").child("gmart9dff0firebaseiocom").
                    child("Gstore");
            setListFromDatabase();
            updateMarket um = new updateMarket();
            um.execute();
        }catch (Exception e){
            Log.i("kjods",e.toString());
        }
    }


    public class updateMarket extends AsyncTask<Void, Void, Void> {
        public String downloadstatus = "";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            SharedPreferences sp=getSharedPreferences("user_prefs",MODE_PRIVATE);
            String id=sp.getString("id","");
            dbref.orderByChild("brand").equalTo(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        Log.i("oijao", "downloading");
                        snapshot = dataSnapshot;
//                         Items = new ArrayList<maeketItems>();
                        for (DataSnapshot items : snapshot.getChildren()) {
                            maeketItems mitem = items.getValue(maeketItems.class);
                            Items.add(mitem);
                        }
                        if (Items.size() > 0) {
                            saveroom sr = new saveroom();
                            sr.execute();
                            Log.i("oijaso", "downloading");
                            publishProgress();
                        }
                    } catch (Exception e) {
                        Log.i("jguil", e.toString());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    upnote="error";
                    publishProgress();
                }
            });

            publishProgress();
            return null;
        }
        String upnote="";
        @Override
        protected void onProgressUpdate(Void... values) {
            if (!upnote.matches("")) {
                Toast.makeText(getBaseContext(),upnote,Toast.LENGTH_SHORT).show();}
            super.onProgressUpdate(values);
        }
    }

    public class saveroom extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);

        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                itemDao dao = mroom.getitemDao();
                ArrayList<maeketItems> mmitems = (ArrayList<maeketItems>) dao.getAllItems();

                for (maeketItems mitem : mmitems) {
                    dao.delete(mitem);
                }
                Log.i("totalitem", String.valueOf(dao.getAllItems().size()));
                for (DataSnapshot items : snapshot.getChildren()) {
                    maeketItems mitem = items.getValue(maeketItems.class);
                    String idPtitle = mitem.getTitle() + "," + items.getKey();
                    mitem.setTitle(idPtitle);
                    dao.insertAll(mitem);
                }
                objs = dao.getAllItems().size();

                Log.i("totalitemafter", String.valueOf(dao.getAllItems().size()));
                Items = (ArrayList<maeketItems>) dao.getAllItems();
//                itembits=null;
                publishProgress();
            } catch (Exception e) {
                Log.i("kjiodzs", e.toString());
            }
            return null;
        }

        String updateType;
        int objs;

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
//            Toast.makeText(getApplicationContext(),String.valueOf(objs),Toast.LENGTH_SHORT).show();
            setlist();
        }

    }

    public class setroom extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                itemDao dao = mroom.getitemDao();
                Items = (ArrayList<maeketItems>) dao.getAllItems();
//
//                publishProgress();
            } catch (Exception e) {
                Log.i("kjiodzs", e.toString());
            }finally {
                publishProgress();
            }
            return null;
        }

        String updateType;

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            setlist();

        }

    }
    public void progress(boolean b) {
        if (b) {
            pbar.setVisibility(View.VISIBLE);
        } else {
            pbar.setVisibility(View.GONE);
        }
    }
    public void setlist() {
        try {

            progress(false);
            rview.setLayoutManager(new LinearLayoutManager(this));
            myItemAdapter myadapter = new myItemAdapter(Items, null, this);
            rview.setAdapter(myadapter);

//            Log.i("oihjo", String.valueOf(Items.size()));
        } catch (Exception e) {
            Log.i("oihjoids", e.toString());
        }
    }

    public void setListFromDatabase() {
        try {
            progress(true);
            setroom sr = new setroom();
            sr.execute();
        } catch (Exception e) {
            Log.i("iohogr", e.toString());
        }
    }
}
