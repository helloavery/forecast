package com.itavery.forecast.domain.mithra.product;
import java.sql.Timestamp;
public class ProductDemandDB extends ProductDemandDBAbstract
{
	public ProductDemandDB(Timestamp processingDate
	)
	{
		super(processingDate
		);
		// You must not modify this constructor. Mithra calls this internally.
		// You can call this constructor. You can also add new constructors.
	}

	public ProductDemandDB()
	{
		this(com.gs.fw.common.mithra.util.DefaultInfinityTimestamp.getDefaultInfinity());
	}
}
