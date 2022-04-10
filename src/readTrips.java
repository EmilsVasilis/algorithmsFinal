import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class readTrips  {
    ArrayList<trip> allTrips;
    public ArrayList<trip> readTrips(String fileName) {
        try{
            File file = new File(fileName);
            BufferedReader br = new BufferedReader(new FileReader(file));
            br.readLine();
            String temp;
            ArrayList<trip> trips = new ArrayList<trip>();

            while((temp = br.readLine()) != null){
                temp = temp.trim();
                String[] key = temp.split(",");
                trip cur = new trip(key[1], temp, Integer.parseInt(key[0]));
                trips.add(cur);
            }
            allTrips = trips;
            return trips;
        }catch(Exception e){
            System.out.println("File not found");
            e.printStackTrace();
        }
        return null;
    }



    static class trip {
        String time;
        String info;
        int ID;
        trip(String time, String info, int ID){
            this.time = time;
            this.info = info;
            this.ID = ID;
        }
        int getID(){
            return this.ID;
        }


    }

    public ArrayList<trip> getTripsWithTime ( String time){
        ArrayList<trip> a = new ArrayList<trip>();
        for (trip t: allTrips
             ) {
            String tmp = t.time.trim();
            if(tmp.equals(time)){
                a.add(t);
            }
        }

        a.sort(Comparator.comparing(trip::getID));

        return a;
    }

}
