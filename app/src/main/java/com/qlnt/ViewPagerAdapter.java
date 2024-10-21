package com.qlnt;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new LoginFragment(); // Fragment cho đăng nhập
            case 1:
                return new RegisterFragment(); // Fragment cho đăng ký
            default:
                return new RegisterFragment(); // Mặc định là trang đăng ký
        }
    }

    @Override
    public int getItemCount() {
        return 2; // Có 2 tab
    }
}
