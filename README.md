# Email Client

This project is a Java client for sending emails using the Mailtrap API.

## Requirements

- Java 17
- Maven

## Setup

1. Clone the repository:
   ```sh
   git clone https://github.com/mlunysh/email-client.git
   cd email-client
   ```

2. Build the project:
   ```sh
   mvn package
   ```

3. To run the tests, please update the `api.token` and `recipient.email` in `test.properties` with your mailtrap API token and your email and run the command:

   ```sh
   mvn test
   ```

## Usage

To use this client, create an instance and call the `send` method with the required parameters:

```java
String apiToken = "mailtrap_api_token";
EmailClient emailClient = new EmailClient(apiToken);
emailClient.send(...);
```


### Using as standalone library

If you want to use this in your project, you can include it as a dependency. Here is an example:

1. Add the JAR file to your project's `lib` directory.
2. Add the following dependency to your `pom.xml`:

    ```xml
    <dependency>
        <groupId>com.railsware</groupId>
        <artifactId>email-client</artifactId>
        <version>1.0-SNAPSHOT</version>
        <scope>system</scope>
        <systemPath>${project.basedir}/lib/email-client-1.0-SNAPSHOT.jar</systemPath>
    </dependency>
    ```
