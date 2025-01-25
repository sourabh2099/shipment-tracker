package com.shipment.track.location.service.utils;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.function.Predicate;

import static com.shipment.track.location.service.utils.AppConstants.SUPPORTED_OSM_TYPE;

public class AppUtils {
    public static final Predicate<JsonNode> checkOsmNodeType = jsonData ->
            !jsonData.path(AppConstants.OSM_TYPE).isMissingNode()
                    && SUPPORTED_OSM_TYPE.contains(
                            (jsonData.path(AppConstants.OSM_TYPE).asText())
            );

}
