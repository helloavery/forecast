package com.itavery.forecast.domain.mithra.user;
import java.sql.Timestamp;
public class UsersDB extends UsersDBAbstract
{
	public UsersDB(Timestamp processingDate
	)
	{
		super(processingDate
		);
		// You must not modify this constructor. Mithra calls this internally.
		// You can call this constructor. You can also add new constructors.
	}

	public UsersDB()
	{
		this(com.gs.fw.common.mithra.util.DefaultInfinityTimestamp.getDefaultInfinity());
	}
}
