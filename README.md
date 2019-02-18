# CombinationGenerator
A simple collection subsets generator made in Java. Includes a proper command line interface for end user.

If you want to only use the application, without having to compile it, download the provided `jar` file in the `dist` folder. Run it with the command below:

`java -jar combinations.jar`

If you want to print the output to a file, just use your system redirector. For example:

`java -jar combinations.jar -g -i -r -d "," -k 6 10 12 23 30 39 45 46 > m.txt`
