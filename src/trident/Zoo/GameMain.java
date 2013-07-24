/*
 * ゲーム本体。
 */
package trident.Zoo;

import java.util.Random;

import mai.sato.DBAdapter;
import mai.sato.ZooData;

import kaoru.matsuno.GameMap;
import kaoru.matsuno.MenuButton;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.view.WindowManager;

/**
 * ゲーム本体のアクティビティ。
 *
 * @author minnna
 */
public class GameMain {

	/** ================== 変数宣言 ================== **/

	/**
	 * FPS。
	 */
	private int fps;

	/**
	 * 経過時間
	 */
	private long startTime = 0;

	/**
	 * 合計経過時間
	 */
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

	/**
	 * 乱数オブジェクト。
	 */
	private Random r = new Random();

	/**
	 * BGM。
	 */
	private MediaPlayer bgm;

	/**
	 * 効果音。
	 */
	private MediaPlayer se;

	/**
	 * コンテキスト。
	 */
	private Context context;

	/**
	 * 背景。
	 */
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

	/** ============================================ **/

	/**
	 * コンストラクタ
	 */
	public GameMain(Context context) {
		this.context = context;

		// タッチ処理用コントローラを作成する。
		virtualController = new VirtualController();

		// タッチ座標用変数を作成
		touch_push = new Vector2D();
		touch_now = new Vector2D();
		touch_release = new Vector2D();
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
		// タッチ処理用コントローラを作成する。
		virtualController = new VirtualController();

		// BGMを読み込む。
		bgm = MediaPlayer.create(context, R.raw.bgm);

		// SEの読み込み
		se = MediaPlayer.create(context, R.raw.se);

		// リソースから背景画像を読み込む。
		bg = BitmapFactory
				.decodeResource(context.getResources(), R.drawable.bg);
		bg = Bitmap.createScaledBitmap(bg, disp.getWidth(), disp.getHeight(), true);

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inScaled = false;
		mapImg = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.quartersample,options);

		map = new GameMap(mapImg);

		// ランダムの作成
		r = new Random();

		// ベクトルの作成
		vec = new Vector2D();

	}

	/**
	 * 0～maxの中からランダムな整数を得る。
	 *
	 * @param max
	 * @return 乱数値
	 */
	public int getRandom(int max) {
		return r.nextInt(max);
	}

	/**
	 * 全サウンドを終了する（アプリ終了時にも呼ばれる）
	 */
	public void stopSound() {
		bgm.stop();
		se.stop();
	}

	/**
	 * ゲームを初期化する。
	 */
	void initialize() {
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
		updateGame(elapsedTime);
	}

	/**
	 * フレーム毎に描画する。
	 *
	 * @param sv
	 *            サーフェイスビュー
	 */
	public void draw(GameSurfaceView sv) {
		drawGame(sv);
	}

	/**
	 * ゲームの状態を更新する。
	 */
	void updateGame(float elapsedTime) {

		if (elapsedTime != 0.0f) {
			// デバック用　現在意味をなしていない GameActivityに問題あり
			// 合計経過時間
			totalElapsedTime += elapsedTime;
		}
		// 画面にタッチしてる？
		if (VirtualController.isTouch(0)) {
			// FPSの表示座標をタッチ位置に更新
			if (VirtualController.isTouchTrigger(0)) {
				touch_push.x = VirtualController.getTouchX(0);
				touch_push.y = VirtualController.getTouchY(0);

				// タッチ座標を入れる
				for(int i = 0;i < menu.length;i++){
					if(menu[i].TouchButton((int)touch_push.x, (int)touch_push.y)){
						selectButton = menu[i].getName();
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
			se.start();
		} else {
			// 指を離した時の座標を入れる
			touch_release.x = VirtualController.getTouchX(0);
			touch_release.y = VirtualController.getTouchY(0);

			vec.x = touch_release.x - touch_push.x;
			vec.y = touch_release.y - touch_push.y;
		}

	}

	/**
	 * ゲームシーンを描画する。
	 *
	 * @param sv
	 *            サーフェイスビュー
	 */
	void drawGame(GameSurfaceView sv) {
		// 背景を表示する。
		sv.DrawImage(bg, 0, 0);

		map.draw(sv);
		// テキストを表示する。
		sv.DrawText("FPS:" + fps, 10, 20, Color.BLACK);
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

	/**
	 * FPSを設定する。
	 *
	 * @param fps
	 *            FPS
	 */
	public void setFps(int fps) {
		this.fps = fps;
	}
}
