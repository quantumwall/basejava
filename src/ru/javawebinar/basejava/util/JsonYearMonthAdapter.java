package ru.javawebinar.basejava.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class JsonYearMonthAdapter implements JsonSerializer<YearMonth>, JsonDeserializer<YearMonth> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");

    @Override
    public JsonElement serialize(YearMonth date, Type type, JsonSerializationContext jsc) {
        return new JsonPrimitive(formatter.format(date));
    }

    @Override
    public YearMonth deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        return YearMonth.parse(je.getAsString(), formatter);
    }

}
