package com.srh.diet_tracker;

import java.time.LocalDate;
import java.time.LocalTime;



public class ControllerEntry {

    private EntryDAO entryDAO;

    public ControllerEntry() {}

    // What happens when "Bestätigen" Button is clicked !!! Parameters are now just for testing purposes!!!
    public void saveEntry(boolean isSport,double calories, double sugar){



        LocalDate date;
        LocalTime time;

        // Date and Tim will further be dependent, with an if-clause, from the boolean coming from checkbox "Jetzige Zeit"
        date = LocalDate.now();
        time = LocalTime.now();

        Entry entry = new Entry(isSport,calories,sugar,date,time);
        EntryDAO entryDAO = new EntryDAO(entry);
        entryDAO.insertEntryData();
    }
}
