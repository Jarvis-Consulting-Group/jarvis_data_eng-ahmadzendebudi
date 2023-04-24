package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JavaGrepImpl implements JavaGrep{

  private final Logger logger = LoggerFactory.getLogger(JavaGrepImpl.class);

  private String regex;
  private String rootPath;
  private String outFile;

  @Override
  public void process() throws IOException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'process'");
  }

  @Override
  public List<File> listFiles(String rootDir) {
    if (rootDir == null) {
      throw new RuntimeException("rootDir is null");
    }
    File rootDirFile = new File(rootDir);
    if (!rootDirFile.isDirectory()) {
      return new ArrayList<>();
    }
    return _listFiles(rootDirFile);
  }

  /**
   * List files inside a given directory. If the given file is not a directory, an empty list is returned
   * @param rootDir The root directory to list the containing files  
   * @return A list of files inside the rootDir
   */
  private List<File> _listFiles(File rootDir) {
    List<File> files = new ArrayList<>();
    
    for (File file : rootDir.listFiles()) {
      if (file.isDirectory()) {
        files.addAll(_listFiles(file));
      } else {
        files.add(file);
      }
    }
    return files;
  }

  @Override
  public List<String> listLines(File inputFile) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'listLines'");
  }

  @Override
  public boolean containsPattern(String line) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'containsPattern'");
  }

  @Override
  public void writeToFile(List<String> lines) throws IOException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'writeToFile'");
  }

}
