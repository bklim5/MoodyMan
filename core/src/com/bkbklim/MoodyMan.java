package com.bkbklim;

import com.badlogic.gdx.Game;
import com.bkbklim.Helpers.AssetLoader;
import com.bkbklim.Helpers.ThirdPartyController;
import com.bkbklim.Screens.SplashScreen;

public class MoodyMan extends Game {

	private ThirdPartyController thirdPartyController;

	public MoodyMan(ThirdPartyController thirdPartyController) {
		this.thirdPartyController = thirdPartyController;
	}

	@Override
	public void create () {
		AssetLoader.load();
		setScreen(new SplashScreen(this, thirdPartyController));
	}

	
	@Override
	public void dispose () {
		super.dispose();
		AssetLoader.dispose();
	}
}
