package dk.au.mad21fall.appproject.justdrink.Model;

import java.util.List;

import dk.au.mad21fall.appproject.justdrink.Model.DrinkItems;
import dk.au.mad21fall.appproject.justdrink.Model.OpenHours;

public class Location {
    int id;
    double lat;
    double long1;
    String name;
    Day[] openhours = new Day[7];
    int phoneNumer;
    String address;
    int numChekIns;
    double rating;
    List<DrinkItems> drinkItems;
}
