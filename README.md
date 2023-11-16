# Human or AI

The project is split into two separate projects. One client application written in Typescript and a server application written in Java.

## Development Setup
### Server
Switch to the server folder
```
cd server
```
install the java dependencies:
```
mvn install
```

The quote generator is based on [GPT4All](https://gpt4all.io/). In order for the quote generator to work you need to download a language model from [here](https://gpt4all.io/index.html). In case you unsure which model to pick use the `orca-mini-13b.ggmlv3.q4_0.bin model.


Create a copy of the `application.properties` file
```
cp src/main/resources/application.properties src/main/resources/application-dev.properties
```
and adjust `application-dev.properties` variables for development.

Start server with `dev` profile:
```
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Upon starting the server it will automatically create the needed database tables. New user accounts can be created using the client's register form. The project automatically creates an admin account with the email `admin@example.org` and the password `IamAdmin` and a user account with the email `user@example.org` and the password `IamUser`. New quotes can be added using the admin account.

### Client
```
cd client

# Install dependencies:
npm install

# Create `.env` file for development:
cp example.env .env
# Then adjust `.env` variables for development...

# Start frontend server:
npm run dev
```

## Project structure

### Backend

The backend is contained in the `at.codecool.humanorai` consisting of the following sub packages
- `config` contains the config classes (security, cors)
- `controller` contains the controllers for all the endpoints
- `model` contains all the model classes
- `repositories` contains all the repositories
- `services` contains all the service classes

Everything that does not fit into these packages is located directly in top level package.

