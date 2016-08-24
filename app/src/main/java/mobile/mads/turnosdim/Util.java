package mobile.mads.turnosdim;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mati on 8/23/16.
 */

public class Util {

    private Date dateObj;


    public Date StringToDate(String s){
        SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dateObj = curFormater.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateObj;
    }
    public Date StringToDate(String s, int i){
        SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy kk:mm");
        try {
            dateObj = curFormater.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateObj;
    }

    public String getDay(Date date){
        String s = new SimpleDateFormat("EE").format(date);
        return s;
    }

    public String getNumberDay(Date date){
        String s = new SimpleDateFormat("dd").format(date);
        return s;
    }

    public String getMonth(Date date){
        String s = new SimpleDateFormat("MMM").format(date);
        return s;
    }
}
