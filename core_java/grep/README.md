# Introduction
The grep app is a powerful tool that enables users to search for any regular expression pattern in files within a directory. The app can handle directories with a hierarchical structure and files at any depth level. The app performs a recursive traversal of the directory tree, reads the files, and writes the lines that match the pattern to an output file. The app is optimized for processing large amounts of data by using buffers and stream API, which avoid loading all the files into memory simultaneously. The app is implemented using Java `nio`, lambda and stream API, docker, maven, and junit technologies.

# Quick Start
The app can be executed in two ways: using the Dockerfile or running directly on a JVM inside the host machine. Both methods require building the image or the jar file with the following commands: `docker build -t zendebudi/grep .` and `mvn package`. The app accepts three arguments as input:

- `regex`: The regex pattern to be matched in the files
- `rootPath`: The path to the directory that contains the files to be searched
- `outFile`: The path to a file (either existing or non-existing) where the output lines will be stored. If the file already exists, it will be overwritten with the new output.

# Implemenation
The JavaGrep interface defines the following methods for implementing the pattern matching functionality:

- `process`: This method encapsulates the main logic of the task, which involves listing files, reading lines and checking for pattern matches.
- `listFiles`: This method recursively traverses a given directory and returns all the files under it.
- `listLines`: This method reads a file and returns all the lines in it as a list of strings.
- `containsPattern`: This method determines if a given line matches the regex pattern specified by the user.

## Pseudocode
The process method performs the following steps:

1. It creates a stream of all the files to be read from a given source.
2. It transforms the stream of files into a stream of lines by reading each file content.
3. It applies a regex pattern to filter the stream of lines and obtain a new stream of matching lines.
4. It writes the stream of matching lines to a specified output file.

## Performance
The app leverages buffers and stream APIs to achieve a high level of performance and memory efficiency.

# Test
To verify the correctness and robustness of the proposed solution, unit testing is performed on all the methods in the `JavaGrepImpl` class using the JUnit framework. This allows for checking the expected behavior and output of each method under various scenarios and inputs in an isolated and modular manner.

# Deployment
The app can be deployed in two ways:

### Maven and JVM
This method requires Java 8 and Maven to be installed locally on the system. All the dependencies are specified in the `pom.xml` file, which allows the app to be packaged on any platform. To package the app, use the command `mvn package`.

To run the app, use the following command:
```
java -jar target/grep*.jar REGEX ROOTDIR OUTPUT_FILE
```
where `REGEX` is the regular expression to search for, `ROOTDIR` is the directory to search in, and `OUTPUT_FILE` is the file to write the matched lines to.

### Docker
This method requires Docker to be installed locally on the system. A Dockerfile is provided in the root folder of the app, which contains all the necessary steps to create an image of the app. The host machine does not need to have Java or Maven installed. To build an image of the app, use the command `docker build -t zendebudi/grep .`.

To run the app, use the following command:
```
docker run -v ROOTDIR:/rootdir -v OUTPUT:output zendebudi:grep REGEX /rootdir output/OUTPUT_FILE
```
where `REGEX`, `ROOTDIR` and `OUTPUT_FILE` are the same as above, and `OUTPUT` is the directory where `OUTPUT_FILE` resides on the host machine.
# Improvement
- To optimize the performance of the app and utilize the full potential of modern processors with multiple cores, Project Reactor can be employed to implement the app in a reactive manner, enabling parallel processing across multiple threads.
- To prevent the output file from becoming too large and difficult to handle, a mechanism can be implemented to divide the output into multiple files based on a specified threshold.
- To enhance the user experience and provide feedback on the app’s progress, a progress report can be incorporated into the app to display relevant information to the user during the execution of the app.