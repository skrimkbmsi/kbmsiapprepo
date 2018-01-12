package com.kbmsi.kbmsiapps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
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

public class BeritaActivity extends AppCompatActivity {

    WebView webView;
    String id_berita, judul, last_update, nama;
    private ProgressDialog dialog;
    TextView vtanggal, vnama, vjudul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita);
        vtanggal = (TextView) findViewById(R.id.vtanggal);
        vnama = (TextView) findViewById(R.id.vnama);
        vjudul = (TextView) findViewById(R.id.judul);
        dialog = ProgressDialog.show(this, "", "Loading...");
        webView = (WebView) findViewById(R.id.webview);
        Intent get = getIntent();
        id_berita = get.getStringExtra("id_berita");

        WebSettings settings = webView.getSettings();
        settings.setMinimumFontSize(18);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        webView.setWebChromeClient(new WebChromeClient());

        data();
    }

    String htmlText;

    private void data() {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//        String url = "http://192.168.1.17/backup/welcomesss/id_berita/" + id_berita;
        String url = "http://kbmsi.filkom.ub.ac.id/welcomesss/id_berita/" + id_berita;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jA = new JSONArray(response);
                            JSONObject object = jA.getJSONObject(0);
                            htmlText = object.get("KONTEN") + "";
                            judul = object.get("JUDUL") + "";
                            nama = object.get("NAMA") + "";
                            last_update = object.get("LAST_UPDATE") + "";
                            String substring = last_update.substring(0,10);
                            String[] tanggal = substring.split("-");
                            String namaBulan [] = {" ", "Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul", "Agt", "Sept", "Nov", "Dec"};
                            vtanggal.setText(tanggal[2]+" "+namaBulan[Integer.parseInt(tanggal[1])]+" "+tanggal[0]);
                            vnama.setText(nama);
                            vjudul.setText(judul);
                            String changeFontHtml = Util.changedHeaderHtml(/*Util.bbcode(*/htmlText/*)*/);
                            webView.loadDataWithBaseURL(null, changeFontHtml,
                                    "text/html", "UTF-8", null);

                            webView.setVisibility(View.VISIBLE);

                            //holder.post_content_on_topic.loadData(Util.bbcode(post.getPost_content()), "text/html", null);

                            webView.getSettings().setBuiltInZoomControls(true);


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
