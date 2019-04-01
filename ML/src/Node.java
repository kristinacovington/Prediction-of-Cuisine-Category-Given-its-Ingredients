import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;



public class Node {
   	
   	Random random;
   	boolean randomize; 
    public int attribute;
    public int label;
    public Node[] children;
    public int cat = 20;
    
    Node(DataSet data, HashSet<Integer> attributes, ArrayList<Integer> examples) {

    	this.label = -1;

        if (examples.size() == 0) {
            this.attribute = -1;
            this.label = 0;
            return;       
        }

        int majority = 0;
        //TODO
        int count[] = new int[cat];

        for (int ex : examples) {
            count[data.trainLabel[ex]]++;
        }

        
        int max = 0;
        //TODO
        for(int i = 0; i < cat; i++){
        	if(count[i] > max){
        		max = i;
        	}
        }
        
        majority = max;
        
        if (count[majority] == examples.size() || attributes.size() == 0) {
            this.attribute = -1;
            this.label = majority;
            return;
        }

        if (randomize) {
            int numAttr = numFeatures(attributes.size());
            HashSet<Integer> attrSample = new HashSet<Integer>(numAttr);
            for (int attr : attributes) {
                if (random.nextInt(attributes.size()) < numAttr) {
                    attrSample.add(attr);
                }
            }
            this.attribute = chooseAttribute(data, attrSample, examples);
        } else {
            this.attribute = chooseAttribute(data, attributes, examples);
        }

        if (this.attribute == -1) {
            this.label = majority;
            return;
        }

        attributes.remove(this.attribute);

        ArrayList<ArrayList<Integer>> childExamples = new ArrayList<ArrayList<Integer>>(data.attrVals[this.attribute].length);
        for (int i = 0; i < data.attrVals[this.attribute].length; i++) {
            childExamples.add(new ArrayList<Integer>());
        }

        for (int ex : examples) {
            childExamples.get(data.trainEx[ex][this.attribute]).add(ex);
        }

        children = new Node[data.attrVals[this.attribute].length];

        for (int i = 0; i < data.attrVals[this.attribute].length; i++) {
            children[i] = new Node(data,attributes,childExamples.get(i));
  
            if (childExamples.get(i).size() == 0) {
                children[i].label = majority;
            }
        }
        attributes.add(this.attribute);
    }
    
    private int numFeatures(int total) {
        	
    	return (int)Math.sqrt(total) + 1;
    }

    private double entropy (double[] colArray) {
		double totalOccurrences = 0;
		int counter = 0;
		for (int i = 0; i < colArray.length; i ++) {
			if ((double)colArray[i] == 0)
				counter++;
			totalOccurrences += (double)colArray[i];
		}
		if (counter == colArray.length - 1)
			return 0;
		if (totalOccurrences == 0)
			return 0;
		double entropy = 0;
		for (int i = 0; i < colArray.length; i++) {
			if (colArray[i] != 0)
				entropy += (-1) * (colArray[i]/totalOccurrences) * (Math.log10(colArray[i]/totalOccurrences) / Math.log10(2)); 
		}
		return entropy;
	}  

    int chooseAttribute(DataSet data,  HashSet<Integer> attributes, ArrayList<Integer> examples) {
    	int bestAttr = -1;
        double bestGain = -1;
        //TODO
        double[] labelCount = new double[cat];
        for (int ex : examples) {
        	labelCount[data.trainLabel[ex]]++;
        }

        double setEntropy = entropy(labelCount);
        for (int attr : attributes) {
        	//TODO
            double[][] count = new double[data.attrVals[attr].length][cat];
            for (int ex : examples) {
                count[data.trainEx[ex][attr]][data.trainLabel[ex]]++;
            }
                
            double gain = setEntropy;
            for (int val = 0; val < data.attrVals[attr].length; val++) {
            	//TODO
            	double[] colArray = new double [cat];
            	double sum = 0;
            	//TODO
            	for(int i = 0; i < cat; i++){
            		colArray[i] = count[val][i];
            		sum += count[val][i];
            	}
            	
                gain -= (sum / examples.size())
                            * entropy(colArray);
             }

            if (gain >= bestGain) {
                bestAttr = attr;
                bestGain = gain;
            }
        }

        return bestAttr;
    }


}
