package com.shipment.track.location.service.utils.enums;

import com.shipment.track.location.service.customs.CustomLocationCollector;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;

public enum OsmQueryParams {
    FORMAT("format","json"),
    ADDRESS_TYPE("addressDetails","1"),
    LIMIT("limit","10"),
    POLYGON_SVG("polygon_svg","1");

    private String key;
    private String value;

    OsmQueryParams(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey(){
        return this.key;
    }

    public String getValue(){
        return this.value;
    }

    public static MultiValueMap<String,String> getValuesInMap(){
        return Arrays.stream(OsmQueryParams.values()).collect(CustomLocationCollector.toOsmValueMap());
    }

}
