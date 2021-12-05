package dk.au.mad21fall.appproject.justdrink.View;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import java.util.ArrayList;

import dk.au.mad21fall.appproject.justdrink.Model.Location;
import dk.au.mad21fall.appproject.justdrink.Model.PlaceListLogic.ItemClick;
import dk.au.mad21fall.appproject.justdrink.Model.Repository;
import dk.au.mad21fall.appproject.justdrink.R;
import dk.au.mad21fall.appproject.justdrink.databinding.ActivityMainBinding;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity implements ItemClick {

    private ActivityMainBinding binding;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.getTabAt(1).setIcon(R.drawable.icon_map);
        tabs.setupWithViewPager(viewPager);


        tabs.getTabAt(0).setIcon(R.drawable.icon_map);
        tabs.getTabAt(1).setIcon(R.drawable.icon_profile);
        tabs.getTabAt(2).setIcon(R.drawable.icon_chat);
        tabs.getTabAt(3).setIcon(R.drawable.icon_list);



        //https://stackoverflow.com/questions/34562117/how-do-i-change-the-color-of-icon-of-the-selected-tab-of-tablayout
        for(int i=0;i<4;i++) {
            tabs.getTabAt(i).getIcon().setColorFilter(Color.parseColor("#D09C2E"), PorterDuff.Mode.SRC_IN);
        }

    }

    @Override
    public void onItemClick(int position) {
        if(viewPager.getCurrentItem()==3) {
            //https://stackoverflow.com/questions/18609261/getting-the-current-fragment-instance-in-the-viewpager
            Fragment fragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.view_pager + ":" + viewPager.getCurrentItem());
            ((ListviewFragment)fragment).clickPlace(position);
        }
    }
}