package ca.jrvs.apps.grep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

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
}
