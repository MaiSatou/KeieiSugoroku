package Yamauchi;

import trident.Zoo.GameSurfaceView;
import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * イベント１クラス
 * 
 * @author Yamauchi
 */

public class Event1
{
	
	/**
	 * イベント画像
	 */
	private Bitmap eventImg;
	
	/**
	 * イベント文字画像
	 */
	private Bitmap textImg;
	
	/**
	 * コンストラクタ
	 * @param img
	 */
	public Event1(Bitmap img,Bitmap img2)
	{
		this.eventImg = img;
		this.textImg = img2;
	}

	/**
	 * 初期化
	 */
	public void Initialize()
	{

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
	
	/**
	 * イベント２
	 *//*
	public void DrawTwo(GameSurfaceView sv)
	{
		sv.DrawImage(eventImg,150,200);
		sv.DrawImage(textImg, 150, 130);
		sv.DrawText("動物捕獲ゲーム", 450, 130, Color.BLACK);
		sv.DrawText("スマホをタップして", 450, 150, Color.BLACK);
		sv.DrawText("動き回る猿をタップできれば猿捕獲！", 450, 170, Color.BLACK);
	}*/
	
}