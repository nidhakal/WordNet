import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wordnet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {

        if (nouns.length == 0)
            throw new IllegalArgumentException("Noun list is Empty");

        int max = 0;
        String temp, out = null;
        int dis;

        for (int i = 0; i < nouns.length; i++) {
            dis = 0;
            temp = nouns[i];

            for (String noun : nouns)
                dis += wordnet.distance(temp, noun);


            if (dis > max) {
                max = dis;
                out = temp;
            }
        }

        return out;
    }

    // test client (see below)
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);

        for (int i = 2; i < args.length; i++) {
            In in = new In(args[i]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[i] + ": " + outcast.outcast(nouns));
        }
    }

}
