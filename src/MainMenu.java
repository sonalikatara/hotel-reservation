
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class MainMenu {
    public Map<String, String> mainMenuOptions = new HashMap();
    static final Scanner input = new Scanner(System.in);
    HotelResource hotelResource = HotelResource.getInstance();

    public MainMenu(){
        super();
        this.mainMenuOptions.put("1","Find and Reserve a room");
        this.mainMenuOptions.put("2","See my reservations");
        this.mainMenuOptions.put("3","Create an account");
        this.mainMenuOptions.put("4","Admin");
        this.mainMenuOptions.put("5","Exit");
    }

    private void displayMenu(){
        System.out.println("\n Welcome to the Hotel Reservation Application");
        System.out.println("____________________________________________");
        this.mainMenuOptions.forEach((key,option) ->{
            System.out.println(key+". "+option);
        });
        System.out.println("____________________________________________");
        System.out.println("Please select a number from the menu option  ");
    }

    // returns false if there there was an invalid date entered by the user
    // true otherwise
    private void findAndReserveARoom() throws Exception{
        final String datePattern = "MM/dd/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
        Calendar cal = Calendar.getInstance();
        Date today = null;
        IRoom bookRoom = null;
        Map availableRooms = new HashMap<String, IRoom>();
        Customer customer = null;
        String email = "";
        String choice = "";
        String checkInDate="",checkOutDate="";
        try {
            today = dateFormat.parse(dateFormat.format(cal.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("Find and Reserve a Room");
        System.out.println("____________________________________________");
        boolean validDate = false;
        String validDateRegex=  "^[0-10][0-9]/[0-3][0-9]/(?:[0-9][0-9])?[0-9][0-9]$";
        Pattern patternDate = Pattern.compile(validDateRegex);
        while(!validDate) {
            System.out.println("Enter CheckIn Date mm/dd/yyyy' for example 07/27/2021  ");
            checkInDate = input.next().trim();
            validDate = patternDate.matcher(checkInDate).matches();
            if(!validDate) System.out.println("Invalid Date ! ");
        }
        Date checkinDate = Validator.validateAndParseDate(checkInDate, datePattern);
        if (checkinDate.before(today)) {
            System.out.println("CheckIn date has to be today or after.");
        } else {
            validDate = false;
            while(!validDate) {
                System.out.println("Enter CheckOut Date mm/dd/yyyy' for example 07/27/2021  ");
                checkOutDate = input.next().trim();
                validDate = patternDate.matcher(checkOutDate).matches();
                if(!validDate) System.out.println("Invalid Date ! ");
            }
            Date checkoutDate = Validator.validateAndParseDate(checkOutDate, datePattern);

            if (!checkoutDate.after(checkinDate)) {
                System.out.println("Checkout date has to be after checkIn Date. Please try again! ");
            } else {
                availableRooms = (Map) roomsAvailableForReservation(checkinDate, checkoutDate);
                if (!availableRooms.isEmpty()) {
                    //book a room
                    boolean invalidChoice = true;
                    while(invalidChoice) {
                        System.out.println("Would you like to book a room y/n ?");
                        choice = input.next().trim().toLowerCase();
                        if (choice.equals("y")) {
                            System.out.println("Do you have an account with us y/n ?");
                            choice = input.next().trim().toLowerCase();
                        }

                        if (!choice.equals("y") && !choice.equals("n")) {
                            System.out.println("Please type y (yes) or n (no). ");
                        }
                        else{
                            invalidChoice = false;
                        }
                    }
                    if(choice.equals("n")){
                        // the customer has no account with us
                        System.out.println("Please create an account with using the option 3 in Main Menu!");
                    }
                    if (choice.equals("y")) {
                        // get customer account
                        input.nextLine();
                        System.out.println("Enter Email format: name@domain.com");
                        email = input.next();
                        customer = hotelResource.getCustomer(email);
                    }

                    if (choice.equals('Y') && customer == null) {
                        System.out.println("Please create an account with to make reservation. ");
                    } else {
                        while (choice.equals("y") && bookRoom == null) { //!displayRooms.containsKey(bookRoomNumber)
                            System.out.println("What Room ( Room Number ) would you like to reserve : ");
                            String bookRoomNumber = input.next().trim();
                            if (availableRooms.containsKey(bookRoomNumber)) {
                                bookRoom = (IRoom) availableRooms.get(bookRoomNumber);
                            } else {
                                System.out.println("Invalid Room Number, Please enter a Room Number from the above list ");
                                System.out.println("Would you like to book a room y/n ?");
                                choice = input.next().trim().toLowerCase();

                            }
                        }
                        if(bookRoom != null) {
                            hotelResource.bookARoom(email, bookRoom, checkinDate, checkoutDate);
                            System.out.println("Your room is booked ! \n ");
                        }
                    }
                }
            }
        }
    }
    // display the list of available / suggested rooms and return the one selected by the user
    private Map<String, IRoom> roomsAvailableForReservation(Date checkinDate, Date checkoutDate){
        String bookRoomNumber=null;
        IRoom bookRoom= null;
       // String choice ="y";
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
        try {
            Customer customer = hotelResource.getCustomer(email);
            if (customer != null) {
                myReservations = hotelResource.getCustomersReservations(email);
                if (!myReservations.isEmpty()) {
                    for (Reservation reservation : myReservations) {
                        System.out.println(reservation);
                    }
                } else {
                    System.out.println("You have no reservations yet");
                }
            } else {
                System.out.println("You have no account with us yet. Please create an account to make reservations.");
            }
        } catch(IllegalArgumentException e){
            System.out.println("Invalid email, please try again");
        }
    }

    private void createAnAccount(){
        String email = "";
        String firstName = "";
        String lastName = "";
        Boolean validEmail = false;
        input.nextLine();
        while(!validEmail) {
            System.out.println("Enter Email format:  name@domain.com : ");
            email = input.nextLine().trim();
            String emailRegex = "^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";

            Pattern pattern = Pattern.compile(emailRegex);
            if (pattern.matcher(email).matches()) {
                validEmail = true;
            } else{
                System.out.println("Error, Invalid email");
            }
        }
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
            try{
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
                    default:
                        System.out.println("Invalid Option. Please try again!");
                }
            } catch(Exception e){
                System.out.println("Invalid Input. Please try again! ");
                input.nextLine();
            }

        }
        while(!quit);
    }


}
