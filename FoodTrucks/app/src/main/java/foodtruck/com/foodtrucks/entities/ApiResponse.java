package foodtruck.com.foodtrucks.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/** This class communicates data from Repository to ViewModel and ultimately to Activity.
If there is an error while fetching data ,will
 set Error in the ApiResponse,else we set the list of result objects into it*/

public class ApiResponse {
    private List<Result> result;
    private Throwable error;

    public ApiResponse(List<Result> result) {
        this.result = result;
        this.error = null;
    }

    public ApiResponse(Throwable error) {
        this.error = error;
        this.result = null;
    }

    public List<Result> getResponseList() {
        if (result != null) {
            List<Result> resultListSorted = new ArrayList<>();
            for (int i = 0; i < result.size(); i++) {
                String startTime =
                    (result.get(i).getStart24().split(":"))[0].equals("24") ? "23:59:00"
                        : result.get(i).getStart24() + ":00";
                String endTime = (result.get(i).getEnd24().split(":"))[0].equals("24") ? "23:59:00"
                    : result.get(i).getEnd24() + ":00";

                if (isNowBetweenDateTime(startTime, endTime)) {
                    resultListSorted.add(result.get(i));
                }
            }
            Collections.sort(resultListSorted, new Comparator<Result>() {
                @Override public int compare(Result obj1, Result obj2) {
                    return obj1.getApplicant().compareTo(obj2.getApplicant());
                }
            });
            result = resultListSorted;
        }
        return result;
    }

    public Throwable getError() {
        return error;
    }

    boolean isNowBetweenDateTime(final String s, final String e) {
        final Date now = new Date();
        return now.after(dateFromHourMinSec(s)) && now.before(dateFromHourMinSec(e));
    }

    private Date dateFromHourMinSec(final String hhmmss) {
        if (hhmmss.matches("([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])")) {
            final String[] hms = hhmmss.split(":");
            final GregorianCalendar gc = new GregorianCalendar();
            gc.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hms[0]));
            gc.set(Calendar.MINUTE, Integer.parseInt(hms[1]));
            return gc.getTime();
        } else {
            throw new IllegalArgumentException(
                hhmmss + " is not a valid time, expecting HH:MM:SS format");
        }
    }
}