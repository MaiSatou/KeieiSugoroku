/**
 * 描画用サーフェイス。
 */
package trident.Zoo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 描画用サーフェイス。
 *
 * @author wada
 */
public class GameSurfaceView extends SurfaceView implements
		SurfaceHolder.Callback {
	/**
	 * 塗りつぶし色。
	 */
	public int clearColor = Color.argb(255, 0, 0, 50);

	/**
	 * 最大描画オブジェクト数。
	 */
	protected Canvas canvas;

	/**
	 * 端末の画面サイズ。
	 */
	private Rect m_ScreenSize;

	/**
	 * ゲーム設定画面サイズ。
	 */
	private Rect gameScreenSize;

	/**
	 * 描画用設定。
	 */
	private Paint paint = new Paint();

	/**
	 * 保存用のサーフェイスのホルダー。
	 */
	private SurfaceHolder surfaceHolder;

	/**
	 * 数字用ビットマップ。
	 */
	private Bitmap bmpNumber;

	/**
	 * コンストラクタで自分をホルダーに登録する。
	 *
	 * @param context
	 *            コンテキスト
	 * @param screen
	 *            スクリーンサイズ
	 * @param gameScreen
	 */
	public GameSurfaceView(Context context, Rect screen, Rect gameScreen) {
		super(context);

		// m_ScreenSize = screen;
		// gameScreenSize = gameScreen;
		setFocusable(true);
		requestFocus();

		// ホルダーに自分を登録する
		surfaceHolder = getHolder();
		surfaceHolder.setFormat(PixelFormat.TRANSPARENT);
		surfaceHolder.addCallback(this);

		// 数字用テクスチャの読み込み
		bmpNumber = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.number);
	}

	/**
	 * サーフェイスの作成、変更、破棄（未使用）。
	 */
	public void surfaceCreated(SurfaceHolder holder) {
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
	}

	/**
	 * 描画を開始する。 成功したら必ずEndで閉めること。
	 *
	 * @return 成功ならtrue
	 */
	protected boolean Begin() {
		// キャンバスロック
		canvas = surfaceHolder.lockCanvas();
		return (canvas != null);
	}

	/**
	 * 描画を終了する。 Beginで開始したら必ずEndで閉めること。
	 */
	protected void End() {
		// ロック解除
		if (canvas != null) {
			surfaceHolder.unlockCanvasAndPost(canvas);
		}
	}

	/**
	 * キャンバスを指定色でクリアする。 必ずBegin～End間で呼ぶこと。
	 *
	 * @param color
	 *            色
	 * @return 成功したらtrue
	 */
	protected boolean Clear(int color) {
		if (canvas == null) {
			return false;
		}

		// 指定色で完全に上書きする(アルファブレンドオフ)
		canvas.drawColor(color, PorterDuff.Mode.SRC);
		return true;
	}

	/**
	 * キャンバスを指定色でクリアする 必ずBegin～End間で呼ぶこと
	 *
	 * @param color
	 *            色
	 * @return 成功したらtrue
	 */
	protected boolean ClearBlur(int color) {
		if (canvas == null) {
			return false;
		}

		canvas.drawColor(color, PorterDuff.Mode.SRC_OVER);
		return true;
	}

	/**
	 * テキストを描画する。
	 *
	 * @param s
	 *            文字列
	 * @param x
	 *            X座標
	 * @param y
	 *            Y座標
	 * @param color
	 *            色
	 */
	public void DrawText(String s, int x, int y, int color) {
		paint.setColor(color);
		paint.setAntiAlias(true);
		paint.setTextSize(20);

		canvas.drawText(s, x, y, paint);
	}

	/**
	 * 画像を描画する。
	 *
	 * @param bmp
	 *            ビットマップ
	 * @param x
	 *            描画先のX座標
	 * @param y
	 *            描画先のY座標
	 */
	public void DrawImage(Bitmap bmp, int x, int y) {
		canvas.drawBitmap(bmp, x, y, paint);
	}

	/**
	 * 画像を描画する。
	 *
	 * @param bmp
	 *            ビットマップ
	 * @param x
	 *            描画先のX座標
	 * @param y
	 *            描画先のY座標
	 * @param sx
	 *            描画元のX座標
	 * @param sy
	 *            描画元のY座標
	 * @param sw
	 *            描画元の幅
	 * @param sh
	 *            描画元の高さ
	 * @param reverse
	 *            反転するか？
	 */
	public void DrawImage(Bitmap bmp, int x, int y, int sx, int sy, int sw,
			int sh, boolean reverse) {

		Rect src = new Rect(sx, sy, sx + sw, sy + sh);
		Rect desc = new Rect(x, y, x + sw, y + sh);

		if (reverse) {
			desc.left = desc.right * -1;
			desc.right = (int) (desc.left + sw);

			canvas.save();
			canvas.scale(-1.0f, 1.0f, 1.0f, 1.0f);
			canvas.drawBitmap(bmp, src, desc, null);
			canvas.restore();
		} else {
			canvas.drawBitmap(bmp, src, desc, null);
		}

	}

	/**
	 * 画像を描画する。
	 *
	 * @param bmp
	 *            ビットマップ
	 * @param x
	 *            描画先のX座標
	 * @param y
	 *            描画先のY座標
	 * @param sx
	 *            描画元のX座標
	 * @param sy
	 *            描画元のY座標
	 * @param sw
	 *            描画元の幅
	 * @param sh
	 *            描画元の高さ
	 * @param reverse
	 *            反転するか？
	 */
	public void DrawRect(int x, int y,int w,
			int h,int color) {

		paint.setColor(color);
		Rect rect = new Rect(x, y, w,h);
			canvas.save();
			canvas.drawRect(rect,paint);
	}
	/**
	 * 数字をテクスチャで描画する。
	 *
	 * @param value
	 *            数値
	 * @param x
	 *            描画先のX座標
	 * @param y
	 *            描画先のY座標
	 */
	public void DrawNumber(int value, int x, int y) {
		// 桁数を求める
		int count = 1;
		int value2 = value;
		while (true) {
			if (value2 / 10 >= 10) {
				count++;
				value2 /= 10;
			} else {
				count++;
				break;
			}
		}

		value2 = value;
		for (int i = 0; i < count; i++) {
			value2 = value % 10;
			value /= 10;
			DrawImage(bmpNumber, x + ((count - i) * 32), y, value2 * 32, 0, 32,
					32, false);
		}
	}
}
