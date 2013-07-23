package mai.sato;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class ZooData {
	private String name;
	private int rank;
	private int money;
	private SharedPreferences sharedPre;

	public ZooData(Context context){
		sharedPre = PreferenceManager.getDefaultSharedPreferences(context);

		name = sharedPre.getString("UserName", "");
		rank = sharedPre.getInt("Rank", 2);
		money = sharedPre.getInt("Money", 2000);

	}

	public void setName(String name){
		Editor editor = sharedPre.edit();
		editor.putString("UserName", name);
		editor.commit();
	}

	public String getName(){
		return name;
	}
	public int getRank(){
		return rank;
	}
	public int getMoney(){
		return money;
	}

	public void setRank(int rank){
		Editor editor = sharedPre.edit();
		editor.putInt("Rank", rank);
		editor.commit();
	}

	public void setMoney(int money){
		Editor editor = sharedPre.edit();
		editor.putInt("Rank", money);
		editor.commit();
	}

}
