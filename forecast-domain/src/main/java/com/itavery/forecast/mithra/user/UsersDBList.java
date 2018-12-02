package com.itavery.forecast.mithra.user;

import com.gs.fw.finder.Operation;

import java.util.Collection;
public class UsersDBList extends UsersDBListAbstract
{
	public UsersDBList()
	{
		super();
	}

	public UsersDBList(int initialSize)
	{
		super(initialSize);
	}

	public UsersDBList(Collection c)
	{
		super(c);
	}

	public UsersDBList(Operation operation)
	{
		super(operation);
	}
}
