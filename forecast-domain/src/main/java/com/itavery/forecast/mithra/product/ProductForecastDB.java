package com.itavery.forecast.mithra.product;
import java.sql.Timestamp;
public class ProductForecastDB extends ProductForecastDBAbstract
{
	public ProductForecastDB(Timestamp processingDate
	)
	{
		super(processingDate
		);
		// You must not modify this constructor. Mithra calls this internally.
		// You can call this constructor. You can also add new constructors.
	}

	public ProductForecastDB()
	{
		this(com.gs.fw.common.mithra.util.DefaultInfinityTimestamp.getDefaultInfinity());
	}
}
