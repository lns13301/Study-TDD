package bookstoread;

import org.junit.jupiter.api.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A book shelf")
public class BookShelfSpec {

    private BookShelf bookShelf;
    private Book effectiveJava;
    private Book codeComplete;
    private Book mythicalManMonth;
    private Book cleanCode;

    @BeforeEach
    void init() {
        bookShelf = new BookShelf();
        effectiveJava = new Book("Effective Java", "Joshua Bloch", LocalDate.of(2008, Month.MAY, 8));
        codeComplete = new Book("Code Complete", "Steve McConnel", LocalDate.of(2004, Month.JUNE, 9));
        mythicalManMonth = new Book("The Mythical Man-Month", "Frederick Phillips Brooks", LocalDate.of(1975, Month.JANUARY, 1));
        cleanCode = new Book("Clean Code", "Robert C. Martin", LocalDate.of(2008, Month.AUGUST, 1));
    }

    @Nested
    class IsEmpty {
        @Test
        @DisplayName("emptyBookShelfWhenNoBookAdded")
        void emptyBookShelfWhenNoBookAdded() {
            List<Book> books = bookShelf.books();
            assertTrue(books.isEmpty(), "BookShelf should be empty");
        }

        @Test
        void emptyBookShelfWhenAddIsCalledWithoutBooks() {
            bookShelf.add();
            List<Book> books = bookShelf.books();
            assertTrue(books.isEmpty(), "BookShelf should be empty");
        }
    }

    @Nested
    class BooksAreAdded {
        @Test
        @DisplayName("contains two books")
        void bookshelfContainsTwoBooksWhenTwoBooksAdded() {
            bookShelf.add(effectiveJava, codeComplete);
            List<Book> books = bookShelf.books();
            assertEquals(2, books.size(), "BookShelf should have two books");
        }

        @Test
        @DisplayName("returns an immutable books collection to client")
        void bookshelfIsImmutableForClient() {
            bookShelf.add(effectiveJava, codeComplete);
            List<Book> books = bookShelf.books();
            try {
                books.add(mythicalManMonth);
                fail("Should not be able to add book to books");
            } catch (Exception e) {
                assertTrue(e instanceof UnsupportedOperationException, "BookShelf should throw UnsupportedOperationException");
            }
        }
    }

    @Nested
    class IsArranged {
        @Test
        void bookshelfArrangedByBookTitle() {
            bookShelf.add(effectiveJava, codeComplete, mythicalManMonth);
            List<Book> bookList = bookShelf.arrange();
            assertEquals(Arrays.asList(codeComplete, effectiveJava, mythicalManMonth), bookList, "Books in a bookshelf should be arranged lexicographically by book title");
        }

        @Test
        void bookshelfArrangedByUserProvidedCriteria() {
            bookShelf.add(effectiveJava, codeComplete, mythicalManMonth);
            Comparator<Book> reversed = Comparator.<Book>naturalOrder().reversed();
            List<Book> bookList = bookShelf.arrange(reversed);
            assertThat(bookList).isSortedAccordingTo(reversed);
        }

        @Test
        void groupBooksInBookShelfByPublicationYear() {
            bookShelf.add(effectiveJava, codeComplete, mythicalManMonth, cleanCode);
            Map<Year, List<Book>> booksByPublicationYear = bookShelf.groupByPublicationYear();

            assertThat(booksByPublicationYear)
                    .containsKey(Year.of(2008))
                    .containsValues(Arrays.asList(effectiveJava, cleanCode));

            assertThat(booksByPublicationYear)
                    .containsKey(Year.of(2004))
                    .containsValues(singletonList(codeComplete));

            assertThat(booksByPublicationYear)
                    .containsKey(Year.of(1975))
                    .containsValues(singletonList(mythicalManMonth));

        }
    }

    @Test
    void throwsExceptionWhenBooksAreAddedAfterCapacityIsReached() {
        BookShelf bookShelf = new BookShelf(2);
        bookShelf.add(effectiveJava, codeComplete);
        BookShelfCapacityReached throwException = assertThrows(BookShelfCapacityReached.class, () -> bookShelf.add(mythicalManMonth));
        assertEquals("BookShelf capacity of 2 is reached. You can't add more books.", throwException.getMessage());
    }

//    @Test
//    void testShouldCompleteInOneSecond() {
//        assertTimeoutPreemptively(Duration.of(1, ChronoUnit.SECONDS), () -> Thread.sleep(2000));
//    }
//
//    @RepeatedTest(value = 10, name = "testRepeatedTest_{currentRepetition}/{totalRepetitions}")
//    void testRepeatedTest() {
//        assertTrue(true);
//    }

}
