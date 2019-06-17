package bookstoread;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.StringJoiner;

public class Book implements Comparable<Book> {
    private final String title;
    private final String author;
    private final LocalDate publishedOn;
    private LocalDate startedReadingOn;
    private LocalDate finishedReadingOn;

    public Book(String title, String author, LocalDate publishedOn) {
        this.title = title;
        this.author = author;
        this.publishedOn = publishedOn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDate getPublishedOn() {
        return publishedOn;
    }

    public LocalDate getStartedReadingOn() {
        return startedReadingOn;
    }

    public LocalDate getFinishedReadingOn() {
        return finishedReadingOn;
    }

    public void startedReadingOn(LocalDate startedReadingOn) {
        this.startedReadingOn = startedReadingOn;
    }

    public void finishedReadingOn(LocalDate finishedReadingOn) {
        this.finishedReadingOn = finishedReadingOn;
    }

    public boolean isRead() {
        return startedReadingOn != null && finishedReadingOn != null;
    }

    public boolean isProgress() {
        return startedReadingOn != null && finishedReadingOn == null;
    }

    @Override
    public int compareTo(@NotNull Book that) {
        return this.title.compareTo(that.title);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Book.class.getSimpleName() + "[", "]")
                .add("title='" + title + "'")
                .add("author='" + author + "'")
                .add("publishedOn=" + publishedOn)
                .toString();
    }

}
