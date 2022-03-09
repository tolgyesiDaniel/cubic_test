package hu.cubicfox.booking.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import hu.cubicfox.booking.entity.Hotel;
import hu.cubicfox.booking.entity.Room;
import hu.cubicfox.booking.service.HotelService;
import hu.cubicfox.booking.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Route(value = "/hotels", layout = MainLayout.class)
@PageTitle("Hotels")
public class HotelView extends VerticalLayout {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RoomService roomService;

    private Hotel hotel;
    private final static String FIELD_NAME = "hotel_name";

    private HorizontalLayout container = new HorizontalLayout();
    private Dialog dialogRoom = new Dialog();
    private VerticalLayout form;
    private Binder<Hotel> binder;
    private TextField hotelName;
    private TextField filter = new TextField();
    private Grid<Hotel> grid = new Grid<>();
    private Button closeBtn = new Button("Close");

    @PostConstruct
    public void init(){
        add(new H3("Hotels"));
        grid.setItems(hotelService.findByName(filter.getValue(), FIELD_NAME));

        Grid.Column<Hotel> nNameColumn = grid.addColumn(Hotel::getHotelName).setHeader("Hotel name");
        grid.addColumn(Hotel::getHotelGeoLat).setHeader("Hotel latitude");
        grid.addColumn(Hotel::getHotelGeoLong).setHeader("Hotel longitude");

        GridSortOrder<Hotel> order = new GridSortOrder<>(nNameColumn, SortDirection.DESCENDING);
        grid.sort(Arrays.asList(order));

        grid.asSingleSelect().addValueChangeListener(event -> {
            hotel = event.getValue();
            roomDialog(hotel);
            dialogRoom.open();
        });

        closeBtn.addClickListener(buttonClickEvent -> {
            dialogRoom.close();
            UI.getCurrent().getPage().reload();
        });

        grid.setWidth("100%");
        filter.setPlaceholder("Search ...");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(event -> refreshGrid(filter.getValue()));

        container.setWidth("100%");
        container.add(grid);
        add(filter);
        add(container);

    }

    public void roomDialog(Hotel hotel){
        List<Room> roomList = roomService.findByKey(hotel.getId(), "hotel_room_id");

        closeBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);
        Label lb = new Label(hotel.getHotelName() + "'s rooms");

        Grid<Room> roomListGrid = new Grid<>();
        roomListGrid.setItems(roomList);
        roomListGrid.addColumn(Room::getRoomNumber).setHeader("Room number");
        roomListGrid.addColumn(Room::getRoomPrice).setHeader("Room price");

        lb.setWidth("100%");
        lb.getElement().getStyle().set("margin-bottom", "15px").set("padding-top", "15px").set("font-size", "18px");
        dialogRoom.setWidth("60%");
        roomListGrid.setWidth("100%");
        closeBtn.setWidth("100%");

        dialogRoom.add(lb);
        dialogRoom.add(roomListGrid);
        dialogRoom.add(closeBtn);
    }

    private void refreshGrid(String value){ grid.setItems(hotelService.findByName(value, FIELD_NAME)); }

}
