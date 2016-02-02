package io.abhishekpareek.app.chirp;

/**
 * Created by apareek on 1/27/16.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private FragmentManager mFm;
    private Context mContext;

    private InboxFragment mInboxFragment;
    private SecondFragment mSecondFragment;

    public InboxFragment getInboxFragment() {
        return mInboxFragment;
    }

    public void setInboxFragment(InboxFragment inboxFragment) {
        mInboxFragment = inboxFragment;
    }

    public SecondFragment getSecondFragment() {
        return mSecondFragment;
    }

    public void setSecondFragment(SecondFragment secondFragment) {
        mSecondFragment = secondFragment;
    }
    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);

        mFm = fm;
        mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
        // save the appropriate reference depending on position
        switch (position) {
            case 0:
                setInboxFragment((InboxFragment) createdFragment);
                break;
            case 1:
                setSecondFragment((SecondFragment) createdFragment);
                break;
        }
        return createdFragment;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;

        // getItem is called to instantiate the fragment for the given page.
        switch (position) {
            case 0:
                Log.d(mContext.getClass().getSimpleName(), "Position 0");
                InboxFragment frag1 = InboxFragment.newInstance(position + 1);
                frag =  frag1;
                break;

            case 1:
                Log.d(mContext.getClass().getSimpleName(), "Position 1");
                SecondFragment frag2 = SecondFragment.newInstance(position + 1);
                frag = frag2;
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