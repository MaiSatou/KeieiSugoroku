/**
 * ゲーム名：うんちゃらかんちゃら
 * 
 * マップチップクラス
 */
package kaoru.matsuno;

import trident.Zoo.GameSurfaceView;
import trident.Zoo.Vector2D;
import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * マップクラス
 * 
 * @author matsuno
 * 
 */
public class GameMap {

	/*
	 * マップ制作方針 なんとなくつけているもの ・マップチップの大きさ（幅　高さ） ・マップのサイズ(縦○マス　横○マス) ・マップの座標
	 * ・マップの種類 ・リソース画像
	 */

	/**
	 * 画面の幅
	 */
	private int SCREEN_WIDTH = 800;

	/**
	 * 画面の高さ
	 */
	private int SCREEN_HEIGHT = 480;
	
	//aaaaaaaaaaaaaaaa
	private int _SCREEN_HEIGHT = 480;

	/**
	 * マップのマス数（横）
	 */
	private int MAP_WIDTH = 11;

	/**
	 * マップのマス数（縦）
	 */
	private int MAP_HEIGHT = 10;

	/**
	 * チップの幅
	 */
	private int MAP_CHIPSIZE_WIDTH = 64;

	/**
	 * チップの高さ
	 */
	private int MAP_CHIPSIZE_HEIGHT = 32;

	/**
	 * マップオフセット オフセットは画面の大きさの半分
	 */
	private int map_Offset_x = SCREEN_WIDTH / 2;
	private int map_Offset_y = SCREEN_HEIGHT / 2;

	/**
	 * チップのオフセット
	 */
	private int chip_offset_x = MAP_CHIPSIZE_WIDTH / 2;
	private int chip_offset_y = MAP_CHIPSIZE_HEIGHT / 2;

	/**
	 * マップチップビュー座標
	 */
	private int mapchip_x;
	private int mapchip_y;

	/**
	 * クォータービュー座標
	 */
	private int quarter_x;
	private int quarter_y;

	private int _quarter_x[][] = new int[MAP_HEIGHT][MAP_WIDTH];
	private int _quarter_y[][] = new int[MAP_HEIGHT][MAP_WIDTH];

	/**
	 * マスの番号保存用
	 */
	private int chip_num_x;
	private int chip_num_y;

	/**
	 * マップチップの画像読み込み幅
	 */
	private int CHIP_RES_LENGTH = 4;

	/**
	 * ひし形当たり判定用変数達
	 */
	private int map_cx;
	private int map_cy;
	private int _map_cx[][] = new int[MAP_HEIGHT][MAP_WIDTH];
	private int _map_cy[][] = new int[MAP_HEIGHT][MAP_WIDTH];
	private float gradient;
	private int col1, col2, col3, col4;
	public boolean flag;
	private int debug_num1 = 0, debug_num2 = 0;

	/**
	 * リソース画像
	 */
	private Bitmap img;

	/**
	 * マップの種類
	 */
	private int map_num;

	/**
	 * マップチップ
	 */
	private int MapChip[][] = { { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,/*
																 * 0, 0, 0, 0,
																 * 0, 0, 0, 0, 0
																 */},
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,/* 0, 0, 0, 0, 0, 0, 0, 0, 0 */},
			{ 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1,/* 0, 0, 0, 0, 0, 0, 0, 0, 0 */},
			{ 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1,/* 0, 0, 0, 0, 0, 0, 0, 0, 0 */},
			{ 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1,/* 0, 0, 0, 0, 0, 0, 0, 0, 0 */},
			{ 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1,/* 0, 0, 0, 0, 0, 0, 0, 0, 0 */},
			{ 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1,/* 0, 0, 0, 0, 0, 0, 0, 0, 0 */},
			{ 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1,/* 0, 0, 0, 0, 0, 0, 0, 0, 0 */},
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,/* 0, 0, 0, 0, 0, 0, 0, 0, 0 */},
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,/* 0, 0, 0, 0, 0, 0, 0, 0, 0 */}, };

	/**
	 * コンストラクタ
	 * 
	 * @param img
	 *            : リソース画像
	 */
	public GameMap(Bitmap img) {

		this.img = img;

		Initialize();

		quarterConvert();
	}

	/**
	 * 初期化
	 */
	public void Initialize() {
		mapchip_x = 0;
		mapchip_y = 0;

		quarter_x = 0;
		quarter_y = 0;

		chip_num_x = 0;
		chip_num_y = 0;

		map_num = 0;

		// 縦の配列 マップの高さ分回す
		for (int i = 0; i < MAP_HEIGHT; i++) {
			// 横の配列 マップの横幅分回す
			for (int j = 0; j < MAP_WIDTH; j++) {
				_quarter_x[i][j] = 0;
				_quarter_y[i][j] = 0;

				_map_cx[i][j] = 0;
				_map_cy[i][j] = 0;
			}
		}

	}

	/**
	 * 更新
	 * 
	 * @param
	 */
	public void Update(float mouse_x, float mouse_y) {

		// 縦の配列 マップの高さ分回す
		for (int i = 0; i < MAP_HEIGHT; i++) {
			// 横の配列 マップの横幅分回す
			for (int j = 0; j < MAP_WIDTH; j++) {
				mapChip_col(i, j, mouse_x, mouse_y);
			}
		}

		// もしマップチップが変更されていたらマップチップ更新
		// if () {
		// quarterConvert();
		// }
	}

	/**
	 * 描画
	 */
	public void draw(GameSurfaceView sv) {

		// 縦の配列 マップの高さ分回す
		for (int i = 0; i < MAP_HEIGHT; i++) {
			// 横の配列 マップの横幅分回す
			for (int j = 0; j < MAP_WIDTH; j++) {

				// マップの種類を読み込む
				map_num = MapChip[i][j];

				// 画像の描画
				sv.DrawImage(img, _quarter_x[i][j], _quarter_y[i][j],
						MAP_CHIPSIZE_WIDTH * map_num, 0, MAP_CHIPSIZE_WIDTH,
						MAP_CHIPSIZE_HEIGHT, false);

				sv.DrawText("クォーターX：" + _quarter_x[0][0] + "クォーターY："
						+ _quarter_y[0][0], 50, 400, Color.BLACK);
				sv.DrawText("横何個目：" + debug_num1 + "縦何個目" + debug_num2, 50,
						450, Color.BLACK);
			}
		}
	}

	/**
	 * マップチップ座標をクォータービュー座標に変換
	 */
	// private void quarterConvert() {
	// // マップチップ座標をクォータービュー座標に変換する
	// // クォーターX = (チップの幅W/2) * (チップの座標X + チップの座標Y)
	// // クォーターY = (チップの高さH/2) * ( チップの座標X - チップの座標Y)
	// quarter_x = (MAP_CHIPSIZE_WIDTH / 2) * (chip_num_x + chip_num_y);
	// quarter_y = (MAP_CHIPSIZE_HEIGHT / 2) * (chip_num_x - chip_num_y);
	//
	// // マップ全体のオフセットを反映
	// // quarter_x = quarter_x + map_Offset_x;
	// quarter_y = quarter_y + map_Offset_y;
	//
	// // チップ分のオフセットをする
	// quarter_y = quarter_y - chip_offset_y;
	// }

	/**
	 * マップチップ座標をクォータービュー座標に変換
	 */
	private void quarterConvert() {

		// 縦の配列 マップの高さ分回す
		for (int i = 0; i < MAP_HEIGHT; i++) {
			// 横の配列 マップの横幅分回す
			for (int j = 0; j < MAP_WIDTH; j++) {

				// マップチップ座標をクォータービュー座標に変換する
				// クォーターX = (チップの幅W/2) * (チップの座標X + チップの座標Y)
				// クォーターY = (チップの高さH/2) * ( チップの座標X - チップの座標Y)
				_quarter_x[i][j] = (MAP_CHIPSIZE_WIDTH / 2) * (i + j);
				_quarter_y[i][j] = (MAP_CHIPSIZE_HEIGHT / 2) * (i - j);

				// マップ全体のオフセットを反映
				_quarter_y[i][j] = _quarter_y[i][j] + map_Offset_y;

				// チップ分のオフセットをする
				_quarter_y[i][j] = _quarter_y[i][j] - chip_offset_y;

			}
		}
	}

	/**
	 * クォータービュー座標をマップチップ座標に変換
	 */
	// private void mapChipConvert() {
	// mapchip_x = quarter_x;
	// mapchip_y = quarter_y;
	//
	// // マップ全体のオフセットを反映
	// // mapchip_x = mapchip_x + map_Offset_x;
	// mapchip_y = (mapchip_y + chip_offset_y) - map_Offset_y;
	//
	// // チップ分のオフセットをする
	// mapchip_x = mapchip_x - (chip_offset_x * chip_num_y)
	// + (chip_offset_x * chip_num_x);
	// mapchip_y = -mapchip_y + (chip_offset_y * chip_num_x)
	// + (chip_offset_y * chip_num_y);
	// }

	/**
	 * マップの中心を計算し入れておく
	 */
	private void setMapCenter() {
		// マップチップの中心を算出
		// 縦の配列 マップの高さ分回す
		for (int i = 0; i < MAP_HEIGHT; i++) {
			// 横の配列 マップの横幅分回す
			for (int j = 0; j < MAP_WIDTH; j++) {
				_map_cx[i][j] = _quarter_x[i][j] + (MAP_CHIPSIZE_WIDTH / 2);
				_map_cy[i][j] = _quarter_y[i][j] + (MAP_CHIPSIZE_HEIGHT / 2);
			}
		}
	}

	/**
	 * マップチップの当たり判定 やり方 ：一次関数を使い当たり判定をとる 参考 ：URL.txt "ひし形当たり判定"
	 */
	public void mapChip_col(int i, int j, float mouse_x, float mouse_y) {

		// マップチップの中心を算出
		map_cx = _quarter_x[i][j] + (MAP_CHIPSIZE_WIDTH / 2);
		map_cy = _quarter_y[i][j] + (MAP_CHIPSIZE_HEIGHT / 2);

		// 傾きを産出
		gradient = MAP_CHIPSIZE_HEIGHT / (float) MAP_CHIPSIZE_WIDTH;

		// int 辺1 = (int)( 傾き*( mx - ( cx - h)) + cy ); //辺1の数値
		// int 辺2 = (int)( 傾き*( mx - ( cx + h)) + cy ); //辺2の数値
		// int 辺3 = (int)(-傾き*( mx - ( cx + h)) + cy ); //辺3の数値
		// int 辺4 = (int)(-傾き*( mx - ( cx - h)) + cy ); //辺4の数値
		// 辺2→ ／＼ ←辺3
		// 辺4→ ＼／ ←辺1
		col1 = (int) (gradient * (mouse_x - (map_cx - MAP_CHIPSIZE_HEIGHT)) + map_cy);
		col2 = (int) (gradient * (mouse_x - (map_cx + MAP_CHIPSIZE_HEIGHT)) + map_cy);
		col3 = (int) (-gradient * (mouse_x - (map_cx + MAP_CHIPSIZE_HEIGHT)) + map_cy);
		col4 = (int) (-gradient * (mouse_x - (map_cx - MAP_CHIPSIZE_HEIGHT)) + map_cy);

		if (mouse_y <= col1 && mouse_y >= col2 && mouse_y <= col3
				&& mouse_y >= col4) {
			flag = true;

			debug_num1 = i;
			debug_num2 = j;
		} else {
			flag = false;
		}

		//return flag;
	}

	/**
	 * マップチップの当たり判定 やり方 ：一次関数を使い当たり判定をとる 参考 ：URL.txt "ひし形当たり判定"
	 */
	public boolean _mapChip_col(float mouse_x, float mouse_y) {

		// 傾きを産出
		gradient = MAP_CHIPSIZE_HEIGHT / (float) MAP_CHIPSIZE_WIDTH;

		// int 辺1 = (int)( 傾き*( mx - ( cx - h)) + cy ); //辺1の数値
		// int 辺2 = (int)( 傾き*( mx - ( cx + h)) + cy ); //辺2の数値
		// int 辺3 = (int)(-傾き*( mx - ( cx + h)) + cy ); //辺3の数値
		// int 辺4 = (int)(-傾き*( mx - ( cx - h)) + cy ); //辺4の数値
		// 辺2→ ／＼ ←辺3
		// 辺4→ ＼／ ←辺1

		// マップチップの中心を算出
		// 縦の配列 マップの高さ分回す
		label:for (int i = 0; i < MAP_HEIGHT; i++) {
			// 横の配列 マップの横幅分回す
			for (int j = 0; j < MAP_WIDTH; j++) {
				col1 = (int) (gradient
						* (mouse_x - (_map_cx[i][j] - MAP_CHIPSIZE_HEIGHT)) + _map_cy[i][j]);
				col2 = (int) (gradient
						* (mouse_x - (_map_cx[i][j] + MAP_CHIPSIZE_HEIGHT)) + _map_cy[i][j]);
				col3 = (int) (-gradient
						* (mouse_x - (_map_cx[i][j] + MAP_CHIPSIZE_HEIGHT)) + _map_cy[i][j]);
				col4 = (int) (-gradient
						* (mouse_x - (_map_cx[i][j] - MAP_CHIPSIZE_HEIGHT)) + _map_cy[i][j]);

				if (mouse_y <= col1 && mouse_y >= col2 && mouse_y <= col3
						&& mouse_y >= col4) {

					debug_num1 = i;
					debug_num2 = j;

					flag = true;

					break label;

				} else {
					flag = false;
				}

			}
		}

		return flag;
	}

	/**
	 * フラグを返す（デバッグ用）
	 * 
	 * @return
	 */
	public boolean getFlag() {
		return flag;
	}
}