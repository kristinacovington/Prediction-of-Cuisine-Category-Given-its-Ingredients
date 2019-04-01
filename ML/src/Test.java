import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;



public class Test {
	
	public static void main(String argv[]) throws IOException{

    	//InputFile
		HashMap<String, Integer> ingredients = new HashMap<String, Integer>();
		
		File file = new File(argv[0]);
		
		Scanner scanner = new Scanner(file);
		
		int counter = 0;
		
		
		int[][] recipeList = new int[1794][2398];
		
		
		
		while(scanner.hasNextLine()) {
		
			String str = scanner.nextLine();
			
			//System.out.println(str);
			System.out.println("STRING: " + str);
			ingredients.put(str, counter);
			
			counter++;
	
		}
	
	
		File file2 = new File(argv[1]);
		
		BufferedReader br = null;
		
		br = new BufferedReader(new FileReader(file2));
		
		int value = 0;
		
		String line = "";
		
		String[] cuisines = new String[1794];
		
		
		while((line = br.readLine()) != null) {
		
			String[] recipe = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
			
			for(int i = 0; i<recipe.length; i++) {
			
			//System.out.print(recipe[i]);
			
			if(i>1) {
			
				//System.out.println(value);
				
				if(ingredients.get(recipe[i]) == null) {
				
					System.out.println(recipe[i]);
					System.out.println("Get is null. Ingredient is not in hashtable");
			
			
				}
			
				value = ingredients.get(recipe[i]);
				
				recipeList[(Integer.parseInt(recipe[0]))][value] = 1;
				
				cuisines[Integer.parseInt(recipe[0])] = recipe[1];
		
			}

		}

		}

	
		scanner.close();
		//Test t = new Test(argv[2]);
	}

	public Test (String str) throws IOException {

		   			String content = "kristina";

					File file3 = new File(str);

					if (!file3.exists()) {
						file3.createNewFile();
					}

					FileWriter fw = new FileWriter(file3.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);
					
				    String className [] = {"\"brazilian\"", "\"british\"", "\"cajun_creole\"", "\"chinese\"", "\"filipino\"", "\"french\"", "\"greek\"", "\"indian\"", "\"irish\"", "\"italian\"", "\"jamaican\"", "\"japanese\"", "\"korean\"", "\"mexican\"", "\"moroccan\"", "\"russian\"", "\"southern_us\"", "\"spanish\"", "\"thai\"", "\"vietnamese\""};

				    for( int i = 0; i < className.length; i++){
				    	
						bw.write(className[i]+",");

				    }
				    bw.flush();

					bw.close();

					System.out.println("Done");
					

	
		
		
	}
}
