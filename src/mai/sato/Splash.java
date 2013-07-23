package mai.sato;

import trident.Zoo.GameActivity;
import trident.Zoo.R;
import trident.Zoo.R.id;
import trident.Zoo.R.layout;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.TextView;

public class Splash extends Activity{

	ZooData zooData;
	TextView textView;
	@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			// タイトルを非表示にします。
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			// splash.xmlをViewに指定します。
			zooData = new ZooData(this);
			setContentView(R.layout.title);
			textView = (TextView)findViewById(R.id.title);
			textView.setText("Sugoroku Project");
			Handler hdl = new Handler();
			// 500ms遅延させてsplashHandlerを実行します。
			hdl.postDelayed(new splashHandler(), 1500);
		}
		class splashHandler implements Runnable {
			public void run() {
				// スプラッシュ完了後に実行するActivityを指定します
				if(zooData.getName().equals("")){
					Intent intent = new Intent(getApplication(), Entry.class);
					startActivity(intent);
					// SplashActivityを終了させます。
					Splash.this.finish();
				}else{
					Intent intent = new Intent(getApplication(),GameActivity.class);
					startActivity(intent);
					// SplashActivityを終了させます。
					Splash.this.finish();

				}
			}
	}

}
