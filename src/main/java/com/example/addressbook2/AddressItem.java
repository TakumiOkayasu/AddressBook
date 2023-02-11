package com.example.addressbook2;

public class AddressItem
{
	public long id;
	public String name, address, tel, birthday;

	public AddressItem()
	{
		this( "", "", "", "" );
	}

	public AddressItem( long id, String name, String address, String tel, String birthday )
	{
		this( name, address, tel, birthday );
		this.id = id;
	}

	public AddressItem( String name, String address, String tel, String birthday )
	{
		this.name = name;
		this.address = address;
		this.tel = tel;
		this.birthday = birthday;
	}
}
