#### Notes:

 * Statistics calculated 10000 milisecond sensivity (this can be improved with using a bigger array, current is fixed 60 elements size)
 * While test script running there are delays to test expired transactions (total about 30 seconds)

```bash
mvn clean package
java -jar target/n26-test-project-0.1.0.jar
```
