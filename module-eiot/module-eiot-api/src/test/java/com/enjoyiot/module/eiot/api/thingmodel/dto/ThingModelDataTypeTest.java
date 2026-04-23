package com.enjoyiot.module.eiot.api.thingmodel.dto;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class ThingModelDataTypeTest {

    @Test
    void parsesNumericAndTemporalAliases() {
        ThingModel.DataType intType = new ThingModel.DataType();
        intType.setType("int");
        assertEquals(12, intType.parse("12"));

        ThingModel.DataType longType = new ThingModel.DataType();
        longType.setType("int64");
        assertEquals(1234567890123L, longType.parse("1234567890123"));

        ThingModel.DataType doubleType = new ThingModel.DataType();
        doubleType.setType("double");
        assertEquals(12.5d, doubleType.parse("12.5"));

        ThingModel.DataType datetimeType = new ThingModel.DataType();
        datetimeType.setType("datetime");
        assertEquals("2026-04-21 10:00:00", datetimeType.parse("2026-04-21 10:00:00"));
    }

    @Test
    void parsesObjectAndArrayPayloadsRecursively() {
        ThingModel.DataType doubleType = new ThingModel.DataType();
        doubleType.setType("double");

        ThingModel.Parameter lat = new ThingModel.Parameter();
        lat.setIdentifier("lat");
        lat.setDataType(doubleType);
        ThingModel.Parameter lng = new ThingModel.Parameter();
        lng.setIdentifier("lng");
        lng.setDataType(doubleType);

        ThingModel.DataType objectType = new ThingModel.DataType();
        objectType.setType("object");
        Map<String, Object> objectSpecs = new LinkedHashMap<>();
        objectSpecs.put("properties", List.of(lat, lng));
        objectType.setSpecs(objectSpecs);

        Object parsedObject = objectType.parse("{\"lat\":12.3,\"lng\":45.6}");
        assertInstanceOf(Map.class, parsedObject);
        Map<?, ?> parsedMap = (Map<?, ?>) parsedObject;
        assertEquals(12.3d, parsedMap.get("lat"));
        assertEquals(45.6d, parsedMap.get("lng"));

        ThingModel.DataType arrayType = new ThingModel.DataType();
        arrayType.setType("array");
        arrayType.setSpecs(Map.of("itemType", doubleType));

        Object parsedArray = arrayType.parse("[1.2,2.4,3.6]");
        assertInstanceOf(List.class, parsedArray);
        assertEquals(List.of(1.2d, 2.4d, 3.6d), parsedArray);
    }
}
