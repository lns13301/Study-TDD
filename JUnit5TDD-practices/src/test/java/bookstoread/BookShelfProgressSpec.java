package bookstoread;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("A bookshelf progress")
@ExtendWith(BooksParameterResolver.class)
public class BookShelfProgressSpec {

    private BookShelf bookShelf;

    @BeforeEach
    void init(Book[] books, TestInfo testInfo) {
        System.out.println("-----> " + testInfo.getDisplayName());
        bookShelf = new BookShelf();
        bookShelf.add(books);
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
    void progressWithCompletedAndToReadPercentages(Book[] books) {
        Assumptions.assumeTrue(bookShelf.books().stream().filter(b -> b == books[0]).count() == 1);
        Assumptions.assumeTrue(bookShelf.books().stream().filter(b -> b == books[1]).count() == 1);

        books[0].startedReadingOn(LocalDate.of(2016, Month.JULY, 1));
        books[0].finishedReadingOn(LocalDate.of(2016, Month.JULY, 31));

        books[1].startedReadingOn(LocalDate.of(2016, Month.AUGUST, 1));
        books[1].finishedReadingOn(LocalDate.of(2016, Month.AUGUST, 31));

        Progress progress = bookShelf.progress();
        assertThat(progress.completed()).isEqualTo(40);
        assertThat(progress.toRead()).isEqualTo(60);
    }

    @Test
    @DisplayName("is 40% completed, 20% in-progress, and 40% to-read when 2 books read, 1 book in progress, and 2 books unread")
    void reportProgressOfCurrentlyReadingBooks(Book[] books) {
        Assumptions.assumeTrue(bookShelf.books().stream().filter(b -> b == books[0]).count() == 1);
        Assumptions.assumeTrue(bookShelf.books().stream().filter(b -> b == books[1]).count() == 1);
        Assumptions.assumeTrue(bookShelf.books().stream().filter(b -> b == books[2]).count() == 1);

        books[0].startedReadingOn(LocalDate.of(2016, Month.JULY, 1));
        books[0].finishedReadingOn(LocalDate.of(2016, Month.JULY, 31));

        books[1].startedReadingOn(LocalDate.of(2016, Month.AUGUST, 1));
        books[1].finishedReadingOn(LocalDate.of(2016, Month.AUGUST, 31));

        books[2].startedReadingOn(LocalDate.of(2016, Month.SEPTEMBER, 1));

        Progress progress = bookShelf.progress();

        assertThat(progress.completed()).isEqualTo(40);
        assertThat(progress.inProgress()).isEqualTo(20);
        assertThat(progress.toRead()).isEqualTo(40);
    }

}
