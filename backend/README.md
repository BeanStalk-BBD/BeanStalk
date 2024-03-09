# Backend
https://bootify.io/app/P2DPH0T6CYGG
This app was created with Bootify.io - tips on working with the code [can be found here](https://bootify.io/next-steps/).
Feel free to contact us for further questions.

## Development

Update your local database connection in `application.yml` or create your own `application-local.yml` file to override
settings for development.

During development it is recommended to use the profile `local`. In IntelliJ `-Dspring.profiles.active=local` can be
added in the VM options of the Run Configuration after enabling this property in "Modify options".

Lombok must be supported by your IDE. For IntelliJ install the Lombok plugin and enable annotation processing -
[learn more](https://bootify.io/next-steps/spring-boot-with-lombok.html).

After starting the application it is accessible under `localhost:8080`.

## How to configure local db to work
First:  https://www.bezkoder.com/spring-boot-sql-server/ Handle SQLServerException TCP/IP connection
Then create a SQL connectopn account: https://youtu.be/dJ6c3OgIVDM
Then edit the application.yml
