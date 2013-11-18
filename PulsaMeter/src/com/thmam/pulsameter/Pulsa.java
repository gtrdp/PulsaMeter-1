package com.thmam.pulsameter;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Pulsa extends Activity{

	private static final int SWIPE_MIN_DISTANCE = 5;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 5;
    private GestureDetector gestureDetector;
    private TextView sisaPulsa, pemakaianPulsa, masaBerlaku, jumlahSms, welcomeMessage;
    private String Operator, Username, encodedHash = Uri.encode("#");
    private String[] c, dateKuota;
    private double[] kuota;
    private double kuotabaru;
    private IntentFilter intentFilter;
    
    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
    	@Override
    	public void onReceive(Context context, Intent intent) {
    		Bundle bundle = intent.getExtras(); 
    		String origin = bundle.getString("origin");
    		if(origin.equals(c[2]))
    		{
    			String msg = bundle.getString("msg");
    			Method data = new Method();
    			//kuota = data.getSisaKuota(msg, Operator);
    			kuotabaru = data.getSisaKuota(msg, Operator);
    			//kuota[1] = Double.valueOf(data.getMaksKuota(msg, Operator));
    			dateKuota = data.getDateUSSD(msg, Operator, "kuota");
    		}
    	};
    };
	
	@Override
	protected void onCreate(Bundle outstate) {
		super.onCreate(outstate);
		setContentView(R.layout.page_pulsa);
		
		intentFilter = new IntentFilter();
		intentFilter.addAction("SMS_RECEIVED_ACTION");
		
		welcomeMessage=(TextView)findViewById(R.id.welcometext);
		sisaPulsa=(TextView)findViewById(R.id.sisapulsa);
		//pemakaianPulsa=(TextView)findViewById(R.id.pemakaianpulsa);
		masaBerlaku=(TextView)findViewById(R.id.masaberlaku);
		jumlahSms=(TextView)findViewById(R.id.jumlahsms);
		
		TelephonyManager telephonyManager =(TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
	    Operator = telephonyManager.getNetworkOperatorName();
	    c = changeImage(Operator); Method data = new Method();    
	    
	    Username = data.query(getApplicationContext());
	    int totalSMS = data.getSMSCount(getApplicationContext());
	    //int durasiTotal = getDurations();
		welcomeMessage.setText("Selamat Datang, "+ Username);
		jumlahSms.setText("Total SMS Terkirim Hari Ini : "+String.valueOf(totalSMS));
		//pemakaianPulsa.setText(String.valueOf(durasiTotal));
		call("*" + c[0] + encodedHash);	
		gesture_detector();
		
		Bundle bundle2 = new Bundle();
		//kuota = bundle2.getDoubleArray("kuota");
		kuotabaru = bundle2.getDouble("kuota");
		dateKuota = bundle2.getStringArray("masaBerlaku");
	}
	
	public int getDurations()
	{
		/*GregorianCalendar gc = new GregorianCalendar();
		gc.set(Calendar.MILLISECOND, 0);
		gc.set(Calendar.SECOND, 0);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.HOUR, 0);
		if (gc.get(Calendar.DATE) < gc.DAY_OF_MONTH) {
			gc.add(Calendar.MONTH, -1);
		}
		gc.set(Calendar.DATE, gc.DAY_OF_MONTH);*/
		
		// Create a query to get outgoing call durations from the call log
		//Uri uri = Calls.CONTENT_URI;
		//String[] column = new String[]{Calls.DURATION};
		//String tipe = Calls.TYPE + " = " + Calls.OUTGOING_TYPE + " AND " + Calls.DATE + " > ?";
		//String sorts = Calls.DATE + " DESC";
		//String[] parameter = new String[]{String.valueOf(gc.getTimeInMillis())};
		//Cursor c = getContentResolver().query(Calls.CONTENT_URI, new String[]{Calls.DURATION}, null, null);
		//String[] strFields = {Calls.NUMBER, Calls.TYPE, Calls.CACHED_NAME, Calls.CACHED_NUMBER_TYPE};
		//String[] duration={"","",""};
		//String strOrder = Calls.DATE + " DESC"; 
		 //this.getContentResolver().query(Calls.CONTENT_URI, null, Calls.TYPE + " = " + Calls.OUTGOING_TYPE, null, null);
		 //Cursor c = context.getContentResolver().query(android.provider.CallLog.Calls.CONTENT_URI,null, null, null,android.provider.CallLog.Calls.DATE + " DESC");
		int total=0;
		/*String[] strFields = {
		        android.provider.CallLog.Calls.NUMBER, 
		        android.provider.CallLog.Calls.TYPE,
		        android.provider.CallLog.Calls.CACHED_NAME,
		        android.provider.CallLog.Calls.CACHED_NUMBER_TYPE
		        };
		String strOrder = android.provider.CallLog.Calls.DATE + " DESC"; 
		 
		Cursor mCallCursor = this.getContentResolver().query(
		        android.provider.CallLog.Calls.CONTENT_URI,
		        strFields,
		        null,
		        null,
		        strOrder
		        );*/
		
		/*Cursor c =
				context.getContentResolver().query(android.provider.CallLog.Calls.CONTENT_URI,null,
				null, null,android.provider.CallLog.Calls.DATE + " DESC");
				total=c.getCount();*/
		 //context.getContentResolver().delete(CallLog.Calls.CONTENT_URI, null, null);
		 //int jumlah=0; 
		 //jumlah = c.getCount();
		 //c.close();
		 return total;
		 //return jumlah;
		 
		//int durationColumn = c.getColumnIndex(Calls.DURATION);
			/*int j = c.getCount(); int callduration=0; int[] callDuration = new int[j];
					// iterate through the call durations we got back from the query
					if (c.moveToFirst()) {
						for(int i=0;i<j;i++) 
						{
							// calculate duration of call in minutes, using ceiling to round to nearest larger int
							//callDuration[i] = (int)Math.ceil((double) c.getInt(durationColumn));
							callduration += callDuration[i];
							c.moveToNext();
						}
					}			
					c.close();			
					return callduration;
				}
		String[] strFields = {Calls.NUMBER, Calls.TYPE, Calls.CACHED_NAME, Calls.CACHED_NUMBER_TYPE};
		String[] duration={"","",""};
		String strOrder = Calls.DATE + " DESC"; 
		 
		Cursor mCallCursor = getContentResolver().query(Calls.CONTENT_URI, strFields, null, null, strOrder);
		
		// calculate the date bill was last reset
			GregorianCalendar gc = new GregorianCalendar();
			gc.set(Calendar.MILLISECOND, 0);
			gc.set(Calendar.SECOND, 0);
			gc.set(Calendar.MINUTE, 0);
			gc.set(Calendar.HOUR, 0);
			if (gc.get(Calendar.DATE) < dayOfMonthReset) {
				gc.add(Calendar.MONTH, -1);
			}
			gc.set(Calendar.DATE, dayOfMonthReset);
		*/
	}
		
  	
	public void RefreshonClick(View v)
	{
		call("*" + c[0] + encodedHash);	
	}
	
	public void UbahonClick(View a)
	{		
		Intent i = new Intent(getApplicationContext(), UbahNama.class); 
        i.putExtra("user", Username);          
        startActivityForResult(i, 2);  
	}
	
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
 	
 	protected void call(String phoneNumber) 
 	{
		try 
		{
			startActivityForResult(new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumber)), 1);
		} 
		catch (Exception eExcept) 
		{
			this.sisaPulsa.setText("\n\n " + "\n" + eExcept.toString());
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		Method Data = new Method();
		USSD ussd = new USSD(4000, 4000);
		if(requestCode==2) 
			{
				if(resultCode==RESULT_OK)
				{
					Username = data.getStringExtra("user");
					welcomeMessage.setText("Selamat Datang, "+ Username);
				}
			}
		else if (ussd.IsFound())
		{
			String Message= ussd.getMsg();
			int sisaPulsa = Data.getBalUSSD(Message, Operator);
			String[] a = Data.getDateUSSD(Message, Operator, "");
			this.sisaPulsa.setText("Rp."+sisaPulsa+",00");
			this.masaBerlaku.setText(a[1]+" "+a[2]+" "+a[3]);
			//sendSMS(c[1], c[2]);
		}
		else 
			{
			this.sisaPulsa.setText("Error Gan :p");
			this.masaBerlaku.setText("Error Gan :p");
			//sendSMS(c[1], c[2]);
			}
	}
 	
	private void sendSMS(String message, String phoneNumber)
    {
    	String SENT = "SMS_SENT";
    	PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0); 	
    	
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
    	
    	SmsManager sms = SmsManager.getDefault();
    	sms.sendTextMessage(phoneNumber, null, message, sentPI, null);
    }
	
	@Override
	protected void onResume() {
	registerReceiver(intentReceiver, intentFilter);
	super.onResume();
	}
	
	@Override
	protected void onPause() {
	unregisterReceiver(intentReceiver);
	super.onPause();
	}
	
	public void gesture_detector()
    {
    	gestureDetector = new GestureDetector(new MyGestureDetector());
        View mainview = (View) findViewById(R.id.mainView);
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
	    Intent intent = new Intent(Pulsa.this.getBaseContext(), Pulsa.class);
        
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
                return false;
            }
            // right to left swipe
            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {          
            Intent i = new Intent(getApplicationContext(), Internet.class);   
    		Bundle bundlekuota = new Bundle();
    		bundlekuota.putString("user", Username);
    		//bundlekuota.putDoubleArray("kuota", kuota);
    		bundlekuota.putDouble("kuota", kuotabaru);
			bundlekuota.putStringArray("masaBerlaku", dateKuota);
    		i.putExtras(bundlekuota);    		
            startActivity(i);
            
            Pulsa.this.overridePendingTransition(
			R.anim.slide_in_right,
			R.anim.slide_out_left
    		);
            }
            return false;
        }
 
        @Override
        public boolean onDown(MotionEvent e) {
	        	return true;
        }
    } 
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.pulsa, menu);
		return true;
	}	
}
