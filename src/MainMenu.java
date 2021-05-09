
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MainMenu {
    public Map<String, String> mainMenuOptions = new HashMap();
    static final Scanner input = new Scanner(System.in);
    HotelResource hotelResource = HotelResource.getInstance();

    private MainMenu(){
        super();
        this.mainMenuOptions.put("1","Find and Reserve a room");
        this.mainMenuOptions.put("2","See my reservations");
        this.mainMenuOptions.put("3","Create an account");
        this.mainMenuOptions.put("4","Admin");
        this.mainMenuOptions.put("5","Exit");
    }

    private void displayMenu(){
        System.out.println("Welcome to the Hotel Reservation Application");
        System.out.println("____________________________________________");
        this.mainMenuOptions.forEach((key,option) ->{
            System.out.println(key+". "+option);
        });
        System.out.println("____________________________________________");
        System.out.println("Please select a number from the menu option");
    }

    // returns false if there there was an invalid date entered by the user
    // true otherwise
    private boolean findAndReserveARoom() {
        final String datePattern = "MM/dd/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
        Calendar cal = Calendar.getInstance();
        Date today = null;
        IRoom bookRoom = null;
        Map availableRooms = new HashMap<String,IRoom>();
        Customer customer = null;
        try {
            today = dateFormat.parse(dateFormat.format(cal.getTime()));
        } catch(ParseException e){
            e.printStackTrace();
        }

        System.out.println("Find and Reserve a Room");
        System.out.println("____________________________________________");
        System.out.println("Enter CheckIn Date DD/MM/YYYY' for example 27/07/2021 ");
        Date checkinDate = Validator.validateAndParseDate(input.next().trim(),datePattern);
        if(checkinDate.before(today)){
            System.out.println("CheckIn date has to be today or after.");
            return false;
        }
        System.out.println("Enter CheckOut Date  DD/MM/YYYY' for example 27/07/2021 ");
        Date checkoutDate = Validator.validateAndParseDate(input.next().trim(),datePattern);
        if(!checkoutDate.after(checkinDate)){
            System.out.println("Enter Checkout date has to be after checkin Date.");
            return false;
        }
        availableRooms = (Map) roomsAvailableForReservation(checkinDate,checkoutDate);
        if(!availableRooms.isEmpty()){
            //book a room
            System.out.println("Would you like to book a room y/n ?");
            String choice  = input.next().trim().toLowerCase();
            if (choice == "y") {
                // get customer account
                input.nextLine();
                System.out.println("Enter Email format: name@domain.com");
                String email = input.next();
                customer = hotelResource.getCustomer(email);
            }
            while(choice.equals("y") && bookRoom == null) { //!displayRooms.containsKey(bookRoomNumber)
                System.out.println("Please enter a Room Number from the above list : ");
                String bookRoomNumber = input.next().trim();
                if(availableRooms.containsKey(bookRoomNumber)){
                    bookRoom = (IRoom) availableRooms.get(bookRoomNumber);
                } else{
                    System.out.println("Invalid Room Number, Please enter a Room Number from the above list ");
                    System.out.println("Would you like to book a room y/n ?");
                    choice = input.next().trim().toLowerCase();
                }
            }
            hotelResource.bookARoom(customer.getEmail(),bookRoom,checkinDate,checkoutDate);
        }
        return true;
    }
    // display the list of available / suggested rooms and return the one selected by the user
    private Map<String, IRoom> roomsAvailableForReservation(Date checkinDate, Date checkoutDate){
        String bookRoomNumber=null;
        IRoom bookRoom= null;
        String choice ="y";
        Map availableRooms = new HashMap<String, IRoom>();
        Map suggestedRooms = new HashMap<String, IRoom>();
        Map displayRooms = new HashMap<String, IRoom>();

        availableRooms = (Map) hotelResource.findARoom(checkinDate,checkoutDate);
        if(!availableRooms.isEmpty()){
            availableRooms = hotelResource.findARoom(checkinDate,checkoutDate);
        } else {
            suggestedRooms = hotelResource.findARoom(addDaysToDate(checkinDate,7),
                    addDaysToDate(checkoutDate,7));
        }
        if(!suggestedRooms.isEmpty()) {
            System.out.println("No Rooms available for these dates, we have some rooms available for the week.");
            suggestedRooms.forEach((k,room)->{
                System.out.println(room.toString());
            });
            displayRooms = suggestedRooms;
        } else if(!availableRooms.isEmpty()){
            System.out.println("Rooms available for these dates.");
            availableRooms.forEach((k,room)->{
                System.out.println(room.toString());
            });
            displayRooms=availableRooms;
        } else{
            System.out.println("No Rooms available for these dates, we currently don't have any rooms available for the week after either.");
        }

        return displayRooms;
    }

    private Date addDaysToDate(Date oldDate, Integer days) {
        Date newDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cal = Calendar.getInstance();
        //Setting the date to the given date
        cal.setTime(oldDate);
        //Number of Days to add
        cal.add(Calendar.DAY_OF_MONTH, days);

        try{
           newDate = dateFormat.parse(dateFormat.format(cal.getTime()));
        }catch(ParseException e){
            e.printStackTrace();
        }
        return newDate;
    }

    private void viewMyReservations(){
        Collection<Reservation> myReservations = null;
        input.nextLine();
        System.out.println("Enter your email:");
        String email = input.next();

        Customer customer = hotelResource.getCustomer(email);
        if(customer!=null){
           myReservations = hotelResource.getCustomersReservations(email);
            if (!myReservations.isEmpty()) {
                for (Reservation reservation : myReservations) {
                    System.out.println(reservation);
                }
            } else {
                System.out.println("You have no reservations yet");
            }
        }
    }

    private void createAnAccount(){
        String email = "";
        String firstName = "";
        String lastName = "";
        input.nextLine();
        System.out.println("Enter Email:");
        email = input.nextLine().trim();
        System.out.println("First Name:");
        firstName = input.nextLine().trim();
        System.out.println("Last Name:");
        lastName = input.nextLine().trim();
        hotelResource.createACustomer(email,firstName,lastName);
    }

    private void showAdminMenu(){
        AdminMenu adminMenu = new AdminMenu();
        adminMenu.start();
    }

    public void start() {
        boolean quit = false;
        String choice;
        do{
            displayMenu();
            choice = input.next().trim();
            switch(choice){
                case "1":
                    findAndReserveARoom();
                    break;
                case "2":
                    viewMyReservations();
                    break;
                case "3":
                    createAnAccount();
                    break;
                case "4":
                    showAdminMenu();
                    break;
                case "5":
                    quit=true;
                    break;
            }
        }
        while(!quit);
    }


}
