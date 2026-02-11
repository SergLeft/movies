Filmbewertungen analysieren
Zur Verfügung steht ein Datensatz (movielens-full) mit 33 Millionen Filmbewertungen von verschiedenen Nutzern. Für weniger leistungsfähige Computer oder zum Testen ineffizienter Algorithmen gibt es auch eine reduzierte Version (movielens-reduced) mit nur einer Million Einträgen. Der Datensatz besteht aus zwei Teilen: ratings und movies.

movies enthält für jeden Film eine eindeutige Film-ID und den Filmtitel sowie eine Liste von Genres.
ratings speichert anonyme Nutzerbewertungen auf einer Skala von 1 bis 5 inklusive des Zeitpunkts der Bewertung.
Beide Teile werden automatisch eingelesen und in entsprechende Scala-Klassen umgewandelt.

Darstellung als Zeichenkette
Ergänzen Sie geeignete toString-Methoden in den Klassen MovieData und RatingData, damit Sie die Objekte in lesbarer Form ausgeben können.

Grundlegende Operationen
Implementieren Sie in der Klasse MovieDataset alle fehlenden Methoden, die nicht mit query beginnen.

Erweiterte Analysen
Implementieren Sie in der Klasse MovieDataset die verbleibenden zwei Methoden.

queryAverageRating soll die durchschnittliche Bewertung für einen gegebenen Film berechnen.
queryTopMovies soll eine festgelegte Anzahl von Filmen mit den höchsten durchschnittlichen Bewertungen zurückgeben. Filme, die eine bestimmte Mindestanzahl von Bewertungen nicht erreichen, sollen dabei ausgeschlossen werden. Sie können in Scala die Methoden sortBy oder sortWith für die Sortierung verwenden.
Indizierung
Fügen Sie der Klasse MovieDataset eine private Map[Long, MovieData], die ein schnelles Nachschlagen von Film-IDs zu MovieData-Objekten ermöglicht.

Fügen Sie außerdem eine private Map[Long, Seq[RatingData]] hinzu, die ein schnelles Nachschlagen von Film-IDs zu den zugehörigen Bewertungen ermöglicht.

Query-Logging
Schreiben Sie eine zusätzliche Klasse QueryLog, mit der alle ausgeführten Abfragen samt Argumenten als Liste von Zeichenketten gespeichert werden. QueryLog soll eine Methode logQuery bereitstellen, mit der eine Abfrage protokolliert werden kann. Außerdem sollen zwei Methoden lastQuery und queryCounts implementiert werden, die die zuletzt ausgeführte Abfrage (falls eine existiert) und alle bisherigen Abfragen mit deren Häufigkeit zurückgeben.
