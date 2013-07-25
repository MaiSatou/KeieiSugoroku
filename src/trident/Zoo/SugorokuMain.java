/*
 * ゲーム本体。
 */
package trident.Zoo;

import java.util.Random;

import takuya.kumagai.SugorokuMap;
import Yamauchi.Event1;
import Yamauchi.Event2;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.Display;
import android.view.WindowManager;

/**
 * ゲーム本体のアクティビティ。
 *
 * @author minnna
 */
public class SugorokuMain {

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

	private WindowManager wm;
	private Display disp;

	/**
	 * ビットマップ用画像
	 */
	private Bitmap mapImg;

	/**
	 * ゲーム用マップ
	 */
	private SugorokuMap sugorokumap;

	/**
	 * ベクトル
	 */
	private Vector2D vec;

	/**
	 * ダイス
	 */
	int dice;
	
	/**
	 * イベント１
	 */
	private Event1 event1;
	
	/**
	 * イベント用画像１
	 */
	private Bitmap eventImg1;
	
	/**
	 * イベント文字画像１
	 */
	private Bitmap textImg1;
	
	/**
	 * イベント２
	 */
	private Event2 event2;
	
	/**
	 * イベント用画像２
	 */
	private Bitmap eventImg2;
	
	/**
	 * スタート画像
	 */
	private Bitmap startImg;
	
	//サイコロ
	private Bitmap one;
	private Bitmap two;
	private Bitmap three;
	private Bitmap four;
	private Bitmap five;
	private Bitmap six;
	
	//カウント
	private int count;
	
	//サイコロフラグ
	private boolean saikoroflag;
	
	//結果フラグ
	private boolean resultflag;
	
	
	/** ============================================ **/

	/**
	 * コンストラクタ
	 */
	public SugorokuMain(Context context) {
		this.context = context;


		// タッチ処理用コントローラを作成する。
		virtualController = new VirtualController();

		// タッチ座標用変数を作成
		touch_push = new Vector2D();
		touch_now = new Vector2D();
		touch_release = new Vector2D();
		wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		disp = wm.getDefaultDisplay();
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
				R.drawable.mapchip,options);

		// 各種生成
		sugorokumap = new SugorokuMap(mapImg); // マップ
		// ランダムの作成
		r = new Random();

		// ベクトルの作成
		vec = new Vector2D();
		
		//イベント１画像を読み込む
		eventImg1 = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.dog,options);
		
		//イベント１文字画像を読み込む
		textImg1 = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.event,options);
		
		//スタート画像を読み込む
		startImg = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.start,options);
		
		//イベント１生成
		event1 = new Event1(eventImg1,textImg1,startImg);
		
		//イベント２画像を読み込む
		eventImg2 = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.saru,options);
		
		//イベント２生成
		event2 = new Event2(eventImg2,textImg1,startImg);
		
		//サイコロ
		one = BitmapFactory.decodeResource(context.getResources(),
				 R.drawable.one,options);				
		two = BitmapFactory.decodeResource(context.getResources(),
				 R.drawable.two,options);
		three = BitmapFactory.decodeResource(context.getResources(),
				 R.drawable.three,options);
	 	four = BitmapFactory.decodeResource(context.getResources(),
	 			 R.drawable.four,options);
		five = BitmapFactory.decodeResource(context.getResources(),
				 R.drawable.five,options);
		six = BitmapFactory.decodeResource(context.getResources(),
				 R.drawable.six,options);
		
		count = 0;
		
		saikoroflag = false;
		
		resultflag = false;
		
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

		event1.On_EventFlag();
		if(event1.Return_StartFlag() == false)
		{
			dice = 0;
		}
		// 画面にタッチした瞬間にカウントを上げる
		if (VirtualController.isTouchTrigger(0))
		{
			count ++;
		}
		else
		{
		}
		//スタートしてタッチしていないならば結果フラグを点灯
		if(event1.Return_StartFlag() == true)
		{
			if(VirtualController.isTouch(0) == false)
			{
				resultflag = true;
			}
		}
		
		
		if (elapsedTime != 0.0f) {
			// デバック用　現在意味をなしていない GameActivityに問題あり
			// 合計経過時間
			totalElapsedTime += elapsedTime;
		}
		// 画面にタッチしてる？
		if (VirtualController.isTouch(0)) {
			// FPSの表示座標をタッチ位置に更新
	//		if (VirtualController.isTouchTrigger(0)) {
				touch_push.x = VirtualController.getTouchX(0);
				touch_push.y = VirtualController.getTouchY(0);
				
				if(touch_push.x > 300)
				{
					event1.On_StartFlag();
				}
				
				if(event1.Return_StartFlag() == true && event1.Return_StartFlag() == true)
				{
					if(touch_push.x > 300)
					{
						//カウントが１の場合サイコロを振る
						if(count == 1)
						{
							// 1~6のダイス
							dice = getRandom(5) + 1;
						}
					}
				}
				
				
				// マップの更新
				sugorokumap.Update(dice);
		
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

		/*//サイコロアニメーション
		if(saikoro.getNumber() == 1)
		{
			sv.DrawImage(one, 520,220);
		}
		else if(saikoro.getNumber() == 2)
		{
			sv.DrawImage(two, 520,220);
		}
		else if(saikoro.getNumber() == 3)
		{
			sv.DrawImage(three, 520,220);
		}
		else if(saikoro.getNumber() == 4)
		{
			sv.DrawImage(four, 520,220);
		}
		else if(saikoro.getNumber() == 5)
		{
			sv.DrawImage(five, 520,220);
		}
		else
		{
			sv.DrawImage(six, 520,220);
		}*/

		

		/*//イベント結果
		if(VirtualController.isTouch(0) == false)
		{
			if(saikoro.getNumber() != 0)
			{
				if(saikoro.getNumber()%2 == 0)
				{
					sv.DrawText("成功", 530, 340, Color.BLACK);
				}
				else
				{
					sv.DrawText("失敗", 530, 340, Color.BLACK);
				}
				textflag = true;
			}
		}*/

		// マップの表示
	//	sugorokumap.draw(sv);

		sv.DrawText("dice:" + dice, 10, 40, Color.BLACK);
		// テキストを表示する。
		sv.DrawText("FPS:" + fps, 10, 20, Color.BLACK);
		sv.DrawText("TIME:" + totalElapsedTime + "現在意味なし", 10, 40, Color.BLACK);
		sv.DrawText("x:" + touch_push.x + " y:" + touch_push.y, 100, 20,
				Color.WHITE);
		sv.DrawText("x2:" + touch_release.x + " y2:" + touch_release.y, 300,
				20, Color.WHITE);
		sv.DrawText("vec_X:" + vec.x + " vec_y:" + vec.y, 300, 40, Color.WHITE);
		
		//スタートしていなければ
		if(event1.Return_StartFlag() == false)
		{
			//イベント１とスタートの表示
			event1.Draw(sv);
			event1.DrawStart(sv);
		}
		//スタートしたら
		else
		{
			//イベント１の表示
			event1.Draw(sv);
		}
		
		// FPSの表示座標をタッチ位置に更新
		if (VirtualController.isTouch(0)) {
			//サイコロ
			if(dice == 1)
			{
				sv.DrawImage(one, 520,220);
			}
			else if(dice == 2)
			{
				sv.DrawImage(two, 520,220);
			}
			else if(dice == 3)
			{
				sv.DrawImage(three, 520,220);
			}
			else if(dice == 4)
			{
				sv.DrawImage(four, 520,220);
			}
			else if(dice == 5)
			{
				sv.DrawImage(five, 520,220);
			}
			else if(dice == 6)
			{
				sv.DrawImage(six, 520,220);
			}
			else
			{
			}
		}
		else
		{
			//サイコロ
			if(dice == 1)
			{
				sv.DrawImage(one, 520,220);
			}
			else if(dice == 2)
			{
				sv.DrawImage(two, 520,220);
			}
			else if(dice == 3)
			{
				sv.DrawImage(three, 520,220);
			}
			else if(dice == 4)
			{
				sv.DrawImage(four, 520,220);
			}
			else if(dice == 5)
			{
				sv.DrawImage(five, 520,220);
			}
			else if(dice == 6)
			{
				sv.DrawImage(six, 520,220);
			}
			else
			{
			}
		}
		
		if(resultflag == true)
		{
			if(dice %2 == 0)
			{
				sv.DrawText("成功", 530, 340, Color.WHITE);
			}
			else
			{
				sv.DrawText("失敗", 530, 340, Color.WHITE);
			}
		}
		
		sv.DrawText(""+saikoroflag, 530, 400, Color.WHITE);
		sv.DrawText(""+count, 580, 400, Color.WHITE);
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