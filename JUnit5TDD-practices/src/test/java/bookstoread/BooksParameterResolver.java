package bookstoread;

import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Parameter;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BooksParameterResolver implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Parameter parameter = parameterContext.getParameter();
        return Objects.equals(parameter.getParameterizedType().getTypeName(), "java.util.Map<java.lang.String, bookstoread.Book>");
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Map<String, Book> books = new HashMap<>();

        books.put("Effective Java", new Book("Effective Java", "Joshua Bloch", LocalDate.of(2008, Month.MAY, 8)));
        books.put("Code Complete", new Book("Code Complete", "Steve McConnel", LocalDate.of(2004, Month.JUNE, 9)));
        books.put("The Mythical Man-Month", new Book("The Mythical Man-Month", "Frederick Phillips Brooks", LocalDate.of(1975, Month.JANUARY, 1)));
        books.put("Clean Code", new Book("Clean Code", "Robert C. Martin", LocalDate.of(2008, Month.AUGUST, 1)));

        return books;
    }
}
