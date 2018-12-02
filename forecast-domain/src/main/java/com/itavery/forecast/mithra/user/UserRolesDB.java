package com.itavery.forecast.mithra.user;
import java.sql.Timestamp;
public class UserRolesDB extends UserRolesDBAbstract
{
	public UserRolesDB(Timestamp processingDate
	)
	{
		super(processingDate
		);
		// You must not modify this constructor. Mithra calls this internally.
		// You can call this constructor. You can also add new constructors.
	}

	public UserRolesDB()
	{
		this(com.gs.fw.common.mithra.util.DefaultInfinityTimestamp.getDefaultInfinity());
	}
}
