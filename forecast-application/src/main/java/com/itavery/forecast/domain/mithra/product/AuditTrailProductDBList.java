package com.itavery.forecast.domain.mithra.product;

import com.gs.fw.finder.Operation;

import java.util.Collection;
public class AuditTrailProductDBList extends AuditTrailProductDBListAbstract
{
	public AuditTrailProductDBList()
	{
		super();
	}

	public AuditTrailProductDBList(int initialSize)
	{
		super(initialSize);
	}

	public AuditTrailProductDBList(Collection c)
	{
		super(c);
	}

	public AuditTrailProductDBList(Operation operation)
	{
		super(operation);
	}
}
