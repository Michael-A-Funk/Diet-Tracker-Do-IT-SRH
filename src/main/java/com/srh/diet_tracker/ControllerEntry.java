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

        // Date and Time will further be dependent, with an if-clause, from the boolean coming from checkbox "Jetzige Zeit"
        date = LocalDate.now();
        time = LocalTime.now();

        Entry entry = new Entry(isSport,calories,sugar,date,time);
        EntryDAO entryDAO = new EntryDAO(entry);
        entryDAO.insertEntryData();
        System.out.println("Neuer Eintrag wurde gesetzt");
    }

    // waits for event from Button "Löschen".
    public void eliminateLastEntry(){
        EntryDAO entryDAO = new EntryDAO();
        entryDAO.deleteEntry();
        /*int lastId = entryDAO.getLastId();
        System.out.println("Resultat von getLastId" + )*/
//        System.out.println("Letzter Eintrag wurde gelöscht");

    }

}


