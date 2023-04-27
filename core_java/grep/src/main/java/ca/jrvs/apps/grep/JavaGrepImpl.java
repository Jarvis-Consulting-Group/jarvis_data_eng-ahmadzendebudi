package ca.jrvs.apps.grep;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JavaGrepImpl implements JavaGrep {

  private final Logger logger = LoggerFactory.getLogger(JavaGrepImpl.class);

  private String regex;
  private String rootPath;
  private String outFile;

  @Override
  public void process() throws IOException {
    Stream<Path> files = listFiles(rootPath);
    Stream<String> matchingLines = files
        .flatMap(file -> this.listLines(file))
        .filter(line -> this.containsPattern(line));
    this.writeToFile(matchingLines);
  }

  @Override
  public Stream<Path> listFiles(String rootDir) {
    if (rootDir == null) {
      throw new RuntimeException("rootDir is null");
    }
    
    Path rootDirPath = Paths.get(rootDir);

    if (!Files.isDirectory(rootDirPath)) {
      return Stream.of(rootDirPath);
    }

    return _listFiles(rootDirPath);
  }

  /**
   * List files inside a given directory. If the given file is not a directory, an
   * empty list is returned
   * 
   * @param rootDir The root directory to list the containing files
   * @return A list of files inside the rootDir
   * @throws IOException
   */
  private Stream<Path> _listFiles(Path rootDir) {
    try {
      return Files.list(rootDir)
        .flatMap(path -> {
          if (!Files.isDirectory(path)) {
            return Stream.of(path);
          }
          
          try {
            return Files.list(path);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    
  }

  @Override
  public Stream<String> listLines(Path inputFile) {
    if (inputFile == null) {
      throw new RuntimeException("inputFile is null");
    }
    try {
      return Files.lines(inputFile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean containsPattern(String line) {
    Pattern pattern = Pattern.compile("^" + this.regex + "$");
    return pattern.matcher(line).find();
  }

  @Override
  public void writeToFile(Stream<String> lines) throws IOException {
    Path outFile = Paths.get(this.outFile);
    Files.deleteIfExists(outFile);
    Files.createFile(outFile);

    if (lines == null) {
      return;
    }

    BufferedWriter writer = Files.newBufferedWriter(outFile);
    lines.forEach(t -> {
      try {
        writer.write(t);
        writer.newLine();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    writer.close();
  }

}
