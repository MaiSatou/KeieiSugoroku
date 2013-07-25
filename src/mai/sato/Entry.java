package mai.sato;

import trident.Zoo.GameActivity;
import trident.Zoo.R;
import trident.Zoo.R.id;
import trident.Zoo.R.layout;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Entry extends Activity implements OnClickListener {

	  EditText edittext;
	  Button  saveButton;
	  Button cancelButton;
	  TextView textView;
	  ZooData zooData;

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);
	        zooData = new ZooData(this);
	        findViews();
	        textView.setText("名前を入力してください(全角8文字以内)");
	        saveButton.setOnClickListener(this);
	        if(!(zooData.getName().equals(""))){
	        	cancelButton.setOnClickListener(this);
	        }
	    }

	    protected void findViews(){
	      textView = (TextView)findViewById(R.id.textview);
	      edittext = (EditText)findViewById(R.id.edittext);
	      saveButton = (Button)findViewById(R.id.saveButton);
	      if(!(zooData.getName().equals(""))){
	    	  cancelButton = (Button)findViewById(R.id.cancelButton);
	      }
	    }



	  @Override
	  public void onClick(View v) {
	    switch(v.getId()){
	    case R.id.saveButton:
	    	if(!edittext.getText().toString().equals("")){
		      zooData.setName(edittext.getText().toString());
		      	Intent intent = new Intent(getApplication(),GameActivity.class);
				startActivity(intent);
				// SplashActivityを終了させます。
				Entry.this.finish();
	    	}
	      break;

	    }
	  }
	}