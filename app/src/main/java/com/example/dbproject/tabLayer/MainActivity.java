package com.example.dbproject.tabLayer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dbproject.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    private static final String[] TAB_NAMES= {"조회", "대여", "홈", "예약", "문의"};
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager2 = findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), getLifecycle(), dbHelper);
        viewPager2.setAdapter(pagerAdapter);

        viewPager2.setCurrentItem(2, false);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            tab.setText(TAB_NAMES[position]);
        }).attach();
    }
}