package com.curvegames.crazyballs.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.curvegames.ads.AdsController;
import com.curvegames.crazyballs.helper.AssetLoader;
import com.curvegames.crazyballs.screens.SplashScreen;

public class MyGdxGame extends Game{
	
	private AdsController adsController;
	 
	public MyGdxGame(AdsController adsController){
		if (adsController != null) {
            this.adsController = adsController;
        } else {
            this.adsController = new DummyAdsController();
        }
	}
	
	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		super.render();
		
	}


	@Override
	public void create () {
		Gdx.app.log("MyGdxGame","create method");
		AssetLoader.load();
		setScreen(new SplashScreen(this));
		if(adsController.isWifiConnected()) {adsController.showBannerAd();}
	}
	
	@Override
    public void dispose() {
		if (adsController.isWifiConnected()) {
		    adsController.showInterstitialAd(new Runnable() {
		        @Override
		        public void run() {
		                System.out.println("Interstitial app closed");
		                Gdx.app.exit();
		        }
		    });
		} else {
		    System.out.println("Interstitial ad not (yet) loaded");
		}
        super.dispose();
        AssetLoader.dispose();
    }
}