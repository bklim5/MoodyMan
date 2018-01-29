package com.bkbklim.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bkbklim.Helpers.DummyController;
import com.bkbklim.MoodyMan;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "MoodyMan";
		config.width = 272;
		config.height = 408;
		new LwjglApplication(new MoodyMan(new DummyController()), config);
	}
}
