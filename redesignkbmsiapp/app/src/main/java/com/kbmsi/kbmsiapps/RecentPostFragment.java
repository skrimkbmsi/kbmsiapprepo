package com.kbmsi.kbmsiapps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.kbmsi.kbmsiapps.BaseActivity.vUpdate;

/**
 * Created by Rinov on 12/22/2017.
 */

public class RecentPostFragment extends Fragment {

    //KBMSI
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar progressBarKBMSI;
    private EventAdapter adapter;
    private List<Event> eventList;


    //Info Akademik
    private RecyclerView mRecyclerViewInfo;
    private RecyclerView.LayoutManager mLayoutManagerInfo;
    private ProgressBar progressBarInfo;
    private EventInfoAdapter adapterInfo;
    private List<EventInfo> eventListInfo;

    //Pengumuman
    private RecyclerView mRecyclerViewPengumuman;
    private RecyclerView.LayoutManager mLayoutManagerPengumuman;
    private ProgressBar progressBarPengumuman;
    private EventPengumumanAdapter adapterPengumuman;
    private List<EventPengumuman> eventListPengumuman;

    //
    LinearLayout kbmsi, info, pengumuman;


    Button feedback;

    private FirebaseAuth mAuth;
    //firebase
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }
    void firebaseAnon() {
        final String TAG = "WADU";
        mAuth.signInAnonymously()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInAnonymously:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            Toast.makeText( RecentPostFragment.super.getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FirebaseDatabase database;
    DatabaseReference forceupdate, update, myRef;
    String updateVer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recentpostfragment, container, false);

        feedback = (Button) view.findViewById(R.id.feedback);

        kbmsi = (LinearLayout) view.findViewById(R.id.rec_kbmsi);
        info = (LinearLayout) view.findViewById(R.id.rec_info);
        pengumuman = (LinearLayout) view.findViewById(R.id.rec_pengumuman);

        mAuth = FirebaseAuth.getInstance();
        firebaseAnon();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("proker");
        update = database.getReference("update");
        forceupdate = database.getReference("forceupdate");

        update = database.getReference("update");
        update.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value;
                try{
                    value = dataSnapshot.getValue(String.class);
                } catch (Exception e){
                    value = vUpdate;
                }

                if (!value.equalsIgnoreCase(vUpdate)){
                    feedback.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                    mRecyclerViewInfo.setVisibility(View.GONE);
                    mRecyclerViewPengumuman.setVisibility(View.GONE);
                    info.setVisibility(View.GONE);
                    pengumuman.setVisibility(View.GONE);
                    feedback.setText("New Update to "+value+" !");

                } else {
                    feedback.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mRecyclerViewInfo.setVisibility(View.VISIBLE);
                    mRecyclerViewPengumuman.setVisibility(View.VISIBLE);
                    info.setVisibility(View.VISIBLE);
                    pengumuman.setVisibility(View.VISIBLE);
                }
                updateVer = value;
                System.out.println("===Bisa nop "+dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("===Error nop "+error);
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = getContext().getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                } catch (Exception e){

                }
            }
        });

        kbmsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextAct = new Intent(getContext(), RecentKbmsiActivity.class);
                nextAct.putExtra("id", 1);
                startActivity(nextAct);
            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextAct = new Intent(getContext(), RecentKbmsiActivity.class);
                nextAct.putExtra("id", 3);
                startActivity(nextAct);
            }
        });
        pengumuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextAct = new Intent(getContext(), RecentKbmsiActivity.class);
                nextAct.putExtra("id", 2);
                startActivity(nextAct);
            }
        });
//        //kbmsi
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_kbmsi);
        progressBarKBMSI = (ProgressBar) view.findViewById(R.id.loading_postkbmsi);
        eventList = new ArrayList<>();
        adapter = new EventAdapter(getContext(), eventList);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
        data();
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Event event = eventList.get(position);
//                Toast.makeText(getContext(), "id : "+event.getID_KONTEN(), Toast.LENGTH_SHORT).show();
                Intent next = new Intent(getContext(), BeritaActivity.class);
                next.putExtra("id_berita", event.getID_KONTEN());
                startActivity(next);
            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        //Pengumuman
        mRecyclerViewPengumuman = (RecyclerView) view.findViewById(R.id.recycler_view_pengumumanfilkom);
        progressBarPengumuman = (ProgressBar) view.findViewById(R.id.loading_postpengumuman);
        eventListPengumuman = new ArrayList<>();
        adapterPengumuman = new EventPengumumanAdapter(getContext(), eventListPengumuman);
        mLayoutManagerPengumuman = new LinearLayoutManager(getActivity());
        mRecyclerViewPengumuman.setLayoutManager(mLayoutManagerPengumuman);
        mRecyclerViewPengumuman.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewPengumuman.setAdapter(adapterPengumuman);
        dataPengumuman();
        mRecyclerViewPengumuman.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerViewPengumuman, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                EventPengumuman event = eventListPengumuman.get(position);
//                Toast.makeText(getContext(), "id : ", Toast.LENGTH_SHORT).show();
                Intent next = new Intent(getContext(), WebActivity.class);
                next.putExtra("id_berita", event.getInfo());
                startActivity(next);
            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        //info
        mRecyclerViewInfo = (RecyclerView) view.findViewById(R.id.recycler_view_infoakademik);
        progressBarInfo= (ProgressBar) view.findViewById(R.id.loading_postinfo);
        eventListInfo = new ArrayList<>();
        adapterInfo = new EventInfoAdapter(getContext(), eventListInfo);
        mLayoutManagerInfo = new LinearLayoutManager(getActivity());
        mRecyclerViewInfo.setLayoutManager(mLayoutManagerInfo);
        mRecyclerViewInfo.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewInfo.setAdapter(adapterInfo);
        dataInfo();
        mRecyclerViewInfo.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerViewInfo, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                EventInfo event = eventListInfo.get(position);
//                Toast.makeText(getContext(), "id : ", Toast.LENGTH_SHORT).show();
                Intent next = new Intent(getContext(), WebActivity.class);
                next.putExtra("id_berita", event.getInfo());
                startActivity(next);
            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));




        return view;
    }

    private ProgressDialog dialog;
    Event a; EventPengumuman aPengumuman; EventInfo aInfo;
    private void data(){
        dialog = ProgressDialog.show(getActivity(),"","Loading...");
        RequestQueue queue = Volley.newRequestQueue(getActivity());
//        String url = "http://192.168.1.17/backup/welcomesss";
        String url = "http://kbmsi.filkom.ub.ac.id/welcomesss";
//        String url = "http://samples.openweathermap.org/data/2.5/box/city?bbox=12,32,15,37,10&appid=b1b15e88fa797225412429c1c50c122a1";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
//                            Weather
//                            JSONObject jsonObject = new JSONObject(response);
//                            JSONArray list = jsonObject.getJSONArray("list");
//                            for (int i = 0; i<list.length(); i++){
//                                JSONObject object = list.getJSONObject(i);
//                                String name = object.getString("name");
//                                a = new Event(""+name);
//                                eventList.add(a);
//                                adapter.notifyDataSetChanged();
//                            }

                            JSONArray jA = new JSONArray(response);
//                            for (int i = 0; i<jA.length(); i++){
                            for (int i = 0; i<3; i++){
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
                            }
                            progressBarKBMSI.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Please check your internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
        dialog.dismiss();

    }

    private void dataInfo(){
        dialog = ProgressDialog.show(getActivity(),"","Loading...");
        RequestQueue queue = Volley.newRequestQueue(getActivity());
//        String url = "http://192.168.1.17/backup/welcomesss";
//        String url = "http://192.168.1.17/scrapping/scrapppinfoakademik.php";
        String url = "http://api.kbmsi.or.id/scrapping/scrapppinfoakademik.php";
//        String url = "http://samples.openweathermap.org/data/2.5/box/city?bbox=12,32,15,37,10&appid=b1b15e88fa797225412429c1c50c122a1";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jA = new JSONArray(response);
//                            for (int i = 0; i<jA.length(); i++){
                            for (int i = 0; i<3; i++){
                                JSONObject object = jA.getJSONObject(i);
                                String judul = object.getString("nama");
                                String info = object.getString("info");
                                String tanggal = object.getString("tanggal");
                                aInfo = new EventInfo(""+judul, ""+info, tanggal);
                                eventListInfo.add(aInfo);
                                System.out.println("masuk kok nop");
                                adapterInfo.notifyDataSetChanged();
                            }
                            progressBarInfo.setVisibility(View.GONE);
                        } catch (JSONException e) {
//                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Please check your internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
        dialog.dismiss();

    }

    private void dataPengumuman(){
        dialog = ProgressDialog.show(getActivity(),"","Loading...");
        RequestQueue queue = Volley.newRequestQueue(getActivity());
//        String url = "http://192.168.1.17/backup/welcomesss";
        String url = "http://api.kbmsi.or.id/scrapping/scrapppengumumanfilkom.php";
//        String url = "http://samples.openweathermap.org/data/2.5/box/city?bbox=12,32,15,37,10&appid=b1b15e88fa797225412429c1c50c122a1";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray jA = new JSONArray(response);
//                            for (int i = 0; i<jA.length(); i++){
                            for (int i = 0; i<2; i++){
                                JSONObject object = jA.getJSONObject(i);
                                String judul = object.getString("nama");
                                String info = object.getString("info");
                                String tanggal = object.getString("tanggal");
                                aPengumuman = new EventPengumuman(""+judul, ""+info, tanggal);
                                eventListPengumuman.add(aPengumuman);
                                System.out.println("MASUK KOK NOP "+judul+info+tanggal);
                                adapterPengumuman.notifyDataSetChanged();
                            }
                            progressBarPengumuman.setVisibility(View.GONE);
                        } catch (JSONException e) {
//                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Please check your internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
        dialog.dismiss();

    }
}
