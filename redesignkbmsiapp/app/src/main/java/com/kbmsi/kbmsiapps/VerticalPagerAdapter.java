package com.kbmsi.kbmsiapps;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import static com.kbmsi.kbmsiapps.Utils.setupItem;

/**
 * Created by GIGAMOLE on 7/27/16.
 */
public class VerticalPagerAdapter extends PagerAdapter {

    private final Utils.LibraryObject[] KBMSI_TWO_WAY_LIBRARIES = new Utils.LibraryObject[]{
            new Utils.LibraryObject(
                    R.drawable.iclogokbmsi_low,
                    "KBMSI", "KBMSI", "http://kbmsi.filkom.ub.ac.id/lembagakbmsiapp/kbmsi"
            ),
    };

    private String link;

    private final Utils.LibraryObject[] EMSI_TWO_WAY_LIBRARIES = new Utils.LibraryObject[]{
            new Utils.LibraryObject(
                    R.drawable.icemsi_nondep,
                    "Non-Departement", "NONDEP","http://kbmsi.filkom.ub.ac.id/lembagakbmsiapp/emsi/nondept"
            ),
            new Utils.LibraryObject(
                    R.drawable.icemsi,
                    "EMSI", "EMSI","no"
            ),
            new Utils.LibraryObject(
                    R.drawable.icemsi_pduas,
                    "P2S", "P2S","http://kbmsi.filkom.ub.ac.id/lembagakbmsiapp/emsi/p2s/"
            ),
            new Utils.LibraryObject(
                    R.drawable.icemsi_kwu,
                    "Kewirausahaan", "KWU","http://kbmsi.filkom.ub.ac.id/lembagakbmsiapp/emsi/kwu"
            ),
            new Utils.LibraryObject(
                    R.drawable.icemsi_advo,
                    "ADVOKESMA", "ADVOKESMA","http://kbmsi.filkom.ub.ac.id/lembagakbmsiapp/emsi/advokesma/"
            ),
            new Utils.LibraryObject(
                    R.drawable.icemsi_psdm,
                    "PSDM", "PSDM","http://kbmsi.filkom.ub.ac.id/lembagakbmsiapp/emsi/psdm"
            ),
            new Utils.LibraryObject(
                    R.drawable.icemsi_sosma,
                    "SOSMA", "SOSMA","http://kbmsi.filkom.ub.ac.id/lembagakbmsiapp/emsi/sosma"
            ),
            new Utils.LibraryObject(
                    R.drawable.icemsi_medkom,
                    "MEDKOMINFO", "MEDKOM","http://kbmsi.filkom.ub.ac.id/lembagakbmsiapp/emsi/infokom"
            )
    };

    private final Utils.LibraryObject[] BPMSI_TWO_WAY_LIBRARIES = new Utils.LibraryObject[]{
            new Utils.LibraryObject(
                    R.drawable.icbpmsi_advo,
                    "ADVOKASI", "ADVO","http://kbmsi.filkom.ub.ac.id/lembagakbmsiapp/bpmsi/aspirasi"
            ),
            new Utils.LibraryObject(
                    R.drawable.icbpmsi_huu,
                    "HUU", "HUU","http://kbmsi.filkom.ub.ac.id/lembagakbmsiapp/bpmsi/legislasi"
            ),
            new Utils.LibraryObject(
                    R.drawable.icbpmsi,
                    "BPMSI", "BPMSI","no"
            ),
            new Utils.LibraryObject(
                    R.drawable.icbpmsi_kominfo,
                    "KOMINFO", "KOMINFO","http://kbmsi.filkom.ub.ac.id/lembagakbmsiapp/bpmsi/kominfo"
            )
    };

    private LayoutInflater mLayoutInflater;
    private int positions;
    private Context context;

    public VerticalPagerAdapter(final Context context, int position) {
        mLayoutInflater = LayoutInflater.from(context);
        this.positions = position;
        this.context = context;
    }


    @Override
    public int getCount() {
        if (positions == 0) {
            return KBMSI_TWO_WAY_LIBRARIES.length;
        } else if (positions == 1) {
            return EMSI_TWO_WAY_LIBRARIES.length;
        } else if (positions == 2) {
            return BPMSI_TWO_WAY_LIBRARIES.length;
        }
        return KBMSI_TWO_WAY_LIBRARIES.length;
    }

    @Override
    public int getItemPosition(final Object object) {
        return POSITION_NONE;
    }

    private String deptataukom;
    private Utils.LibraryObject simpan;
    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {

        final View view = mLayoutInflater.inflate(R.layout.item, container, false);
        if (this.positions == 0) {
            deptataukom = "kbmsi";
            setupItem( context ,view, KBMSI_TWO_WAY_LIBRARIES[position]);
            CardView cardView = view.findViewById(R.id.cardnya);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent nextAct = new Intent(context, WebActivity.class);
                    nextAct.putExtra("id_berita", KBMSI_TWO_WAY_LIBRARIES[position].getLink());
                    if(KBMSI_TWO_WAY_LIBRARIES[position].getLink()+"" != "-"){
                        context.startActivity(nextAct);
                    }
                }
            });

            simpan = KBMSI_TWO_WAY_LIBRARIES[position];
        } else if (this.positions == 1) {
            deptataukom = "emsi";
            CardView cardView = view.findViewById(R.id.cardnya);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent nextAct = new Intent(context, WebActivity.class);
                    nextAct.putExtra("id_berita", EMSI_TWO_WAY_LIBRARIES[position].getLink());
                    if(!EMSI_TWO_WAY_LIBRARIES[position].getLink().equalsIgnoreCase("no")){
                        context.startActivity(nextAct);
                    }
                }
            });
            setupItem(context , view, EMSI_TWO_WAY_LIBRARIES[position]);
            simpan = EMSI_TWO_WAY_LIBRARIES[position];

        } else if (this.positions == 2) {
            deptataukom = "bpmsi";
            CardView cardView = view.findViewById(R.id.cardnya);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent nextAct = new Intent(context, WebActivity.class);
                    nextAct.putExtra("id_berita", BPMSI_TWO_WAY_LIBRARIES[position].getLink());
                    if(!BPMSI_TWO_WAY_LIBRARIES[position].getLink().equalsIgnoreCase("no")){
                        context.startActivity(nextAct);
                    }
                }
            });
            setupItem(context , view, BPMSI_TWO_WAY_LIBRARIES[position]);
            simpan = BPMSI_TWO_WAY_LIBRARIES[position];
        }
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }
}
