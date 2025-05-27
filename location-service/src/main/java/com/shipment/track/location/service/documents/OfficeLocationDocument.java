package com.shipment.track.location.service.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("office-location-document")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfficeLocationDocument {
    @Id
    private ObjectId id;
    private String officeName;
    private String officeCountry;
    private String officeLocation;
    private String officeType;
    private GeoJsonPoint officeCoordinates;
}
