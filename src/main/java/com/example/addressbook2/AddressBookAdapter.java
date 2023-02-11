package com.example.addressbook2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AddressBookAdapter extends BaseAdapter
{
	private List< AddressItem > items = new ArrayList<>();
	private Context context;
	private int resource;
	private LayoutInflater inflater;

	public AddressBookAdapter( Context context, List< AddressItem > items, int resource )
	{
		this.context = context;
		this.items = items;
		this.resource = resource;
		this.inflater = ( LayoutInflater ) this.context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
	}

	@Override
	public int getCount()
	{
		return items.size();
	}

	@Override
	public Object getItem( int position )
	{
		return items.get( position );
	}

	@Override
	public long getItemId( int position )
	{
		return items.get( position ).id;
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent )
	{
		View v = ( convertView != null ) ? convertView : inflater.inflate( resource, null );
		AddressItem item = items.get( position );

		( ( TextView ) v.findViewById( R.id.tv_list_ID ) ).setText( String.valueOf( item.id ) );
		( ( TextView ) v.findViewById( R.id.tv_list_name ) ).setText( item.name );
		( ( TextView ) v.findViewById( R.id.tv_list_address ) ).setText( item.address );
		( ( TextView ) v.findViewById( R.id.tv_list_tel ) ).setText( item.tel );
		( ( TextView ) v.findViewById( R.id.tv_list_birthday ) ).setText( item.birthday );

		return v;
	}
}
