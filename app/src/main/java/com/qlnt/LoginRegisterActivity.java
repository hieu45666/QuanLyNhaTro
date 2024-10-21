package com.qlnt;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class LoginRegisterActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private int currentColor; // Thay đổi màu theo ViewPager

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        // Set adapter cho ViewPager2
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        // Liên kết TabLayout với ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Đăng Nhập");
                    break;
                case 1:
                    tab.setText("Đăng Ký");
                    break;
            }
        }).attach();

        // Thay đổi màu nền khi chuyển giữa các tab
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    currentColor = getResources().getColor(android.R.color.holo_blue_light);
                } else {
                    currentColor = getResources().getColor(android.R.color.holo_green_light);
                }

                // Cập nhật màu cho TabLayout
                tabLayout.setBackgroundColor(currentColor);
                viewPager.setBackgroundColor(currentColor);
            }
        });

        // Đặt màu nền cho TabLayout ban đầu
        currentColor = getResources().getColor(android.R.color.holo_blue_light);
        tabLayout.setBackgroundColor(currentColor);
        viewPager.setBackgroundColor(currentColor);
    }
}

