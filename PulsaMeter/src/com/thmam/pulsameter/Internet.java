package com.thmam.pulsameter;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Internet extends Activity {

	private static final int SWIPE_MIN_DISTANCE = 5;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 5;
    private GestureDetector gestureDetector;
	String[] b,dateKuota; double[] kuota;
	String Username, Operator;
	double kuotabaru;
	TextView sisaKuota,welcomeMessage,pemakaianInternet,masaBerlaku;
	ProgressBar kuotaBar;
	private IntentFilter intentFilter;
	
	private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
    	@Override
    	public void onReceive(Context context, Intent intent) {
    		Bundle bundle = intent.getExtras(); 
    		String origin = bundle.getString("origin");
    		if(origin.equals(b[2]))
    		{
    			String msg = bundle.getString("msg");
    			Method data = new Method();
    			//kuota = data.getSisaKuota(msg, Operator);
    			kuotabaru = data.getSisaKuota(msg, Operator);
    			//kuota[1] = Double.valueOf(data.getMaksKuota(msg, Operator));
    			
    			sisaKuota=(TextView)findViewById(R.id.sisakuota);
    			masaBerlaku=(TextView)findViewById(R.id.masaberlakuinternet);
    			kuotaBar=(ProgressBar)findViewById(R.id.progressBar);
    			
    			sisaKuota.setText(String.valueOf(kuotabaru)+" MB");
    			dateKuota = data.getDateUSSD(msg, Operator, "kuota");
    			masaBerlaku.setText(dateKuota[1]+" "+dateKuota[2]+" "+dateKuota[3]);
    			
    			kuotaBar.setMax(2000);
    			kuotaBar.setProgress((int) kuotabaru);
    		}
    	};
    };
	
	@Override
	protected void onCreate(Bundle outstate) {
		super.onCreate(outstate);
		setContentView(R.layout.page_internet);
		
		intentFilter = new IntentFilter();
		intentFilter.addAction("SMS_RECEIVED_ACTION");
		
		welcomeMessage=(TextView)findViewById(R.id.welcometext2);
		sisaKuota=(TextView)findViewById(R.id.sisakuota);
		pemakaianInternet=(TextView)findViewById(R.id.pemakaianinternet);
		masaBerlaku=(TextView)findViewById(R.id.masaberlakuinternet);
		kuotaBar=(ProgressBar)findViewById(R.id.progressBar);
		
		TelephonyManager telephonyManager =(TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        Operator = telephonyManager.getNetworkOperatorName();
        b = changeImage(Operator);  
        /*if (outstate != null && outstate.containsKey("sisakuota"))
		{
        	Bundle baru = getIntent().getExtras();
        	Username = baru.getString("user");
			welcomeMessage.setText("Selamat Datang, "+ Username);
			sisaKuota.setText(outstate.getString("sisakuota"));	
			masaBerlaku.setText(outstate.getString("masaberlaku"));
		}
        els*/
        
        	Bundle a = getIntent().getExtras();
        	if(a!=null && a.containsKey("user")) Username = a.getString("user");
        	welcomeMessage.setText("Selamat Datang, "+ Username);		
        	if(a.getDoubleArray("kuota")!=null)
        	{
        		//kuota=a.getDoubleArray("kuota");
        		kuotabaru = a.getDouble("kuota");
        		dateKuota = a.getStringArray("masaBerlaku");
        		sisaKuota.setText(String.valueOf(kuotabaru)+" MB");
        		masaBerlaku.setText(dateKuota[1]+" "+dateKuota[2]+" "+dateKuota[3]);
        		kuotaBar.setMax(2000);
    			kuotaBar.setProgress((int) kuotabaru);
        	}
		gesture_detector();
	}

	public void kuotaOnClick(View v)
	{
		sendSMS(b[1], b[2]);	
	}
	
	private void sendSMS(String message, String phoneNumber)
    {
    	String SENT = "SMS_SENT";
    	//String DELIVERED = "SMS_DELIVERED";
    	PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
    	//PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);
    	
    	//---when the SMS has been sent---
    	registerReceiver(new BroadcastReceiver(){
    	@Override
    	public void onReceive(Context arg0, Intent arg1) {
    	switch (getResultCode())
    	{
    	case Activity.RESULT_OK:
    	Toast.makeText(getBaseContext(), "SMS sent",
    	Toast.LENGTH_SHORT).show();
    	break;
    	case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
    	Toast.makeText(getBaseContext(), "Generic failure",
    	Toast.LENGTH_SHORT).show();
    	break;
    	case SmsManager.RESULT_ERROR_NO_SERVICE:
    	Toast.makeText(getBaseContext(), "No service",
    	Toast.LENGTH_SHORT).show();
    	break;
    	case SmsManager.RESULT_ERROR_NULL_PDU:
    	Toast.makeText(getBaseContext(), "Null PDU",
    	Toast.LENGTH_SHORT).show();
    	break;
    	case SmsManager.RESULT_ERROR_RADIO_OFF:
    	Toast.makeText(getBaseContext(), "Radio off",
    	Toast.LENGTH_SHORT).show();
    	break;
    	}
    	}
    	}, new IntentFilter(SENT));
    	/*//---when the SMS has been delivered---
    	registerReceiver(new BroadcastReceiver(){
    	@Override
    	public void onReceive(Context arg0, Intent arg1) {
    	switch (getResultCode())
    	{
    	case Activity.RESULT_OK:
    	Toast.makeText(getBaseContext(), "SMS delivered",
    	Toast.LENGTH_SHORT).show();
    	break;
    	case Activity.RESULT_CANCELED:
    	Toast.makeText(getBaseContext(), "SMS not delivered",
    	Toast.LENGTH_SHORT).show();
    	break;
    	}
    	}
    	}, new IntentFilter(DELIVERED));*/
    	SmsManager sms = SmsManager.getDefault();
    	sms.sendTextMessage(phoneNumber, null, message, sentPI, null);
    	//sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    	//sms.sendTextMessage(phoneNumber, null, message, null, null);
    }
	
	/*@Override
	public void onSaveInstanceState(Bundle outState) 
	{
		outState.putString("sisakuota", sisaKuota.getText().toString());
		outState.putString("masaberlaku", masaBerlaku.getText().toString());
		super.onSaveInstanceState(outState);
	}*/
	
	public String[] changeImage(String opimages)
 	{
 	ImageView operatorImage = (ImageView) findViewById(R.id.opimage); 
 	String[] a = {"","",""};
 	if(opimages.equals("3")) 
  	{
  		operatorImage.setImageResource(R.drawable.operator3);a[0]="111*1";a[1]="info data";a[2]="234";
  	}
  	else if(opimages.equals("Telkomsel") || opimages.equals("TELKOMSEL"))
  	{
  		operatorImage.setImageResource(R.drawable.operatortelkomsel);a[0]="888";a[1]="flash info";a[2]="3636"; 
  	}
  	else if(opimages.equals("Indosat") || opimages.equals("INDOSAT"))
  	{
  		operatorImage.setImageResource(R.drawable.operatorindosat);a[0]="388";a[1]="USAGE";a[2]="363";
 	}
  	else if(opimages.equals("XL") || opimages.equals("xl") || opimages.equals("Xl"))
  	{
  		operatorImage.setImageResource(R.drawable.operatorxl);a[0]="123";a[1]="KUOTA";a[2]="868";
 	}
  	else if(opimages.equals("Axis") || opimages.equals("AXIS"))
  	{
  		operatorImage.setImageResource(R.drawable.operatoraxis);a[0]="888";a[1]="KUOTA";a[2]="123";
 	}
  	else if(opimages.equals("Android"))
  	{
  		operatorImage.setImageResource(R.drawable.ic_launcher);a[0]="000";a[1]="sembarangan";a[2]="909090";
 	}
 	return a;
 }
	
	public void gesture_detector()
    {
    	gestureDetector = new GestureDetector(new MyGestureDetector());
        View mainview = (View) findViewById(R.id.mainView);
        // Set the touch listener for the main view to be our custom gesture listener
        mainview.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) {
                    return true;
                }
                return false;
            }
        });
    }
    
    class MyGestureDetector extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
	    Intent intent = new Intent(Internet.this.getBaseContext(), Internet.class);
        
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
                return false;
            }
    	    // left to right swipe
            if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {   
            Intent i = new Intent(getApplicationContext(), Pulsa.class);
            i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            Bundle bundle3 = new Bundle();
            //bundle3.putDoubleArray("kuota", kuota);
            bundle3.putDouble("kuota", kuotabaru);
            bundle3.putStringArray("masaBerlaku", dateKuota);
            i.putExtras(bundle3); 
            startActivity(i);	 
            Internet.this.overridePendingTransition(
			R.anim.slide_in_left, 
			R.anim.slide_out_right
    		);
            }
            return false;
        }
 
        // It is necessary to return true from onDown for the onFling event to register
        @Override
        public boolean onDown(MotionEvent e) {
	        	return true;
        }
    } 
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.internet, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
	//---register the receiver---
	registerReceiver(intentReceiver, intentFilter);
	super.onResume();
	}
	
    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
    	//---unregister the receiver---
    	unregisterReceiver(intentReceiver);
    	super.onPause();	
		finish();
	}
}
