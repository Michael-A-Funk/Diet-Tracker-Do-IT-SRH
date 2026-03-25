package com.srh.diet_tracker;

import java.time.LocalDate;
import java.time.LocalTime;



public class ControllerEntry {

    private EntryDAO entryDAO;

    public ControllerEntry() {}

    // What happens when "Bestätigen" Button is clicked !!! Parameters are now just for testing purposes!!!
    public void saveEntry(boolean isSport,double calories, double sugar){

        // Frage : Kann ich es auch außerhalb der methode machen um es nicht zu wiederholen?
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

    public void updateLastEntry(boolean isSport, double calories, double sugar){

        // Frage : Kann ich es auch außerhalb der methode machen um es nicht zu wiederholen?
        LocalDate date;
        LocalTime time;

        // Date and Time will further be dependent, with an if-clause, from the boolean coming from checkbox "Jetzige Zeit"
        date = LocalDate.now();
        time = LocalTime.now();


        Entry entry = new Entry(isSport,calories, sugar, date, time);
        EntryDAO entryDAO = new EntryDAO(entry);
        entryDAO.updateEntryData(0);
        System.out.println("Daten von letzten Eintrag wurden aktualisiert");

    }


    // Frage : Macht es Sinn Daten zu löschen oder lasst man User einfach Eintrag Bearbeiten?

    // waits for event from Button "Löschen".
    /*public void eliminateLastEntry(int id){
        EntryDAO entryDAO = new EntryDAO();
        entryDAO.deleteEntry(id);
        /*int lastId = entryDAO.getLastId();
        System.out.println("Resultat von getLastId" + )
        System.out.println("Letzter Eintrag wurde gelöscht");

    }*/

}


