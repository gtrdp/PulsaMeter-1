package com.thmam.pulsameter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {
	//Semua kolom pada table_simple
	public static final String[] TABLE_SIMPLE_ALL_COLUMNS = new String[]
	{
		DBHelper.TABLE_SIMPLE_ID, DBHelper.TABLE_SIMPLE_VALUE
	};
	
	private DBHelper dbHelper;
	private SQLiteDatabase db;
	
	// Konstruktor class
	public DBAdapter(Context context)
	{
		//Membuat dbHelper
		dbHelper = new DBHelper(context);
	}
	
	// Method buka koneksi database
	public void open()
	{
		//Mengambil database dari dbHelper
		db = dbHelper.getWritableDatabase();
	}
	
	// Method tutup koneksi database 
	public void close()
	{
		db.close();
	}
	
	public long insert(Method obj)
	{
		ContentValues mContentValues = new ContentValues();
		mContentValues.put(DBHelper.TABLE_SIMPLE_VALUE, obj.getUsername());	
		// Insert ke Database
		long result = db.insert(DBHelper.TABLE_SIMPLE, null, mContentValues);
		return result;
	}
	
	public String getUsername()
	{
		Cursor cursor = db.query(DBHelper.TABLE_SIMPLE, TABLE_SIMPLE_ALL_COLUMNS, null, null, null, null, null);
		String Username="";
		if(cursor.moveToFirst())
		{
			do
			{			
				Username = cursor.getString(1);
				Method obj = new Method();
				obj.setUsername(Username);
			} while(cursor.moveToNext());
		}
		return Username;
	}
	
	public long delete(long id)
	{
		return db.delete(DBHelper.TABLE_SIMPLE, DBHelper.TABLE_SIMPLE_ID + " = ?", new String[]{Long.toString(id)});
	}
	
	public void deleteall()
	{
		db.execSQL("delete from "+ DBHelper.TABLE_SIMPLE);
	}
	
	public int updateuser(String userBaru, String userLama)
	{
		ContentValues values = new ContentValues();
		values.put(DBHelper.TABLE_SIMPLE_VALUE, userBaru);
		return db.update(DBHelper.TABLE_SIMPLE, values, DBHelper.TABLE_SIMPLE_VALUE + " = ?", new String[] { userLama });	
	}
	
	// Menulis ObjectSimple ke database (buat insert ke database)
		/*public long insert(ObjectSimple objectSimple)
		{
			ContentValues mContentValues = new ContentValues();
			mContentValues.put(DBHelper.TABLE_SIMPLE_VALUE, objectSimple.getValue());
			
			// Insert ke Database
			long result = db.insert(DBHelper.TABLE_SIMPLE, null, mContentValues);
			return result;
		}
		
		public ArrayList<ObjectSimple> getAllObjectSimple()
		{
			ArrayList<ObjectSimple> list = new ArrayList<ObjectSimple>();
			
			//
			Cursor cursor = db.query(DBHelper.TABLE_SIMPLE, 
					TABLE_SIMPLE_ALL_COLUMNS, null, null, null, null, null);
			if(cursor.moveToFirst())
			{
				do
				{
				long id = cursor.getLong(0);
				String value = cursor.getString(1);
				//Buat object berdasarkan nilai yang ada
				ObjectSimple obj = new ObjectSimple();
				obj.setId(id);
				obj.setValue(value);
				// Tambahkan ke List
				list.add(obj);
				} while (cursor.moveToNext());
			}
			return list;
		}*/
	
	/*public long deleteMin()
	{
		return db.delete(DBHelper.TABLE_SIMPLE, DBHelper.TABLE_SIMPLE_ID)
	}*/
	
	//ContentValues args = new ContentValues(); 
	//args.put(DBHelper.TABLE_SIMPLE_VALUE, userBaru); 
	//return db.update(DBHelper.TABLE_SIMPLE, args, DBHelper.TABLE_SIMPLE_VALUE + "=" + userLama, null)>0;
	
	//db.execSQL("UPDATE "+DBHelper.TABLE_SIMPLE+" SET "+DBHelper.TABLE_SIMPLE_VALUE+" = "+userBaru+" WHERE "+DBHelper.TABLE_SIMPLE_VALUE+" = "+userLama);
	//UPDATE "main"."operator" SET "user" = ?1 WHERE  "_id" = 3	
}
