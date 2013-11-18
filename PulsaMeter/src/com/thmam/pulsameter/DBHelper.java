package com.thmam.pulsameter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	//Deklarasi konstanta nama database
	public static final String NAMA_DATABASE = "account";
	
	//Tabel
	public static final String TABLE_SIMPLE = "operator";
	
	//Kolom pada tabel
	public static final String TABLE_SIMPLE_ID = "_id";
	public static final String TABLE_SIMPLE_VALUE = "user";
	
	//Versi database
	public static final int VERSI_DATABASE = 1;
	
	//Deklarasi sintaks create table simple
	public static final String CREATE_TABLE_SIMPLE = "CREATE TABLE " 
													 + TABLE_SIMPLE + "(" + TABLE_SIMPLE_ID 
													 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
													 + TABLE_SIMPLE_VALUE + " TEXT NOT NULL)";
	
	//Konstruktor kelas DBHelper
	//Jangan lupa memanggil konstruktor super classnya
	public DBHelper(Context context)
	{
		super(context, NAMA_DATABASE, null, VERSI_DATABASE);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE_SIMPLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		//Mengecek apakah versi database yang baru lebih tinggi dari versi lama
		//Jika benar, lakukan sesuatu
		if(newVersion > oldVersion)
		{
			//Menghapus table, kemudian membuat baru
			db.execSQL("DROP TABLE IF EXIST "+TABLE_SIMPLE);
			db.execSQL(CREATE_TABLE_SIMPLE);
		}
	}
}
