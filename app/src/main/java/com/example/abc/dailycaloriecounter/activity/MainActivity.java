package com.example.abc.dailycaloriecounter.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.abc.dailycaloriecounter.fragments.FragmentCamera;
import com.example.abc.dailycaloriecounter.fragments.FragmentDiary;
import com.example.abc.dailycaloriecounter.R;
import com.example.abc.dailycaloriecounter.adapter.FragmentViewPagerAdapter;
import com.example.abc.dailycaloriecounter.fragments.FragmentHome;
import com.example.abc.dailycaloriecounter.fragments.FragmentProfile;
import com.example.abc.dailycaloriecounter.fragments.FragmentReports;
import com.example.abc.dailycaloriecounter.transforms.NonSwipeableViewPager;
import com.example.abc.dailycaloriecounter.views.TabLayoutPlus;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {
    NonSwipeableViewPager viewPager;
    TabLayoutPlus tabLayout;
    FragmentViewPagerAdapter adapter;
    private static final int REQUEST_CAMERA = 1;
    private static String[] PERMISSION_CAMERA = {
            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (NonSwipeableViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayoutPlus) findViewById(R.id.tabs);
        createViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(10);
        createTabIcons();
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(this, PERMISSION_CAMERA, REQUEST_CAMERA);
        }


    }

    private void clarifaiBuilder(OkHttpClient okHttpClient) {
    }

    private void createViewPager(ViewPager viewPager) {
        adapter = new FragmentViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentProfile(), "Tab 1");
        adapter.addFragment(new FragmentDiary(), "Tab 2");
        adapter.addFragment(new FragmentCamera(), "Tab 3");
        adapter.addFragment(new FragmentReports(), "Tab 4");
        adapter.addFragment(new FragmentHome(), "Tab 5");
        viewPager.setAdapter(adapter);

    }

    private void createTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Profile");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.profile_selector, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Diary");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.diary_selector, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("Add Food");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.camera_selector, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setText("Reports");
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.report_selector, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFour);

        TextView tabFive = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFive.setText("Home");
        tabFive.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_selector, 0, 0);
        tabLayout.getTabAt(4).setCustomView(tabFive);
    }

    public void switchTab(int index) {
        viewPager.setCurrentItem(index, true);
    }
}
