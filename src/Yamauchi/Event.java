package Yamauchi;

import trident.Zoo.GameSurfaceView;
import android.graphics.Bitmap;

/**
 * イベントクラス
 * 
 * @author Yamauchi
 */

abstract public class Event
{
	
	/**
	 * イベント画像
	 */
	protected Bitmap eventImg;
	
	/**
	 * イベント文字画像
	 */
	protected Bitmap textImg;
	
	/**
	 * スタート画像
	 */
	protected Bitmap startImg;
	
	/**
	 * コンストラクタ
	 * @param img
	 */
	public Event(Bitmap img,Bitmap img2,Bitmap img3)
	{
		this.eventImg = img;
		this.textImg = img2;
		this.startImg = img3;
	}

	/**
	 * 初期化
	 */
	abstract void Initialize();
	
	/**
	 * 更新
	 */
	abstract void Update();
	
	/**
	 * イベント１
	 */
	abstract void Draw(GameSurfaceView sv);
}
	