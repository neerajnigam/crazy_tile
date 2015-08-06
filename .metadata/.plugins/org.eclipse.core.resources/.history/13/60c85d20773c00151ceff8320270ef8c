package com.guts.tile;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.curvegames.ads.AdsController;
import com.curvegames.crazyballs.game.MyGdxGame;

public class MainActivity extends AndroidApplication implements AdsController{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        
        initialize(new MyGdxGame(this), cfg);
    }

	@Override
	public void showBannerAd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hideBannerAd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isWifiConnected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void showInterstitialAd(Runnable then) {
		// TODO Auto-generated method stub
		
	}
}