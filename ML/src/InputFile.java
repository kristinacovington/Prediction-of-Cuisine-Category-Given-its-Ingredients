import java.io.BufferedReader;

import java.io.File;

import java.io.FileNotFoundException;

import java.io.FileReader;

import java.io.IOException;

import java.util.HashMap;

import java.util.HashSet;

import java.util.Scanner;



public class InputFile {

public static void main (String[] args) throws IOException {

HashMap<String, Integer> ingredients = new HashMap<String, Integer>();

File file = new File(args[0]);

Scanner scanner = new Scanner(file);

int counter = 0;


int[][] recipeList = new int[1794][2398];


while(scanner.hasNextLine()) {

String str = scanner.nextLine();

System.out.println(str);

ingredients.put(str, counter);

counter++;

}


File file2 = new File(args[1]);

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

System.out.println("Get is null. Ingredient is not in hashtable");


}

value = ingredients.get(recipe[i]);

recipeList[(Integer.parseInt(recipe[0]))][value] = 1;

cuisines[Integer.parseInt(recipe[0])] = recipe[1];

}


}


System.out.println("");

}


for(int i = 0; i<1793; i++) {

System.out.print(i + " ");

for(int j = 0; j<2398; j++) {

if(recipeList[i][j] == 1) {

//System.out.println(" 1 ");

}

}

System.out.println("");

}


for(int i = 0; i<cuisines.length; i++) {

System.out.println(cuisines[i]);


}




}


}