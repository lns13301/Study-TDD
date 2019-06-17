package bookstoread;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.*;

import java.time.Year;
import java.util.*;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Book Shelf Spec Tests")
@ExtendWith(BooksParameterResolver.class)
public class BookShelfSpec {

    private BookShelf bookShelf;
    private Book effectiveJava;
    private Book codeComplete;
    private Book mythicalManMonth;
    private Book cleanCode;

    @BeforeEach
    void init(Map<String, Book> books) {
        bookShelf = new BookShelf();
        this.effectiveJava = books.get("Effective Java");
        this.codeComplete = books.get("Code Complete");
        this.mythicalManMonth = books.get("The Mythical Man-Month");
        this.cleanCode = books.get("Clean Code");
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
        void bookshelfContainsTwoBooksWhenTwoBooksAdded() {
            bookShelf.add(effectiveJava, codeComplete);
            List<Book> books = bookShelf.books();
            assertEquals(2, books.size(), "BookShelf should have two books.");
        }


        @Test
        void bookshelfIsImmutableForClient() {
            bookShelf.add(effectiveJava, codeComplete);
            List<Book> books = bookShelf.books();
            try {
                books.add(mythicalManMonth);
                fail("Should not be able to add book to books");
            } catch (Exception e) {
                assertTrue(e instanceof UnsupportedOperationException, "Should throw UnsupportedOperationException.");
            }
        }
    }

    @Nested
    class IsArranged {
        @Test
        void bookshelfArrangedByBookTitle() {
            bookShelf.add(effectiveJava, codeComplete, mythicalManMonth);
            List<Book> books = bookShelf.arrange(Comparator.<Book>naturalOrder().reversed());
            assertEquals(Arrays.asList(mythicalManMonth, effectiveJava, codeComplete), books, "Books in a bookshelf should be arranged lexicographically by book title");
        }

        @Test
        void bookshelfArrangedByUserProvidedCriteria() {
            bookShelf.add(effectiveJava, codeComplete, mythicalManMonth);
            Comparator<Book> reversed = Comparator.<Book>naturalOrder().reversed();
            List<Book> books = bookShelf.arrange(reversed);
            assertThat(books).isSortedAccordingTo(reversed);
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
}
