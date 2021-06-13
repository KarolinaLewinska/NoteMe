# NoteMe AndroidStudio
**Nazwa aplikacji:** NoteMe<br/>
**Autorzy:** [Karolina Lewińska](https://github.com/KarolinaLewinska), [Justyna Gapys](https://github.com/justynagapys)<br/>
**Cel aplikacji:** Aplikacja pozwalająca na zapis, odczyt, edycję i usuwanie stworzonych przez użytkownika notatek.<br/>
**Język:** Java<br/>
**Baza danych:** Firebase<br/><br/>
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
![HomeActivity](https://github.com/KarolinaLewinska/NoteMe_AndroidStudio/blob/master/Screenshots/homeactivity.PNG)
![AddNoteActivity](https://github.com/KarolinaLewinska/NoteMe_AndroidStudio/blob/master/Screenshots/EditNoteView.PNG)
![LoginActivity](https://github.com/KarolinaLewinska/NoteMe_AndroidStudio/blob/master/Screenshots/loginview.PNG)
![RegisterActivity](https://github.com/KarolinaLewinska/NoteMe_AndroidStudio/blob/master/Screenshots/registerview.PNG)
![MainActivity](https://github.com/KarolinaLewinska/NoteMe_AndroidStudio/blob/master/Screenshots/mainactivity.PNG)
![newNoteView](https://github.com/KarolinaLewinska/NoteMe_AndroidStudio/blob/master/Screenshots/newNoteView.PNG)

**Opis realizacji projektu:**<br/>
Na początku stworzyłyśmy [logo aplikacji](https://www.freelogodesign.org/).<br/>
Następnie skonfigurowałyśmy bazę danych oraz mechanizm uwierzytelniania za pomocą adresu email i hasła.<br/>
![dbRules](https://github.com/KarolinaLewinska/NoteMe_AndroidStudio/blob/master/Screenshots/dbRules.PNG)<br/>
![authConfig](https://github.com/KarolinaLewinska/NoteMe_AndroidStudio/blob/master/Screenshots/AuthConfig.PNG)<br/>

**AddNoteActivity**<br/>
Deklaracja zmiennych (w tym pasek narzędzi, zmienne Firebase i menu, ikona usuwania notatki). Jeśli id notatki nie istnieje to ikona z usuwaniem notatki jest niewidoczna (przy tworzeniu nowej notatki).<br/>
![AddNoteActivity1](https://github.com/KarolinaLewinska/NoteMe_AndroidStudio/blob/master/Screenshots/AddNoteActivity1.PNG)<br/>
Sprawdzenie czy istnieje id notatki (ważne do odróżnienia czy ma zostać dodana nowa notatka czy zaktualizowana istniejąca). Podpięcie zmiennych pod elementy interfejsu. Ustawienie stworzonego paska narzędzi i dodanie do niego ikonki powrotu do interfejsu ze wszystkimi notatkami. Stworzenie instancji autoryzacji Firebase oraz pobranie referencji obiektów w bazie o nazwie „Notatki” zalogowanego obecnie użytkownika.<br/>
![AddNoteActivity2](https://github.com/KarolinaLewinska/NoteMe_AndroidStudio/blob/master/Screenshots/AddNoteActivity2.PNG)<br/>
Po kliknięciu przycisku dane zapisywane są w bazie. Jeśli któreś z pól jest puste ukazuje się użytkownikowi komunikat o wymagalności obu pól. Metoda showCurrentNote wyświetla dane z istniejącej już notatki.<br/>
![AddNoteActivity3](https://github.com/KarolinaLewinska/NoteMe_AndroidStudio/blob/master/Screenshots/AddNoteActivity3.PNG)<br/>
Metoda służąca do aktualizacji/tworzenia notatki. Sprawdzane jest czy użytkownik jest zalogowany oraz czy notatka już istnieje. Jeśli tak, to tworzony jest nowy HashMap o kluczach, takich, które są już w  bazie oraz następują aktualizacja danych. Następnie ukazuje się komunikat potwierdzający aktualizację. Jeśli notatka nie istnieje to generowana jest referencja do odpowiedniego miejsca w bazie (metoda push). Następnie tworzony jest hashmap i dane są dodawane w oddzielnym wątku by nie zablokować interfejsu aplikacji. Jeśli uda się stworzyć notatkę to zostanie wyświetlony komunikat o powodzeniu. W przeciwnym razie ukaże się użytkownikowi komunikat o błędzie zapisu albo braku zalogowanego użytkownika.<br/>
![AddNoteActivity4](https://github.com/KarolinaLewinska/NoteMe_AndroidStudio/blob/master/Screenshots/AddNoteActivity4.PNG)<br/>
Metoda odpowiadająca za pasek narzędzi. W zależności od przypadku powracamy do głównego interfejsu z notatkami bądź usuwamy notatkę (jeśli jej id istnieje).<br/>
![AddNoteActivity5](https://github.com/KarolinaLewinska/NoteMe_AndroidStudio/blob/master/Screenshots/AddNoteActivity5b.PNG)<br/>
Metoda odpowiadająca za usuwanie notatek z bazy. Gdy usunięcie notatki przebiegnie pomyślnie ukazywany jest komunikat w odwrotnym przypadku – komunikat o błędzie.<br/>
![AddNoteActivity6](https://github.com/KarolinaLewinska/NoteMe_AndroidStudio/blob/master/Screenshots/AddNoteActivity6.PNG)<br/>

**HomeActivity**<br/>
Deklaracja przycisków służących do przejścia do panelu logowania/rejestracji oraz zmiennej Firebase. Podpięcie przycisków pod zmienne. Po wciśnięciu przycisku logowania następuje przejście do panelu logowania bądź rejestracji.<br/>
![HomeActivity1](https://github.com/KarolinaLewinska/NoteMe_AndroidStudio/blob/master/Screenshots/HomeActivity1.PNG)<br/>
Metoda setInterface sprawdza czy jest zalogowany użytkownik. Jeśli tak to przekierowuje widok na panel z jego notatkami (MainActivity).<br/>
![HomeActivity2](https://github.com/KarolinaLewinska/NoteMe_AndroidStudio/blob/master/Screenshots/HomeActivity2.PNG)<br/>

**LoginActivity**<br/>
Deklaracja zmiennych. Podpięcie elementów interfejsu pod te zmienne. stworzenie instancji uwierzytelniania Firebase’a. Ustawienie w pasku narzędzi możliwości powrotu do HomeActivity. Po wciśnięciu przycisku „zaloguj się” sprawdzane jest, czy pola są puste. Jeśli tak, to wyświetlany jest komunikat o wymagalności pola. W przeciwnym wypadku następuje logowanie.Podczas logowania wyświetla się pasek informujący o trwaniu logowania. Po zakończeniu operacji jest on wyłączany. Gdy logowanie przebiegnie pomyślnie to następuje przejście do panelu z notatkami oraz wyświetlany jest komunikat o pomyślnym zalogowaniu. W przeciwnym razie wyświetlany jest komunikat o błędzie.<br/>
![LoginActivity1](https://github.com/KarolinaLewinska/NoteMe_AndroidStudio/blob/master/Screenshots/LoginActivity1.PNG)<br/>
![LoginActivity2](https://github.com/KarolinaLewinska/NoteMe_AndroidStudio/blob/master/Screenshots/LoginActivity2.PNG)<br/>
![LoginActivity3](https://github.com/KarolinaLewinska/NoteMe_AndroidStudio/blob/master/Screenshots/LoginActivity3.PNG)<br/>

**RegisterActivity**<br/>
Stworzony analogicznie do panelu logowania.<br/>
![RegisterActivity1](https://github.com/KarolinaLewinska/NoteMe_AndroidStudio/blob/master/Screenshots/RegisterActivity1.PNG)<br/>
![RegisterActivity2](https://github.com/KarolinaLewinska/NoteMe_AndroidStudio/blob/master/Screenshots/RegisterActivity2.PNG)<br/>
![RegisterActivity3](https://github.com/KarolinaLewinska/NoteMe_AndroidStudio/blob/master/Screenshots/RegisterActivity3.PNG)<br/>

**MainActivity**<br/>
Deklaracja zmiennych, konfiguracja wyświetlania dynamicznej listy notatek. Stworzenie instancji Firebase’a. Sprawdzenie czy użytkownik jest zalogowany, jeśli tak to wyświetlane są jego notatki przy użyciu adaptera. W przeciwnym razie następuje przekierowanie do HomeActivity – metoda setInterface. Metoda loadData służy do załadowania notatek. Po kliknięciu na daną notatkę następuje przekierowanie do edycji – AddNoteActivity.<br/>
![MainActivity1](https://github.com/KarolinaLewinska/NoteMe_AndroidStudio/blob/master/Screenshots/MainActivity1.PNG)<br/>
![MainActivity2](https://github.com/KarolinaLewinska/NoteMe_AndroidStudio/blob/master/Screenshots/MainActivity2.PNG)<br/>
Ustawione jest nowe menu (main_activity_menu) zawierające ikony dodawania notatek oraz wylogowania się z aplikacji. W zależności od wciśniętego przycisku następuje przejście do tworzenia nowej notatki (AddNoteActivity) bądź wylogowanie się z aplikacji i przejście do HomeActivity.<br/>
![MainActivity3](https://github.com/KarolinaLewinska/NoteMe_AndroidStudio/blob/master/Screenshots/MainActivity3.PNG)<br/>
![MainActivity4](https://github.com/KarolinaLewinska/NoteMe_AndroidStudio/blob/master/Screenshots/MainActivity4.PNG)<br/>

**NoteModel**<br/>
Model notatki potrzebny do wyświetlenia tytułu i daty notatek w MainActivity.<br/>
![NoteModel](https://github.com/KarolinaLewinska/NoteMe_AndroidStudio/blob/master/Screenshots/NoteModel.PNG)<br/>

**NoteView**<br/>
View holder do ustawiania tytułu i daty utworzenia notatki.<br/>
![ViewHolder](https://github.com/KarolinaLewinska/NoteMe_AndroidStudio/blob/master/Screenshots/NoteView.PNG)<br/>

**TimeOfNote**<br/>
Klasa służąca do skonwertowania czasu utworzenia notatki na przedziały czasowe.<br/>
![TimeOfNote](https://github.com/KarolinaLewinska/NoteMe_AndroidStudio/blob/master/Screenshots/TimeOfNote.PNG)<br/>

**Rezultaty w bazie**<br/>
Zarejestrowani użytkownicy:<br/>
![users](https://github.com/KarolinaLewinska/NoteMe_AndroidStudio/blob/master/Screenshots/users.PNG)<br/>
Dodane notatki w bazie:<br/>
![db](https://github.com/KarolinaLewinska/NoteMe_AndroidStudio/blob/master/Screenshots/db.PNG)<br/>




