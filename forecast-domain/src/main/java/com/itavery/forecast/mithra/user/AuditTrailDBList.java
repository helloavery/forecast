package com.itavery.forecast.mithra.user;

import com.gs.fw.finder.Operation;

import java.util.Collection;
public class AuditTrailDBList extends AuditTrailDBListAbstract
{
	public AuditTrailDBList()
	{
		super();
	}

	public AuditTrailDBList(int initialSize)
	{
		super(initialSize);
	}

	public AuditTrailDBList(Collection c)
	{
		super(c);
	}

	public AuditTrailDBList(Operation operation)
	{
		super(operation);
	}
}
