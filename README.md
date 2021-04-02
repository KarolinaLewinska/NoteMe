# NoteMe_AndroidStudio
**Nazwa aplikacji:** NoteMe<br/>
**Autorzy:** Karolina Lewińska, Justyna Gapys<br/>
**Cel aplikacji:** Aplikacja pozwalająca na zapis, odczyt, edycję i usuwanie stworzonych przez użytkownika notatek.<br/>
**Język:** Java<br/>
**Baza danych:** Firebase<br/>
**Struktura projektu:**<br/>
**AddNoteActivity** – interfejs odpowiadający za dodawanie, usuwanie oraz edycję notatki<br/>
**HomeActivity** – ekran początkowy z przyciskami do logowania i rejestracji<br/>
**LoginActivity** – panel logowania<br/>
**RegisterActivity** – panel rejestracji<br/>
**MainActivity** – widok wszystkich notatek użytkownika<br/>
**NoteModel** – model notatki<br/>
**NoteView** – view holder służący do ustawiania tytułu i czasu edycji pojedynczej notatki<br/>
**TimeOfNote** – klasa stworzona w celu zamiany czasu edycji notatki (wyświetlanego w MainActivity). Czas edycji podzielono na kategorie takie jak m.in. „przed chwilą”, „wczoraj”, "minutę temu"<br/>

**Interfejsy aplikacji:**<br/>
![AddNoteActivity](https://github.com/KarolinaLewinska/NoteMe_AndroidStudio/blob/master/Screenshots/EditNoteView.PNG)

Opis realizacji projektu:
Na początku stworzyłyśmy logo aplikacji
Następnie stworzyłyśmy i skonfigurowałyśmy bazę danych oraz mechanizm uwierzytelniania za pomocą adresu email i hasła

Kod aplikacji: 
AddNoteActivity:
1.	Deklaracja zmiennych (w tym pasek narzędzi, zmienne Firebase i menu) oraz ustawienie nowego menu z ikonką do usuwania notatki. Jeśli id notatki nie istnieje ikona z usuwaniem jest niewidoczna (przy tworzeniu nowej notatki).
2.	Sprawdzenie czy istnieje id notatki (ważne do odróżnienia czy ma zostać dodana nowa notatka czy zaktualizowana istniejąca stworzona). Podpięcie zmiennych pod elementy interfejsu. Ustawienie stworzonego paska narzędzi i dodanie do niego ikonki powrotu do interfejsu ze wszystkimi notatkami. Stworzenie instancji autoryzacji Firebase oraz pobranie referencji obiektów w bazie o nazwie „Notatki” zalogowanego obecnie użytkownika.
3.	Po kliknięciu przycisku dane zapisywane są w bazie, sprawdzana jest walidacja na puste pola. Jeśli któreś z pól jest puste ukazuje się użytkownikowi komunikat o wymagalności obu pól. Metoda showCurrentNote wyświetla dane z istniejących już notatek.
4.	Metoda służąca do tworzenia notatki. Sprawdzane jest czy użytkownik jest zalogowany oraz czy notatka już istnieje. Jeśli tak, to tworzony jest nowy HashMap o kluczach, takich, które są już w  bazie oraz i następują aktualizacja danych. Następnie ukazuje się komunikat potwierdzający aktualizację. Jeśli notatka nie istnieje to generowana jest referencja do odpowiedniego miejsca w bazie (metoda push). Następnie tworzony jest hashmap i dane są dodawane w oddzielnym wątku by nie zablokować interfejsu aplikacji. Jeśli uda się stworzyć notatkę to zostanie wyświetlony komunikat o powodzeniu. W przeciwnym razie ukaże się użytkownikowi komunikat o błędzie zapisu albo braku zalogowanego użytkownika.
5.	Metoda odpowiadająca za pasek narzędzi. W zależności od przypadku powracamy do głównego interfejsu z notatkami bądź usuwamy notatkę (jeśli jej id istnieje) w przeciwnym razie użytkownikowi wyświetlany jest komunikat o braku notatki do usunięcia.
6.	Metoda odpowiadająca za usuwanie notatek z bazy. Gdy usunięcie notatki przebiegnie pomyślnie ukazywany jest komunikat w odwrotnym przypadku – komunikat o błędzie.
HomeActivity
1.	Deklaracja przycisków służących do przejścia do panelu logowania/rejestracji oraz uwierzytelniania Firebase. Podpięcie przycisków pod zmienne. Po wciśnięciu przycisku logowania następuje przejście do panelu logowania bądź rejestracji. Metoda setInterface sprawdza czy jest zalogowany użytkownik. Jeśli tak to przekierowuje widok na panel z jego notatkami.
LoginActivity
1.	Deklaracja zmiennych, podpięcie elementów interfejsu pod te zmienne, stworzenie instancji uwierzytelniania Firebase’a, ustawienie w pasku narzędzi możliwości powrotu do HomeActivity. Po wciśnięciu przycisku „zaloguj się” sprawdzane jest, czy pola są puste. Jeśli tak, to wyświetlany jest komunikat o wymagalności pola. W przeciwnym wypadku następuje logowanie.
2.	Podczas logowania wyświetla się pasek informujący o trwaniu logowania. Po zakończeniu operacji jest on wyłączany. Gdy logowanie przebiegnie pomyślnie to następuje przejście do panelu z notatkami oraz wyświetlany jest komunikat o pomyślnym zalogowaniu. W przeciwnym razie wyświetlany jest komunikat o błędzie.

RegisterActivity
1.	Deklaracja zmiennych, podpięcie elementów interfejsu pod te zmienne, stworzenie instancji Firebase’a, ustawienie w pasku narzędzi możliwości powrotu do HomeActivity. Po wciśnięciu przycisku „zarejestruj się” sprawdzane jest, czy pola są puste. Jeśli tak, to wyświetlany jest komunikat o wymagalności pola. W przeciwnym wypadku następuje rejestracja.
2.	Podczas rejestracji wyświetla się pasek informujący o przebiegu rejestracji. Po zakończeniu operacji jest on wyłączany. Gdy rejestracja przebiegnie pomyślnie to następuje przejście do panelu logowania oraz wyświetlany jest komunikat o pomyślnym przebiegu rejestracji. W przeciwnym razie wyświetlany jest komunikat o błędzie.
MainActivity
1.	Deklaracja zmiennych, ustawienie grid layotu oraz podpięcie go pod recyclerView, który służy do wyświetlania listy notatek. Stworzenie instancji Firebase’a. Sprawdzenie czy użytkownik jest zalogowany, jeśli tak to wyświetlane są jego notatki przy użyciu adaptera. W przeciwnym razie następuje przekierowanie do HomeActivity – metoda setInterface. Metoda loadData służy do załadowania notatek na podstawie modelu z bazy na podstawie referencji. Następnie są one wyświetlane. Po kliknięciu na daną notatkę następuje przekierowanie do edycji – AddNoteActivity. Ustawione jest nowe menu (main_activity_menu) zawierające ikony dodawania notatek oraz wylogowania się z aplikacji. W zależności od wciśniętego przycisku następuje przejście do tworzenia nowej notatki (AddNoteActivity) bądź wylogowanie się z aplikacji i przejście do HomeActivity.

NoteModel
Model notatki potrzebny do wyświetlenia tytułu i daty notatek w MainActivity.

NoteView
View holder do ustawiania tytułu i daty utworzenia notatki.

TimeOfNote 
Klasa służąca do skonwertowania czasu utworzenia notatki na przedziały czasowe.





