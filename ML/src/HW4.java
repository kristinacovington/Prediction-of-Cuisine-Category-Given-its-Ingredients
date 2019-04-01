import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;


public class HW4 {
	
	/** Shuffles a 2D array with the same number of columns for each row. */
	public static void shuffle(double[][] matrix, int columns, Random rnd) {
	    int size = matrix.length * columns;
	    for (int i = size; i > 1; i--)
	        swap(matrix, columns, i - 1, rnd.nextInt(i));
	}

	/** 
	 * Swaps two entries in a 2D array, where i and j are 1-dimensional indexes, looking at the 
	 * array from left to right and top to bottom.
	 */
	public static void swap(double[][] matrix, int columns, int i, int j) {
	    double tmp = matrix[i / columns][i % columns];
	    matrix[i / columns][i % columns] = matrix[j / columns][j % columns];
	    matrix[j / columns][j % columns] = tmp;
	}

	
	public static void main(String argv[])
	        throws FileNotFoundException, IOException {

	    	
	        DataSet d = new DataSet(argv[0]);

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

	        DecisionTree c = new DecisionTree(d, true);
	        
	        
	        
	  //      File myFile = new File("print.txt");
	  //      PrintStream out = new PrintStream(myFile);
	        for(int i = 0; i < d.numTestExs; i++) {
	        	String st = d.className[c.predict(d.testEx[i])];
	    	    //System.out.println("CLASS : " + st);

	    	}

	        //System.out.println("TEST EXAMPLES: " + d.numTestExs);
	        
		//	d.printTestPredictions(c, out);

	        System.out.println("Testing classifier on " + crossEx.length
	                + " examples");
	        int correct = 0;
	        for (int ex = 0; ex < crossEx.length; ex++) {
	            if (c.predict(crossEx[ex]) == crossLabel[ex]){
		    	    //System.out.println("CLASS : " + crossLabel[ex]);

	                correct++;
	                
	            }
	            
	           // System.out.print(crossLabel[ex] + " ");
	        }
	        System.out.println("Performance on cross set: "
	                + (100*correct / crossEx.length) + "%");
	    }
}
