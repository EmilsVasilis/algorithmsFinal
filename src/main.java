import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class main {

    public static void main(String[] args) {
        readStops stops = new readStops();
        ArrayList<String> keys = stops.readKeys("src/stops.txt");
        ArrayList<String> values = stops.readValues("src/stops.txt");
        readNetwork net = new readNetwork();
        List<readNetwork.Node> graph = net.readStopTimes("src/stop_times.txt");
        //System.out.println(values.toString());
        TST tst = new TST();
        for(int i = 0; i < keys.size(); i++){
            tst.put(values.get(i), keys.get(i));
        }
        //System.out.println(tst.valuesWithPrefix("F").toString());
        for (readNetwork.Node g: graph
             ) {
            System.out.println(g.stop);
            System.out.println(g.children.size());
        }
    }
}