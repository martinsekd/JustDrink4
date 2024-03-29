package dk.au.mad21fall.appproject.justdrink.View;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import dk.au.mad21fall.appproject.justdrink.View.ChatFragment;
import dk.au.mad21fall.appproject.justdrink.R;
import dk.au.mad21fall.appproject.justdrink.View.MapFragment;
import dk.au.mad21fall.appproject.justdrink.View.ProfileFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3, R.string.tab_text_4};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position){
            case 0:
                return new MapFragment();
            case 1:
                return new ProfileFragment();
            case 2:
                return new ChatFragment();
            case 3:
                return new ListviewFragment();
            default:
                return new MapFragment();
        }
        //return PlaceholderFragment.newInstance(position + 1);
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "";
        //return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 4 total pages.
        return 4;
    }
}