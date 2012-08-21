
package com.collective.personalize.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceGroup;
import android.preference.PreferenceScreen;

import com.collective.personalize.AoCPPreferenceFragment;
import com.collective.personalize.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class About extends AoCPPreferenceFragment {

    public static final String TAG = "About";

    Preference mSiteUrl;
    Preference mSourceUrl;
    Preference mReviewUrl;
    Preference mgplusUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_about);
        addPreferencesFromResource(R.xml.prefs_about);
        mSiteUrl = findPreference("aocp_website");
        mSourceUrl = findPreference("aocp_source");
        mReviewUrl = findPreference("aocp_review");
        mgplusUrl = findPreference("aocp_gplus");

        PreferenceGroup devsGroup = (PreferenceGroup) findPreference("devs");
        ArrayList<Preference> devs = new ArrayList<Preference>();
        for (int i = 0; i < devsGroup.getPreferenceCount(); i++) {
            devs.add(devsGroup.getPreference(i));
        }
        devsGroup.removeAll();
        devsGroup.setOrderingAsAdded(false);
        Collections.shuffle(devs);
        for(int i = 0; i < devs.size(); i++) {
            Preference p = devs.get(i);
            p.setOrder(i);

            devsGroup.addPreference(p);
        }
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference == mSiteUrl) {
            launchUrl("http://underconstruction");
        } else if (preference == mSourceUrl) {
            launchUrl("http://github.com/xaocpx");
        } else if (preference == mReviewUrl) {
            launchUrl("http://in_development");
        } else if (preference == mgplusUrl) {
            launchUrl("https://plus.google.com/118410901028408865706/posts");
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    private void launchUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent profile = new Intent(Intent.ACTION_VIEW, uriUrl);
        getActivity().startActivity(profile);
    }
}
