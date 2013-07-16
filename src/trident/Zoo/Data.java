package trident.Zoo;

public class Data {
	  protected int id;	// アイテムの管理番号
	  protected String name; // アイテムの名前(画像ファイル名と統一)
	  protected float x; // アイテムのx座標
	  protected float y; // アイテムのy座標
	  protected float rotate; // アイテムの回転


	  public Data(int id, String name, float x,float y,float rotate){
	    this.id = id;
	    this.name = name;
	    this.x = x;
	    this.y = y;
	    this.rotate = rotate;

	  }

	  public String getName(){
	    return name;
	  }

	  public float getX(){
	    return x;
	  }
	  public float getY(){
		    return y;
	  }

	  public float getRotate(){
		    return rotate;
	  }

	  public int getId(){
	    return id;
	  }

	}