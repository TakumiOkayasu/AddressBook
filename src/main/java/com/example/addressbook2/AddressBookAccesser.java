package com.example.addressbook2;

import java.util.List;

public interface AddressBookAccesser
{
	boolean registerItem( AddressItem item );
	AddressItem getAddressItem( long id );
	List< AddressItem > getAllItems();
	boolean deleteItem( long id );
}
