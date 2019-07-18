package com.itavery.forecast.domain.mithra.product;

import com.gs.fw.finder.Operation;

import java.util.Collection;
public class ProductForecastDBList extends ProductForecastDBListAbstract
{
	public ProductForecastDBList()
	{
		super();
	}

	public ProductForecastDBList(int initialSize)
	{
		super(initialSize);
	}

	public ProductForecastDBList(Collection c)
	{
		super(c);
	}

	public ProductForecastDBList(Operation operation)
	{
		super(operation);
	}
}
