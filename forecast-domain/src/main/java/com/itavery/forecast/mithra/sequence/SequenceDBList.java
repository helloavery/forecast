package com.itavery.forecast.mithra.sequence;

import com.gs.fw.finder.Operation;

import java.util.Collection;
public class SequenceDBList extends SequenceDBListAbstract
{
	public SequenceDBList()
	{
		super();
	}

	public SequenceDBList(int initialSize)
	{
		super(initialSize);
	}

	public SequenceDBList(Collection c)
	{
		super(c);
	}

	public SequenceDBList(Operation operation)
	{
		super(operation);
	}
}
