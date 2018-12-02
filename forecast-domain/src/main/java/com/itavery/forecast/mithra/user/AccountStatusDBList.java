package com.itavery.forecast.mithra.user;

import com.gs.fw.finder.Operation;

import java.util.Collection;
public class AccountStatusDBList extends AccountStatusDBListAbstract
{
	public AccountStatusDBList()
	{
		super();
	}

	public AccountStatusDBList(int initialSize)
	{
		super(initialSize);
	}

	public AccountStatusDBList(Collection c)
	{
		super(c);
	}

	public AccountStatusDBList(Operation operation)
	{
		super(operation);
	}
}
