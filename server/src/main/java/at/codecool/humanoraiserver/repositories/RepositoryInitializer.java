package at.codecool.humanoraiserver.repositories;

import at.codecool.humanoraiserver.controller.PostUsersRequest;
import at.codecool.humanoraiserver.services.UsersService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class RepositoryInitializer implements ApplicationRunner {

    private final Environment environment;

    private final UsersService usersService;

    private final UsersRepository usersRepository;

    public RepositoryInitializer(Environment environment, UsersService usersService, UsersRepository usersRepository) {
        this.environment = environment;
        this.usersService = usersService;
        this.usersRepository = usersRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (this.environment.matchesProfiles("dev")) {
            this.createUsers();
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
}
