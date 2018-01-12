package com.kbmsi.kbmsiapps;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TesActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;

    public static ArrayList<Cuaca> daftarCuaca;
    CustomAdapterC mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tes);
        daftarCuaca = new ArrayList<Cuaca>();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        getAlls();
        mAdapter = new CustomAdapterC(this, daftarCuaca);

    }

    private ProgressDialog dialog;
    private void getAlls() {
        dialog = ProgressDialog.show(this,"","Loading...");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.17/scrapping/scrapp3.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 1; i<jsonArray.length(); i++){
                                JSONObject jO = jsonArray.getJSONObject(i);
                                String nama = jO.get("nama")+"";
                                String info = jO.get("info")+"";
                                String kehadiran = jO.get("kehadiran")+"";
                                String foto = jO.get("foto")+"";
                                daftarCuaca.add(new Cuaca(nama, info, kehadiran, foto));
                            }

                            mRecyclerView.setAdapter(mAdapter);
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Please check your internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);

        dialog.dismiss();
    }
}
