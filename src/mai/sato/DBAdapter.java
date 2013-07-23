package mai.sato;
import java.util.Date;
import java.util.HashMap;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {

  static final String DATABASE_NAME = "sugorokuLife.db";
  static final int DATABASE_VERSION = 1;

  public static final String TABLE_NAME = "mydata";
  public static final String COL_ID = "_id";
  public static final String COL_NAME = "name";
  public static final String COL_X = "x";
  public static final String COL_Y = "y";
  public static final String COL_ROTATE = "rotate";

  protected final Context context;
  protected DatabaseHelper dbHelper;
  protected SQLiteDatabase db;

  public DBAdapter(Context context){
    this.context = context;
    dbHelper = new DatabaseHelper(this.context);
  }

  //
  // SQLiteOpenHelper
  //

  private static class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
      super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      db.execSQL(
        "CREATE TABLE " + TABLE_NAME + " ("
        + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + COL_NAME + " TEXT NOT NULL,"
        + COL_X + " FLOAT, "
        + COL_Y + " FLOAT, "
        + COL_ROTATE + " FLOAT );");
    }

    @Override
    public void onUpgrade(
      SQLiteDatabase db,
      int oldVersion,
      int newVersion) {
      db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
      onCreate(db);
    }

  }

  //
  // Adapter Methods
  //

  public DBAdapter open() {
    db = dbHelper.getWritableDatabase();
    return this;
  }


  public void close(){
    dbHelper.close();
  }


  //
  // App Methods
  //


  public boolean deleteAllData(){
    return db.delete(TABLE_NAME, null, null) > 0;
  }

  public boolean deleteData(int id){
    return db.delete(TABLE_NAME, COL_ID + "=" + id, null) > 0;
  }

  public Cursor getAllData(){
    return db.query(TABLE_NAME, null, null, null, null, null, null);
  }

  public void saveData(String name,float x,float y,float rotate){
	open();
    ContentValues values = new ContentValues();
    values.put(COL_NAME, name);
    values.put(COL_X,x);
    values.put(COL_Y,y);
    values.put(COL_ROTATE,rotate);

    db.insertOrThrow(TABLE_NAME, null, values);
    close();
  }

  public void loadData(){
	  HashMap<String,Data> map = new HashMap<String,Data>();
      // Read
      open();
      Cursor c = getAllData();

      if(c.moveToFirst()){
        do {
        	Data data = new Data(
        			c.getInt(c.getColumnIndex(COL_ID)),
        			c.getString(c.getColumnIndex(COL_NAME)),
        			c.getFloat(c.getColumnIndex(COL_X)),
        			c.getFloat(c.getColumnIndex(COL_Y)),
        			c.getFloat(c.getColumnIndex(COL_ROTATE)));

          map.put("" + data.getId(),data);
        } while(c.moveToNext());
      }

     close();
     for(int i = 0;i < map.size();i++){
    	 if(map.get("" + (i + 1)).equals(null)){
    		 continue;
    	 }
    	  System.out.println(map.get("" + (i + 1)).getName());
      }
  }
}
