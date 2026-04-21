#  Ernährungstracker – SRH Do-IT-Phase 2026

Eine lokal laufende Java-Desktopanwendung zur Erfassung und Auswertung von Kalorien- und Zuckeraufnahme sowie sportlichen Aktivitäten.

Entwickelt im Rahmen der **Do-IT-Phase 2026** an der **SRH Beruflichen Rehabilitation Heidelberg**.

---

##  Projektübersicht

| Feld | Wert |
|---|---|
| Verfasser | Michael Funk |
| Geplante Stunden | 175 h |

---

##  Features

- **Einträge erfassen** – Mahlzeiten und Sportaktivitäten mit Kalorien- und Zuckerwerten
- **Tagesübersicht** – Alle Einträge eines beliebigen Tages anzeigen, bearbeiten und löschen
- **BMR-Berechnung** – Persönliches Tageslimit nach der Harris-Benedict-Formel
- **Fortschrittsanzeige** – Kalorien- und Zuckeranteil prozentual zum Tageslimit
- **Statistik & Graphen** – Liniendiagramm über frei wählbare Zeiträume (absolut oder prozentual)
- **Benutzerprofil** – Körperdaten (Größe, Gewicht, Alter, Geschlecht, Diabetes) verwalten
- **Lokale Datenhaltung** – Vollständig offline, keine Internetverbindung erforderlich

---

##  Tech-Stack

| Komponente | Version |
|---|---|
| Java (Compiler) | 23 |
| JavaFX Controls + FXML | 21.0.6 |
| SQLite JDBC (xerial) | 3.50.3.0 |
| JUnit Jupiter API | 5.12.1 |
| Build-Tool | Apache Maven |

---

##  Architektur

Die Anwendung folgt dem **MVC-Muster** mit klarer Schichtentrennung:

- **Model** – `Entry`, `User`, `TableRow`: Datenklassen
- **View** – 5 FXML-Layoutdateien (JavaFX)
- **Controller** – `ControllerEntry`, `ControllerDayReview`, `ControllerGraph`, `ControllerUser`, `ControllerHomepage`
- **Datenschicht (DAO)** – `EntryDAO`, `UserDAO`, `DbManager`
- **Infrastruktur** – `SceneManager` (Singleton), `SceneType` (Enum), `App`, `Launcher`

---

## ️ Ansichten

| FXML-Datei | Beschreibung |
|---|---|
| `homepage.fxml` | Startseite mit 4 Navigationsbuttons |
| `add_calories_sugar.fxml` | Eintrag erstellen oder letzten bearbeiten |
| `day_review.fxml` | Tagesübersicht mit Tabelle, Bearbeitung, Löschung |
| `user_profile.fxml` | Benutzerprofil und BMR-Anzeige |
| `graph.fxml` | Liniendiagramm mit Statistikoptionen |

---

##  Voraussetzungen & Starten

**Voraussetzungen:**
- Java 23 (JDK)
- Maven (oder den mitgelieferten Maven Wrapper `mvnw` verwenden)

**Anwendung starten:**

```bash
# Mit Maven Wrapper (Windows)
mvnw.cmd javafx:run

# Mit Maven Wrapper (Linux/macOS)
./mvnw javafx:run
```

Die SQLite-Datenbank `diet.db` wird automatisch beim ersten Start im Arbeitsverzeichnis angelegt.

---

##  Projektstruktur

```
Diet-Tracker-Do-IT-SRH/
├── src/main/
│   ├── java/          # Java-Quellcode (Model, Controller, DAO, Infrastruktur)
│   └── resources/     # FXML-Layoutdateien
├── diet.db            # SQLite-Datenbank (wird automatisch erstellt)
├── pom.xml            # Maven-Konfiguration
└── mvnw / mvnw.cmd    # Maven Wrapper
```

---

##  Datenbankstruktur

**Tabelle `entry`** – Ernährungs- und Sporteinträge

| Spalte | Typ | Beschreibung |
|---|---|---|
| id | INTEGER PRIMARY KEY | Automatischer Primärschlüssel |
| activity | TEXT NOT NULL | `'meal'` oder `'sport'` |
| calories | REAL NOT NULL | Kaloriengehalt in kcal |
| sugar | REAL NOT NULL | Zuckergehalt in g (bei Sport = 0) |
| date | TEXT NOT NULL | Format: `yyyy-MM-dd` |
| time | TEXT NOT NULL | Format: `HH:mm:ss` |

**Tabelle `user`** – Benutzerprofil (Einzelnutzer, id=1)

| Spalte | Typ | Beschreibung |
|---|---|---|
| id | INTEGER PRIMARY KEY | Immer 1 |
| height | INTEGER | Körpergröße in cm |
| age | INTEGER | Alter in Jahren |
| weight | INTEGER | Gewicht in kg |
| isMale | BOOLEAN | `true` = männlich |
| hasDiabetes | BOOLEAN | `true` = Diabetiker |

---

## Bekannte Schwächen (v2.0)

**Code-Qualität:**
- Null-Rückgaben statt `Optional<>` in EntryDAO
- `setFieldsByEntryNr()` hat 11 Parameter (Refactoring empfohlen)
- `@FXML`-Felder teilweise doppelt initialisiert
- Gemischte Kommentarsprache (Deutsch/Englisch)

**Testabdeckung:**
- Keine Unit-Tests implementiert (JUnit Jupiter ist konfiguriert)

**UX:**
- Benutzerprofil zeigt keine gespeicherten Werte beim Öffnen
- Kein visuelles Feedback im DatePicker der Tagesübersicht
- Zeitachse im Graphen nicht proportional (CategoryAxis)

---

## Geplante Erweiterungen

- Unit-Tests für `EntryDAO` und `User.getBMR()`
- Null-Rückgaben durch `Optional<>` ersetzen
- Automatische Warnmeldungen bei 50 % / 75 % des Tageslimits
- PDF-Export der Graphen und Einträge
- Mobile Version (Android/Gradle)
- Einbindung einer Lebensmitteldatenbank für automatische Nährwertvorschläge

---

##  Lizenz

Dieses Projekt wurde im Rahmen eines Bildungsprojekts (SRH Do-IT-Phase 2026) erstellt, für die Umschullung "Fachinformatiker Anwendungsentwicklung".

---

*Ernährungstracker | Michael Funk | SRH Do-IT-Phase 2026*
