package Yamauchi;

import trident.Zoo.GameSurfaceView;
import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * イベント１クラス
 * 
 * @author Yamauchi
 */

public class Event1 extends Event
{
	
	/**
	 * コンストラクタ
	 * @param img
	 */
	public Event1(Bitmap img,Bitmap img2,Bitmap img3)
	{
		super(img,img2,img3);
	}

	/**
	 * 初期化
	 */
	public void Initialize()
	{
		startFlag = false;
		eventFlag = false;
	}
	
	/**
	 * 更新
	 */
	public void Update()
	{

	}
	
	/**
	 * イベント１
	 */
	public void Draw(GameSurfaceView sv)
	{
		sv.DrawImage(eventImg,150,200);
		sv.DrawImage(textImg, 150, 130);
		sv.DrawText("さいころゲーム", 450, 130, Color.BLACK);
		sv.DrawText("サイコロを振って出た目の数が", 450, 150, Color.BLACK);
		sv.DrawText("２、４、６の場合犬捕獲！", 450, 170, Color.BLACK);
 	}
	
}