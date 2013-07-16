/*
 * ゲーム本体。
 */
package trident.Zoo;

import java.util.Random;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;

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
	 * 仮想コントローラ。
	 */
	private VirtualController virtualController;

	/**
	 * マウスを押したX,Y座標。
	 */
	private Vector2D touch_push = new Vector2D();

	/**
	 * マウスを離したX,Y座標
	 */
	private Vector2D touch_release = new Vector2D();

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
	/** ============================================ **/

	/**
	 * コンストラクタ
	 */
	public GameMain(Context context) {
		this.context = context;
		// 端末に保存されているユーザー名を取得する。
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

		touch_release.Init();
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
		// 画面にタッチしてる？
		if (VirtualController.isTouch(0)) {
			// FPSの表示座標をタッチ位置に更新
			if (VirtualController.isTouchTrigger(0)) {
				// タッチ座標を入れる
				touch_push.x = VirtualController.getTouchX(0);
				touch_push.y = VirtualController.getTouchY(0);
			}

			// 効果音を再生する。
			se.start();
		} else {
			// 指を離した時の座標を入れる
			touch_release.x = VirtualController.getTouchX(0);
			touch_release.y = VirtualController.getTouchY(0);
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

		// テキストを表示する。
		sv.DrawText("Name:" + zooData.getName(), 10, 20, Color.BLACK);
		sv.DrawText("Rank:", 200, 20, Color.WHITE);
		for(int i = 0;i < zooData.getRank();i++){
			sv.DrawText("★", 250 + (20 * i), 20, Color.WHITE);

		}
		sv.DrawText("Money:" + "￥" + zooData.getMoney(), 500, 20, Color.WHITE);
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
