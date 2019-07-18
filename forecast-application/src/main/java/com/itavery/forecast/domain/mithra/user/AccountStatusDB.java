package com.itavery.forecast.domain.mithra.user;
import com.gs.fw.common.mithra.util.DefaultInfinityTimestamp;

import java.sql.Timestamp;
public class AccountStatusDB extends AccountStatusDBAbstract
{
	public AccountStatusDB(Timestamp processingDate
	)
	{
		super(processingDate
		);
		// You must not modify this constructor. Mithra calls this internally.
		// You can call this constructor. You can also add new constructors.
	}

	public AccountStatusDB()
	{
		this(DefaultInfinityTimestamp.getDefaultInfinity());
	}

	public AccountStatusDB(char status, boolean emailVerified, boolean activeAndVerified){
		super(DefaultInfinityTimestamp.getDefaultInfinity());
		this.setStatus(status);
		this.setEmailVerified(emailVerified);
		this.setActiveAndVerified(activeAndVerified);
	}
}
