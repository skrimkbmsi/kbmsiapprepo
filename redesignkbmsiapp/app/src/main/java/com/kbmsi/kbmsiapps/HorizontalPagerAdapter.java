package com.kbmsi.kbmsiapps;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gigamole.infinitecycleviewpager.VerticalInfiniteCycleViewPager;

import static com.kbmsi.kbmsiapps.Utils.setupItem;


/**
 * Created by GIGAMOLE on 7/27/16.
 */
public class HorizontalPagerAdapter extends PagerAdapter {

    //Website KBMSI, Wangsit (3: wassup, market, academy), Skrim(lomba, pendataan), ISHOT, MRFEST,
    //Porsi, Siluet, E-Complaint, About us.
    private final Utils.LibraryObject[] LIBRARIES = new Utils.LibraryObject[]{
            new Utils.LibraryObject(
                    R.drawable.kbmsi,
                    "Lembaga KBMSI", "pindahact","http://kbmsi.filkom.ub.ac.id/"
            ),
            new Utils.LibraryObject(
                    R.drawable.wangsitnew,
                    "Wangsit", "","http://wangsit.kbmsi.or.id/"
            ),
            new Utils.LibraryObject(
                    R.drawable.skrim,
                    "S-KRIM", "","http://skrim.kbmsi.or.id/"
            ),
            new Utils.LibraryObject(
                    R.drawable.ishot,
                    "ISHOT 2.0", "","http://ishot.kbmsi.or.id/"
            ),
            new Utils.LibraryObject(
                    R.drawable.mrfest,
                    "MR-FEST", "","http://mrfest.kbmsi.or.id/"
            ),
            new Utils.LibraryObject(
                    R.drawable.kbmsi,
                    "PORSI 2017", "","http://wowsi.kbmsi.or.id/"
            ),
            new Utils.LibraryObject(
                    R.drawable.newsiluet,
                    "Siluet Magazine", "","http://kbmsi.filkom.ub.ac.id/magazinekbmsiapp/"
            ),
            new Utils.LibraryObject(
                    R.drawable.kbmsi,
                    "E-Complaint", "","http://ecomplaint.kbmsi.or.id"
            )
//            new Utils.LibraryObject(
//                    R.drawable.ic_design,
//                    "BPMSI", "","http://kbmsi.filkom.ub.ac.id/lembaga/bpmsi"
//            ),
    };

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private boolean mIsTwoWay;

    public HorizontalPagerAdapter(final Context context, final boolean isTwoWay) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mIsTwoWay = isTwoWay;
    }

    @Override
    public int getCount() {
        return mIsTwoWay ? 3 : LIBRARIES.length;
    }

    @Override
    public int getItemPosition(final Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final View view;
        if (mIsTwoWay) {
            view = mLayoutInflater.inflate(R.layout.two_way_item, container, false);

            final VerticalInfiniteCycleViewPager verticalInfiniteCycleViewPager =
                    (VerticalInfiniteCycleViewPager) view.findViewById(R.id.vicvp);
            verticalInfiniteCycleViewPager.setAdapter(
                    new VerticalPagerAdapter(mContext, position)
            );
            verticalInfiniteCycleViewPager.setCurrentItem(position);
        } else {
            view = mLayoutInflater.inflate(R.layout.item, container, false);
            CardView cardView = view.findViewById(R.id.cardnya);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!LIBRARIES[position].getDeptkom().equalsIgnoreCase("pindahact")){
                        Intent nextAct = new Intent(mContext, WebActivity.class);
                        nextAct.putExtra("id_berita", LIBRARIES[position].getLink());
                        mContext.startActivity(nextAct);
                    } else {
                        Intent nextAct = new Intent(mContext, LembagaMainActivity.class);
                        nextAct.putExtra("id_berita", LIBRARIES[position].getLink());
                        mContext.startActivity(nextAct);
                    }

                }
            });

            setupItem(mContext ,view, LIBRARIES[position]);
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
