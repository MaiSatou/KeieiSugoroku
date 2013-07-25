package mai.sato;

import java.util.Random;

import trident.Zoo.GameSurfaceView;
import trident.Zoo.R;
import trident.Zoo.Vector2D;
import trident.Zoo.VirtualController;
import trident.Zoo.R.drawable;

import kaoru.matsuno.GameMap;
import kaoru.matsuno.MenuButton;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.Display;
import android.view.WindowManager;

public class Zoo {

	private float totalElapsedTime;
	/**
	 * 仮想コントローラ。
	 */
	private VirtualController virtualController;
	/**
	 * マウスを押したX,Y座標。
	 */
	private Vector2D touch_push;
	/**
	 * マウスを押している座標
	 */
	private Vector2D touch_now;

	/**
	 * マウスを離したX,Y座標
	 */
	private Vector2D touch_release;
	private Context context;

	/**
	 * 背景。
	 */

	private int selectButtonNum;
	private Bitmap bg;

	private DBAdapter dbAdapter;
	private ZooData zooData;

	private MenuButton[] menu;
	private String[] buttonName = {"TITLE","SUGOROKU","LAYOUT","SHOP","SET"};
	private String selectButton = "noSelect";
	private WindowManager wm;
	private Display disp;
	/**
	 * ビットマップ用画像
	 */
	private Bitmap mapImg;
	/**
	 * マップチップクラス
	 */
	private GameMap map;

	/**
	 * ベクトル
	 */
	private Vector2D vec;
	public Zoo(Context context){
		virtualController = new VirtualController();
		// タッチ座標用変数を作成
		touch_push = new Vector2D();
		touch_now = new Vector2D();
		touch_release = new Vector2D();

		this.context = context;
		wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		disp = wm.getDefaultDisplay();
		// 端末に保存されているユーザー名を取得する。
		menu = new MenuButton[buttonName.length];

		for(int i = 0;i < menu.length;i++){
			menu[i] = new MenuButton(10, 50 + (100 * i), 80,120 + (100 * i),buttonName[i]);
		}
		zooData = new ZooData(this.context);

		dbAdapter = new DBAdapter(this.context);
		dbAdapter.saveData("abcdefg",0,0,0);
		dbAdapter.loadData();

		// リソースから背景画像を読み込む。
		bg = BitmapFactory
				.decodeResource(context.getResources(), R.drawable.bg);
		bg = Bitmap.createScaledBitmap(bg, disp.getWidth(), disp.getHeight(), true);

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inScaled = false;
		mapImg = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.quartersample,options);

		map = new GameMap(mapImg);

		// ベクトルの作成
		vec = new Vector2D();
	}

	public void initialize() {
		// BGMを再生する。
		// bgm.start();

		context = null;

		touch_push.Init();
		touch_now.Init();
		touch_release.Init();

		vec.Init();

		totalElapsedTime = 0;

	}

	/**
	 * フレーム毎に座標などを更新する。
	 */
	public void update(float elapsedTime) {
		touchupdate();
	}

	public void touchupdate(){
		// 画面にタッチしてる？
		if (VirtualController.isTouch(0)) {
			// FPSの表示座標をタッチ位置に更新
			if (VirtualController.isTouchTrigger(0)) {
				touch_push.x = VirtualController.getTouchX(0);
				touch_push.y = VirtualController.getTouchY(0);


				// タッチ座標を入れる
				for(int i = 0;i < menu.length;i++){
					if(menu[i].TouchButton((int)touch_push.x, (int)touch_push.y)){
						selectButtonNum = i;
					}
				}
			}
			// 指を離した時の座標を入れる
			// タッチされている座標を入れる
			touch_now.x = VirtualController.getTouchX(0);
			touch_now.y = VirtualController.getTouchY(0);

			vec.x = touch_now.x - touch_push.x;
			vec.y = touch_now.y - touch_push.y;
			// 効果音を再生する。
		} else {
			// 指を離した時の座標を入れる
			touch_release.x = VirtualController.getTouchX(0);
			touch_release.y = VirtualController.getTouchY(0);

			vec.x = touch_release.x - touch_push.x;
			vec.y = touch_release.y - touch_push.y;
		}

	}
	public void draw(GameSurfaceView sv){
		// 背景を表示する。
		sv.DrawImage(bg, 0, 0);
		map.draw(sv);

		// テキストを表示する。
		sv.DrawText("TIME:" + totalElapsedTime + "現在意味なし", 10, 40, Color.BLACK);
		sv.DrawText("x:" + touch_push.x + " y:" + touch_push.y, 100, 20,
				Color.WHITE);
		sv.DrawText("x2:" + touch_release.x + " y2:" + touch_release.y, 300,
				20, Color.WHITE);
		sv.DrawText("vec_X:" + vec.x + " vec_y:" + vec.y, 300, 40, Color.WHITE);
		sv.DrawText("マップチップ当たり" + map.getFlag() , 300, 300, Color.BLACK);


		// テキストを表示する。
		sv.DrawText("Name:" + zooData.getName(), 10, 20, Color.BLACK);
		sv.DrawText("Rank:", 200, 20, Color.WHITE);
		for(int i = 0;i < zooData.getRank();i++){
			sv.DrawText("★", 250 + (20 * i), 20, Color.WHITE);

		}
		sv.DrawText("Money:" + "￥" + zooData.getMoney(), 500, 20, Color.WHITE);
		sv.DrawText("x:" + touch_push.x, 500, 100, Color.WHITE);
		sv.DrawText("y:" + touch_push.y, 700, 100, Color.WHITE);
		sv.DrawText(selectButton, 800, 20, Color.WHITE);

		// メニューボタン
		for(int i = 0;i < menu.length;i++){
			sv.DrawRect(menu[i].getX(), menu[i].getY(), menu[i].getW(),menu[i].getH(),Color.CYAN);
		}
		sv.DrawRect((int)touch_push.x, (int)touch_push.y, (int)touch_push.x + 10,(int)touch_push.y + 10,Color.CYAN);

	}
}
