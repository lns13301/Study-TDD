package bookstoread;

import java.time.LocalDate;

public class BookPublishedYearFilter implements BookFilter {
    private LocalDate startedDate;

    static BookPublishedYearFilter After(int year) {
        BookPublishedYearFilter filter = new BookPublishedYearFilter();
        filter.startedDate = LocalDate.of(year, 12, 31);
        return filter;
    }

    @Override
    public boolean apply(Book b) {
        return b.getPublishedOn().isAfter(startedDate);
    }
}
