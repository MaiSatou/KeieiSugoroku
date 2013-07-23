package takuya.kumagai;

import trident.Zoo.GameSurfaceView;
import trident.Zoo.Vector2D;
import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * マップクラス
 * 
 * @author kumagai
 */
public class SugorokuMap {

	/** マップのマス数（横） */
	private int MAP_WIDTH = 8;
	/** マップのマス数（縦） */
	private int MAP_HEIGHT = 6;
	
	/** チップの幅 */
	private int CHIP_WIDTH = 100;
	/** チップの高さ */
	private int CHIP_HEIGHT = 80;

	/** 画像 */
	private Bitmap img;
	
	/** 現在のマス数 */
	int pos;
	
	int x;
	int y;
	
	/** 現在のマス座標 */
	Vector2D mapPos = new Vector2D();
	
	/** マップチップ */
	int mapchip[][] =
		{
			{ 3, 0, 1, 0, 0, 0, 2, 1},
			{ 4, 9, 9, 9, 9, 9, 9, 2},
			{ 1, 9, 9, 9, 9, 9, 9, 0},
			{ 0, 9, 9, 9, 9, 9, 9, 0},
			{ 0, 9, 9, 9, 9, 9, 9, 1},
			{ 0, 2, 0, 0, 1, 2, 0, 2},
		};

	/**
	 * コンストラクタ
	 * @param img
	 */
	public SugorokuMap(Bitmap img) {
		this.img = img;
	}

	/**
	 * 初期化
	 */
	public void Initialize() {
		pos = 0;
		
		x = 0;
		y = 0;
	}
	
	/**
	 * 更新
	 */
	public void Update(int dice) {
		
		for(int i = 0;i < dice; i++) {
			// 現在座標にダイス分をたす(23で止まる)
			if(pos <= 23) {
				pos++;
			}
			
			// 上のマス
			if(pos <= 7) {
				x++;
			}
			// 右のマス
			if(pos > 7 && pos <= 12) {
				y++;
			}
			// 下のマス
			if(pos > 12 && pos <= 19) {
				x--;
			}
			// 左のマス
			if(pos > 19 && pos <= 23) {
				y--;
			}
		}
		
		mapPos.setX(x);
		mapPos.setY(y);
	}
	
	/**
	 * 描画
	 */
	public void draw(GameSurfaceView sv) {
		for (int i = 0; i < MAP_HEIGHT; i++) {
			for (int j = 0; j < MAP_WIDTH; j++) {
				if(mapchip[i][j] != 9) {
					sv.DrawImage(img,				// ビットマップ
								 j * CHIP_WIDTH,	// 描画先のX座標
								 i * CHIP_HEIGHT,	// 描画先のY座標
								 CHIP_WIDTH * mapchip[i][j],	// 描画元のX座標
								 0,					//　描画元のY座標
								 CHIP_WIDTH,		// 描画元の幅
								 CHIP_HEIGHT,		// 描画元の高さ
								 false				// 反転させるか？
								 );
				}
			}
		}
		
		sv.DrawImage(img,				// ビットマップ
					 x * CHIP_WIDTH,	// 描画先のX座標
					 y * CHIP_HEIGHT,	// 描画先のY座標
					 0,					// 描画元のX座標
					 CHIP_HEIGHT,		//　描画元のY座標
					 CHIP_WIDTH,		// 描画元の幅
					 CHIP_HEIGHT,		// 描画元の高さ
					 false				// 反転させるか？
					 );
		
		sv.DrawText("現在マス数 = " + pos, 10, 60, Color.BLACK);
		sv.DrawText("現在マス座標 = x:"+ mapPos.getX() + " y:" + mapPos.getY(), 10, 80, Color.BLACK);
		sv.DrawText("[" + mapPos.getY() + "][" + mapPos.getX() + "]", 10, 100, Color.BLACK);
 	}
}