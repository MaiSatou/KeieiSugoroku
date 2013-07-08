package trident.Zoo;

	import android.content.Context;
	import android.database.sqlite.SQLiteDatabase;
	import android.database.sqlite.SQLiteOpenHelper;


	public class DatabaseHelper extends SQLiteOpenHelper {

	    /* データベース名 */
	    private final static String DB_NAME = "androidstudydb";
	    /* データベースのバージョン */
	    private final static int DB_VER = 1;

	    /*
	     * コンストラクタ
	      */
	    public DatabaseHelper(Context context) {
	        super(context, DB_NAME, null, DB_VER);
	    }

	    /*
	     * onCreateメソッド
	     * データベースが作成された時に呼ばれます。
	     * テーブルの作成などを行います。
	     */
	    @Override
	    public void onCreate(SQLiteDatabase db) {
	        String sql = "";
	        sql += "create table MyTable (";
	        sql += " No integer primary key autoincrement";
	        sql += ",Name text not null";
	        sql += ",Tel text";
	        sql += ",Age integer";
	        sql += ")";
	        db.execSQL(sql);
	    }

	    /*
	     * onUpgradeメソッド
	     * onUpgrade()メソッドはデータベースをバージョンアップした時に呼ばれます。
	     * 現在のレコードを退避し、テーブルを再作成した後、退避したレコードを戻すなどの処理を行います。
	     */
	    @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	    }
}
