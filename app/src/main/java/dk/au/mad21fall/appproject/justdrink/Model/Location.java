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
    public int phoneNumer;
    public String address;
    public int numChekIns;
    public double rating;
    public List<DrinkItems> drinkItems;
}
