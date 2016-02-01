package io.abhishekpareek.app.chirp;

/**
 * Created by apareek on 1/27/16.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag = null;
        // getItem is called to instantiate the fragment for the given page.
        switch (position) {
            case 0:
                frag = InboxFragment.newInstance(position + 1);
                break;
            case 1:
                frag = SecondFragment.newInstance(position + 1);
                break;
        }

        return frag;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "INBOX";
            case 1:
                return "SECOND";
        }
        return null;
    }
}