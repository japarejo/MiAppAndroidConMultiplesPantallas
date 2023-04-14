package com.example.miaplicacionmultiplespantallas.model;

import androidx.room.TypeConverter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Converters {
    @TypeConverter
    public static LocalDateTime toLocalDateTime(long date){
        return LocalDateTime.ofEpochSecond(date,0, ZoneOffset.UTC);
    }

    @TypeConverter
    public static long fromLocalDateTime(LocalDateTime date){
        return date.toEpochSecond(ZoneOffset.UTC);
    }
}
