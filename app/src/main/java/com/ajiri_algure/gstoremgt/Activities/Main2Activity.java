package com.ajiri_algure.gstoremgt.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ajiri_algure.gstoremgt.R;
import com.ajiri_algure.gstoremgt.adapter.listAdapter;
import com.ajiri_algure.gstoremgt.market_data.maeketItems;
import com.ajiri_algure.gstoremgt.market_data.marketRoom;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.view.View.GONE;

public class Main2Activity extends AppCompatActivity {

    private Toolbar toolbar;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    TextView name,email;
    ImageView imv;
    private ArrayList<maeketItems> Items;

    public void openItems(View view){
//        startActivity(new Intent(this,MyItems.class));
    }
    public void orderMgt(View view) {
//        startActivity(new Intent(this,Orders.class));
 }

        public void openprofile(View view){
//        startActivity(new Intent(this,Profile.class));
    }
    public void openMarket(View view){
        startActivity(new Intent(this,Mymart.class));
    }
    public void switchUser(View view){
//        startActivity(new Intent(this,LoginActivity.class));
    }
    public void openwallet(View view){
//        startActivity(new Intent(this,GcoinActivity.class));
    }
    public void UploadI(View view){
        startActivity(new Intent(this,Main2Activity.class));
    }
    public void UploadI(){
        startActivity(new Intent(this,Main2Activity.class));
    }

    private static final int REQUEST_STORAGE =1089 ;
    EditText title, dets, price, category, number, note,sprice;
    TextView description;
    ArrayList<String> descs = new ArrayList<String>();

    private static final int REQUEST_IMAGE_CAPTURE =1 ;
    private String formeruri;
    private String imagetype;
    private String formertype;
    private Uri imageuri;
    private EditText sell;
    private TextView sellition;
    private DatabaseReference myRef;
    private ArrayList<Bitmap> imagebits;
    private StorageReference mStorageRef;
    ProgressBar progressbar;
    ScrollView scrollView;
    UploadTask uploadtask;
    LinearLayout laout;
    RelativeLayout reel;
    LinearLayout playout;

    public void openGup(View view){startActivity(new Intent(this,Main2Activity.class));}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.ajiri_algure.gstoremgt.R.layout.activity_main2);
        try {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            toolbar = findViewById(R.id.tb);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Gmart");
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            k=0;
            slidestarted=false;
            title = findViewById(R.id.title);
            dets = findViewById(R.id.details);
            price = findViewById(R.id.price);
            number = findViewById(R.id.itno);
            category = findViewById(R.id.category);
            note = findViewById(R.id.note);
            sellition = findViewById(R.id.ss);
            sell = findViewById(R.id.seller);
            description = findViewById(R.id.des);
            imv=findViewById(R.id.imv);
            downloadurls = new ArrayList<String>();
            playout=findViewById(R.id.playout);//preview layout
            playout.setVisibility(View.GONE);//to hide preview

            previewButton=findViewById(R.id.pview);
            requestStoragePermission();
            reel = findViewById(R.id.reel);
            laout = findViewById(R.id.laout);
            mStorageRef = FirebaseStorage.getInstance().getReference("market");
            sprice=findViewById(R.id.sprice);
            sp=getSharedPreferences("user_prefs",MODE_PRIVATE);
            editor=sp.edit();
            myRef = FirebaseDatabase.getInstance().getReference("Gstore");//instantiates firebase database
            imagebits = new ArrayList<Bitmap>();
            pics = new ArrayList<Uri>();

            name=findViewById(R.id.bname);
            email=findViewById(R.id.bemail);
            progressbar = findViewById(R.id.pbar);
            reel.setVisibility(GONE);
            scrollView = findViewById(R.id.scrollView1);
            setSellerProfile();
        }catch (Exception e){
            Log.i("oijpozs",e.toString());
        }
    }

    public void setSellerProfile(){
        try {
            if (!(sp.contains("email") || sp.contains("phone") || sp.contains("city") || sp.contains("brand") || sp.contains("paystack"))) {
                Toast.makeText(this, "Complete your seller profile", Toast.LENGTH_SHORT).show();
            } else {
                String fname = sp.contains("fname") ? sp.getString("fname", "") : "";
                String sname = sp.contains("sname") ? sp.getString("sname", "") : "";
                String email = sp.contains("email") ? sp.getString("email", "") : "";
                String phone = sp.contains("pno") ? sp.getString("pno", "") : "";
                String address = sp.contains("address") ? sp.getString("address", "") : "";
                String city = sp.contains("city") ? sp.getString("city", "") : "";
                String brand = sp.contains("brand") ? sp.getString("brand", "") : "";
                String paystack = sp.contains("paystack") ? sp.getString("paystack", "") : "";

                String detailS = "Seller details:\n" + brand + "\n" + email + "\n" + phone + "\n" + city + "\n" + paystack;
                sellition.setText(detailS);
                name.setText(brand);
                this.email.setText(email);
            }
        }catch (Exception e){}
    }
    public void showProgress(){

        reel.setVisibility(View.VISIBLE);
    }
    public void stopProgress(){
        reel.setVisibility(View.GONE);
    }

    public void showProgress(View view){

        reel.setVisibility(View.VISIBLE);
    }
    public void stopProgress(View view){
        laout.setVisibility(GONE);
    }
    marketRoom mroom;

    public void addtodes(View view) {
        String dsitem;
        dsitem = dets.getText().toString();
        descs.add(dsitem);
        dets.setText("");
        String note = description.getText().toString();
        note += "\n " + dsitem;
        description.setText(note);
    }

    public void deletedes(View view){
        try {
            String note = description.getText().toString();
            String newnote = note.substring(0, note.lastIndexOf("\n"));
            description.setText(newnote);
            dets.setText(note.substring(note.lastIndexOf("\n") + 1));
            descs.remove(descs.size() - 1);
        }catch (Exception e){}
    }
    int k;
    public void saveItem(View view) {
        try {
            if (true) {//supposed to be preview check

                k++;
                showProgress();
                String t = title.getText().toString();
                String price = this.price.getText().toString()+","+sprice.getText().toString();
                String number = this.number.getText().toString();
                String category = this.category.getText().toString();
                String seller = sellition.getText().toString();
                String details = "";
                String pics = "";
                int y = 0;
                for (String d : descs) {
                    details += "\n" + d;
                    y++;
                }
                pics = "";
                String note = this.note.getText().toString();
                mt = new maeketItems(t, note,  price, details, pics);

                //insertItem(mt);

                if(previewCheck()) {
                    save2cloud savetask = new save2cloud();
                    savetask.execute();
                }
            }else {}
        } catch (Exception e) {
            Log.i("lokpogk", e.toString());
        }
    }

    maeketItems mt;
    public void insertItem(maeketItems items){
        doroom d=new doroom();
        d.execute();
    }
    public class doroom extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                mroom.getitemDao().insertAll(mt);
            }catch (Exception e){
                Log.i("kjiodzs",e.toString());
                if (e.toString().contains("UNIQUE")){
                    updateType="unique";
                    publishProgress();
                }
            }
            return null;
        }

        String updateType;
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            Toast.makeText(getApplicationContext(),"Title must be unique",Toast.LENGTH_LONG).show();        }
    }
    public String piclinks(){
        String string="";
        for (String piclink:downloadurls){
            string+=piclink+",";
        }

        return string;
    }

    Button previewButton;
    public void showPreview(View view){
        try {
            if (previewButton.getText().toString().matches("show Preview")) {
                previewButton.setText("stop preview");
                String t = title.getText().toString();
                String price = this.price.getText().toString();
                String number = this.number.getText().toString();
                String category = this.category.getText().toString();
                String seller = sellition.getText().toString();
                String details = "";
                String pics = "";
                int y = 0;
                for (String d : descs) {
                    details += "\n" + d;
                    y++;
                }
                pics = "";//this.pics.get(0);
//            for (String p:this.pics){
//                pics+=p+",";
//            }
                String note = this.note.getText().toString();
                mt = new maeketItems(t, note,  price,  details, pics);

                playout.setVisibility(View.VISIBLE);
                TextView title = findViewById(R.id.item_title);
                TextView description = findViewById(R.id.item_description);
                TextView detals = findViewById(R.id.item_details);
                TextView sellr = findViewById(R.id.item_seller);
                TextView naira_price = findViewById(R.id.item_price_naira);
                TextView tech_price = findViewById(R.id.item_price_tech);
                RelativeLayout rlll = findViewById(R.id.rl);
                Bitmap[] bb = new Bitmap[imagebits.size()];
//           int kit=0;
                for (int kit = 0; kit < imagebits.size(); kit++) {
                    bb[kit] = imagebits.get(kit);

                }
////
                title.setText(mt.getTitle());
                description.setText(mt.getDescription());
                detals.setText(mt.getDetails());
                naira_price.setText(mt.getPrice());
                slidestarted = true;
            }else if(previewButton.getText().toString().matches("stop preview")){
                previewButton.setText("show Preview");
                stopPreview();
            }
        }catch (Exception R){
            Log.i("lmopsd",R.toString());
        }
    }

    public void  stopPreview(){

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    public boolean previewCheck(){
        String t = title.getText().toString();
        String price = this.price.getText().toString();
        String number = this.number.getText().toString();
        String category = this.category.getText().toString();
        String seller=sellition.getText().toString();
        String details = "";
        String pics="";
        int y = 0;
        String error="";
        if (price.matches("0")||price.matches("00")){
            error= "Invalid price";
        }else if(t.matches("")||t.length()<5){
            error="title is too short";
        }else if(number.trim().matches("0")||number.matches("")){
            error="number of items not filled";
        }else if(category.matches("")||category.length()<3){
            error="invalid category";
        }else if(descs.size()<2){
            error="enter item details";
        }else if(imagebits.size()<4){
            error="images must not be less than 4";
        }else if(imagebits.size()>6){
            error="images must not be greater than 6";
        }
        if (!error.matches("")) {
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            return false;
        }else {
            return true;
        }

    }
    private boolean slidestarted;
    public static ArrayList<String> downloadurls;
    public class save2cloud extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        String cloudSave;

        @Override
        protected Void doInBackground(Void... voids) {

            try {
//
                if (downloadurls.size() == pics.size()) {

//                    //this saves other data
//
                    mt.setPics(piclinks());
//                    mt.setBrand(sp.getString("id", ""));
                    String id = myRef.push().toString().replaceAll("\\-", "")
                            .replaceAll("\\#", "")
                            .replaceAll("\\.", "")
                            .replaceAll("\\$", "")
                            .replaceAll("\\[", "")
                            .replaceAll("\\]", "");
                    myRef.child(id).setValue(mt, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            cloudSave = "saved 2 cloud";
                            publishProgress();
                        }
                    });

                } else {
                    cloudSave = "must upload all pics";
                    publishProgress();
                }
//
            }catch (Exception e){
                Log.i("kjiodzsghgf",e.toString());

            }
            return null;
        }

        String updateType;
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

            stopProgress();
            Toast.makeText(getApplicationContext(),cloudSave,Toast.LENGTH_LONG).show();
            if (cloudSave.matches("saved 2 cloud")){
                insertItem(mt);
            }
            UploadI();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            stopProgress();
        }

        @Override
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);
            stopProgress();
        }
    }

    public Bitmap stringtobitmap(String bitmap){
        try {
            byte[] encodebyte = Base64.decode(bitmap, Base64.DEFAULT);
            Bitmap bm = BitmapFactory.decodeByteArray(encodebyte, 0, encodebyte.length);
            return bm;
        }catch (Exception e){
            return null;
        }
    }
    public void pictake(View v){
        Intent pickintent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent imageintent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Intent chooserintent=Intent.createChooser(pickintent,"choose");
        chooserintent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{imageintent});
        startActivityForResult(chooserintent,REQUEST_IMAGE_CAPTURE);
    }

    public void requestStoragePermission(){
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_STORAGE);
        }
    }
    public String bitmaptistring(Bitmap bitmap){
        ByteArrayOutputStream bas=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bas);
        byte[] b=bas.toByteArray();
        String temp=Base64.encodeToString(b,Base64.DEFAULT);
        return temp;
    }
    boolean cameout;
    ArrayList<Uri> pics;
    public Uri getImageUri(Bitmap bt, Context context, String title){
//        Bitmap outImage=Bitmap.createScaledBitmap(bt,1000,1000,true);
        ByteArrayOutputStream bytes=new ByteArrayOutputStream();
        bt.compress(Bitmap.CompressFormat.JPEG,90,bytes);
        String path= MediaStore.Images.Media.insertImage(context.getContentResolver(),bt,title,null);
        return Uri.parse((path));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        cameout = true;
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK) {
            try {
                Bundle extras = data.getExtras();

                Bitmap bt = (Bitmap) extras.get("data");
                imagebits.add(bt);

                Uri imageuri = getImageUri(bt,getApplicationContext(),"title");

                pics.add(imageuri);

                imagetype = "camera";
////
            } catch (Exception E) {
                Log.i("oihoi", E.toString());

                try {
                    //set image uri and add to pics
                    Uri selectedimage = data.getData();
                    pics.add(selectedimage);

                    imagetype = "gallery";
                    //set image bitmap to imagebits
                    InputStream inps = getContentResolver().openInputStream(selectedimage);
                    Bitmap image = BitmapFactory.decodeStream(inps);
                    imagebits.add(image);

                } catch (Exception e) {
                    Log.i("ijhoisd", e.toString());
                }
            }

            Log.i("kuhii", String.
                    valueOf(pics.get(0)));
        }
        setlist();

    }

    public void deletePic(View view){
        try{
            imagebits.remove(imagebits.size()-1);
            pics.remove(pics.size()-1);
            setlist();}
        catch (Exception e){

        }
    }
    private String getTime() {
        DateFormat df = new SimpleDateFormat("yy_MM_dd_hh_mm_ss");
        String date = df.format(Calendar.getInstance().getTime());
        return  "pp"+date;
    }

    public void addtoseller(View view){
        String sellitem;
        sellitem= sell.getText().toString();
        sell.setText("");
        String note = sellition.getText().toString();
        note += "\n " + sellitem;
        sellition.setText(note);
    }
    public void removeSellerInfo(View view) {
        try {
            String note = sellition.getText().toString();
            sellition.setText(note.substring(0, note.lastIndexOf("\n")));
            sell.setText(note.substring(note.lastIndexOf("\n") + 1));
        }catch (Exception e){}
    }
    public void setlist(){
        GridView gv=findViewById(R.id.pic_items);
        listAdapter l=new listAdapter(this,(R.layout.selpics),imagebits,pics);
        gv.setAdapter(l);
//        imv.setImageBitmap(imagebits.get(0));
    }
    public void upPic(View view){
        save3cloud sv=new save3cloud(view,0);
        sv.execute();
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
                UploadTask uploadtask = mStorageRef.child(getTime()).putFile((pics.get(pos)));
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
                        Main2Activity.downloadurls.add(String.valueOf(taskSnapshot.getDownloadUrl()));
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
            if (task.matches("s")){
                ProgressBar pp=findViewById(R.id.pba);
                TextView stat=findViewById(R.id.status);
                if(pcenat==100){
                    stat.setText("");
                }
                pp.setProgress(pcenat);

                Toast.makeText(getBaseContext(),String.valueOf(pcenat),Toast.LENGTH_SHORT).show();
            }else if(task.matches("d")){

                TextView stat=findViewById(R.id.status);
                stat.setVisibility(View.VISIBLE);
            }else if(task.matches("i")){
                Toast.makeText(getBaseContext(),"uploading",Toast.LENGTH_SHORT).show();
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
    public void openmart(View view){
        DatabaseReference oref=FirebaseDatabase.getInstance().getReference().child("martstat");
        oref.setValue("open", new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                Toast.makeText(getApplicationContext(),"gmart opened",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void closemart(View view){
        DatabaseReference oref=FirebaseDatabase.getInstance().getReference().child("martstat");
        oref.setValue("close", new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                Toast.makeText(getApplicationContext(),"gmart closed",Toast.LENGTH_LONG).show();
            }
        });

    }
}

