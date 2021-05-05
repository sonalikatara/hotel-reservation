package model;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {
   private String firstName;
   private String lastName;
   private String email;

    public String getFirstName(){
        return this.firstName;
    }

    public void setFirstName(String fn){
        this.firstName = fn;
    }

    public String getLastName(){
        return this.lastName;
    }

    public void setLastName(String ln){
        this.lastName = ln;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String eml){
        this.email = eml;
    }


    public Customer(String firstName, String lastName, String email){
        super();
        String emailRegex = "^(.+)@(.+).(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        if(!pattern.matcher(email).matches()){
            throw new IllegalArgumentException("Error, Invalid email");
        };
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public String toString(){
        return "Customer " + firstName + "  " +lastName +" has email id " + email;
    }


}
