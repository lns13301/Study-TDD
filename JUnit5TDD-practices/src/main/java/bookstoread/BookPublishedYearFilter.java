package bookstoread;

import java.time.LocalDate;
import java.util.function.Function;

class BookPublishedYearFilter implements BookFilter {
    private Function<LocalDate, Boolean> comparison;

    static BookPublishedYearFilter After(int year) {
        final LocalDate date = LocalDate.of(year, 12, 31);
        BookPublishedYearFilter filter = new BookPublishedYearFilter();
        filter.comparison = date::isBefore;
        return filter;
    }

    static BookPublishedYearFilter Before(int year) {
        final LocalDate date = LocalDate.of(year, 1, 1);
        BookPublishedYearFilter filter = new BookPublishedYearFilter();
        filter.comparison = date::isAfter;
        return filter;
    }

    @Override
    public boolean apply(final Book b) {
        return b!=null && comparison.apply(b.getPublishedOn());
    }
}
