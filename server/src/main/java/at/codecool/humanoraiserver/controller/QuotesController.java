package at.codecool.humanoraiserver.controller;

import at.codecool.humanoraiserver.model.Quote;
import at.codecool.humanoraiserver.services.QuotesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
public class QuotesController {

    private final QuotesService services;

    public QuotesController(QuotesService newServices) {
        services = newServices;
    }

    @GetMapping("/quotes")
    public Collection<Quote> quotes() {
        return services.getQuotes();
    }
}
