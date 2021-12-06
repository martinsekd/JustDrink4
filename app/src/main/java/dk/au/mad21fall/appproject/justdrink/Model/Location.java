package dk.au.mad21fall.appproject.justdrink.Model;

import java.util.ArrayList;
import java.util.List;

import dk.au.mad21fall.appproject.justdrink.Model.DrinkItems;
import dk.au.mad21fall.appproject.justdrink.Model.OpenHours;

public class Location {
    public int id;
    public double lat;
    public double long1;
    public String name;
    public List<Day> openhours = new ArrayList<Day>(7);
    public int phoneNumber;
    public String address;
    public int numChekIns;
    public double rating;
    public List<DrinkItems> drinkItems;

    public String getName(){
        return name;
    }
    public String getAddress() {
        return address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

}

