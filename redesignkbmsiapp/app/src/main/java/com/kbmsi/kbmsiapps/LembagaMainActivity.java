package com.kbmsi.kbmsiapps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.gigamole.navigationtabstrip.NavigationTabStrip;

public class LembagaMainActivity extends AppCompatActivity {

    ProgressDialog dialogs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            Intent intent = getIntent();
            String action = intent.getAction();
            String data = intent.getDataString();
            if (Intent.ACTION_VIEW.equals(action) && data != null) {
                System.out.println("link : "+data);
                Intent next = new Intent(getApplicationContext(), WebActivity.class);
                next.putExtra("id_berita", data);
                startActivity(next);
            }
        } catch (Exception e){

        }

        dialogs = ProgressDialog.show(this,"","Memuat http://kbmsi.filkom.ub.ac.id ...");
//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_main);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_main);
        viewPager.setAdapter(new LembagaMainPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(2);

        final NavigationTabStrip navigationTabStrip = (NavigationTabStrip) findViewById(R.id.nts);
        navigationTabStrip.setTitles("Lembaga KBMSI");
        navigationTabStrip.setViewPager(viewPager);
        dialogs.dismiss();
    }
}
