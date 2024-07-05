# Illumio Word Match Count Assessment

The WordMatcher is a multithreaded Java program that does the case-insensitive word match counting of given Input sentences against a predefined set of English words.

## Setup

Download the project from the [Github Repo](https://github.com/purva-n/IllumioAssessment).
Preferred IDE to open the project - `JetBrains IntelliJ`

### Java setup
Use the java JDK 22 [jdk-22](https://www.oracle.com/java/technologies/downloads/#jdk22) to run the project / compiled jar file

Note: Your system must have Java Runtime Engine to run the project

### Input files
- The program requires atleast two input files. PredefineWordsTextFile, InputFile.
- At this time the project supports only .txt files, where inputs and words are separated on new lines.
- Place the input files in the `src/resources/input` folder under the `IllumioAssessment` root folder.

## Usage

- From the root folder `IllumioAssessment`. You have 2 options to run the project.
- Write your filenames in the <> templates given.

`Note: FileNames must be case-sensitive with exact file extension (.txt).` <br />
`In the command, write only filename and not the entire file path.`<br/> 
`Place the file names in the order of the templates provided.`


1. Run the pre-compiled jar file
```bash
java -jar ./target/IllumioAssessment-1.0-SNAPSHOT.jar <PredefineWordsTextFileName.txt> <InputFileName.txt>
```

2. Run the program by yourself.
- Compile
```bash
javac ./src/main/java/org/example/*.java
```
- Execute
```bash
java -cp "./target/classes" org.example.Main <PredefineWordsTextFileName.txt> <InputFileName.txt>
```

## Output

- As successful execution, the program automatically places the word and its match count in a new output file on every new execution.
- Find the output files, placed at `src/resources/output` folder under the `IllumioAssessment` root folder.
- The output is `.csv` file.

## Test

There is a test suite included for the project. Tests are places at `src/test` under the `IllumioAssessment` root folder.
1. Test to word match regardless of punctuation marks attached to the words of inputs.
2. Test to word match the entire word instead of a partial word match of the predefined word in the inputs.
3. Test to word match of predefined words to input sentence words are case insensitive.


## Help

For any problems related to setup, execution, please contact the repository admin or `nartam.p@northeastern.edu`