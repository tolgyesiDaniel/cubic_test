package hu.cubicfox.booking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "hotels")
@NamedQuery(name = Hotel.FIND_BY_ROOM_ID, query = "SELECT u.hotelName FROM Hotel u where u.id=:id")
@NamedQuery(name = Hotel.LOCATE_BY_PROXIMITY, query =
        "SELECT u.hotelName, u.hotelGeoLat, u.hotelGeoLong, " +
                "sqrt(POW(u.hotelGeoLat - :geoLat,2) + POW(u.hotelGeoLong - :geoLong,2)) as distance " +
        "FROM Hotel u ORDER BY distance DESC"
)
public class Hotel extends CoreEntity {
    public static final String FIND_BY_ROOM_ID = "Hotel.findByRoomId";
    public static final String LOCATE_BY_PROXIMITY = "Hotel.locateByProximity";

    @Column(name = "hotel_name", nullable = false)
    private String hotelName;

    @Column(name = "hotel_geo_lat", nullable = false)
    private Float hotelGeoLat;

    @Column(name = "hotel_geo_long", nullable = false)
    private Float hotelGeoLong;

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public Float getHotelGeoLat() {
        return hotelGeoLat;
    }

    public void setHotelGeoLat(Float hotelGeoLat) {
        this.hotelGeoLat = hotelGeoLat;
    }

    public Float getHotelGeoLong() {
        return hotelGeoLong;
    }

    public void setHotelGeoLong(Float hotelGeoLong) {
        this.hotelGeoLong = hotelGeoLong;
    }
}
