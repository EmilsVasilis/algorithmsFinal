import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class readStops {
    // Create the two arrays needed to fill the TST
    public ArrayList<String> keys = new ArrayList<String>();
    public ArrayList<String> values = new ArrayList<String>();

    //Read in all the stop names
    public ArrayList<String> readKeys(String fileName) {
        try {
            File file = new File(fileName);
            BufferedReader br = new BufferedReader(new FileReader(file));
            br.readLine();
            String temp;
            //Loop through the file line by line
            while ((temp = br.readLine()) != null) {
                //Trim and split up the line
                temp = temp.trim();
                String[] key = temp.split(",");
                //Remove any keywords at the start of the name
                if (key[2].contains("FLAGSTOP ")) {
                    key[2] = moveKeyWord(key[2]);
                }
                if (key[2].contains("WB ")) {
                    key[2] = moveKeyWord(key[2]);
                }
                if (key[2].contains("NB ")) {
                    key[2] = moveKeyWord(key[2]);
                }
                if (key[2].contains("SB ")) {
                    key[2] = moveKeyWord(key[2]);
                }
                if (key[2].contains("EB")) {
                    key[2] = moveKeyWord(key[2]);
                }
                keys.add(key[2]);

            }
        } catch (Exception e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
        return keys;
    }
    //Read in just the info, the full line
    public ArrayList<String> readValues(String fileName) {

        try {
            File file = new File(fileName);
            BufferedReader br = new BufferedReader(new FileReader(file));
            br.readLine();
            String temp;
            while ((temp = br.readLine()) != null) {
                values.add(temp);
            }
        } catch (Exception e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
        return values;
    }

    // Method to move the keyword using a stringbuilder to take the string apart and put back together
    public String moveKeyWord(String str) {

        String[] tmp = str.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        String keyword = tmp[0];
        //Copy the array but shift it to the left one position
        System.arraycopy(tmp, 1, tmp, 0, tmp.length - 1);
        //The empty last entry can now be filled with the keyword
        tmp[tmp.length - 1] = keyword;
        //Put the string back together
        for (String s : tmp) {
            stringBuilder.append(s);
            stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }
}