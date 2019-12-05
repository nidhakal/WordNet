import edu.princeton.cs.algs4.*;

public class WordNet {
    private Digraph digraph;
    private ST<String, Bag<Integer>> synsetsWordIndex;
    private ST<Integer, String> synsetsIDIndex;
    private ShortestCommonAncestor sca;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        In inSynsets = new In(synsets);
        In inHypernyms = new In(hypernyms);

        this.synsetsWordIndex = new ST<>();
        this.synsetsIDIndex = new ST<>();

        // Instantiate synsetsWordIndex and synsetsIDIndex from Synsets file
        while (inSynsets.hasNextLine()) {
            String[] splitSynsets = inSynsets.readLine().split(",");

            for (String noun : splitSynsets[1].split(" ")) {

                Bag<Integer> id = new Bag<>();

                if (synsetsWordIndex.contains(noun))
                    id = synsetsWordIndex.get(noun);

                id.add(Integer.parseInt(splitSynsets[0]));
                synsetsWordIndex.put(noun, id);
            }
            synsetsIDIndex.put(Integer.parseInt(splitSynsets[0]), splitSynsets[1]);
        }

        // Instantiate DiGraph class from Hypernyms file
        digraph = new Digraph(synsetsIDIndex.size());

        do {
            String[] splitHypernyms = inHypernyms.readLine().split(",");
            int source = Integer.parseInt(splitHypernyms[0]);

            for (int i = 1; i < splitHypernyms.length; i++)
                digraph.addEdge(source, Integer.parseInt(splitHypernyms[i]));

        } while (inHypernyms.hasNextLine());

        // Instantiate ShortestCommonAncestor class with diGraph info
        sca = new ShortestCommonAncestor(digraph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return synsetsWordIndex;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return synsetsWordIndex.contains(word);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sca(String noun1, String noun2) {
        //corner case
        if (!isNoun(noun1) || !isNoun(noun2))
            throw new java.lang.IllegalArgumentException("Noun is not in WordNet");

        Bag<Integer> id1 = synsetsWordIndex.get(noun1);
        Bag<Integer> id2 = synsetsWordIndex.get(noun2);

        if (sca.ancestorSubset(id1, id2) == -1)
            return "None";

        else
            return synsetsIDIndex.get(sca.ancestorSubset(id1, id2));

    }

    // distance between nounA and nounB (defined below)
    public int distance(String noun1, String noun2) {
        //corner case
        if (!isNoun(noun1) || !isNoun(noun2))
            throw new java.lang.IllegalArgumentException("Noun is not in WordNet");

        Bag<Integer> idA = synsetsWordIndex.get(noun1);
        Bag<Integer> idB = synsetsWordIndex.get(noun2);

        return sca.lengthSubset(idA, idB);
    }

    //Unit Testing
    public static void main(String[] args) {
        WordNet wn = new WordNet(args[0], args[1]);

        StdOut.println(wn.nouns());
        StdOut.println(wn.isNoun("Abrocoma"));
        StdOut.println(wn.isNoun("RandomTestfornotanouninwordnet"));
        StdOut.println(wn.sca("Anzio", "Kama"));


    }
}

