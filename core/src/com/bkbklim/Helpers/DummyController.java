package com.bkbklim.Helpers;

/**
 * Created by boonkhailim on 14/9/17.
 */

public class DummyController implements ThirdPartyController {
    @Override
    public void showBannerAd() {

    }

    @Override
    public void hideBannerAd() {

    }

    @Override
    public boolean isAdShown() {
        return true;
    }

//    @Override
//    public void postFacebook(int score, String path) {
//
//    }
}
