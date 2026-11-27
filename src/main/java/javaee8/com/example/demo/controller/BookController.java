package javaee8.com.example.demo.controller;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javaee8.com.example.demo.bean.common.BookValidator;
import javaee8.com.example.demo.model.Book;
import javaee8.com.example.demo.service.BookService;

@Controller
@RequestMapping("/books")
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;
    private final BookValidator bookValidator;

    public BookController(BookService bookService, BookValidator bookValidator) {
        this.bookService = bookService;
        this.bookValidator = bookValidator;
    }

    @InitBinder("book")
    void initBinder(WebDataBinder binder) {
        binder.addValidators(bookValidator);
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books/index";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping
    public String save(@Validated Book book, BindingResult result, RedirectAttributes ra) {
        if (result.hasErrors()) {
            return "books/new";
        }
        log.info("save {}", book);
        bookService.saveBook(book);
        ra.addFlashAttribute("message", "Book added successfully");
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable long id, Model model, RedirectAttributes ra) {
        Optional<Book> book = bookService.getBookById(id);
        if (book.isEmpty()) {
            ra.addFlashAttribute("error", "Book with id " + id + " not found");
            return "redirect:/books";
        }
        model.addAttribute("book", book.get());
        return "books/new";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable long id, @Validated Book book,
                         BindingResult result, RedirectAttributes ra) {
        book.setId(id);
        if (bookService.getBookById(id).isEmpty()) {
            ra.addFlashAttribute("error", "Book with id " + id + " not found");
            return "redirect:/books";
        }
        if (result.hasErrors()) {
            return "books/new";
        }
        log.info("update {}", book);
        bookService.updateBook(book);
        ra.addFlashAttribute("message", "Book with id " + id + " successfully updated");
        return "redirect:/books";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable long id, RedirectAttributes ra) {
        if (bookService.getBookById(id).isEmpty()) {
            ra.addFlashAttribute("error", "Book with id " + id + " not found, nothing deleted");
        } else {
            bookService.deleteBookById(id);
            ra.addFlashAttribute("message", "Book with id " + id + " successfully deleted");
        }
        return "redirect:/books";
    }
}