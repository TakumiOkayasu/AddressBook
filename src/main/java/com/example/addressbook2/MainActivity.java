package com.example.addressbook2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );

		findViewById( R.id.btn_show_list ).setOnClickListener( v -> moveToAddressList() );

		String name = ( ( TextView ) findViewById( R.id.et_name ) ).getText().toString();
		String address = ( ( TextView ) findViewById( R.id.et_address ) ).getText().toString();
		String tel = ( ( TextView ) findViewById( R.id.et_tel ) ).getText().toString();
		String birthday = ( ( TextView ) findViewById( R.id.et_birthday ) ).getText().toString();

		findViewById( R.id.btn_register ).setOnClickListener( v ->
			Toast.makeText( this, registerItem( new AddressItem( name, address, tel, birthday ) ) ? "登録しました" : "登録に失敗しました", Toast.LENGTH_SHORT ).show()
		);
	}

	private void moveToAddressList()
	{
		startActivity( new Intent( this, AddressListViewActivity.class ) );
	}

	private boolean registerItem( AddressItem item )
	{
		return new AddressBookHelper( this ).registerItem( item );
	}
}