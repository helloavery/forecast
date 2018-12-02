package com.itavery.forecast.mithra.user;

import com.gs.fw.finder.Operation;

import java.util.Collection;
public class EmailTokenDBList extends EmailTokenDBListAbstract
{
	public EmailTokenDBList()
	{
		super();
	}

	public EmailTokenDBList(int initialSize)
	{
		super(initialSize);
	}

	public EmailTokenDBList(Collection c)
	{
		super(c);
	}

	public EmailTokenDBList(Operation operation)
	{
		super(operation);
	}
}
