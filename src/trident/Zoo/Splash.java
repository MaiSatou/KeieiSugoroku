package trident.Zoo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class Splash extends Activity{

	ZooData zooData;
	@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			// タイトルを非表示にします。
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			// splash.xmlをViewに指定します。
			zooData = new ZooData(this);
			setContentView(R.layout.main);
			Handler hdl = new Handler();
			// 500ms遅延させてsplashHandlerを実行します。
			hdl.postDelayed(new splashHandler(), 500);
		}
		class splashHandler implements Runnable {
			public void run() {
				// スプラッシュ完了後に実行するActivityを指定します
				if(zooData.getName().equals("")){
					Intent intent = new Intent(getApplication(), Entry.class);
					startActivity(intent);
					// SplashActivityを終了させます。
					Splash.this.finish();
				}
			}
	}

}
