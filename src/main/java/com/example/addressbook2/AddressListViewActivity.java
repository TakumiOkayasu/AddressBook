package com.example.addressbook2;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

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
			String deleteTarget = ( ( TextView ) findViewById( R.id.tv_list_ID ) ).getText().toString();

			new AlertDialog
				.Builder( this )
				.setTitle( "削除" )
				.setMessage( String.format( "ID: %s を削除しますか？", deleteTarget ) )
				.setPositiveButton( "はい", ( dialog, which ) ->
				{
					long delete_id = Integer.parseInt( deleteTarget );
					if( accesser.deleteItem( delete_id ) ) {
						items.remove( position );
						adapter.notifyDataSetChanged();
					}
					else {
						throw new RuntimeException( "delete id = " + delete_id );
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
