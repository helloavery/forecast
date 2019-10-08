package com.itavery.forecast.domain.util;

import com.averygrimes.nexus.pojo.BaseHistory;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

/**
 * @author Avery Grimes-Farrow
 * Created on: 10/7/19
 * https://github.com/helloavery
 */

public class DataVersioningUtil {

    private static final Logger LOGGER = LogManager.getLogger(DataVersioningUtil.class);
    private static final String UTC = "UTC";

    public static DBObject createDBObjectHistory(Object currentObject, Object oldObject) {
        BaseHistory baseHistory = createBaseHistory(currentObject, oldObject);
        return toDBObject(baseHistory);
    }


    private static BaseHistory createBaseHistory(Object currentObject, Object oldObject){
        BaseHistory baseHistory = new BaseHistory();
        Map<String, Object> changeSet = new HashMap<>();
        try {
            for (Field field : currentObject.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value1 = field.get(currentObject);
                Object value2 = field.get(oldObject);
                if (value1 != null && value2 != null) {
                    if (!Objects.equals(value1, value2)) {
                        changeSet.put(field.getName(), value1);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            LOGGER.error("");
        }
        if(MapUtils.isNotEmpty(changeSet)){
            changeSet.put("dateModified", parseLongToUTC(System.currentTimeMillis()));
            baseHistory.setChanges(changeSet);
        }
        return baseHistory;
    }

    private static String parseLongToUTC(long dateToParse){
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.setTimeZone(TimeZone.getTimeZone(UTC));
        return sdf.format(new Date(dateToParse));
    }

    private static DBObject toDBObject(BaseHistory baseHistory){
        return new BasicDBObject().append("changes", baseHistory);
    }
}
