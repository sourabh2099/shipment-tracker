package com.shipment.track.location.service.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(value = "location_document")
public class LocationDocument {
    @Id
    private ObjectId id;
    private GeoJsonPoint geoData;
    private String name;
    private String displayName;
    private LocalDateTime createdDate;

}
