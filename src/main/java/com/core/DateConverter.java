package com.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class DateConverter {
    public Date parse(String line){
        var type = classify(line);
        Date date = null;

        switch (type){
            case DDMMYYYY:
                date = convertDDMMYYYY(line);
                break;
            case DDMMYY:
                date = convertDDMMYY(line);
                break;
            case DDMM:
                date = convertDDMM(line);
                break;
            case MIN:
                date = convertMin(line);
                break;
            case HOUR:
                date = convertHour(line);
                break;
            case HOURMIN:
                date = convertHourMin(line);
                break;
            case SEC:
                date = convertSec(line);
                break;
        }

        date = normalize(date, type);

        return isValid(date)? date : null;
    }

    private DateType classify(String line){
        DateType result = null;

        var ddmmyyyy = Pattern.compile("^[0-9]{1,2}\\.[0-9]{1,2}\\.[0-9]{4}$").matcher(line).find();
        var ddmmyy = Pattern.compile("^[0-9]{1,2}\\.[0-9]{1,2}\\.[0-9]{2}$").matcher(line).find();
        var ddmm = Pattern.compile("^[0-9]{1,2}\\.[0-9]{1,2}$").matcher(line).find();
        var min = Pattern.compile("^[0-9]{1,2} min$").matcher(line).find();
        var hour = Pattern.compile("^[0-9]{1,2} hour$").matcher(line).find();
        var hourMin = Pattern.compile("^[0-9]{1,2} hour [0-9]{1,2} min$").matcher(line).find();
        var sec = Pattern.compile("^[0-9]{1,2} sec$").matcher(line).find();

        if(ddmmyyyy && !(ddmmyy || ddmm || min || hour || hourMin || sec))
            result = DateType.DDMMYYYY;
        if(ddmmyy && !(ddmmyyyy || ddmm || min || hour || hourMin || sec))
            result = DateType.DDMMYY;
        if(ddmm && !(ddmmyyyy || ddmmyy || min || hour || hourMin || sec))
            result = DateType.DDMM;
        if(min && !(ddmmyyyy || ddmmyy || ddmm || hour || hourMin || sec))
            result = DateType.MIN;
        if(hour && !(ddmmyyyy || ddmmyy || ddmm || min || hourMin || sec))
            result = DateType.HOUR;
        if(hourMin && !(ddmmyyyy || ddmmyy || ddmm || min || hour || sec))
            result = DateType.HOURMIN;
        if(sec && !(ddmmyyyy || ddmmyy || ddmm || min || hour || hourMin))
            result = DateType.SEC;

        return result;
    }

    private Date convertHourMin(String line){
        Date date = null;

        var sdf = new SimpleDateFormat("HH 'hour' mm 'min'");
        try{
            date = sdf.parse(line);
        } catch (ParseException parseException){

        }

        return date;
    }

    private Date convertHour(String line){
        Date date = null;

        var sdf = new SimpleDateFormat("HH 'hour'");
        try{
            date = sdf.parse(line);
        } catch (ParseException parseException){

        }

        return date;
    }

    private Date convertMin(String line){
        Date date = null;

        var sdf = new SimpleDateFormat("mm 'min'");
        try{
            date = sdf.parse(line);
        } catch (ParseException parseException){

        }

        return date;
    }

    private Date convertDDMM(String line){
        Date date = null;

        var sdf = new SimpleDateFormat("dd.MM");
        try{
            date = sdf.parse(line);
        }catch (ParseException parseException){

        }

        return date;
    }

    private Date convertDDMMYY(String line){
        Date date = null;

        var sdf = new SimpleDateFormat("dd.MM.yy");
        try {
            date = sdf.parse(line);
        } catch (ParseException parseException) {
        }

        return date;
    }

    private Date convertDDMMYYYY(String line){
        Date date = null;

        var sdf = new SimpleDateFormat("dd.MM.yyyy");
        try{
            date = sdf.parse(line);
        } catch (ParseException parseException){

        }

        return date;
    }

    private Date convertSec(String line){
        Date date = null;

        var sdf = new SimpleDateFormat("mm 'sec'");
        try{
            date = sdf.parse(line);
        }catch (ParseException parseException){

        }

        return date;
    }

    private Date normalize(Date date, DateType type){
        Date newDate = new Date(0);
        Date now = new Date();

        switch (type){
            case DDMMYYYY:
            case DDMMYY:
                newDate = date;
                break;
            case DDMM:
                newDate = date;
                newDate.setYear(now.getYear());
                if(newDate.before(now))
                    newDate.setYear(now.getYear() + 1);
                break;
            case MIN:
                newDate = (Date)now.clone();
                newDate.setMinutes(now.getMinutes() + date.getMinutes());
                break;
            case HOUR:
                newDate = (Date)now.clone();
                newDate.setHours(now.getHours() + date.getHours());
                break;
            case HOURMIN:
                newDate = (Date)now.clone();
                newDate.setMinutes(now.getMinutes() + date.getMinutes());
                newDate.setHours(now.getHours() + date.getHours());
                break;
            case SEC:
                newDate = (Date)now.clone();
                newDate.setSeconds(now.getSeconds() + date.getSeconds());
                break;
        }

        return newDate;
    }

    private boolean isValid(Date next){
        var now = new Date();
        var nowPlusYear = new Date();
        nowPlusYear.setYear(now.getYear() + 1);

        if(next.before(now))
            return false;
        if(Math.abs(now.getTime() - next.getTime()) > Math.abs(nowPlusYear.getTime() - now.getTime()))
            return false;

        return true;
    }
}