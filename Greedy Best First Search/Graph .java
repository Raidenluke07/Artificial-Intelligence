import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Created by HuGuodong on 2017/10/26.
 */

public class Graph {


    private Map<String, AdjList> graph;


    /**
     * test main
     *
     * @param args
     */
    public static void main(String[] args) {
        GBFS("Arad", "Bucharest");

    }

    /**
     * algorithm: Greedy Best First Search
     * Greedy Best-First Search
     * use heuristic function as evaluation function: f(n) = h(n)
     * always expands the node that is closest to the goal node
     * eats the largest chunk out of the remaining distance, hence, “greedy”
     * @param start start node
     * @param end   end node
     */
    public static void GBFS(String start, String end) {
        Graph roads = new Graph();
        roads.init();
        AdjList adjList = roads.graph.get(start);
        System.out.println("Greedy Best First Search Starts!");
        AdjNode startNode = findNode(adjList, start);
        System.out.print("start from node: \n" + start + "(" + startNode.cost + ")" + "-->");
        int totalCost = 0;
        while (adjList.size() > 0) {
            AdjNode nextNode = adjList.poll();
            int nodeCost = nextNode.cost;
            totalCost += nodeCost;

            System.out.print(nextNode.name + "(" + nodeCost + ")-->");
            adjList = roads.graph.get(nextNode.name);

            if (isExist(adjList, end)) {
                int lastNodeCost = adjList.poll().cost;
                totalCost += lastNodeCost;
                System.out.println(end + "(" + lastNodeCost + ").");
                System.out.println("find path! \ntotal cost is : " + totalCost);
                return;
            }
        }
    }

    /**
     * init Touring in Romania road map
     */
    public void init() {
        graph = new HashMap<>();

        String name = "Arad";
        int cost = 366;
        AdjList adjList = new AdjList();
        adjList.add(new AdjNode("Arad", 366));
        adjList.add(new AdjNode("Zerind", 374));
        adjList.add(new AdjNode("Sibiu", 253));
        adjList.add(new AdjNode("Timisoara", 329));
        graph.put(name, adjList);

        name = "Sibiu";
        adjList = new AdjList();
        adjList.add(new AdjNode("Fagaras", 176));
        adjList.add(new AdjNode("Rimnicu", 193));
        adjList.add(new AdjNode("Rimnicu", 380));
        graph.put(name, adjList);

        name = "Fagaras";
        adjList = new AdjList();
        adjList.add(new AdjNode("Sibiu", 253));
        adjList.add(new AdjNode("Bucharest", 0));
        graph.put(name, adjList);
    }

    /**
     * whether the node name is in the adjlist
     *
     * @param adjList
     * @param name
     * @return
     */
    public static boolean isExist(AdjList adjList, String name) {
        for (AdjNode n :
                adjList) {
            if (n.name.equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * find a node by name
     *
     * @param adjList
     * @param name
     * @return
     */
    public static AdjNode findNode(AdjList adjList, String name) {
        for (AdjNode n :
                adjList) {
            if (n.name.equals(name)) {
                return n;
            }
        }
        return null;
    }

}

/**
 * adjacent list
 * priority queue
 */
class AdjList extends PriorityQueue<AdjNode> {

}

/**
 * adjacent node
 */
class AdjNode implements Comparable<AdjNode> {
    String name;
    int cost;

    public AdjNode(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    @Override
    public int compareTo(AdjNode o) {
        return Integer.compare(cost, o.cost);
    }

    @Override
    public String toString() {
        return "AdjNode{" +
                "name='" + name + '\'' +
                ", cost=" + cost +
                '}';
    }
}
