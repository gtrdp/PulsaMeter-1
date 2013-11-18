package com.thmam.pulsameter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

	public class SMSReceiver extends BroadcastReceiver
	{		
		@Override
		public void onReceive(Context context, Intent intent)
		{	
			//---get the SMS message passed in---
			Bundle bundle = intent.getExtras();
			SmsMessage[] msgs = null;
			String msg = "", origin = "";
			if (bundle != null)
			{
				//---retrieve the SMS message received---
				Object[] pdus = (Object[]) bundle.get("pdus");
				msgs = new SmsMessage[pdus.length];	
				for (int i=0; i<msgs.length; i++)
				{
					msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
					origin = msgs[i].getOriginatingAddress();
					msg = msgs[i].getMessageBody().toString();
					msg += "\n";
				}
				
				//---send a broadcast intent to update the SMS received in the activity---//				
				Intent broadcastIntent = new Intent();
				broadcastIntent.setAction("SMS_RECEIVED_ACTION");
				Bundle bundle2 = new Bundle();
				bundle2.putString("origin", origin);
				bundle2.putString("msg", msg);
				broadcastIntent.putExtras(bundle2);
				context.sendBroadcast(broadcastIntent);
			}
		}
	}