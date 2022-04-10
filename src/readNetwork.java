import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Collections;

public class readNetwork {

    //Global stops array
    static ArrayList<Vertex> allStops = new ArrayList<Vertex>();

    //Read in all the files
    public void readStopTimes(String stopsFile, String timesFile, String transfersFile) {
        try {
            //Create the file and reader
            File file = new File(stopsFile);
            BufferedReader br = new BufferedReader(new FileReader(file));
            br.readLine();
            //Create temp string for the loop
            String temp;
            //Variables to track the stop before for creating edges
            String prevID = " ";
            String prevStop = "";

            while ((temp = br.readLine()) != null) {
                temp = temp.trim();
                String[] key = temp.split(",");
                Vertex stop = new Vertex(key[0]);
                //Add all the stops to the allStops array
                if (!allStops.contains(stop)) {
                    allStops.add(stop);
                }
            }
            //Create the file and reader
            file = new File(transfersFile);
            br = new BufferedReader(new FileReader(file));
            br.readLine();
            while ((temp = br.readLine()) != null) {
                temp = temp.trim();
                String[] key = temp.split(",");
                //Add the edges from the transfers
                if (key[2].equals("0")) {
                    Edge e = new Edge((getVertex(allStops, key[1])), 2.0);
                    getVertex(allStops, key[0]).adjacencies.add(e);
                } else {
                    Edge e = new Edge((getVertex(allStops, key[1])), Integer.parseInt(key[3]) / 100.0);
                    getVertex(allStops, key[0]).adjacencies.add(e);
                }
            }
            //Create the file and reader
            file = new File(timesFile);
            br = new BufferedReader(new FileReader(file));
            br.readLine();
            Vertex cur;
            while ((temp = br.readLine()) != null) {
                temp = temp.trim();
                String[] key = temp.split(",");
                //Add the edges from stop_times
                if (prevID.equals(key[0])) {
                    Edge e = new Edge((getVertex(allStops, key[3])), 1.0);
                    getVertex(allStops, prevStop).adjacencies.add(e);
                }
                prevID = key[0];
                prevStop = key[3];

            }

        } catch (Exception e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

    //Vertex data class
    class Vertex implements Comparable<Vertex> {
        public final String name;
        public ArrayList<Edge> adjacencies = new ArrayList<Edge>();
        public double minDistance = Double.POSITIVE_INFINITY;
        public Vertex previous;

        public Vertex(String argName) {
            name = argName;
        }

        public int compareTo(Vertex other) {
            return Double.compare(minDistance, other.minDistance);
        }

    }

    //Edge data class
    class Edge {
        public final Vertex target;
        public final double weight;

        public Edge(Vertex argTarget, double argWeight) {
            target = argTarget;
            weight = argWeight;
        }
    }

    //Use Dijkstra's algorithm to compute all the paths from source to all other stops
    public static void computePaths(Vertex source) {
        //Reset at the start to avoid memory issues and incorrect routes
        for (Vertex v : allStops
        ) {
            v.minDistance = Double.POSITIVE_INFINITY;
            v.previous = null;
        }
        //Set the source vertex distance to 0 so that algorithm starts there
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
        vertexQueue.add(source);

        while (!vertexQueue.isEmpty()) {
            Vertex u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Edge e : u.adjacencies) {
                Vertex v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
                if (distanceThroughU < v.minDistance) {
                    vertexQueue.remove(v);
                    v.minDistance = distanceThroughU;
                    v.previous = u;
                    vertexQueue.add(v);
                }
            }
        }
    }
    //Get the path from a vertex to the destination
    public static List<Vertex> getShortestPathTo(Vertex destination) {
        List<Vertex> path = new ArrayList<Vertex>();
        // Run backwards through the vertices to get the path
        for (Vertex vertex = destination; vertex != null; vertex = vertex.previous) {
            path.add(vertex);
        }
        // Reverse the path so that its in the right order
        Collections.reverse(path);
        return path;
    }

    //Get a vertex given a stop_id
    public Vertex getVertex(ArrayList<Vertex> vertices, String name) {
        //Loop through the vertices and return the vertex if it exists
        for (Vertex v : vertices
        ) {
            if (v.name.equals(name)) {
                return v;
            }
        }
        return null;
    }

}
