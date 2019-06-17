package bookstoread;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("A bookshelf progress")
public class BookShelfProgressSpec {
    private BookShelf bookShelf;
    private Book effectiveJava;
    private Book codeComplete;
    private Book mythicalManMonth;
    private Book cleanCode;
    private Book refactoring;

    @BeforeEach
    void init() {
        effectiveJava = new Book("Effective Java", "Joshua Bloch", LocalDate.of(2008, Month.MAY, 8));
        codeComplete = new Book("Code Complete", "Steve McConnel", LocalDate.of(2004, Month.JUNE, 9));
        mythicalManMonth = new Book("The Mythical Man-Month", "Frederick Phillips Brooks", LocalDate.of(1975, Month.JANUARY, 1));
        cleanCode = new Book("Clean Code", "Robert C. Martin", LocalDate.of(2008, Month.AUGUST, 1));
        refactoring = new Book("Refactoring", "Martin Fowler", LocalDate.of(2003, Month.OCTOBER, 2));
        bookShelf = new BookShelf();
        bookShelf.add(effectiveJava, cleanCode, mythicalManMonth, codeComplete, refactoring);
    }

    @Test
    @DisplayName("is 0% completed and 100% to-read when no book is read yet")
    void progress100PercentUnread() {
        Progress progress = bookShelf.progress();
        assertThat(progress.completed()).isEqualTo(0);
        assertThat(progress.toRead()).isEqualTo(100);
    }

    @Test
    @DisplayName("is 40% completed and 60% to-read when two books read and 3 books not read yet")
    void progressWithCompletedAndToReadPercentages() {
        effectiveJava.startReadingOn(LocalDate.of(2016,Month.JULY, 1));
        effectiveJava.finishedReadingOn(LocalDate.of(2016,Month.JULY, 30));
        cleanCode.startReadingOn(LocalDate.of(2016,Month.AUGUST, 1));
        cleanCode.finishedReadingOn(LocalDate.of(2016,Month.AUGUST, 30));
        Progress progress = bookShelf.progress();
        assertThat(progress.completed()).isEqualTo(40);
        assertThat(progress.toRead()).isEqualTo(60);
    }

    @Test
    @DisplayName("is 40% completed, 20% in-progress, and 40% to-read when 2 books read, 1 book in progress, and 2 books unread")
    void reportProgressOfCurrentlyReadingBooks() {
        effectiveJava.startReadingOn(LocalDate.of(2016,Month.JULY, 1));
        effectiveJava.finishedReadingOn(LocalDate.of(2016,Month.JULY, 30));
        codeComplete.startReadingOn(LocalDate.of(2016,Month.AUGUST, 1));
        codeComplete.finishedReadingOn(LocalDate.of(2016,Month.AUGUST, 30));
        mythicalManMonth.startReadingOn(LocalDate.of(2016, Month.SEPTEMBER, 1));

        Progress progress = bookShelf.progress();

        assertThat(progress.completed()).isEqualTo(40);
        assertThat(progress.inProgress()).isEqualTo(20);
        assertThat(progress.toRead()).isEqualTo(40);
    }

}
