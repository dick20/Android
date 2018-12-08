package adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example2.asus.myapplication.Fragment2;

import view.Fragment1;


public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    final private String[] titles = new String[]{"技能介绍", "出装建议"};

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 1) {
            return new Fragment2();
        }
        return new Fragment1();
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    // ViewPager与TabLayout绑定后，这里获取到的PageTitle就是Tab的text
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
