package com.bkbklim;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.bkbklim.Helpers.ThirdPartyController;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class AndroidLauncher extends AndroidApplication implements ThirdPartyController {
    private static final String ADMOB_ADUNIT = "<your admob adunit here>";
    AdView bannerAd;
    RelativeLayout relativeLayout;
    View gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        gameView = initializeForView(new MoodyMan(this), config);
        relativeLayout = new RelativeLayout(this);
        relativeLayout.addView(gameView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);

        setupAds();

        relativeLayout.addView(bannerAd, params);
        setContentView(relativeLayout);
    }

    private void setupAds() {
        bannerAd = new AdView(this);
        bannerAd.setVisibility(View.VISIBLE);
        bannerAd.setBackgroundColor(0xff000000);
        bannerAd.setAdUnitId(ADMOB_ADUNIT);
        bannerAd.setAdSize(AdSize.SMART_BANNER);

        AdRequest adRequest = new AdRequest.Builder().build();
        bannerAd.loadAd(adRequest);
    }

    @Override
    public void showBannerAd() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bannerAd.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideBannerAd() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bannerAd.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public boolean isAdShown() {
        return bannerAd.getVisibility() == View.VISIBLE;
    }
}
