package com.shipment.track.shipment_tracker_pojo.pojo.utlis;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Slf4j
public class AppUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T getNodeData(JsonNode node, Function<JsonNode, T> mapper, String... path) {
        for (String p : path) {
            node = node.path(p);
        }
        return mapper.apply(node);
    }

    public static <T> List<T> getListDataFromNode(JsonNode node, Class<T> classType, String... path) {
        List<T> result = new ArrayList<>();
        for (String p : path) {
            node = node.path(p);
        }
        if (node.isArray()) {
            ArrayNode arrayNode = (ArrayNode) node;
            for (JsonNode jsonNode : arrayNode) {
                result.add(mapper.convertValue(jsonNode, classType));
            }
        }
        return result;
    }
}
