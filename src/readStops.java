import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class readStops {

    public ArrayList<String> keys = new ArrayList<String>();
    public ArrayList<String> values = new ArrayList<String>();


    public ArrayList<String> readKeys(String fileName) {
        try {
            File file = new File(fileName);
            BufferedReader br = new BufferedReader(new FileReader(file));
            br.readLine();
            String temp;
            while ((temp = br.readLine()) != null) {
                temp = temp.trim();
                String[] key = temp.split(",");
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

    public String moveKeyWord(String str) {

        String[] tmp = str.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        String keyword = tmp[0];
        System.arraycopy(tmp, 1, tmp, 0, tmp.length - 1);
        tmp[tmp.length - 1] = keyword;
        for (String s : tmp) {
            stringBuilder.append(s);
            stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }
}