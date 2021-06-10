package com.m90.shagoon.home;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.m90.shagoon.fragments.Fragment_AboutRadio;
import com.m90.shagoon.fragments.Fragment_AdvertisementRadio;
import com.m90.shagoon.fragments.Fragment_HomeRadio;
import com.m90.shagoon.fragments.Fragment_contactusRadio;
import com.m90.shagoon.R;
import com.yarolegovich.slidingrootnav.SlideGravity;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;
import java.util.Arrays;

public class Home extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener,View.OnClickListener {

    private static final int POS_HOME = 0;
    // private static final int POS_Playing = 1;
    private static final int POS_ADD = 1;
    // private static final int POS_socialmedia = 2;
    private static final int POS_share = 2;
    private static final int POS_ABOUT_US = 3;
    //private static final int NAV7 = 6;
    // Button Btn_logout;
    private String[] screenTitles;
    private Drawable[] screenIcons;
    private SlidingRootNav slidingRootNav;
    ImageView img_share;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_nav__home);

        setStatusbarColor();

        img_share = findViewById(R.id.img_share);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //  toolbar.setForegroundGravity(View.SCROLLBAR_POSITION_RIGHT);
        slidingRootNav = new SlidingRootNavBuilder(this)
                .withDragDistance(180)
                .withRootViewScale(1f)
                // .withRootViewScale(0.75f)
                .withGravity(SlideGravity.LEFT)
                .withRootViewElevation(25)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)//true
                .withContentClickableWhenMenuOpened(true)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.drawer_menu)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_HOME).setChecked(true),
                createItemFor(POS_ADD),
                createItemFor(POS_share),
                createItemFor(POS_ABOUT_US),
                new SpaceItem(5)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.drawer_list);

        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
        adapter.setSelected(POS_HOME);
toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);

        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareIt();

            }
        });

    }

    @Override
    public void onItemSelected(int position) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        if (position == POS_HOME) {
            //    getSupportActionBar().setTitle("Home");

              Fragment Home = new Fragment_HomeRadio();
              fragmentTransaction.replace(R.id.Frame_container, Home);
            //   fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            // Utilities.launchActivity(Select_Nav_Menu.this, Select_Nav_Menu.class,true);

        }


        // else   if (position == POS_Playing) {
        //     getSupportActionBar().setTitle("now");

        //    Utilities.launchActivity(Select_Nav_Menu.this, Select_Nav_Menu.class,true);

        // }
        else if (position == POS_ADD) {
            //  getSupportActionBar().setTitle("Advertisement");
            Fragment Home = new Fragment_AdvertisementRadio();
            fragmentTransaction.replace(R.id.Frame_container, Home);
         fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

        else if (position == POS_share) {
            //  getSupportActionBar().setTitle("CONTACT US");
           Fragment Home = new Fragment_contactusRadio();
            fragmentTransaction.replace(R.id.Frame_container, Home);
           fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (position == POS_ABOUT_US) {
            // getSupportActionBar().setTitle("About Us");
            Fragment Home = new Fragment_AboutRadio();
            fragmentTransaction.replace(R.id.Frame_container, Home);
         fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        slidingRootNav.closeMenu();
    }

    @SuppressWarnings("rawtypes")/////to set color
    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.white))
                .withTextTint(color(R.color.white))
                .withSelectedIconTint(color(R.color.white))
                .withSelectedTextTint(color(R.color.white));//black
    }

    /////load tite
    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.id_activityScreenTitles);
    }

    /////array
    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.id_activityScreenIcon);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }
    public void onBackPressed() {
int count =getSupportFragmentManager().getBackStackEntryCount();
 if ( count==0){
     super.onBackPressed();
 }
 else if (count==1){
     super.onBackPressed();
 }
else {
     getSupportFragmentManager().popBackStack();
 }
        //  finish();
        //  moveTaskToBack(true);
    }




    //color
    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    public void setStatusbarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));

            LayoutInflater inflater = getLayoutInflater();
            View tmpView;
            tmpView = inflater.inflate(R.layout.item_option, null);
            getWindow().addContentView(tmpView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));



        }

    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        super.addContentView(view, params);
    }

    @Override
    public void onClick(View v) {

    }

    private void shareIt() {

        Intent intent =new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Shagoon Radio");
        intent.putExtra(Intent.EXTRA_TEXT,"urllllll");
        intent.setType("text/plain");
        startActivity(intent);

    }



}