package model;

import java.util.Date;

public class Validator {

    // inclusive startDate and endDate
    public static Boolean dateIsWithinRange(Date testDate, Date startDate, Date endDate){
        return testDate.getTime() >= startDate.getTime() && testDate.getTime()<= endDate.getTime();
    }

}
