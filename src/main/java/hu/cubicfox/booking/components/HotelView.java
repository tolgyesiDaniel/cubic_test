package hu.cubicfox.booking.components;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import hu.cubicfox.booking.entity.Hotel;
import hu.cubicfox.booking.entity.Room;
import hu.cubicfox.booking.entity.User;
import hu.cubicfox.booking.entity.UserRoom;
import hu.cubicfox.booking.service.HotelService;
import hu.cubicfox.booking.service.RoomService;
import hu.cubicfox.booking.service.UserRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Route(value = "/hotels", layout = MainLayout.class)
@PageTitle("Hotels")
public class HotelView extends VerticalLayout {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserRoomService userRoomService;

    private Hotel hotel;
    private final static String FIELD_NAME = "hotel_name";

    private HorizontalLayout container = new HorizontalLayout();
    private Dialog dialogHotel = new Dialog();
    private Dialog dialogRoom = new Dialog();
    private TextField filter = new TextField();
    private Grid<Hotel> grid = new Grid<>();

    private Button closeHotelBtn = new Button("Close");
    private Button closeRoomBtn = new Button("Close");
    private Button reserveBtn = new Button("Reserve");

    Notification errorNotification = new Notification();
    Notification successNotification = new Notification();

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
            hotelDialog(hotel);
            dialogHotel.open();
        });

        closeHotelBtn.addClickListener(buttonClickEvent -> {
            dialogHotel.close();
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

    public void hotelDialog(Hotel hotel){
        List<Room> roomList = roomService.findByKey(hotel.getId(), "hotel_room_id");

        closeHotelBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);
        Label lb = new Label(hotel.getHotelName() + " rooms");

        Grid<Room> roomListGrid = new Grid<>();
        roomListGrid.setItems(roomList);
        roomListGrid.addColumn(Room::getRoomNumber).setHeader("Room number");
        roomListGrid.addColumn(Room::getRoomPrice).setHeader("Room price");

        roomListGrid.asSingleSelect().addValueChangeListener(event -> {
            Room room = event.getValue();
            roomDialog(room);
            dialogRoom.open();
        });

        lb.setWidth("100%");
        lb.getElement().getStyle().set("margin-bottom", "15px").set("padding-top", "15px").set("font-size", "18px");
        dialogHotel.setWidth("60%");
        roomListGrid.setWidth("100%");
        closeHotelBtn.setWidth("100%");

        closeHotelBtn.addClickListener(buttonClickEvent -> {
            dialogHotel.close();
        });

        dialogHotel.add(lb);
        dialogHotel.add(roomListGrid);
        dialogHotel.add(closeHotelBtn);
    }

    public void roomDialog(Room room){
        closeRoomBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);
        reserveBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        DatePicker startDate = new DatePicker("Start date");
        DatePicker endDate = new DatePicker("End date");

        startDate.addValueChangeListener(e -> endDate.setMin(e.getValue()));
        endDate.addValueChangeListener(e -> startDate.setMax(e.getValue()));

        closeRoomBtn.addClickListener(buttonClickEvent -> {
            dialogRoom.close();
        });

        reserveBtn.addClickListener(event -> {
            List<UserRoom> reserved = userRoomService.findByDateRange(room.getId(), startDate.getValue(), endDate.getValue(), "id", "res_date");
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = (User)auth.getPrincipal();

            if (reserved.isEmpty()){
                List<LocalDate> dates = getDates(startDate.getValue(), endDate.getValue());

                for (LocalDate date : dates){
                    UserRoom userRoom = new UserRoom();
                    userRoom.setRoomId(room.getId());
                    userRoom.setUserId(user.getId());
                    userRoom.setResDate(date);

                    userRoomService.add(userRoom);
                }
                notifier(false);
                dialogRoom.close();
            }
            else{
                notifier(true);
            }
        });

        dialogRoom.setWidth("30%");
        closeRoomBtn.setWidth("100%");
        startDate.getElement().getStyle().set("margin-right", "15px");
        endDate.getElement().getStyle().set("margin-right", "15px");
        dialogRoom.add(startDate, endDate);
        dialogRoom.add(reserveBtn);
        dialogRoom.add(closeRoomBtn);
    }

    public static List<LocalDate> getDates(LocalDate start, LocalDate end){
        return start.datesUntil(end).collect(Collectors.toList());
    }

    public void notifier(boolean error){
        if (error){
            errorNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            successNotification.close();
            Div text = new Div(new Text("This date range is already reserved!"));

            Button closeEButton = new Button(new Icon("lumo", "cross"));
            closeEButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
            closeEButton.getElement().setAttribute("aria-label", "Close");
            closeEButton.addClickListener(e -> {
                errorNotification.close();
            });

            HorizontalLayout layout = new HorizontalLayout(text, closeEButton);
            layout.setAlignItems(Alignment.CENTER);
            errorNotification.add(layout);
            errorNotification.open();
        }
        else{
            successNotification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            errorNotification.close();
            Div text = new Div(new Text("Reservation successful!"));

            Button closeSButton = new Button(new Icon("lumo", "cross"));
            closeSButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
            closeSButton.getElement().setAttribute("aria-label", "Close");
            closeSButton.addClickListener(e -> {
                successNotification.close();
            });

            HorizontalLayout layout = new HorizontalLayout(text, closeSButton);
            layout.setAlignItems(Alignment.CENTER);
            successNotification.add(layout);
            successNotification.open();
        }
    }

    private void refreshGrid(String value){ grid.setItems(hotelService.findByName(value, FIELD_NAME)); }

}
