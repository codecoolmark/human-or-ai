# Human or AI

The project is split into two separate projects. One client application written in Typescript and a server application written in Java.

## Development Setup
__Server:__
Switch to the server folder
```
cd server
```
install the java dependencies:
```
mvn install
```

The quote generator is based on [GPT4All](https://gpt4all.io/). In order for the quote generator to work you need to download a language model from [here](https://gpt4all.io/index.html). In case you unsure which model to pick use the `ggml-gpt4all-j-v1.3-groovy` model.


Create a copy of the `application.properties` file
```
cp src/main/resources/application.properties src/main/resources/application-dev.properties
```
and adjust `application-dev.properties` variables for development.

Start server with `dev` profile:
```
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Upon starting the server it will automatically create the needed database tables. User accounts can be created using the client's register form. If you want to create quotes you need to set `is_admin` to true for at least one user in the database.

__Client:__
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
