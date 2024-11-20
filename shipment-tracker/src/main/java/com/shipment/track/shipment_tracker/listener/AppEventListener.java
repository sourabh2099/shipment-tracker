package com.shipment.track.shipment_tracker.listener;

import com.shipment.track.shipment_tracker.enums.AddressType;
import com.shipment.track.shipment_tracker.enums.DeliveryStatus;
import com.shipment.track.shipment_tracker.enums.ShipmentStatus;
import com.shipment.track.shipment_tracker.events.CreateShipmentsForUserEvent;
import com.shipment.track.shipment_tracker.model.*;
import com.shipment.track.shipment_tracker.model.Package;
import com.shipment.track.shipment_tracker.repository.PackageRepository;
import com.shipment.track.shipment_tracker.repository.ShipmentRepository;
import com.shipment.track.shipment_tracker.repository.TrackingDetailsRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AppEventListener {
    private static final Logger LOG = LoggerFactory.getLogger(AppEventListener.class);
    private static final String addressOptionString = "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...";
    private static String[] addressWords;
    private final ShipmentRepository shipmentRepository;
    private final PackageRepository packageRepository;
    private final TrackingDetailsRepository trackingDetailsRepository;

    static {
        addressWords = addressOptionString.split(" ");
    }

    public AppEventListener(ShipmentRepository shipmentRepository,
                            PackageRepository packageRepository,
                            TrackingDetailsRepository trackingDetailsRepository) {
        this.shipmentRepository = shipmentRepository;
        this.packageRepository = packageRepository;
        this.trackingDetailsRepository = trackingDetailsRepository;
    }

    private String randomAddressGenerator() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < addressWords.length; i++) {
            sb.append(addressWords[(int) (addressWords.length * Math.random()) % addressWords.length]);
        }
        return sb.toString();
    }

    @Async
    @EventListener
    public void createShipmentsForUser(CreateShipmentsForUserEvent createShipmentsForUserEvent) {
        User user = createShipmentsForUserEvent.getUser();
        LOG.info("Obtained request to create shipment for user: {} with used-id: {}", user.getName(), user.getId());
        // create 2 entry of Shipment containing 5 packages each.
        createShipmentAndPackages(user);
    }

    @Transactional
    private void createShipmentAndPackages(User user) {
        for (int i = 0; i < 2; i++) {
            Shipment shipment = new Shipment();

            shipment.setDestinationAddress(new Address()
                    .setAddressLine1(randomAddressGenerator())
                    .setAddressLine2(randomAddressGenerator())
                    .setPincode(700051L)
                    .setState("West Bengal")
            );
            shipment.setOriginAddress(new Address()
                    .setAddressLine1(randomAddressGenerator())
                    .setAddressLine2(randomAddressGenerator())
                    .setPincode(700432L)
                    .setState("West Bengal")
            );
            shipment.setShipmentStatus(ShipmentStatus.PENDING);
            shipment.setUser(user);
            shipmentRepository.save(shipment);
            //  create tracking details with CREATED as status.
            TrackingDetails trackingDetails = new TrackingDetails();
            trackingDetails.setShipment(shipment);
            trackingDetails.setLocation("INDIA");
            trackingDetails.setDeliveryStatus(DeliveryStatus.READY_TO_BE_SHIPPED);
            trackingDetailsRepository.save(trackingDetails);
            for (int j = 0; j < 5; j++) {
                Package pack = new Package();
                pack.setShipment(shipment);
                pack.setPackageType("PARCEL");
                pack.setWeight(10.00f);
                pack.setPackageValue("10000.00");
                packageRepository.save(pack);
            }
        }
    }
}
