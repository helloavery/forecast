package com.itavery.forecast.mithra;

import com.gs.fw.common.mithra.MithraSequence;
import com.gs.fw.common.mithra.MithraSequenceObjectFactory;
import com.gs.fw.common.mithra.finder.Operation;
import com.itavery.forecast.mithra.sequence.SequenceDB;
import com.itavery.forecast.mithra.sequence.SequenceDBFinder;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-10-16
 * https://github.com/helloavery
 */

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
