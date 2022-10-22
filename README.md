# hospital-ti
## For the correct operation of the program, bear in mind that
1. It was programmed in the IntelliJ IDEA environment with 18 Oracle OpenJDK version 18.0.2.
2. For the correct operation of the database the project needs to load the gson-2.9.1.jar library.
3. It is recommended to configure the execution environment where the code will be checked to avoid problems with the necessary libraries, to ensure the correct functioning of the database, the connections between classes and the tests of the data structures used.

## Explanation of operation
1. Using option 1 you register a patient asking for his data, you can write the UNDO command (capital letters are necessary) if you want to go back and undo an action.
2. The patient will be automatically redirected to the Hematology or General Purpose queue according to what is registered.
3. You can access these queues through options 4 and 3 respectively to manually attend the patient. After attending the patient, he/she will be removed from the corresponding queue and will remain in the reception queue until he/she is manually removed.
4. The program will automatically check the queue of one of the 2 units every 2 minutes to simulate the attention of a patient, it will show a message indicating that the corresponding 2 minutes have passed and then it will indicate the person attended (in case the queue is full, it will warn about this).
5. The patients placed in the reception queue can be attended and dispatched through option 2 of the main menu.
6. try to use option 5 of the main menu to finalize the program, in this way the program will load the registered patients to the database
