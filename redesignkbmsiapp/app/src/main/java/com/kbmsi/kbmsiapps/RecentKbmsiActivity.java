package com.kbmsi.kbmsiapps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecentKbmsiActivity extends AppCompatActivity {

    //KBMSI
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private EventAdapter adapter;
    private List<Event> eventList;

    //Info Akademik
    private RecyclerView mRecyclerViewInfo;
    private RecyclerView.LayoutManager mLayoutManagerInfo;

    private EventInfoAdapter adapterInfo;
    private List<EventInfo> eventListInfo;

    //Pengumuman
    private RecyclerView mRecyclerViewPengumuman;
    private RecyclerView.LayoutManager mLayoutManagerPengumuman;

    private EventPengumumanAdapter adapterPengumuman;
    private List<EventPengumuman> eventListPengumuman;
    EventPengumuman aPengumuman; EventInfo aInfo;

    TextView beritatitle, sumber;

    FirebaseDatabase database;
    DatabaseReference forceupdate, update, myRef;
    String updateVer;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_kbmsi);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);
//        beritatitle = (TextView) findViewById(R.id.beritatitle);
        sumber = (TextView) findViewById(R.id.sumber);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_kbmsi);
        eventList = new ArrayList<>();
        adapter = new EventAdapter(getApplicationContext(), eventList);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);


        //Pengumuman
        mRecyclerViewPengumuman = (RecyclerView) findViewById(R.id.recycler_view_pengumumanfilkom);
        eventListPengumuman = new ArrayList<>();
        adapterPengumuman = new EventPengumumanAdapter(getApplicationContext(), eventListPengumuman);
        mLayoutManagerPengumuman = new LinearLayoutManager(getApplicationContext());
        mRecyclerViewPengumuman.setLayoutManager(mLayoutManagerPengumuman);
        mRecyclerViewPengumuman.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewPengumuman.setAdapter(adapterPengumuman);

        //info
        mRecyclerViewInfo = (RecyclerView) findViewById(R.id.recycler_view_infoakademik);
        eventListInfo = new ArrayList<>();
        adapterInfo = new EventInfoAdapter(getApplicationContext(), eventListInfo);
        mLayoutManagerInfo = new LinearLayoutManager(getApplicationContext());
        mRecyclerViewInfo.setLayoutManager(mLayoutManagerInfo);
        mRecyclerViewInfo.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewInfo.setAdapter(adapterInfo);

        switch (id){
            case 1:{
                dialogs = ProgressDialog.show(this,"","Memuat http://kbmsi.filkom.ub.ac.id ...");
                data();
                mRecyclerViewInfo.setAlpha(0);
                mRecyclerViewPengumuman.setAlpha(0);
//                beritatitle.setText("Post KBMSI");
                sumber.setText("Sumber : http://kbmsi.filkom.ub.ac.id");
                break;
            }
            case 2:{
                dialogs = ProgressDialog.show(this,"","Memuat pengumuman https://filkom.ub.ac.id ...");
                dataPengumuman();
                mRecyclerViewInfo.setAlpha(0);
                mRecyclerView.setAlpha(0);
//                beritatitle.setText("Pengumuman FILKOM");
                sumber.setText("Sumber : https://filkom.ub.ac.id/");
                break;
            }
            case 3:{
                dialogs = ProgressDialog.show(this,"","Memuat info akademik https://filkom.ub.ac.id ...");
                dataInfo();
                mRecyclerView.setAlpha(0);
                mRecyclerViewPengumuman.setAlpha(0);
//                beritatitle.setText("Info Akademik FILKOM");
                sumber.setText("Sumber : https://filkom.ub.ac.id/");
                break;
            }
            default:{
                break;
            }
        }

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Event event = eventList.get(position);
//                Toast.makeText(getContext(), "id : "+event.getID_KONTEN(), Toast.LENGTH_SHORT).show();
                Intent next = new Intent(getApplicationContext(), BeritaActivity.class);
                next.putExtra("id_berita", event.getID_KONTEN());
                startActivity(next);
            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        mRecyclerViewPengumuman.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerViewPengumuman, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                EventPengumuman event = eventListPengumuman.get(position);
//                Toast.makeText(getContext(), "id : ", Toast.LENGTH_SHORT).show();
                Intent next = new Intent(getApplicationContext(), WebActivity.class);
                next.putExtra("id_berita", event.getInfo());
                startActivity(next);
            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        mRecyclerViewInfo.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerViewInfo, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                EventInfo event = eventListInfo.get(position);
//                Toast.makeText(getContext(), "id : ", Toast.LENGTH_SHORT).show();
                Intent next = new Intent(getApplicationContext(), WebActivity.class);
                next.putExtra("id_berita", event.getInfo());
                startActivity(next);
            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));

    }

    ProgressDialog dialogs;
    Event a;
    private void data(){

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://kbmsi.filkom.ub.ac.id/welcomesss";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jA = new JSONArray(response);
                            for (int i = 0; i<jA.length(); i++){
                                JSONObject object = jA.getJSONObject(i);
                                String judul = object.getString("JUDUL");
                                String nama = object.getString("NAMA");
                                String id = object.getString("ID_KONTEN");
                                String last_update = object.getString("LAST_UPDATE");
                                String substring = last_update.substring(0,10);
                                String[] tanggal = substring.split("-");

                                String active = object.getString("IS_ACTIVE");
                                if (active.equalsIgnoreCase("1")){
                                    a = new Event(""+judul, ""+id, ""+nama, tanggal);
                                    eventList.add(a);
                                }
                                adapter.notifyDataSetChanged();
                                dialogs.dismiss();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                dialogs.dismiss();
            }
        });
        queue.add(stringRequest);

    }


    private void dataInfo(){
//        dialogs = ProgressDialog.show(getApplicationContext(),"","Loading...");
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//        String url = "http://192.168.1.17/backup/welcomesss";
        String url = "http://api.kbmsi.or.id/scrapping/scrapppinfoakademik.php";
//        String url = "http://samples.openweathermap.org/data/2.5/box/city?bbox=12,32,15,37,10&appid=b1b15e88fa797225412429c1c50c122a1";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jA = new JSONArray(response);
                            for (int i = 0; i<jA.length(); i++){
                                JSONObject object = jA.getJSONObject(i);
                                String judul = object.getString("nama");
                                String info = object.getString("info");
                                String tanggal = object.getString("tanggal");
                                aInfo = new EventInfo(""+judul, ""+info, tanggal);
                                eventListInfo.add(aInfo);
                                System.out.println("masuk kok nop");
                                adapterInfo.notifyDataSetChanged();
                                dialogs.dismiss();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                dialogs.dismiss();
            }
        });
        queue.add(stringRequest);
//        dialogs.dismiss();
    }

    private void dataPengumuman(){
//        dialogs = ProgressDialog.show(getApplicationContext(),"","Loading...");
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//        String url = "http://192.168.1.17/backup/welcomesss";
        String url = "http://api.kbmsi.or.id/scrapping/scrapppengumumanfilkom.php";
//        String url = "http://samples.openweathermap.org/data/2.5/box/city?bbox=12,32,15,37,10&appid=b1b15e88fa797225412429c1c50c122a1";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray jA = new JSONArray(response);
                            for (int i = 0; i<jA.length(); i++){
                                JSONObject object = jA.getJSONObject(i);
                                String judul = object.getString("nama");
                                String info = object.getString("info");
                                String tanggal = object.getString("tanggal");
                                aPengumuman = new EventPengumuman(""+judul, ""+info, tanggal);
                                eventListPengumuman.add(aPengumuman);
                                System.out.println("MASUK KOK NOP "+judul+info+tanggal);
                                adapterPengumuman.notifyDataSetChanged();
                                dialogs.dismiss();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                dialogs.dismiss();
            }
        });
        queue.add(stringRequest);
//        dialogs.dismiss();
    }
}
