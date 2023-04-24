package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface JavaGrep {
  /**
   * Top level search workflow
   * @throws IOException
   */
  void process() throws IOException;

  /**
   * Trevese a given directory and return all files
   * @param rootDir input Directory
   * @return files under the rootDir
   */
  List<File> listFiles(String rootDir);

  /**
   * Read a file and return all the lines
   * @param inputFile file to be read
   * @return lines of text inside the file
   */
  List<String> listLines(File inputFile);

  /**
   * Check if a line matches the regex pattern (passed by user)
   * @param line input string
   * @return true if these is a match
   */
  boolean containsPattern(String line);

  /**
   * Write lines to the out file
   * @param lines lines to be written to the file
   * @throws IOException if write failed
   */
  void writeToFile(List<String> lines) throws IOException;

  String getRootPath();
  void setRootPath(String rootPath);

  String getRegex();
  void setRegex(String regex);

  String getOutFile();
  void setOutFile(String outfile);
}
