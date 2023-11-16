package at.codecool.humanoraiserver.repositories;

import at.codecool.humanoraiserver.controller.PostUsersRequest;
import at.codecool.humanoraiserver.model.Quote;
import at.codecool.humanoraiserver.services.QuotesService;
import at.codecool.humanoraiserver.services.UsersService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class RepositoryInitializer implements ApplicationRunner {

    private final Environment environment;

    private final UsersService usersService;

    private final UsersRepository usersRepository;

    private final QuotesService quotesService;

    public RepositoryInitializer(Environment environment, UsersService usersService, UsersRepository usersRepository,
                                 QuotesService quotesService) {
        this.environment = environment;
        this.usersService = usersService;
        this.usersRepository = usersRepository;
        this.quotesService = quotesService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (this.environment.matchesProfiles("dev")) {
            this.createUsers();
            this.createQuotes();
        }
    }

    private void createUsers() {
        if (usersRepository.findAll().isEmpty()) {
            var createAdminRequest = new PostUsersRequest();
            var adminEmail = "admin@example.org";
            createAdminRequest.setEmail(adminEmail);
            createAdminRequest.setNickname("admn");
            createAdminRequest.setPassword("IamAdmin");
            this.usersService.registerUser(createAdminRequest);

            var adminOptional = usersRepository.findByEmail(adminEmail);
            adminOptional.ifPresent(admin -> {
                admin.setAdmin(true);
                usersRepository.save(admin);
            });

            var createUserRequest = new PostUsersRequest();
            createUserRequest.setEmail("user@example.org");
            createUserRequest.setNickname("Luser");
            createUserRequest.setPassword("IamUser");
            this.usersService.registerUser(createUserRequest);
        }
    }

    private void createQuotes() {
        if (this.quotesService.getQuotes().isEmpty()) {
            var famousLastWords = new Quote();
            famousLastWords.setText("Famous last words...");
            famousLastWords.setReal(true);
            famousLastWords.setExpires(Instant.now().plus(1, ChronoUnit.DAYS));
            this.quotesService.createQuote(famousLastWords);

            var aiQuote1 = new Quote();
            aiQuote1.setText("European cuisine is a flavorful symphony, where every dish tells a tale of heritage, blending tradition with innovation on the palate.");
            aiQuote1.setReal(false);
            aiQuote1.setExpires(Instant.now().plus(2, ChronoUnit.DAYS));

            this.quotesService.createQuote(aiQuote1);

            var aiQuote2 = new Quote();
            aiQuote2.setText("The past is a canvas painted with the hues of memory, shaping our present and guiding our future with its timeless wisdom.");
            aiQuote2.setReal(false);
            aiQuote2.setExpires(Instant.now().plus(3, ChronoUnit.DAYS));
            this.quotesService.createQuote(aiQuote2);

            var codecoolQuote1 = new Quote();
            codecoolQuote1.setText("Codecool: where confusion finds a home, and bugs throw the wildest parties in your code.");
            codecoolQuote1.setReal(false);
            codecoolQuote1.setExpires(Instant.now().plus(4, ChronoUnit.DAYS));
            this.quotesService.createQuote(codecoolQuote1);

            var codecoolQuote2 = new Quote();
            codecoolQuote2.setText("Codecool: where passion meets innovation, empowering minds to craft the digital future, one line of code at a time.");
            codecoolQuote2.setExpires(Instant.now().plus(5, ChronoUnit.DAYS));
            codecoolQuote2.setReal(false);
            this.quotesService.createQuote(codecoolQuote2);

            var codecoolQuote3 = new Quote();
            codecoolQuote3.setText("Codecool: where debugging is a superpower and coffee is the secret sauce for coding miracles!");
            codecoolQuote3.setReal(false);
            codecoolQuote3.setExpires(Instant.now().plus(6, ChronoUnit.DAYS));
            this.quotesService.createQuote(codecoolQuote3);

            var codecoolQuote4 = new Quote();
            codecoolQuote4.setText("I really liked to be there [Codecool]. Inspiring environment, big emphasis on soft skills.");
            codecoolQuote4.setReal(true);
            codecoolQuote4.setExpires(Instant.now().plus(365, ChronoUnit.DAYS));
            this.quotesService.createQuote(codecoolQuote4);
        }
    }
}
