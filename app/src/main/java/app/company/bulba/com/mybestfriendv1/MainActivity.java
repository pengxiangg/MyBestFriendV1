package app.company.bulba.com.mybestfriendv1;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("To: My Best Friend");
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    setTitle("To: My Best Friend");
                } else if(position==1){
                    setTitle("To: Me");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapterViewPager);



    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;

        public MyPagerAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new FirstFragment();
                case 1:
                    return new SecondFragment();
                default:
                    return null;
            }
        }



        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
    }



}
