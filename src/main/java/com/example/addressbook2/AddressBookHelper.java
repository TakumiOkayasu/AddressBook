package com.example.addressbook2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AddressBookHelper extends SQLiteOpenHelper implements AddressBookAccesser
{
	private static final String DB_NAME = "address_books.sqlite";
	private static final int VERSION = 2;

	public AddressBookHelper( @Nullable Context context )
	{
		super( context, DB_NAME, null, VERSION );
	}

	@Override
	public boolean registerItem( AddressItem item )
	{
		try(
			SQLiteDatabase db = getWritableDatabase() ;
			SQLiteStatement sql = db.compileStatement( "INSERT INTO address( name, address, tel, birthday ) VALUES( ?, ?, ?, ? )" )
		) {
			sql.bindString( 1, item.name );
			sql.bindString( 2, item.address );
			sql.bindString( 3, item.tel );
			sql.bindString( 4, item.birthday );

			return sql.executeInsert() != -1;
		}
	}

	@Override
	public AddressItem getAddressItem( long id )
	{
		try(
			SQLiteDatabase db = getReadableDatabase() ;
			Cursor cs = db.rawQuery( "SELECT * FROM address WHERE id = ?", null )
		) {
			AddressItem item = new AddressItem();

			if( cs.moveToFirst() ) {
				item.name = cs.getString( cs.getColumnIndex( "name" ) );
				item.address = cs.getString( cs.getColumnIndex( "address" ) );
				item.tel = cs.getString( cs.getColumnIndex( "tel" ) );
				item.birthday = cs.getString( cs.getColumnIndex( "birthday" ) );
			}
			else {
				throw new RuntimeException();
			}

			return item;
		}
	}

	@Override
	public List< AddressItem > getAllItems()
	{
		ArrayList< AddressItem > ret = new ArrayList<>();

		try(
			SQLiteDatabase db = getReadableDatabase() ;
			Cursor cs = db.rawQuery( "SELECT * FROM address", null )
		) {
			while( cs.moveToNext() ) {
				AddressItem item = new AddressItem();

				item.id = cs.getLong( cs.getColumnIndex( "id" ) );
				item.name = cs.getString( cs.getColumnIndex( "name" ) );
				item.address = cs.getString( cs.getColumnIndex( "address" ) );
				item.tel = cs.getString( cs.getColumnIndex( "tel" ) );
				item.birthday = cs.getString( cs.getColumnIndex( "birthday" ) );

				ret.add( item );
			}
		}

		return ret;
	}

	@Override
	public boolean deleteItem( long id )
	{
		try(
			SQLiteDatabase db = getWritableDatabase() ;
			SQLiteStatement sql = db.compileStatement( "DELETE FROM address WHERE id = " + id )
		) {
			return sql.executeUpdateDelete() != 0;
		}
	}

	@Override
	public void onCreate( SQLiteDatabase db )
	{
		db.execSQL( "CREATE TABLE address( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, address TEXT, tel TEXT, birthday TEXT)" );
	}

	@Override
	public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion )
	{
		db.execSQL( "DROP TABLE IF EXISTS address" );
		onCreate( db );
	}
}
