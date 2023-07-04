# Human or AI
## Development Setup
__Backend:__
```
cd server

# Install dependencies:
mvn install

# Copy properties to `dev` profile properties:
cp src/main/resources/application.properties src/main/resources/application-dev.properties
# Then adjust `application-dev.properties` variables for development...

# Start server with `dev` profile:
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

__Frontend:__
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
