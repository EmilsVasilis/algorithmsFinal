import java.io.IOException;
import java.util.*;

public class main {

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the Vancouver transit mapper");
        System.out.println("Loading Transport Map, Please wait...");
        readStops stops = new readStops();
        ArrayList<String> keys = stops.readKeys("src/stops.txt");
        ArrayList<String> values = stops.readValues("src/stops.txt");
        TST tst = new TST();
        for (int i = 0; i < keys.size(); i++) {
            tst.put(values.get(i), keys.get(i));
        }

        readTrips t = new readTrips();
        t.readTrips("src/stop_times.txt");
        readNetwork net = new readNetwork();
        net.readStopTimes("src/stops.txt", "src/stop_times.txt", "src/transfers.txt");
        ArrayList<readNetwork.Vertex> v = new ArrayList<>();
        v = readNetwork.allStops;

        Scanner in = new Scanner(System.in);

        boolean exit = false;

        while (!exit) {

            System.out.println("Select and option to continue or enter exit to close the application:");
            System.out.println("1. Get the shortest path between two stops");
            System.out.println("2. Search for a bus stop by name");
            System.out.println("3. Search for all trips with a certain arrival time");
            if (in.hasNextInt()) {
                int option = in.nextInt();
                if (option == 1) {
                    System.out.println("Get the shortest path between two stops, enter the stop ID of the starting stop");
                    String start = in.next();
                    if (start.equalsIgnoreCase("exit")) {
                        exit = true;
                    } else if (net.getVertex(readNetwork.allStops, start) != null) {
                        System.out.println("Please enter the destination stop ID");
                        String finish = in.next();
                        if (net.getVertex(readNetwork.allStops, finish) != null) {
                            readNetwork.computePaths(net.getVertex(v, start));
                            double cost = net.getVertex(v, finish).minDistance;
                            List<readNetwork.Vertex> path = readNetwork.getShortestPathTo(net.getVertex(v, finish));
                            if(cost <= 0 || path.size() == 0){
                                System.out.println("Path not found!");
                            }else {
                                System.out.println("Cost of trip: " + cost);
                                System.out.print("Stops on the trip: ");
                                for (readNetwork.Vertex ver: path
                                     ) {
                                    System.out.print(ver.name + " ");
                                }
                                System.out.println("\n");

                            }

                        } else {
                            System.out.println("Not a valid stop");
                        }
                    } else {
                        System.out.println("Not a valid stop");
                    }
                } else if (option == 2) {
                    System.out.println("Enter name of stop you are searching for");
                    String stop = in.next();
                    if (stop.equalsIgnoreCase("exit")) {
                        exit = true;
                    } else {
                        ArrayList<String> returnedStops = tst.valuesWithPrefix(stop.toUpperCase());
                        if (returnedStops.size() == 0) {
                            System.out.println("No stops found");
                        }else {
                            for (String s : returnedStops) {
                                s = s.trim();
                                System.out.println(s);
                            }
                        }
                    }

                } else if (option == 3) {
                    System.out.println("Enter the time to search by, in the format hh:mm:ss");
                    String time = in.next();
                    if (time.equalsIgnoreCase("exit")) {
                        exit = true;
                    } else {
                        String[] times = time.split(":");
                        if (times.length < 2 || Integer.parseInt(times[0]) > 23 || Integer.parseInt(times[1]) > 59 || Integer.parseInt(times[2]) > 59) {
                            System.out.println("Time entered is not valid!");
                        } else {
                            ArrayList<readTrips.trip> tmp = t.getTripsWithTime(time);
                            System.out.println("trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type,shape_dist_traveled");
                            if(tmp.size() == 0){
                                System.out.println("No trip with this time found!");
                            }else {
                                for (readTrips.trip trp : tmp) {
                                    System.out.println(trp.info);
                                }
                            }
                        }
                    }
                } else {
                    System.out.println("Error not a valid option");
                }
            } else {
                String opt = in.next();
                if (opt.equalsIgnoreCase("exit")) {
                    exit = true;
                }
            }

        }

    }
}