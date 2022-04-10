import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class readTrips {
    ArrayList<trip> allTrips;

    //Read in all the trip times
    public ArrayList<trip> readTrips(String fileName) {
        try {
            //Create the file and reader
            File file = new File(fileName);
            BufferedReader br = new BufferedReader(new FileReader(file));
            br.readLine();
            //Create trips array and temp string for the loop
            String temp;
            ArrayList<trip> trips = new ArrayList<trip>();

            while ((temp = br.readLine()) != null) {
                //Take each line and split it using the commas
                temp = temp.trim();
                String[] key = temp.split(",");
                //Create the trip object from the info in the file
                trip cur = new trip(key[1], temp, Integer.parseInt(key[0]));
                trips.add(cur);
            }
            allTrips = trips;
            return trips;
        } catch (Exception e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
        return null;
    }

    //Trip data class
    static class trip {
        String time;
        String info;
        int ID;

        trip(String time, String info, int ID) {
            this.time = time;
            this.info = info;
            this.ID = ID;
        }

        int getID() {
            return this.ID;
        }


    }

    //Loop through the trips and find ones with the specified matching time
    public ArrayList<trip> getTripsWithTime(String time) {
        ArrayList<trip> a = new ArrayList<trip>();
        for (trip t : allTrips
        ) {
            String tmp = t.time.trim();
            if (tmp.equals(time)) {
                a.add(t);
            }
        }

        a.sort(Comparator.comparing(trip::getID));

        return a;
    }

}
