package com.thmam.pulsameter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */	
	private String operatorName,namaUser;
	private EditText Input;
   
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {  	
    	super.onCreate(savedInstanceState);
    	
    	Method data = new Method(); 
    	namaUser = data.query(getApplicationContext());
        
    	if(namaUser.equals(""))
        {
        setContentView(R.layout.page_daftar);
        TextView Operator,Testbox;	
        Input=(EditText)findViewById(R.id.inputNama);
        Operator=(TextView)findViewById(R.id.namaOperator);
        Testbox=(TextView)findViewById(R.id.testing);
      
        TelephonyManager telephonyManager =(TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        operatorName = telephonyManager.getNetworkOperatorName();
        Operator.setText(operatorName);
        }
        else 
        {	   	
    		Intent i = new Intent(getApplicationContext(),Pulsa.class);        
    		startActivity(i);  
        }   
        //String tekscoba = "Anda memiliki: 3 MB DATA3G.3 MB Flash. Berlaku s/d 13-06-2013 pkl 23.59 Berlaku s/d 15-09-2013. '\n"; operatorName="Telkomsel";
        //String tekscoba = "fasfdsfsd. fdsafdsfafadsfsd  dsfsadfdsaf. Kuota Bulanan 25rb. Kuota tersisa tinggal 371.22 MB.  fdsafadsf 350MB ssfdsfdsf. '\n";operatorName="Indosat";
        //double[] a = data.getSisaKuota(tekscoba, operatorName);
        //int a = data.getMaksKuota(tekscoba, operatorName);
        //Testbox.setText("Kuota "+a);
        //String[] a = data.KuotaConvert(tekscoba, operatorName);
        //Testbox.setText("Kuota "+a[0]+" Tersisa"+a[1]);
        //Toast.makeText(getApplicationContext(),Username + " " + Operator + " " + a[0]+" "+a[1]+" "+a[2],Toast.LENGTH_LONG).show();
        //String tekscoba="Sisa Pulsa Anda Rp.5600. Masa berlaku sampai dengan 05/11/2014. Yo Yo.'\n"; operatorName="Telkomsel";
        //String tekscoba="Pulsa 19350 s/d 18Aug13.'\n"; operatorName="XL";
        //String tekscoba="Pulsa anda sisa Rp 1234 berlaku s/d 18-AUG-14. '\n"; operatorName="3";
        //String tekscoba="Pulsa Anda Rp. 36,998 berlaku s/d 03/05/2014 '\n"; operatorName="SMARTFREN";
        //String tekscoba="Balance Rp.8500. Active 08.16.13, GracePeriod 09.15.13. Daily SMS 0 SMS to ISAT & 0 SMS to others.'\n"; operatorName="Indosat";
        //int a = data.getBalUSSD(tekscoba, operatorName);
        //String[] a = data.getDateUSSD(tekscoba, operatorName, "");
        //Testbox.setText("Rp."+a+",00 "+b[1]+" "+b[2]+" "+b[3]);
        //String[] a = data.USSDConvert(tekscoba,operatorName);
        //Toast.makeText(getApplicationContext(),"Rp."+hasil[0]+",00"+" "+tanggal[0]+" "+tanggal[1]+" "+tanggal[2], Toast.LENGTH_LONG).show();
        //Testbox.setText("Rp."+a[0]+",00"+" "+a[2]+" "+a[3]+" "+a[4]);
    }
    
    public void daftarOnClick(View view)
    {
    	namaUser = Input.getEditableText().toString();
    	if(namaUser!="")
    	{
    		Method data = new Method();  	   	
        	data.setUsername(namaUser); data.add(getApplicationContext(), data);
    		Intent i = new Intent(getApplicationContext(),Pulsa.class);     
    		startActivity(i);  
    	}
    	else Toast.makeText(getApplicationContext(),"Isi nama anda dengan benar", Toast.LENGTH_LONG).show();
    }
 	
    @Override
	protected void onPause() 
    {
		// TODO Auto-generated method stub
		super.onPause();		
		finish();
	}     
}
