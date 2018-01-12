package com.kbmsi.kbmsiapps;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by GIGAMOLE on 8/18/16.
 */
public class Utils extends AppCompatActivity {

    Context mContext;
    public static void setupItem(final Context context, final View view, final LibraryObject libraryObject) {
        final TextView txt = (TextView) view.findViewById(R.id.txt_item);
        txt.setText(libraryObject.getTitle());

        final ImageView img = (ImageView) view.findViewById(R.id.img_item);
        img.setImageResource(libraryObject.getRes());
    }


    public static class LibraryObject {

        private String mTitle, deptkom, link;
        private int mRes;

        public String getDeptkom() {
            return deptkom;
        }

        public LibraryObject(final int res, final String title, String deptkom, String link) {
            mRes = res;
            mTitle = title;
            this.deptkom = deptkom;
            this.link = link;
        }

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(final String title) {
            mTitle = title;
        }

        public int getRes() {
            return mRes;
        }

        public void setRes(final int res) {
            mRes = res;
        }

        public String getLink() {
            return link;
        }
    }
}
