package Yamauchi;

//Dice クラスの定義
//Dice.java というファイル名でセーブすること
public class Dice
{
	 // ■ インスタンス変数の定義 ■
	 // インスタンス変数はオブジェクト毎に存在する
	 // 現在のサイコロの目を表す変数．非公開．
	 private int num;
	
	// ■ コンストラクタの定義 ■
	public Dice()
	{
		// メソッドを呼び出すこともできる．
		// this に注意
		this.roll();
	}

	// ■ メソッドの定義 ■
	// サイコロを振る (公開メソッド)
	// 値は返さない (void)
	 public void roll()
	 {
	 	num = (int)(Math.random() * 6) + 1;
	 }
	
	 // サイコロの表示している目を得る．
	 // int 型の値を返す
	 public int getNumber()
	 {
	 	// 値を返すには return 文を使う．
	 	return num;
	 }
	
	 // サイコロを特定の目にセットする
	 public void setNumber(int n)
	 {
	 	num = n;
	 }
}