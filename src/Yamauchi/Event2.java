package Yamauchi;

import trident.Zoo.GameSurfaceView;
import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * イベント２クラス
 * 
 * @author Yamauchi
 */

public class Event2 extends Event
{
	
	/**
	 * コンストラクタ
	 * @param img
	 */
	public Event2(Bitmap img,Bitmap img2,Bitmap img3)
	{
		super(img,img2,img3);
	}

	/**
	 * 初期化
	 */
	public void Initialize()
	{
		startFlag = false;
	}
	
	/**
	 * 更新
	 */
	public void Update()
	{

	}
	
	/**
	 * イベント２
	 */
	public void Draw(GameSurfaceView sv)
	{
		sv.DrawImage(eventImg,150,200);
		sv.DrawImage(textImg, 150, 130);
		sv.DrawText("動物捕獲ゲーム", 450, 130, Color.BLACK);
		sv.DrawText("スマホをタップして", 450, 150, Color.BLACK);
		sv.DrawText("動き回る猿をタップできれば猿捕獲！", 450, 170, Color.BLACK);
 	}
	
}