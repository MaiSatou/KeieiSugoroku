package trident.Zoo;

public class Vector2D {

	/** ==================メンバ変数の定義================== **/
	public float x; // x座標
	public float y; // y座標

	/** ================================================ **/

	/**
	 * Vectorクラスのコンストラクタ
	 * 
	 */
	public Vector2D() {
		Init();
	}

	/**
	 * 引数付きコンストラクタ
	 * 
	 * @param x
	 * @param y
	 */
	public Vector2D(float x, float y) {

		this.x = x;
		this.y = y;
	}

	/**
	 * 角度を取得する
	 * 
	 */
	public float getAngle() {

		// atan2() = 2点を結ぶ線の角度を求める。
		return (float) Math.atan2(y, x);
	}

	public void Init() {
		// 初期化
		x = 0;
		y = 0;
	}

	/**
	 * 大きさを取得する
	 * 
	 */
	public float getLength() {

		// sqrt() = 平方根を求める
		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * ベクトルの大きさの上限設定
	 */
	public void setLengthCap(float maxLength) {

		float length = getLength();

		if (maxLength == 0) {

			return;
		}

		// maxLengthより大きければ大きさをmaxLengthにする
		if (length > maxLength) {

			float rate = length / maxLength;
			x /= rate;
			y /= rate;

		}
	}

	/**
	 * ベクトル方向に、引数のrate分だけ、ベクトルを加える
	 * 
	 * @param vec
	 *            ベクトル
	 * @param rate
	 *            比率
	 */
	public void add(Vector2D vec, float rate) {

		float width = vec.x - x;
		float height = vec.y - y;

		x += width * rate;
		y += height * rate;
	}

	/**
	 * Xの値を返す
	 * 
	 * @return x : X要素
	 */
	public float getX() {
		return x;
	}

	/**
	 * Yの値を返す
	 * 
	 * @
	 */
	public float getY() {
		return y;
	}

	/**
	 * Xに値を設定する
	 * 
	 * @param x
	 *            :
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Yに値を設定する
	 * 
	 * @param y
	 *            :
	 */
	public void setY(float y) {
		this.y = y;
	}

}
