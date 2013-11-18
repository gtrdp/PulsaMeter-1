package com.thmam.pulsameter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class Method {
	private String operator, username, sisapulsa, pemakaianpulsa, masaberlaku, namaBulan;
	
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSisapulsa() {
		return sisapulsa;
	}
	public void setSisapulsa(String sisapulsa) {
		this.sisapulsa = sisapulsa;
	}
	public String getPemakaianpulsa() {
		return pemakaianpulsa;
	}
	public void setPemakaianpulsa(String pemakaianpulsa) {
		this.pemakaianpulsa = pemakaianpulsa;
	}
	public String getMasaberlaku() {
		return masaberlaku;
	}
	public void setMasaberlaku(String masaberlaku) {
		this.masaberlaku = masaberlaku;
	}
	
	public int getBalUSSD(String textUSSD, String operator)
	{		  
		String regex_ussd=""; String a="";		
		if(operator.equals("XL") || operator.equals("Xl") || operator.equals("xl")) regex_ussd = ".*[\\s](\\d+)[\\s]s/d.*";
		else regex_ussd = ".*Rp[.]??[\\s]??(\\d+[,]??\\d+)[.,\\s]??.*";
		
		Pattern p = Pattern.compile(regex_ussd);
		Matcher m = p.matcher(textUSSD.trim());
		if(m.matches()) 
		{
			a = m.group(1);
			a = a.replace(",", "");
		}
	    return Integer.valueOf(a);
	}

	public String[] getDateUSSD(String textUSSD, String operator, String mode)
	{		  
		final SimpleDateFormat df, databaseformat = new SimpleDateFormat("yyyy-MM-dd");
		final Calendar c = Calendar.getInstance();
		String regex_date="\\d+[./-][A-Z0-9]+[./-]\\d+"; String[] b = new String[4];	
		if(operator.equals("Telkomsel") || operator.equals("TELKOMSEL") || operator.equals("telkomsel")) 
		{
			if(mode.equals("kuota"))
			{
				df = new SimpleDateFormat("dd-MM-yyyy"); 
			}
			else df = new SimpleDateFormat("dd/MM/yyyy");		
		}
		else if(operator.equals("Indosat") || operator.equals("INDOSAT")) df = new SimpleDateFormat("MM.dd.yy");
		else if(operator.equals("3")) df = new SimpleDateFormat("dd-MMM-yy");
		else if(operator.equals("XL") || operator.equals("Xl") || operator.equals("xl"))
		{
			regex_date = "\\d{2}[A-Za-z]{3}\\d{2}";
			df = new SimpleDateFormat("ddMMMyy");
		}
		else df = new SimpleDateFormat("dd/MM/yyyy");
		
		String[][] a = new String[2][4]; int i=0;
		Pattern p = Pattern.compile(regex_date);
		Matcher m = p.matcher(textUSSD.trim());
		
		while(m.find()) 
		{
			a[i][0] = m.group(0);		
			try
			{
				c.setTime(df.parse(a[i][0]));
				a[i][0]= databaseformat.format(c.getTime());
				a[i][1] = String.valueOf(c.get(Calendar.DATE));
				a[i][2] = String.valueOf(c.get(Calendar.MONTH));
				a[i][3] = String.valueOf(c.get(Calendar.YEAR));
		
				switch(Integer.parseInt(a[i][2])+1)
				{
					case 1 : namaBulan="Januari";break;
					case 2 : namaBulan="Februari";break;
					case 3 : namaBulan="Maret";break;
					case 4 : namaBulan="April";break;
					case 5 : namaBulan="Mei";break;
					case 6 : namaBulan="Juni";break;
					case 7 : namaBulan="Juli";break;
					case 8 : namaBulan="Agustus";break;
					case 9 : namaBulan="September";break;
					case 10 : namaBulan="Oktober";break;
					case 11 : namaBulan="November";break;
					case 12 : namaBulan="Desember";break;
				}
				a[i][2]=namaBulan;
			}
			catch (Exception e)
			{
			}
			i++;
		}
		b[0] = a[0][0];		
		for(int d=1;d<=3;d++)
		{
			b[d] = a[0][d];
		}
	    return b;
	}
	
	public int getMaksKuota(String textKuota, String operator)
	{		  
		String regex_kuota=""; int a=0;	
		if(operator.equals("Telkomsel") || operator.equals("TELKOMSEL") || operator.equals("telkomsel")) regex_kuota=".*?(Rp.\\d+.).*";
		else if(operator.equals("Indosat") || operator.equals("INDOSAT")) regex_kuota=".*[\\s](\\d+)[\\s]??rb.*";
		else regex_kuota=".*?(Rp.\\d+.).*";
		
		Pattern p = Pattern.compile(regex_kuota);
		Matcher m = p.matcher(textKuota.trim());	
		if(m.matches()) a = Integer.valueOf(m.group(1));
	    return (a*1000);
	}
	
	public double getSisaKuota(String textKuota, String operator)
	{		  
		String regex_kuota="[\\s](\\d+[.]??\\d*)[\\s]??[MkK]??B";
		double[] a = new double[2]; int i=0; double baru=0;
		a[0] = 0; a[1] = 0;
		Pattern p = Pattern.compile(regex_kuota);
		Matcher m = p.matcher(textKuota.trim());
		
		while(m.find())
		{
			a[i] = Double.valueOf(m.group(1)); i++;
		}
		baru=a[0];
	    return baru;
	}
	
	public String[] USSDConvert(String textUSSD, String operator)
	{		  
		//Pattern p = Pattern.compile(".*?(\\d{2}(am|pm)).*");
		
		//Pattern p = Pattern.compile(".*?(\\d{2}(am|pm)).*");
	  	//String regex_ussd="*?([Rp.]\\d+)[.].*";
	  	//String regex_ussd = ".*Rp(.|[^.])?(.*?)\\..*";
	  	//String regex_ussd = ".*Rp.(.*?)\\..*";
	  	//String regex_ussd=".*?(Rp.\\d+).*";
		
		String regex_ussd="";
		final SimpleDateFormat df;
		
		if(operator.equals("Telkomsel") || operator.equals("TELKOMSEL") || operator.equals("telkomsel")) 
		{
			regex_ussd=".*?(Rp.\\d+.).*?(\\d{2}/\\d{2}/\\d{4}).*";
			df = new SimpleDateFormat("dd/MM/yyyy");
		}
		else if(operator.equals("Indosat") || operator.equals("INDOSAT")) 
		{
			regex_ussd=".*?(Rp.\\d+.).*?(\\d{2}.\\d{2}.\\d{2}).*";
			df = new SimpleDateFormat("MM.dd.yy");
		}
		else
		{
			regex_ussd=".*?(Rp.\\d+.).*?(\\d{2}.\\d{2}.\\d{4}).*";
			df = new SimpleDateFormat("dd/MM/yyyy");
		}
		
		String[] hasil = {"","","","",""};
		Pattern p = Pattern.compile(regex_ussd);
		Matcher m = p.matcher(textUSSD.trim());
		
		if(m.matches())
		{
		  hasil[0] = m.group(1);
		  hasil[1] = m.group(2);
		  String[] sementara = hasil[0].split("\\.");
		  hasil[0] = sementara[1];
		}
		
        final Calendar c = Calendar.getInstance();
		
        try
		{
           c.setTime(df.parse(hasil[1]));
           hasil[2] = String.valueOf(c.get(Calendar.DATE));
           hasil[3] = String.valueOf(c.get(Calendar.MONTH));
           hasil[4] = String.valueOf(c.get(Calendar.YEAR));
		
           switch(Integer.parseInt(hasil[3])+1)
           {
				case 1 : namaBulan="Januari";break;
				case 2 : namaBulan="Februari";break;
				case 3 : namaBulan="Maret";break;
				case 4 : namaBulan="April";break;
				case 5 : namaBulan="Mei";break;
				case 6 : namaBulan="Juni";break;
				case 7 : namaBulan="Juli";break;
				case 8 : namaBulan="Agustus";break;
				case 9 : namaBulan="September";break;
				case 10 : namaBulan="Oktober";break;
				case 11 : namaBulan="November";break;
				case 12 : namaBulan="Desember";break;
           }
		   hasil[3]=namaBulan;
		}
		catch (Exception e)
		{
		}
	    return hasil;
	}
	
	public String[] kuotaConvert(String textKuota, String operator)
	{		  
		String regex_ussd="";	
		if(operator.equals("Telkomsel") || operator.equals("TELKOMSEL")) 
		{
			regex_ussd=".*?(Rp.\\d+.).*";
		}
		else if(operator.equals("Indosat") || operator.equals("INDOSAT")) 
		{
			regex_ussd=".*?(\\d+)rb.*?(\\d*(.|[^.])\\d*)(\\s|\\S)MB.*";
			//regex_ussd=".*?(\\d+)rb.*?(\\d*(.|[^.])\\d*(\\s|\\S))MB.*";
			//regex_ussd=".*(\\d+(?=rb)).*(\\d+(?=MB)).*";
			//regex_ussd=".*?(\\d+)rb.*?(\\d*.\\d*)MB.*";
			//Kuota Bulanan 25rb, 375.0MB
			//df = new SimpleDateFormat("MM.dd.yy");
			
			//regex_ussd=".*?(\\d+)rb.*?(\\d*(.|[^.])\\d*)(\\s|\\S)MB.*";
			//regex_ussd=".*?(\\d+)rb.*?(\\d*(.|[^.])\\d*(\\s|\\S))MB.*";
			//regex_ussd=".*(\\d+(?=rb)).*(\\d+(?=MB)).*";
			//regex_ussd=".*?(\\d+)rb.*?(\\d*.\\d*)MB.*";
			//Kuota Bulanan 25rb, 375.0MB
			//df = new SimpleDateFormat("MM.dd.yy");
			
			//regex_kuota=".*?(\\d*(.|[^.])\\d*)(\\s|\\S)MB.*";
			//regex_ussd=".*?(\\d+)rb.*?(\\d*(.|[^.])\\d*(\\s|\\S))MB.*";
			//regex_ussd=".*(\\d+(?=rb)).*(\\d+(?=MB)).*";
			//regex_ussd=".*?(\\d+)rb.*?(\\d*.\\d*)MB.*";
			//Kuota Bulanan 25rb, 375.0MB
			//df = new SimpleDateFormat("MM.dd.yy");
			
			//regex_ussd=".*?(\\d+)rb.*?(\\d*(.|[^.])\\d*)(\\s|\\S)MB.*"; TERAKHIR MAU
			//regex_ussd=".*?(\\d+)rb.*?(\\d*(.|[^.])\\d*(\\s|\\S))MB.*";
			//regex_ussd=".*(\\d+(?=rb)).*(\\d+(?=MB)).*";
			//regex_ussd=".*?(\\d+)rb.*?(\\d*.\\d*)MB.*";
			//Kuota Bulanan 25rb, 375.0MB
			//df = new SimpleDateFormat("MM.dd.yy");
		}
		else
		{
			regex_ussd=".*?(Rp.\\d+.).*?(\\d{2}.\\d{2}.\\d{4}).*";
		}
		
		String[] hasil = {"","","","",""};
		Pattern p = Pattern.compile(regex_ussd);
		Matcher m = p.matcher(textKuota.trim());
		
		if(m.matches())
		{
		  hasil[0] = m.group(1);
		  hasil[1] = m.group(2);
		  //String[] sementara = hasil[0].split("\\.");
		  //hasil[0] = sementara[1];
		}
		
	    /*final Calendar c = Calendar.getInstance();
		
	    try
		{
	       c.setTime(df.parse(hasil[1]));
	       hasil[2] = String.valueOf(c.get(Calendar.DATE));
	       hasil[3] = String.valueOf(c.get(Calendar.MONTH));
	       hasil[4] = String.valueOf(c.get(Calendar.YEAR));
		
	       switch(Integer.parseInt(hasil[3])+1)
	       {
				case 1 : namaBulan="Januari";break;
				case 2 : namaBulan="Februari";break;
				case 3 : namaBulan="Maret";break;
				case 4 : namaBulan="April";break;
				case 5 : namaBulan="Mei";break;
				case 6 : namaBulan="Juni";break;
				case 7 : namaBulan="Juli";break;
				case 8 : namaBulan="Agustus";break;
				case 9 : namaBulan="September";break;
				case 10 : namaBulan="Oktober";break;
				case 11 : namaBulan="November";break;
				case 12 : namaBulan="Desember";break;
	       }
		   hasil[3]=namaBulan;
		}
		catch (Exception e)
		{
		}*/
	    return hasil;
	}
	
	public String getCurDate()
	{
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String strDate = sdf.format(c.getTime());
		return strDate;
	}
	
	public int getSMSCount(Context context)
 	{
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");  
		final Calendar ca = Calendar.getInstance();
        Uri urisms = Uri.parse("content://sms/sent");
        String columns[] = new String[] {"date"}; String sortOrder = "date DESC"; 
        Cursor c = context.getContentResolver().query(urisms, columns, null, null,sortOrder);
        int j = c.getCount(), i = 0; Method data = new Method();
        String[] dates = new String[j]; String[] dates3 = new String[j]; String curdate = data.getCurDate();
        if(c.moveToFirst())
        {
	            do
	            {       
	            		dates3[i] = c.getString(0);
	            		Long timestamp = Long.parseLong(dates3[i]);
	            		Calendar calendar = Calendar.getInstance();
	                    calendar.setTimeInMillis(timestamp);
	                    Date finaldate = calendar.getTime();
	                    String smsDate = finaldate.toString();	                    
	            		dates[i] = df.format(finaldate);
	                    if(dates[i].equals(curdate)) i++; else break;  
	            }while(c.moveToNext());
        }
        c.close(); 
        return i; 
 	}
	
	public String query(Context context)
  	{
  		DBAdapter dbAdapter = new DBAdapter(context);
  		dbAdapter.open();
  		String usernames = dbAdapter.getUsername();
  		dbAdapter.close();
  		return usernames;
  	}
	
	public void add(Context context, Method obj)
 	{
 		DBAdapter dbAdapter = new DBAdapter(context);
 		dbAdapter.open();
 		dbAdapter.insert(obj);
 		dbAdapter.close();	
 	}
	
	public void updateData(Context context, String userBaru, String userLama)
	{
		DBAdapter dbAdapter = new DBAdapter(context);
 		dbAdapter.open();
 		dbAdapter.updateuser(userBaru, userLama);
 		dbAdapter.close();
	}
	
	public void deleteall(Context context)
 	{
 		DBAdapter dbAdapter = new DBAdapter(context);
 		dbAdapter.open();
 		dbAdapter.deleteall();
 		dbAdapter.close();
 	}
	
 	/*public String query()
  	{
  		DBAdapter dbAdapter = new DBAdapter(getApplicationContext());
  		dbAdapter.open();
  		String usernames = dbAdapter.getUsername();
  		dbAdapter.close();
  		return usernames;
  	}*/
  	
 	/*public void add(String Users)
 	{
 		DBAdapter dbAdapter = new DBAdapter(getApplicationContext());
 		dbAdapter.open();
 		dbAdapter.insert(Users);
 		dbAdapter.close();	
 	}*/
	
	 /*private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 100;
    private GestureDetector gestureDetector;
	
	public void gestur_detector()
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
    }*/   
    
	/*class MyGestureDetector extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
	    Intent intent = new Intent(MainActivity.this.getBaseContext(), MainActivity.class);
        
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
                return false;
            }
 
            // right to left swipe
            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
          
            Intent i = new Intent(getApplicationContext(), Pulsa.class);           	     
            startActivity(i);
            MainActivity.this.overridePendingTransition(
			R.anim.slide_in_right,
			R.anim.slide_out_left
    		);}
    	    // left to right swipe
            //}  else 
            	/*if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            Intent i = new Intent(getApplicationContext(), Pulsa.class);            	            
            startActivity(i);
            MainActivity.this.overridePendingTransition(
			R.anim.slide_in_left, 
			R.anim.slide_out_right
    		);
            }
            return false;*/
        
/*
//It is necessary to return true from onDown for the onFling event to register
@Override
public boolean onDown(MotionEvent e) {
    	return true;
} */
	
	//Username = bundle.getString("user");		
			/*if (outstate != null && outstate.containsKey("user"))
			{
				Username = outstate.getString("user");
				welcomeMessage.setText("Selamat Datang, "+ Username);
				sisaPulsa.setText(outstate.getString("sisapulsa"));	
				masaBerlaku.setText(outstate.getString("masaberlaku"));
			}*/
			//else
			/*Bundle bundle = getIntent().getExtras();
			if(bundle!=null && bundle.containsKey("user"))
			{
				Username = bundle.getString("user");			
				welcomeMessage.setText("Selamat Datang, "+ Username);
				call("*" + c[0] + encodedHash);	
			}*	
			/*Intent i = new Intent(getApplicationContext(), Internet.class);	 
			Bundle bundlekuota = new Bundle();
			bundlekuota.putString("username", Username);
			bundlekuota.putString("operator", Operator);
			i.putExtras(bundlekuota);*/
		    
		    //pemakaianPulsa.setText(Username + " "+ Operator + " "+ a[0]+" "+" "+a[1]+" "+a[2]+" ");
		    //Toast.makeText(getApplicationContext(),Username + " " + Operator + " " + a[0]+" "+a[1]+" "+a[2],Toast.LENGTH_LONG).show();
		    //Toast.makeText(getApplicationContext(),Username + " " + Operator + " " + number,Toast.LENGTH_LONG).show();
			//gestur_detector();  
		    //String tekscoba="Sisa Pulsa Anda Rp.568484. Masa berlaku sampai dengan 05/11/2014. Yo Yo";
	        //String[] hasil = data.USSDConvert(tekscoba);
	        //String[] tanggal= data.getTanggal(hasil[1]);
	        //Toast.makeText(getApplicationContext(),"Rp."+hasil[0]+",00"+" "+tanggal[0]+" "+tanggal[1]+" "+tanggal[2], Toast.LENGTH_LONG).show();
	
	//pemakaianPulsa.setText("From"+" "+origin+": "+"Paket Rp."+a[1]+",00 "+"Sisa "+a[0]+" MB");
	//pemakaianPulsa=(TextView)findViewById(R.id.pemakaianpulsa);
	
	//String date = c.getString(3);
    //c.close();[4]	"date" (id=830015890640) "date" (id=830015890640)\t	

   //textview.setText(c.getCount());		
	//Uri uri = Uri.parse("content://sms/sent");
    //Cursor c= getContentResolver().query(uri, null, null ,null,null);
    //startManagingCursor(c);	    
   //body = new String[c.getCount()];
   //number = new String[c.getCount()]; 	                    
   /*if(c.moveToFirst()){
            for(int i=0;i<c.getCount();i++){
                    body[i]= c.getString(c.getColumnIndexOrThrow("body")).toString();
                    number[i]=c.getString(c.getColumnIndexOrThrow("address")).toString();
                    c.moveToNext();
            }
   }
   c.close();*/		
	//Cursor curSms = managedQuery(Uri.parse("content://sms/sent"), null, null, null, null); 
	//dateCol = mCurSms.getColumnIndex("date"); 
	
	//double[] kuota = a.getDoubleArray("kuota");
	//String[] masa = a.getStringArray("masaBerlaku");	
	//String Username = a.getString("username");
	//String Operator = a.getString("operator");
	//sisaKuota.setText(kuota[0]+" MB");
    //masaBerlaku.setText(masa[1]+" "+masa[2]+" "+masa[3]);
	/*if(a.getDoubleArray("kuota")!=null && a.getStringArray("masaBerlaku")!=null)
	{
		double[] kuota=a.getDoubleArray("kuota");
		String[] masa = a.getStringArray("masaBerlaku");
		sisaKuota.setText(kuota[0]+" MB");
	    masaBerlaku.setText(masa[1]+" "+masa[2]+" "+masa[3]);
	}*/
	
	// left to right swipe
    /*if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
    Intent i = new Intent(getApplicationContext(), MainActivity.class);            	            
    startActivity(i);
    Pulsa.this.overridePendingTransition(
	R.anim.slide_in_left, 
	R.anim.slide_out_right
	);*/
	
	 // right to left swipe
    /*if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
    Intent i = new Intent(getApplicationContext(), Internet.class);           	     
    startActivity(i);
    Pulsa.this.overridePendingTransition(
	R.anim.slide_in_right,
	R.anim.slide_out_left
	);
	/*String date =  cursor.getString(cursor.getColumnIndex("date"));
	                    Long timestamp = Long.parseLong(date);    
	                    Calendar calendar = Calendar.getInstance();
	                    calendar.setTimeInMillis(timestamp);
	                    Date finaldate = calendar.getTime();
	                    String smsDate = finaldate.toString();
	                    	                    //try{ca.setTime(df.parse(dates3[i]));}
	                    //catch(Exception e){}           
	                    //dates[i]= df.format(ca.getTime());
	                    */
	
}
