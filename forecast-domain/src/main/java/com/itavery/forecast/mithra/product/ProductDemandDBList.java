package com.itavery.forecast.mithra.product;

import com.gs.fw.finder.Operation;

import java.util.Collection;
public class ProductDemandDBList extends ProductDemandDBListAbstract
{
	public ProductDemandDBList()
	{
		super();
	}

	public ProductDemandDBList(int initialSize)
	{
		super(initialSize);
	}

	public ProductDemandDBList(Collection c)
	{
		super(c);
	}

	public ProductDemandDBList(Operation operation)
	{
		super(operation);
	}
}
