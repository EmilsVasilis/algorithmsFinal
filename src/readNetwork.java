import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class readNetwork {
    List<Node> g = new ArrayList<>();
    public List<Node> readStopTimes(String fileName) {
        try{
            File file = new File(fileName);
            BufferedReader br = new BufferedReader(new FileReader(file));
            br.readLine();
            String temp;
            String prevID = " ";
            int prevStop = 0;

            while((temp = br.readLine()) != null){
                temp = temp.trim();
                String[] key = temp.split(",");
                Node node = new Node(Integer.parseInt(key[3]));
                if(getNodeByNum(g,Integer.parseInt(key[3])) == null){
                    g.add(node);
                }
                if(key[0].equals(prevID)){
                    getNodeByNum(g,prevStop).add_child(Integer.parseInt(key[3]), 1);

                }
                prevID = key[0];
                prevStop = Integer.parseInt(key[3]);

            }
            return g;
        }catch(Exception e){
            System.out.println("File not found");
            e.printStackTrace();
        }
        return null;
    }





    /**
     * Node object to store node data in an array
     */
    static class Node {
        int stop;

        // Adjacency list that shows the
        // vertexNumber of child vertex
        // and the weight of the edge
        List<Pair> children;

        Node(int stop)
        {
            this.stop = stop;
            children = new ArrayList<>();
        }

        // Function to add the child for
        // the given node
        void add_child(int stop, int cost)
        {
            Pair p = new Pair(stop, cost);
            children.add(p);
        }

    }

    static class Pair
    {
        int first;
        int second;

        public Pair(int first, int second)
        {
            this.first = first;
            this.second = second;
        }
    }
    static Node getNodeByNum(List<Node> g, int stop){
        for(int i = 0 ; i < g.size(); i++){
            if(g.get(i).stop == stop ){
                return g.get(i);
            }
        }
        return null;
    }
}
