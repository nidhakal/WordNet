import edu.princeton.cs.algs4.*;

public class ShortestCommonAncestor {
    private Digraph digraph;
    private int visited = 0;
    private boolean[] marked;
    private boolean cycle = false;

    // constructor takes a rooted DAG as argument
    public ShortestCommonAncestor(Digraph G) {
        if (G == null)
            throw new IllegalArgumentException("Digraph is empty / Null");

        int counter = 0;
        digraph = new Digraph(G);

        for (int i = 0; i < digraph.V(); i++) {
            visited = 0;
            marked = new boolean[digraph.V()];
            dfs(digraph, i, i);

            if (digraph.outdegree(i) == 0)
                counter += 1;
        }

        if (counter != 1)
            throw new IllegalArgumentException();


    }

    //recurive dfs method that checks for cycle ensuring the digraph is rooted in contructor.
    private void dfs(Digraph g, int curr, int orig) {
        visited++;
        marked[curr] = true;

        for (int w : g.adj(curr)) {

            if (w == orig)
                cycle = true;

            else if (!marked[w])
                dfs(g, w, orig);

        }
    }

    // length of shortest ancestral path between v and w
    public int length(int v, int w) {
        if (v < 0 || v > digraph.V() || w < 0 || w > digraph.V())
            throw new IllegalArgumentException();

        //BFS search on both vetices
        BreadthFirstDirectedPaths BFDP = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths BFDPx = new BreadthFirstDirectedPaths(digraph, w);

        return modforReturn(helper(BFDP, BFDPx))[0];
    }

    // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w) {
        if (v < 0 || v > digraph.V() || w < 0 || w > digraph.V())
            throw new IllegalArgumentException();

        BreadthFirstDirectedPaths BFDP = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths BFDPx = new BreadthFirstDirectedPaths(digraph, w);

        return modforReturn(helper(BFDP, BFDPx))[1];
    }

    private int[] helper(BreadthFirstDirectedPaths bfs1, BreadthFirstDirectedPaths bfs2) {
        int length = Integer.MAX_VALUE, ancestor = 0;
        int temp = 0;

        for (int i = 0; i < digraph.V(); i++) {

            if (bfs1.hasPathTo(i) && bfs2.hasPathTo(i)) {
                temp = bfs1.distTo(i) + bfs2.distTo(i);

                if (temp < length) {
                    length = temp;
                    ancestor = i;
                }
            }
        }

        return new int[]{length, ancestor};
    }

    private int[] modforReturn(int[] a) {
        if (a[0] == Integer.MAX_VALUE) {
            a[0] = -1;
            a[1] = -1;
        }
        return a;
    }

    // length of shortest ancestral path of vertex subsets A and B
    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        if (subsetA == null || subsetB == null)
            throw new IllegalArgumentException();

        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(digraph, subsetA);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(digraph, subsetB);

        return modforReturn(helper(bfs1, bfs2))[0];
    }

    // a shortest common ancestor of vertex subsets A and B
    public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        if (subsetA == null || subsetB == null)
            throw new IllegalArgumentException();

        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(digraph, subsetA);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(digraph, subsetB);

        return modforReturn(helper(bfs1, bfs2))[1];

    }

    // unit testing (required)
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sca.length(v, w);
            int ancestor = sca.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

}
