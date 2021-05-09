import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Validator {
    // Validate and Parse  Date String -> Date
    public static Date validateAndParseDate(String dateString, String dateFormat){
        Date date = null;
        DateFormat givenDateFormat = new SimpleDateFormat(dateFormat);
        givenDateFormat.setLenient(false);
        try{
            date = givenDateFormat.parse(dateString);
        }catch(ParseException e){
            e.printStackTrace();
        }
        return date;
    }
}
