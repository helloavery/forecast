package com.itavery.forecast.mithra.sequence;
import com.gs.fw.common.mithra.MithraSequence;

public class SequenceDB extends SequenceDBAbstract implements MithraSequence
{
	public SequenceDB()
	{
		super();
		// You must not modify this constructor. Mithra calls this internally.
		// You can call this constructor. You can also add new constructors.
	}

	public long getNextId(){
		return this.getNextValue();
	}

	public void setNextId(long nextValue){
		this.setNextValue(nextValue);
	}
}
