package com.itavery.forecast.mithra.user;

import com.gs.fw.finder.Operation;

import java.util.Collection;
public class UserRolesDBList extends UserRolesDBListAbstract
{
	public UserRolesDBList()
	{
		super();
	}

	public UserRolesDBList(int initialSize)
	{
		super(initialSize);
	}

	public UserRolesDBList(Collection c)
	{
		super(c);
	}

	public UserRolesDBList(Operation operation)
	{
		super(operation);
	}
}
