package com.shipment.track.location.service.utils;

public class ApiResponsesContants {
    public static final String GET_CO_ORDINATES_API_RESPONSE = """
            [
            	{
            		"place_id": 222480195,
            		"licence": "Data © OpenStreetMap contributors, ODbL 1.0. http://osm.org/copyright",
            		"osm_type": "node",
            		"osm_id": 3411073296,
            		"lat": "27.0377554",
            		"lon": "88.2631760",
            		"class": "place",
            		"type": "city",
            		"place_rank": 16,
            		"importance": 0.5445588346649259,
            		"addresstype": "city",
            		"name": "Darjeeling",
            		"display_name": "Darjeeling, Darjeeling Pulbazar, Darjeeling, West Bengal, 734101, India",
            		"boundingbox": [
            			"26.8777554",
            			"27.1977554",
            			"88.1031760",
            			"88.4231760"
            		],
            		"svg": "cx=\\"88.263176\\" cy=\\"-27.0377554\\""
            	}
            ]
            """;

    public static final String CREATE_OFFICE_LOCATION_API_RESPONSE = """
            {
                "id": {
                    "timestamp": 1772256398,
                    "date": "2026-02-28T05:26:38.000+00:00"
                },
                "officeName": "Malda Hub",
                "officeCountry": "India",
                "officeLocation": "Malda",
                "officeType": "Hub",
                "officeCoordinates": {
                    "x": 88.263176,
                    "y": 27.0377554,
                    "type": "Point",
                    "coordinates": [
                        88.263176,
                        27.0377554
                    ]
                }
            }
            """;
    public static final String GET_OFFICE_LOCATION_API_RESPONSE = """
            [
                {
                    "place_id": 231608479,
                    "licence": "Data © OpenStreetMap contributors, ODbL 1.0. http://osm.org/copyright",
                    "osm_type": "node",
                    "osm_id": 570086096,
                    "lat": "22.7191072",
                    "lon": "88.4826229",
                    "class": "place",
                    "type": "city",
                    "place_rank": 16,
                    "importance": 0.46977615874031897,
                    "addresstype": "city",
                    "name": "Barasat",
                    "display_name": "Barasat, Kolkata Metropolitan Area, Barasat - I, North 24 Parganas, West Bengal, 700124, India",
                    "boundingbox": [
                        "22.5591072",
                        "22.8791072",
                        "88.3226229",
                        "88.6426229"
                    ],
                    "svg": "cx=\\"88.4826229\\" cy=\\"-22.7191072\\""
                },
                {
                    "place_id": 230222013,
                    "licence": "Data © OpenStreetMap contributors, ODbL 1.0. http://osm.org/copyright",
                    "osm_type": "node",
                    "osm_id": 6137147503,
                    "lat": "22.9210061",
                    "lon": "89.6434592",
                    "class": "place",
                    "type": "village",
                    "place_rank": 19,
                    "importance": 0.1467205119401846,
                    "addresstype": "village",
                    "name": "Barasat",
                    "display_name": "Barasat, তেরখাদা উপজেলা, খুলনা জেলা, খুলনা বিভাগ, 9230, বাংলাদেশ",
                    "boundingbox": [
                        "22.9010061",
                        "22.9410061",
                        "89.6234592",
                        "89.6634592"
                    ],
                    "svg": "cx=\\"89.6434592\\" cy=\\"-22.9210061\\""
                }
            ]
            """;

}
