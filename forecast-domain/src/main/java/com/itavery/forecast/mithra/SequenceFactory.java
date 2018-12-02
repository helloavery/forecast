package com.itavery.forecast.mithra;
 
 /*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  10/16/18            
 |            
 *===========================================================================*/

import com.gs.fw.common.mithra.MithraSequence;
import com.gs.fw.common.mithra.MithraSequenceObjectFactory;
import com.gs.fw.common.mithra.finder.Operation;
import com.itavery.forecast.mithra.sequence.SequenceDB;
import com.itavery.forecast.mithra.sequence.SequenceDBFinder;

public class SequenceFactory implements MithraSequenceObjectFactory {

    public MithraSequence getMithraSequenceObject(String sequenceName, Object sourceAttribute, int initialValue) {
        Operation op = SequenceDBFinder.sequenceName().eq(sequenceName);
        SequenceDB sequence = SequenceDBFinder.findOne(op);
        if (sequence == null) {
            sequence = new SequenceDB();
            sequence.setSequenceName(sequenceName);
            sequence.setNextId(initialValue);
            sequence.insert();
        }
        return sequence;
    }
}
