package javaee8.com.example.demo.bean.common;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import javaee8.com.example.demo.model.Book;

@Component
public class BookValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        if (book.getTitle() == null || book.getTitle().isBlank()) {
            errors.rejectValue("title", "book.title.required", "Title must not be empty");
        }
        if (book.getAuthor() == null || book.getAuthor().isBlank()) {
            errors.rejectValue("author", "book.author.required", "Author must not be empty");
        }
        if (!errors.hasFieldErrors("year")) {
            if (book.getYear() == null) {
                errors.rejectValue("year", "book.year.required", "Year must not be empty");
            } else if (book.getYear() < 1000 || book.getYear() > 2100) {
                errors.rejectValue("year", "book.year.invalid", "Year must be between 1000 and 2100");
            }
        }
    }
}