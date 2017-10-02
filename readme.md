#### Notes:

 * Statistics calculated with grouping the transactions by their seconds (this can be improved with using a bigger array, current is fixed 60 elements size)
 * While test script is running there are delays because of testing expired transactions (total about 30 seconds)

```bash
mvn clean package
java -jar target/n26-test-project-0.1.0.jar
```
