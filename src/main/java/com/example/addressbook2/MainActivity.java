package com.example.addressbook2;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
		findViewById( R.id.btn_register ).setOnClickListener( v ->
		{
			AddressItem item = new AddressItem();

			EditText name = findViewById( R.id.et_name );
			EditText address = findViewById( R.id.et_address );
			EditText tel = findViewById( R.id.et_tel );
			EditText birthday = findViewById( R.id.et_birthday );

			// 最後の項目でエンターキーが押されたらキーボードを非表示にする
			birthday.setOnEditorActionListener( ( v1, actionId, event ) ->
			{
				if( actionId == EditorInfo.IME_ACTION_DONE ) {
					hideSoftwareKeyboard();
				}
				return false;
			} );

			item.name = name.getText().toString();
			item.address = address.getText().toString();
			item.tel = tel.getText().toString();
			item.birthday = birthday.getText().toString();

			// 空欄がなければ登録するフラグ
			boolean isRegisterable = !( item.name.isEmpty() || item.address.isEmpty() || item.tel.isEmpty() || item.birthday.isEmpty() );

			if( isRegisterable ) {
				Toast.makeText( this, registerItem( item ) ? "登録しました" : "登録に失敗しました", Toast.LENGTH_SHORT ).show();

				name.setText( "" );
				address.setText( "" );
				tel.setText( "" );
				birthday.setText( "" );
			}
			else {
				new AlertDialog
					.Builder( this )
					.setTitle( "エラー" )
					.setMessage( "入力項目に空欄があります。" )
					.setPositiveButton( "はい", ( dialog, which ) -> {} )
					.show();
			}
		} );
	}

	private void moveToAddressList()
	{
		startActivity( new Intent( this, AddressListViewActivity.class ) );
	}

	private boolean registerItem( AddressItem item )
	{
		try( AddressBookHelper accesser = new AddressBookHelper( this ) ) {
			return accesser.registerItem( item );
		}
	}

	private void hideSoftwareKeyboard()
	{
		InputMethodManager imm = ( InputMethodManager ) getSystemService( INPUT_METHOD_SERVICE );
		imm.hideSoftInputFromWindow( getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS );
	}
}