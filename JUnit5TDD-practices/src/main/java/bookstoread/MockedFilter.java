package bookstoread;

public class MockedFilter implements BookFilter {

    boolean returnValue;
    boolean invoked;

    public MockedFilter(boolean returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public boolean apply(Book b) {
        invoked = true;
        return returnValue;
    }
}
