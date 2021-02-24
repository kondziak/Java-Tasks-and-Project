package sample;

import java.io.IOException;

public class FileManipulation {
    private ReadFile reader;
    private StringBuffer fileContent;

    public void setReader(ReadFile reader) throws IOException {
        this.reader = reader;
    }

    public StringBuffer getFileContent(){return fileContent;}

    public void setContent() throws IOException {
        this.fileContent = reader.getFileContent();
    }

    public Integer countChars() throws IOException {
        return fileContent.length();
    }

    public Integer countWhiteChars() throws IOException{
        Integer totalWhiteChars = 0;
        for(Character c : fileContent.toString().toCharArray()){
            if(c.equals(' ') || c.equals('\n') || c.equals('\t') || c.equals('\r')){
                totalWhiteChars++;
            }
        }
        return totalWhiteChars;
    }

    public Integer countWords() throws IOException{
        Integer totalWords = 0;
        String[] split = fileContent.toString().split( " ");
        totalWords += split.length;
        return totalWords;
    }

    public void cipherData(Integer offset){
        offset = offset % 26;
        StringBuffer buff = new StringBuffer();
        Integer index = 0;
        for(Character c : fileContent.toString().toCharArray()){
            if(c >= 'a' && c <= 'z'){
                if(c + offset > 'z'){
                    c = (char)('a'+ offset-1 - ('z' - (int)c));
                }
                else{
                    c = (char)(c +offset);
                }
            }
            else if(c >= 'A' && c <='Z'){
                if(c + offset > 'Z'){
                    c = (char)('A' + offset-1 - ('Z' - (int)c));
                }
                else{
                    c = (char)(c + offset);
                }
            }
            fileContent.setCharAt(index++, c);
        }
    }

    public void decipherData(Integer offset){
        offset = offset % 26;
        Integer index = 0;
        for(Character c : fileContent.toString().toCharArray()){
            if(c >= 'a' && c <= 'z'){
                if(c - offset < 'a'){
                    c = (char)('z'- (offset-1 - (c-'a')));
                }
                else{
                    c = (char)(c -offset);
                }
            }
            else if(c >= 'A' && c <='Z'){
                if(c - offset < 'A'){
                    c = (char)('Z' - (offset-1 - (c-'A')));
                }
                else{
                    c = (char)(c - offset);
                }
            }
            fileContent.setCharAt(index++, c);
        }
    }

}
