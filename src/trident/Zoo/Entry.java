package trident.Zoo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Entry extends Activity implements OnClickListener {

	  EditText edittext;
	  Button  saveButton;
	  ZooData zooData;

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);
	        zooData = new ZooData(this);
	        findViews();
	        saveButton.setOnClickListener(this);
	    }

	    protected void findViews(){
	      edittext = (EditText)findViewById(R.id.edittext);
	      saveButton = (Button)findViewById(R.id.saveButton);

	    }



	  @Override
	  public void onClick(View v) {
	    switch(v.getId()){
	    case R.id.saveButton:
	      zooData.setName(edittext.getText().toString());
	      break;
	    }
	  }
	}