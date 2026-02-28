package com.shipment.track.location.service.controller;

import com.shipment.track.location.service.service.OfficeLocationService;
import com.shipment.track.location.service.service.OsmService;
import com.shipment.track.location.service.utils.ApiResponsesContants;
import com.shipment.track.shipment_tracker_pojo.pojo.dto.ClientAddressLocationDto;
import com.shipment.track.shipment_tracker_pojo.pojo.dto.OfficeDto;
import com.shipment.track.shipment_tracker_pojo.pojo.dto.OfficeLocationDto;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;

@RestController
@Tag(name = "Location-Controller",description = "Use this to get location Details from OSM and create office Locations")
public class LocationServiceController {
    private static final Logger LOG = LoggerFactory.getLogger(LocationServiceController.class);
    private final OsmService osmService;
    private final OfficeLocationService officeLocationService;

    public LocationServiceController(OsmService osmService,
                                     OfficeLocationService officeLocationService) {
        this.osmService = osmService;
        this.officeLocationService = officeLocationService;
    }

    private void addValueToMap(String key, String value, MultiValueMap<String, String> map) {
        if (!Objects.isNull(value)) {
            map.put(key, List.of(value));
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Response", content = {
                    @Content(mediaType = "application/json",schema = @Schema(
                            example = ApiResponsesContants.GET_CO_ORDINATES_API_RESPONSE
                    ))
            })
    })
    @GetMapping("/get-location-coordinates")
    public ResponseEntity<?> getLocationCoordinates(@Parameter(description = "Add the location to be searched",required = true)
                                                    @RequestParam(name = "location") String location,
                                                    @Parameter(description = "Add the precision with search is to be done", required = true)
                                                    @RequestParam(name = "addressDetails", required = false) String addressNumber,
                                                    @Parameter(description = "Add the limit to be searched for 1 is most relevant results", required = true)
                                                    @RequestParam(name = "limit", required = false) String limit,
                                                    @Parameter(description = "Add the precise parameter",required = true)
                                                    @RequestParam(name = "polygon_svg", required = false) String polygon_svg) {
        MultiValueMap<String, String> locationQueryParams = new LinkedMultiValueMap<>();
        addValueToMap("q", location, locationQueryParams);
        addValueToMap("format", "json", locationQueryParams);
        addValueToMap("addressDetails", addressNumber, locationQueryParams);
        addValueToMap("limit", limit, locationQueryParams);
        addValueToMap("polygon_svg", polygon_svg, locationQueryParams);
        return ResponseEntity.ok(osmService.getLocationData(locationQueryParams, "query"));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successful Response", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(example = ApiResponsesContants.GET_OFFICE_LOCATION_API_RESPONSE))
            })
    })
    @GetMapping("/register-office")
    public ResponseEntity<?> getDetailsForOfficeRegistry(@RequestBody OfficeDto officeDto) {
        LOG.info("Got request {} to query for Office info to save subsequently", officeDto);
        return ResponseEntity.ok(officeLocationService.possibleOfficeLocations(officeDto));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Response", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(example = ApiResponsesContants.CREATE_OFFICE_LOCATION_API_RESPONSE))}),
            @ApiResponse(responseCode = "200", description = "Successful Response", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(example = ""))})
    })
    @PostMapping("/register-office")
    public ResponseEntity<?> registerOffice (@RequestBody OfficeLocationDto request) {
        LOG.info("Got Request to save office data {}", request);
        return ResponseEntity.ok(officeLocationService.registerOffice(request));
    }

    @PostMapping("/pick-up-parcel-nearby") // return the office location nearest to a point
    public ResponseEntity<?> findOfficeNearBy(@RequestBody ClientAddressLocationDto clientAddressLocationDto){
       LOG.info("Got Request to query for nearby Office to pick up parcels {}",clientAddressLocationDto);
        Flux<Document> nearByOfficeLocation = officeLocationService.findNearByOfficeLocation(clientAddressLocationDto);
        return ResponseEntity.ok(nearByOfficeLocation);
    }


}
