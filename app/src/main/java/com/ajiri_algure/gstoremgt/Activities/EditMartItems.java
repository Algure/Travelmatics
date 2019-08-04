package com.ajiri_algure.gstoremgt.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ajiri_algure.gstoremgt.R;
import com.ajiri_algure.gstoremgt.adapter.editListAdapter;
import com.ajiri_algure.gstoremgt.market_data.maeketItems;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class EditMartItems extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE =111 ;
    private Toolbar toolbar;
    String title;
    private DatabaseReference itemref;
    ScrollView sv;
    ProgressBar pb;
    ImageView gv;
    private String description,details,price,pics;
    String[] pictures,prices;
    EditText t_title,t_description,t_details,t_price,t_gprice,s_price;
    TextView des,sellerprof;
    private DatabaseReference myRef;
    private SharedPreferences sp;


    public void UploadI(View view){
        startActivity(new Intent(this,Main2Activity.class));
    }
    public void deletePic(View view){
    }
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mart_items);
        toolbar = findViewById(R.id.tb);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Gmart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try {
            sv = findViewById(R.id.scrollView1);
            pb = findViewById(R.id.pbe);
            gv = findViewById(R.id.pic_items);

            t_title = findViewById(R.id.title);
            t_description = findViewById(R.id.note);
            t_price = findViewById(R.id.price);
            sellerprof = findViewById(R.id.ss);

            sp = getSharedPreferences("user_prefs", MODE_PRIVATE);
            showView(false);
            Bundle bundle = getIntent().getExtras();
            title = bundle.getString("title");
            id = title.split(",")[1];
            itemref = FirebaseDatabase.getInstance().getReference().child("Gstore").child("https:").child("gmart9dff0firebaseiocom").child("Gstore").child(title.split(",")[1]);

            myRef = itemref;
            itemref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    maeketItems mt = dataSnapshot.getValue(maeketItems.class);
                    title = mt.getTitle();
                    description = mt.getDescription();
                    details = mt.getDetails();
                    price = mt.getPrice();
                    prices = price.contains(",") ? price.split(",") : new String[]{price};
                    pics = mt.getPics();
//
                    pictures = pics.split(",");
                    prices = mt.getPrice().contains(",") ? mt.getPrice().split(",") : new String[]{mt.getPrice()};
                    String rprice = "\u20a6 " + prices[0];
                    if (prices.length > 1 && !(prices[1].matches("") || prices[1].matches("0"))) {
                        rprice += "+" + prices[1] + " shipping";
                    } else {
                        rprice += "+" + "free shipping";
                    }
                    picChange = pics;
                    showView(true);
                    t_title.setText(title.split(",")[0]);
                    t_description.setText(description);
                    des.setText(details);
                    t_price.setText(prices[0]);
                    s_price.setText(prices.length > 1 ? prices[1] : "");
                    setPics();
                    if (prices.length > 1) {
                        t_gprice.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getBaseContext(), "Item is currently not available. Check Internet connection", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){

            Toast.makeText(getBaseContext(), "Item is currently not available. Check connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void pictake(View v){
        Intent pickintent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        Intent imageintent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Intent chooserintent=Intent.createChooser(pickintent,getResources().getString(R.string.choose));
        chooserintent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{imageintent});
        startActivityForResult(chooserintent,REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle extras=data.getExtras();
        String pic="";
        Bitmap bitmap = null;
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK) {
            try {
                Bitmap bt = (Bitmap) extras.get("data");
                bitmap=bt;
                Uri imageuri = getImageUri(bt,getApplicationContext(),"title");
                pic=bitmaptistring(bt);
            } catch (Exception E) {
                Log.i("oihoi", E.toString());

                try {
                    //set image uri and add to pics
                    Uri selectedimage = data.getData();
                    InputStream inps = getContentResolver().openInputStream(selectedimage);
                    Bitmap image = BitmapFactory.decodeStream(inps);
                    bitmap=image;

                    pic=bitmaptistring(image);
                } catch (Exception e) {
                    Log.i("ijhoisd", e.toString());
                }
            }
        }
        if (!pic.equals("")) {
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("profilePic", pic);
            edit.commit();
            gv.setImageBitmap(bitmap);
        }

    }

    public Uri getImageUri(Bitmap bt, Context context, String title){
        ByteArrayOutputStream bytes=new ByteArrayOutputStream();
        bt.compress(Bitmap.CompressFormat.JPEG,90,bytes);
        String path= MediaStore.Images.Media.insertImage(context.getContentResolver(),bt,title,null);
        return Uri.parse((path));
    }


    public String bitmaptistring(Bitmap bitmap){
        ByteArrayOutputStream bas=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bas);
        byte[] b=bas.toByteArray();
        String temp= Base64.encodeToString(b,Base64.DEFAULT);
        return temp;
    }


    public static String picChange;
    public void setPics(){


        Picasso.with(this).load(Uri.parse(picChange.split(",")[0]))
                .placeholder(R.drawable.img)
                .into(gv);
    }
    public void saveItem(View view){
        this.title=t_title.getText().toString();
        this.description=t_description.getText().toString();
        this.details=des.getText().toString();
        this.price=t_price.getText().toString()+","+s_price.getText().toString();
        showView(false);
        maeketItems mitem=new maeketItems(title,description,price,details,EditMartItems.picChange.substring(1));
        save2cloud sc=new save2cloud(mitem);
        sc.execute();
    }

    public void delMitem(View view){
        if (editListAdapter.removed.size()==editListAdapter.allpics.size()) {
            final AlertDialog.Builder dialg = new AlertDialog.Builder(this);
            dialg.setTitle("Are you sure you want to remove this item from  the market?");
            dialg.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            dialg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialogInterface, int i) {
                    try {

                        showView(false);

                        maeketItems items = new maeketItems();
                        myRef.child(id).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                try {
//                                    openItems();
                                } catch (Exception e) {
                                    Log.i("oih9o", e.toString());
                                }
                            }
                        });
                    } catch (Exception e) {
                        Log.i("kuhsidhio", e.toString());
                    }
                }
            });

            dialg.create();
            dialg.show();
        }else {
            Toast.makeText(this,"must delete all pics",Toast.LENGTH_SHORT).show();
        }
    }
    public class save2cloud extends AsyncTask<Void, Void, Void> {
        public save2cloud(maeketItems mt){
            this.mt=mt;
        }
        maeketItems mt;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        String cloudSave;

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                String[] pics2=picChange.substring(1).split(",");
//                mt.setBrand(sp.getString("id", ""));
                Log.i("iu9es90j",mt.getPics());
                myRef.child(id).setValue(mt, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        cloudSave = "saved 2 cloud";
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

            showView(true);
            if (cloudSave.matches("saved 2 cloud")){
                Toast.makeText(getApplicationContext(),cloudSave,Toast.LENGTH_LONG).show();
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
    public void showView(boolean b){
        sv.setVisibility(b?View.VISIBLE:View.GONE);
        pb.setVisibility(b?View.GONE:View.VISIBLE);
    }
    public void deleteItem(View view){}

}


