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
	 * スタートフラグ
	 */
	protected boolean startFlag;
	
	/**
	 * イベントフラグ
	 */
	protected boolean eventFlag;
	
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
	 * 描画
	 */
	abstract void Draw(GameSurfaceView sv);
	
	/**
	 * スタートフラグを返す
	 */
	public boolean Return_StartFlag()
	{
		return startFlag;
	}
	
	/**
	 * スタートフラグを点灯させる
	 */
	public void On_StartFlag()
	{
		startFlag = true;
	}
	
	/**
	 * スタートフラグを消灯させる
	 */
	public void Off_StartFlag()
	{
		startFlag = false;
	}

	/**
	 * イベントフラグを返す
	 */
	public boolean Return_EventFlag()
	{
		return eventFlag;
	}
	
	/**
	 * イベントフラグを点灯させる
	 */
	public void On_EventFlag()
	{
		eventFlag = true;
	}
	
	/**
	 * イベントフラグを消灯させる
	 */
	public void Off_EventFlag()
	{
		eventFlag = false;
	}
	
	/**
	 * スタート画像を表示
	 */
	public void DrawStart(GameSurfaceView sv)
	{
		sv.DrawImage(startImg,450,200);
	}

}
	