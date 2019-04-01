import java.io.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashSet;
public class DecisionTree{

	Node treeRoot;
    Random random;
    boolean randomize;

    public DecisionTree(DataSet data, boolean rand) {
        random = new Random();

        this.randomize = rand;
        HashSet<Integer> attributes = new HashSet<Integer>(data.numAttrs);
        ArrayList<Integer> examples = new ArrayList<Integer>(data.numTrainExs);

        for (int i = 0; i < data.numAttrs; i++) { 
        	attributes.add(i); 
        }
        for (int i = 0; i < data.numTrainExs; i++) { 
        	examples.add(i); 
        }

        treeRoot = new Node(data, attributes, examples);
    }

    public DecisionTree(DataSet data, HashSet<Integer> attributes, boolean rand) {
        random = new Random();

        this.randomize = rand; 

        ArrayList<Integer> examples = new ArrayList<Integer>(data.numTrainExs);
        for (int i = 0; i < data.numTrainExs; i++) { examples.add(i); }

        treeRoot = new Node(data, attributes, examples);
    }

    public DecisionTree(DataSet data, HashSet<Integer> attributes,
            ArrayList<Integer> examples, boolean rand) {
        random = new Random();
        this.randomize = rand;
        treeRoot = new Node(data, attributes, examples);
    }

    public int predict(int[] ex) {
        Node current = treeRoot;
        while (current.attribute != -1) {
            current = current.children[ex[current.attribute]];
        }

        return current.label;
    }

    public static void main(String argv[])
        throws FileNotFoundException, IOException {

        if (argv.length < 1) {
            System.err.println("argument: filestem");
            return;
        }

        String filestem = argv[0];

    	
    	//InputFile
    	
    	
        DataSet d = new DataSet(filestem);

        Random random = new Random();
        for (int i = 0; i < d.numTrainExs; i++) {
        	
            int swap = random.nextInt(d.numTrainExs - i);
            int[] tempEx = d.trainEx[swap];
            d.trainEx[swap] = d.trainEx[d.numTrainExs - i - 1];
            d.trainEx[d.numTrainExs - i - 1] = tempEx;
            int tempLabel = d.trainLabel[swap];
            d.trainLabel[swap] = d.trainLabel[d.numTrainExs - i - 1];
            d.trainLabel[d.numTrainExs - i - 1] = tempLabel;
            
        }

        int crossSize = d.numTrainExs/6;

        int[][] crossEx = new int[crossSize][];
        int[] crossLabel = new int[crossSize];

        int[][] dEx = new int[d.numTrainExs - crossSize][];
        int[] dLabel = new int[d.numTrainExs - crossSize];

        for (int i = 0; i < d.numTrainExs - crossSize; i++) {
            dEx[i] = d.trainEx[i];
            dLabel[i] = d.trainLabel[i];
        }

        for (int i = 0; i < crossSize; i++) {
            crossEx[i] = d.trainEx[d.numTrainExs - i - 1];
            crossLabel[i] = d.trainLabel[d.numTrainExs - i - 1];
        }

        d.numTrainExs = dEx.length;
        d.trainEx = dEx;
        d.trainLabel = dLabel;
        System.out.println("Training classifier on " + d.numTrainExs
                + " examples");

        DecisionTree c = new DecisionTree(d, false);

        System.out.println("Testing classifier on " + crossEx.length
                + " examples");
        int correct = 0;
        for (int ex = 0; ex < crossEx.length; ex++) {
            if (c.predict(crossEx[ex]) == crossLabel[ex])
                correct++;
        }
        System.out.println("Performance on cross set: "
                + (100*correct / crossEx.length) + "%");
    }
}