package com.itavery.forecast.mithra.user;

import com.gs.fw.finder.Operation;

import java.util.Collection;
public class UserCredsDBList extends UserCredsDBListAbstract
{
	public UserCredsDBList()
	{
		super();
	}

	public UserCredsDBList(int initialSize)
	{
		super(initialSize);
	}

	public UserCredsDBList(Collection c)
	{
		super(c);
	}

	public UserCredsDBList(Operation operation)
	{
		super(operation);
	}
}
