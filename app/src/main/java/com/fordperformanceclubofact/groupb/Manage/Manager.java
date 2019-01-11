package com.fordperformanceclubofact.groupb.Manage;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.fordperformanceclubofact.groupb.Fragments.Announcements;
import com.fordperformanceclubofact.groupb.Fragments.EventsAll;
import com.fordperformanceclubofact.groupb.Fragments.Notify;
import com.fordperformanceclubofact.groupb.R;

import java.util.ArrayList;
import java.util.List;

public class Manager extends AppCompatActivity {


    private Toolbar toolbar;

    private TabLayout tabLayout;

    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        int intentFragment = getIntent().getExtras().getInt("email_key");

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Manage");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);

        viewPager.setCurrentItem(intentFragment);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new Announcements(), "Announcements ");

        adapter.addFragment(new EventsAll(), "Events");

        adapter.addFragment(new Notify(), "Notifications");

        viewPager.setAdapter(adapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();

        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {

            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {

            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {

            mFragmentList.add(fragment);

            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
