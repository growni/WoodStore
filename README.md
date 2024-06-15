To run the backend part of the project locally follow the guidlines:

1. Install MySQL server for connection with the database - https://www.mysql.com/products/workbench/
   - (Optional) During installation add MySQL workbench for easier database management and layout
   - Start mySQL server locally and authorize
2. Install IDE for the project. (Recommended Intellij IDEA - https://www.jetbrains.com/idea/)
   - Clone the project and open it with the IDE.
   - Navigate to application.properties file and adjust the following properties:
       - spring.datasource.url - adjust the port your server is running on, and additional parameters
       - spring.datasource.username - your username for MySQL
       - spring.datasource.password - your password for MySQL
       - server.port - check if the predefined port is available on your machine
3. (Optional) Navigate to the Runner class and uncomment "addEntities()" in the run method to add example entities in the database.

For setting up the frontend, follow the documentation on https://github.com/martin0626/Wood_shop_project
