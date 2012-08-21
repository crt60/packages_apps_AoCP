
package com.collective.personalize.widgets;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.collective.personalize.R;
import com.koushikdutta.urlimageviewhelper.UrlImageViewCallback;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.io.InputStream;
import java.net.HttpURLConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

public class DeveloperPreference extends Preference {

    private static final String TAG = "DeveloperPreference";

    private ImageView twitterButton;
    private ImageView profileButton;
    private ImageView photoView;

    private TextView devName;

    private String nameDev;
    private String twitterName;
    private String profileLink;

    public DeveloperPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DeveloperPreference);
        nameDev = a.getString(R.styleable.DeveloperPreference_nameDev);
        twitterName = a.getString(R.styleable.DeveloperPreference_twitterHandle);
        profileLink = a.getString(R.styleable.DeveloperPreference_profileLink);
        a.recycle();
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        super.onCreateView(parent);

        View layout = View.inflate(getContext(), R.layout.dev_card, null);

        twitterButton = (ImageView) layout.findViewById(R.id.twitter_button);
        profileButton = (ImageView) layout.findViewById(R.id.about_button);
        devName = (TextView) layout.findViewById(R.id.name);
        photoView = (ImageView) layout.findViewById(R.id.photo);

        return layout;
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);

        if (profileLink != null) {
            final OnClickListener openDonate = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri profileURL = Uri.parse(profileLink);
                    final Intent intent = new Intent(Intent.ACTION_VIEW, profileURL);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getContext().startActivity(intent);
                }
            };

            profileButton.setOnClickListener(openDonate);
        } else {
            profileButton.setVisibility(View.GONE);
        }

        if (twitterName != null) {
            final OnPreferenceClickListener openTwitter = new OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Uri twitterURL = Uri.parse("http://twitter.com/#!/" + twitterName);
                    final Intent intent = new Intent(Intent.ACTION_VIEW, twitterURL);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getContext().startActivity(intent);
                    return true;
                }
            };

            // changed to clicking the preference to open twitter
            // it was a hit or miss to click the twitter bird
            // twitterButton.setOnClickListener(openTwitter);
            this.setOnPreferenceClickListener(openTwitter);
            final String url = "http://api.twitter.com/1/users/profile_image/" + twitterName
                    + "?size=original";
            UrlImageViewHelper.setUrlDrawable(this.photoView, url, R.drawable.ic_null,
                    UrlImageViewHelper.CACHE_DURATION_ONE_WEEK);
        } else {
            twitterButton.setVisibility(View.INVISIBLE);
            photoView.setVisibility(View.GONE);
        }

        devName.setText(nameDev);

    }
}
