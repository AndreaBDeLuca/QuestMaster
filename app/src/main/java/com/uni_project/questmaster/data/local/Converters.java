
package com.uni_project.questmaster.data.local;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static List<String> fromString(String value) {
        return value == null ? null : Arrays.asList(value.split(","));
    }

    @TypeConverter
    public static String fromList(List<String> list) {
        if (list == null) {
            return null;
        }
        StringBuilder string = new StringBuilder();
        for (String s : list) {
            string.append(s).append(",");
        }
        if (string.length() > 0) {
            string.deleteCharAt(string.length() - 1);
        }
        return string.toString();
    }
}
