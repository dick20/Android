package view;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.design.widget.*;
import android.view.WindowManager;

import com.example2.asus.myapplication.R;
import adapter.TitleFragmentPagerAdapter;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    MyFragmentTOCollection tab1 ;
    MyFragmentTOMain tab2;
    MyFragmentTOGood tab3;
    private ViewPager viewpager;
    private  Drawer drawer;
    TitleFragmentPagerAdapter adapter;
    int change = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        goToMainPage();

        initTab();
        initDrawer();
    }

    private void initDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().withName("user").withIcon(getResources().getDrawable(R.drawable.user_header))
                )
//                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
//                    @Override
//                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
//                        return false;
//                    }
//                })
                .build();

        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("主页");
        // .withIcon(getDrawable(R.mipmap.ic_launcher))
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(1).withName("收藏");
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(1).withName("物品");
        PrimaryDrawerItem item5 = new PrimaryDrawerItem().withIdentifier(1).withName("比较英雄");
        SecondaryDrawerItem item4 = new SecondaryDrawerItem().withIdentifier(2).withName("注销");

        //create the drawer and remember the `Drawer` result object
        drawer = new DrawerBuilder()
                .withAccountHeader(headerResult)
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        item2,
                        item3,
                        item5,
                        new DividerDrawerItem(),
                        item4
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        switch (position){
                                case 1:
                                    viewpager.setCurrentItem(1);
                                    drawer.closeDrawer();
                                    break;
                                case 2:
                                    viewpager.setCurrentItem(0);
                                    drawer.closeDrawer();
                                    break;
                                case 3:
                                    viewpager.setCurrentItem(2);
                                    drawer.closeDrawer();
                                    break;
                                case 4:
                                    CompareActivity.start(MainActivity.this);
                                    break;
                                default:
                                    ;
                            return true;
                        }
//                        Toast.makeText(getApplicationContext(),"click:"+position,Toast.LENGTH_SHORT).show();
                        return true;
                    }
                })
                .build();
    }

    private void initTab() {
        change = 0;
        TabLayout tabLayout = findViewById(R.id.tab);
        viewpager = findViewById(R.id.pager);

        tabLayout.setTabTextColors(Color.WHITE, Color.BLACK);//设置文本在选中和为选中时候的颜色
        tabLayout.setSelectedTabIndicatorColor(Color.BLACK);//设置选中时的指示器的颜色
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//可滑动，默认是FIXED

        List<Fragment> fragments = new ArrayList<>();
        tab1 = new MyFragmentTOCollection();
        tab2 = new MyFragmentTOMain();
        tab3 = new MyFragmentTOGood();
        fragments.add(tab1);
        fragments.add(tab2);
        fragments.add(tab3);

        adapter = new TitleFragmentPagerAdapter(getSupportFragmentManager(), fragments, new String[]{"收藏", "主页", "物品"});

        viewpager.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPosition = tab.getPosition();
                switch (tabPosition){
                    case 0:
                        if(change == 1){
                            ((MyFragmentTOCollection) adapter.getItem(0)).refresh();
                        } else {
                            change = 1;
                        }
                        goTOCollectionPage();
                        break;
                    case 1:
                        goToMainPage();
                        break;
                    case 2:
                        goToGoodPage();
                        break;
                    default:
                        goToMainPage();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
        tabLayout.setupWithViewPager(viewpager);
        tabLayout.getTabAt(1).select();
    }

    private void goToMainPage() {
//        Toast.makeText(getApplicationContext(),"go to main page",Toast.LENGTH_SHORT).show();
    }

    private void goTOCollectionPage() {
//        Toast.makeText(getApplicationContext(),"go to collection page",Toast.LENGTH_SHORT).show();
    }

    private void goToGoodPage() {
//        Toast.makeText(getApplicationContext(),"go to good page",Toast.LENGTH_SHORT).show();
    }
}
