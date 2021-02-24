package sample;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile {
    private BufferedReader fileRead = null;

    ReadFile(String filepath){
        try{
            BufferedReader tempReader = new BufferedReader(new FileReader(filepath));
            fileRead = tempReader; }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeFile() throws IOException {
        if(fileRead != null) fileRead.close();
    }

    public StringBuffer getFileContent() throws IOException{
        String line = null;
        StringBuffer fileContent = new StringBuffer();
        while((line = fileRead.readLine()) != null){
            fileContent.append(line);
        }
        return fileContent;
    }
}
