package kaoru.matsuno;

public class MenuButton {
private int x;
private int y;
private int w;
private int h;
private String name;

	public MenuButton(int x,int y,int w,int h, String name){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.name = name;

	}

	public boolean TouchButton(int touchX,int touchY){
		if(x < touchX && w > touchX){
			if(y < touchY && h > touchY){
				return true;
			}
		}
		return false;
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}
	public int getW(){
		return w;
	}
	public int getH(){
		return h;
	}

	public String getName(){
		return name;
	}
}
