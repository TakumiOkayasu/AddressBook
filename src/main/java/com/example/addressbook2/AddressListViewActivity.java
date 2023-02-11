package com.example.addressbook2;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Locale;

public class AddressListViewActivity extends AppCompatActivity
{
	private AddressBookAccesser accesser = new AddressBookHelper( this );

	@Override
	protected void onCreate( @Nullable Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_list_items );

		List< AddressItem > items = accesser.getAllItems();
		BaseAdapter adapter = new AddressBookAdapter( this, items, R.layout.list_item );
		ListView listView = findViewById( R.id.list_view_items );
		listView.setAdapter( adapter );

		listView.setOnItemLongClickListener( ( parent, view, position, id ) ->
		{
			new AlertDialog
				.Builder( this )
				.setTitle( "削除" )
				.setMessage( String.format( Locale.JAPAN, "ID: %d を削除しますか？", id ) )
				.setPositiveButton( "はい", ( dialog, which ) ->
				{
					if( accesser.deleteItem( id ) ) {
						items.remove( position );
						adapter.notifyDataSetChanged();
					}
					else {
						throw new RuntimeException( "delete id = " + id );
					}
				} )
				.setNegativeButton( "キャンセル", ( dialog, which ) -> {} )
				.show();
			return false;
		} );

		findViewById( R.id.btn_back ).setOnClickListener( v -> moveToTitle() );
	}

	private void moveToTitle()
	{
		finish();
	}
}
