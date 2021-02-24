package sample;

import java.io.FileWriter;
import java.io.IOException;

public class WriteFile {
        private FileWriter writer = null;

        WriteFile(String path) throws IOException {
            writer = new FileWriter(path);
        }

        public void writeToFile(String content) throws IOException {
            for(Character c : content.toCharArray()){
                writer.write(c);
            }
        }

        public void closeFile() throws IOException {if(writer != null) writer.close();}
}
