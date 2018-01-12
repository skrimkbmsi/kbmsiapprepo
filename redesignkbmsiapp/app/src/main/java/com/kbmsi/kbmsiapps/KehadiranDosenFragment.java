package com.kbmsi.kbmsiapps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static com.kbmsi.kbmsiapps.BaseActivity.vUpdate;

/**
 * Created by Rinov on 12/25/2017.
 */

public class KehadiranDosenFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private KehadiranDosenAdapter mAdapter;
    private List<Cuaca> daftarCuaca;
    AutoCompleteTextView tvSearch;
    ImageButton btnSearch;
    CardView sumbercard;
    ImageView closeSumber;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.kehadiranfragment, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        tvSearch = (AutoCompleteTextView) view.findViewById(R.id.tvSearch);
        btnSearch = (ImageButton) view.findViewById(R.id.btnSearch);
        sumbercard = (CardView) view.findViewById(R.id.sumbercard);
        closeSumber = (ImageView) view.findViewById(R.id.closeSumber);
        sumbercard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextAct = new Intent(getContext(), WebActivity.class);
                nextAct.putExtra("id_berita", "https://filkom.ub.ac.id/info/hadir");
                startActivity(nextAct);
            }
        });
        closeSumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sumbercard.setVisibility(View.GONE);
            }
        });
//        mRecyclerView.setHasFixedSize(true);

        daftarCuaca = new ArrayList<>();
        mAdapter = new KehadiranDosenAdapter(getActivity(), daftarCuaca);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Cuaca event = daftarCuaca.get(position);
//                Toast.makeText(getActivity(), "id : ", Toast.LENGTH_SHORT).show();
//                Intent next = new Intent(getContext(), BeritaActivity.class);
//
//                startActivity(next);
            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("nop : "+tvSearch.getText());
                if (cekText(tvSearch.getText()+"")){
                    String plenteks = tvSearch.getText()+"";
                    if (plenteks.equalsIgnoreCase("")){
                        data(" ");
                    } else {
                        data(tvSearch.getText()+"");
                    }
                }

            }
        });
        return view;
    }
    private boolean cekText(String plaintext){
        if (plaintext.contains("?") || plaintext.contains("@") || plaintext.contains("php") || plaintext.contains("<") || plaintext.contains(";") ) {
            Toast.makeText(getActivity(), "Masukkan nama", Toast.LENGTH_SHORT).show();
            tvSearch.setText("");
            return false;
        }
        return true;
    }
    private ProgressDialog dialog;
    Cuaca a;
    private void data(final String data){
        mAdapter.clearData();
        dialog = ProgressDialog.show(getActivity(),"","Memuat https://filkom.ub.ac.id/info/hadir ...");
        RequestQueue queue = Volley.newRequestQueue(getActivity());
//        String url = "http://192.168.1.17/scrapping/scrappkehadirandosen.php";
        String url = "http://api.kbmsi.or.id/scrapping/scrappkehadirandosen.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
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
                            System.out.println("nop : "+response);
                            JSONArray jA = new JSONArray(response);
                            for (int i = 0; i<jA.length(); i++){
                                JSONObject jO = jA.getJSONObject(i);
                                String nama = jO.get("nama")+"";
                                String info = jO.get("info")+"";
                                String kehadiran = jO.get("kehadiran")+"";
                                String foto = jO.get("foto")+"";
                                if (!nama.equalsIgnoreCase("")){
                                    a = new Cuaca(""+nama, ""+info, ""+kehadiran, foto);
                                    daftarCuaca.add(a);
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                            sumbercard.setVisibility(View.VISIBLE);

                            dialog.dismiss();
                        } catch (JSONException e) {
                            System.out.println("nop : " +e);
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();
                params.put("name", data);
                return params;
            }
        };
        queue.add(stringRequest);

    }
}
