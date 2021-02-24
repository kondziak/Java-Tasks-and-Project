package sample;

import java.io.IOException;

public class Main{

    public static void main(String[] args) {
        ReadFile file = new ReadFile("wiersz.txt");
        try {
            FileManipulation manipulation = new FileManipulation();
            manipulation.setReader(file);
            manipulation.setContent();
            System.out.println(manipulation.countChars() + " " + manipulation.countWhiteChars() +
                    " " + manipulation.countWords());
            manipulation.cipherData(7);
            WriteFile timeToWrite = new WriteFile("output.txt");
            timeToWrite.writeToFile(manipulation.getFileContent().toString());
            manipulation.decipherData(7);
            file.closeFile();
            timeToWrite.closeFile();
        } catch(IOException error){
            error.printStackTrace();
        }
    }
}
