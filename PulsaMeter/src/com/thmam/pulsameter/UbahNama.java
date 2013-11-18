package com.thmam.pulsameter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class UbahNama extends Activity {

	EditText userbaru;
	String user_baru="", Username="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_ubah);
		
		userbaru = (EditText) findViewById(R.id.input);	
		Username = getIntent().getExtras().getString("user");
		
		TextView userlama = (TextView) findViewById(R.id.userlama);		
		userlama.setText("Nama Lama : "+Username);	
	}
	
	public void yesOnClick(View v)
	{		
		Method data = new Method();
		user_baru = userbaru.getEditableText().toString();
		data.updateData(getApplicationContext(), user_baru, Username);
		Intent i = new Intent(getApplicationContext(), Pulsa.class);
		i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		Intent ini = new Intent();
		ini.putExtra("user", user_baru);
		setResult(RESULT_OK, ini);
		startActivity(i);
	}
	
	public void noOnClick(View v)
	{
		Intent i = new Intent(getApplicationContext(), Pulsa.class);
		i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		Intent ini = new Intent();
		setResult(RESULT_CANCELED, ini);
		startActivity(i);
	}
	
	@Override
	protected void onPause() 
    {
		// TODO Auto-generated method stub
		super.onPause();		
		finish();
	}  
}