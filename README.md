# CombinationGenerator
A simple collection subsets generator made in Java. Includes a proper command line interface for end user.

If you want to only use the application, without having to compile it, download the provided `jar` file in the `dist` folder. Run it with the command below:

`java -jar combinations.jar`

If you want to print the output to a file, just use your system redirector.

For example, printing a lottery draw to a txt file (“`> m.txt`”):

`java -jar combinations.jar -g -i -r -d "," -k 6 10 12 23 30 39 45 46 > m.txt`

This is a quick program for personal purposes and is _not_ meant to be used as a reference for code quality, abstraction and so forth. I literally made it in less than an hour, and I was learning to program in Java at that time, so there is also some experimentation in code quality and readability to see what suited my tastes.
