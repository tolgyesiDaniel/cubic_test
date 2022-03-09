package hu.cubicfox.booking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "hotels")
public class Hotel extends CoreEntity {

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
