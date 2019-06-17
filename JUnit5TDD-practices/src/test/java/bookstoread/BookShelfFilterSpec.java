package bookstoread;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("A bookshelf filter")
@ExtendWith(BooksParameterResolver.class)
public class BookShelfFilterSpec {
    private Book cleanCode;
    private Book codeComplete;

    @BeforeEach
    void init() {
        cleanCode = new Book("Clean Code", "Robert C. Martin", LocalDate.of(2008, Month.AUGUST, 1));
        codeComplete = new Book("Code Complete", "Steve McConnel", LocalDate.of(2004, Month.JUNE, 9));
    }

    @Nested
    @DisplayName("book published date post specified year")
    class BookPublishedAfterFilterSpec  {
        BookFilter filter;

        @BeforeEach
        void init() {
            filter = BookPublishedYearFilter.After(2007);
        }

        @Test
        @DisplayName("should give matching book")
        void validateBookPublishedDatePostAskedYear() {
            assertTrue(filter.apply(cleanCode));
            assertFalse(filter.apply(codeComplete));
        }
    }

}
