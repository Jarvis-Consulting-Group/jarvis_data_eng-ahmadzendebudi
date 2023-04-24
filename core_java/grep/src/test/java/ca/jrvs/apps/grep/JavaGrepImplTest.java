package ca.jrvs.apps.grep;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

public class JavaGrepImplTest {
  @Test
  public void listFiles() throws IOException {
    File fileRoot = null;
    try {
      fileRoot = new File("./tmp/testing/listFiles");
      fileRoot.mkdirs();

      FileWriter fileWriter;

      File file1 = new File(fileRoot, "testFile1.txt");
      fileWriter = new FileWriter(file1);
      fileWriter.write("Two\nLines");
      fileWriter.close();

      File file2 = new File(fileRoot, "testFile2.txt");
      fileWriter = new FileWriter(file2);
      fileWriter.write("Three\nLines\nInThisFile");
      fileWriter.close();

      JavaGrepImpl javaGrep = new JavaGrepImpl(null, fileRoot.getAbsolutePath(), null);

      List<File> files = javaGrep.listFiles(fileRoot.getAbsolutePath());

      assertEquals(2, files.size());
      assertTrue(files.get(0).getAbsolutePath().equals(file1.getAbsolutePath()) ||
          files.get(0).getAbsolutePath().equals(file2.getAbsolutePath()));

      file1.delete();
      file2.delete();

      files = javaGrep.listFiles(fileRoot.getAbsolutePath());
      assertEquals(0, files.size());
    } finally {
      FileUtils.deleteDirectory(fileRoot);
    }

  }

  @Test
  public void ListLines() throws IOException {
    File fileRoot = null;
    try {
      fileRoot = new File("./tmp/testing/listLines");
      fileRoot.mkdirs();

      FileWriter fileWriter;

      File file1 = new File(fileRoot, "testFile1.txt");
      fileWriter = new FileWriter(file1);
      fileWriter.write("Two\nLines");
      fileWriter.close();

      File file2 = new File(fileRoot, "testFile2.txt");
      fileWriter = new FileWriter(file2);
      fileWriter.write("Three\nLines\nInThisFile");
      fileWriter.close();

      JavaGrepImpl javaGrep = new JavaGrepImpl(null, fileRoot.getAbsolutePath(), null);

      List<String> lines = javaGrep.listLines(file1);

      assertEquals(2, lines.size());
      assertEquals("Two", lines.get(0));
      assertEquals("Lines", lines.get(1));

      lines = javaGrep.listLines(file2);

      assertEquals(3, lines.size());
      assertEquals("Three", lines.get(0));
      assertEquals("Lines", lines.get(1));
      assertEquals("InThisFile", lines.get(2));
    } finally {
      FileUtils.deleteDirectory(fileRoot);
    }
  }

  @org.junit.jupiter.api.Test
  public void writeToFile() throws IOException {
    File outFileDir = new File("./tmp/testing/writeToFile");
    outFileDir.mkdirs();
    File outFile = new File(outFileDir, "outFile.txt");
    JavaGrepImpl javaGrep = new JavaGrepImpl(null, null, outFile.getAbsolutePath());
    
    List<String> lines = Arrays.asList("Line1", "Line2", "Line3");
    javaGrep.writeToFile(lines);
    List<String> LinesInFile = Files.lines(outFile.toPath()).collect(Collectors.toList());
    assertIterableEquals(lines, LinesInFile);

    lines = Arrays.asList();
    javaGrep.writeToFile(lines);
    LinesInFile = Files.lines(outFile.toPath()).collect(Collectors.toList());
    assertIterableEquals(lines, LinesInFile);
  }
}
