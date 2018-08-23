/* Mubashir Ali          */
/* Bayesian SMS Spam Filter*/

package fileReader;

import java.nio.*;
import java.io.*;
import java.nio.channels.*;

public class FileReader
{
  private String filename;
  private String content;
  
  public FileReader(String name) throws IOException {
    filename = name;
    readContent();
  }
  
  public void readContent() throws IOException {
    // Create an input stream and file channel
    // Using first arguemnt as file name to read in
    FileInputStream fis = new FileInputStream(filename);
    FileChannel fc = fis.getChannel();

    // Read the contents of a file into a ByteBuffer
    ByteBuffer bb = ByteBuffer.allocate((int)fc.size());
    fc.read(bb);
    fc.close();

    // Convert ByteBuffer to one long String
    content = new String(bb.array());
    
    fis.close();
  }
  
  public String getContent() {
    return content;
  }
}



