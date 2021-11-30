package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
public class Solver {

    private MinPQ<PathNode> pq;
    private PathNode minP;

    public Solver(WorldState initial) {
        PathNode initNode = new PathNode(initial, null, 0);
        pq = new MinPQ<>();
        pq.insert(initNode);
        while(!pq.isEmpty()) {
            PathNode minPath = pq.delMin();
            if (minPath.distToGoal == 0) {
                minP = minPath;
                return;
            }
            for(WorldState ws : minPath.ws.neighbors()) {
                if (minPath.last == null || !ws.equals(minPath.last.ws)) {
                    PathNode newPath = new PathNode(ws, minPath, minPath.distFromStart + 1);
                    pq.insert(newPath);
                }
            }
        }
    }

    public int moves() {
        return minP.distFromStart;
    }

    public Iterable<WorldState> solution() {
        Stack<WorldState> stack = new Stack<>();
        PathNode lastNode = minP;
        while(lastNode.last != null) {
            stack.push(lastNode.ws);
            lastNode = lastNode.last;
        }
        stack.push(lastNode.ws);
        return stack;
    }

    private class PathNode implements Comparable<PathNode> {
        private WorldState ws;
        private PathNode last;
        private int distFromStart;
        private int distToGoal;

        public PathNode(WorldState ws, PathNode last, int distFromStart) {
            this.ws = ws;
            this.last = last;
            this.distFromStart = distFromStart;
            distToGoal = ws.estimatedDistanceToGoal();
        }

        public int compareTo(PathNode otherPath) {
            return distToGoal + distFromStart - otherPath.distToGoal - otherPath.distFromStart;
        }

    }

}
