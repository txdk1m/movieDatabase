// Ted Kim - CSC 20 Final Project

package Main;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

public class FinalProject{
	public static void main(String[] args) throws IOException{
		System.out.print("Welcome to the Movie Database Management System.\n");
		keyboardInput ki = new keyboardInput();
		fileIO fio = new fileIO();
		ki.menu();
		ki.mainEntry();
		fio.writetoDB();
		ki.close();
	}
	
	public static <E> void p(E item){
		System.out.println(item);
	}
}

class fileIO{ 
	newEntry line = new newEntry();
	File database = new File("db.txt");
	
	
	public void writeline() throws IOException{
		BufferedWriter movies = new BufferedWriter(new FileWriter("db.txt"));
		movies.write(line.addInfo());
		movies.newLine();
		movies.close();
	}
	
	public void appendline() throws IOException{
		BufferedWriter movies = new BufferedWriter(new FileWriter("db.txt", true));
		movies.append(line.addInfo());
		movies.newLine();
		movies.close();
	}
	
	public void readandwrite() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader("db.txt"));
		if (br.readLine() != null){
			appendline();
		} else {
			writeline();
		} br.close();
	}
	
	public int findLineCount() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader("db.txt"));
		int lc = 0;
		while(br.readLine() != null){
			lc++;
		}
		br.close();
		return lc;
	}
	
	public String[][] writetoDB() throws IOException{
		String[][] moviesDB = new String[6][findLineCount()]; //titles = 0, actor 1 = 1, actor 2 = 2, director = 3, year = 4, runtime = 5
		BufferedReader br = new BufferedReader(new FileReader("db.txt"));
		String row;
		int t = 0, a1 = 0, a2 = 0, d = 0, y = 0, r = 0;
		while((row = br.readLine()) != null){
			StringTokenizer st = new StringTokenizer(row, "/");
			moviesDB[0][t] = st.nextToken();
			moviesDB[1][a1] = st.nextToken();
			moviesDB[2][a2] = st.nextToken();
			moviesDB[3][d] = st.nextToken();
			moviesDB[4][y] = st.nextToken();
			moviesDB[5][r] = st.nextToken();
			t++; a1++; a2++; d++; y++; r++;
		}
		br.close();
		return moviesDB;
	}
	
	public void movieData() throws IOException{
	try{
		if(database.createNewFile()){
			System.out.println("File created.");
		} else {
			System.out.println("File already exists");
		}
		readandwrite();
		System.out.println("Successfully written to file!");
	} catch (IOException e){
		e.printStackTrace();
		}
	}	
}

class keyboardInput{
	private static Scanner command = new Scanner(System.in);
	
	public static String cin(){
		String inputReturn = "";
		inputReturn = command.nextLine();
		return inputReturn;
	}

	public void mainEntry() throws IOException {
        String sc;
        while (!command.hasNextLine()) {
            command.nextLine();
        }
        sc = command.nextLine();
        UserInput(sc);
    }
	
	public void menu(){
		System.out.println();
		System.out.print("a) New Entry\nb) Search by Actor\nc) Search by Year\nd) Search by Runtime (in minutes)\ne) Search by Director\nf) Search by Title\ng) Quit\nEnter command > ");
	}
	
	public void UserInput(String option) throws IOException {
        fileIO fio = new fileIO();
        database db = new database();
            if (option.equalsIgnoreCase("a") || option.equalsIgnoreCase("New Entry")) {
                fio.movieData(); // input movie data
                menu(); // display menu
                mainEntry(); //return to main menu
            } 
            else if (option.equalsIgnoreCase("b") || option.equalsIgnoreCase("Search by Actor")) {
            	System.out.print("Enter actor > ");
            	db.searchbyActor(cin(), fio.writetoDB());
            	menu();
            	mainEntry();
            }
            else if (option.equalsIgnoreCase("c") || option.equalsIgnoreCase("Search by Year")) {
            	System.out.print("Enter year > ");
            	db.searchbyYear(cin(), fio.writetoDB());
            	menu();
            	mainEntry();

            }
            else if (option.equalsIgnoreCase("d") || option.equalsIgnoreCase("Search by Runtime") || option.equalsIgnoreCase("Search by Runtime (in minutes)")) {
            	System.out.print("Enter runtime (minutes) > ");
            	db.searchbyRuntime(cin(), fio.writetoDB());
            	menu();
            	mainEntry();
            }
            else if (option.equalsIgnoreCase("e") || option.equalsIgnoreCase("Search by Director")) {
            	System.out.print("Enter director > ");
            	db.searchbyDirector(cin(), fio.writetoDB());
            	menu();
            	mainEntry();
            }
            else if (option.equalsIgnoreCase("f") || option.equalsIgnoreCase("Search by Title")) {
            	System.out.print("Enter title > ");
            	db.searchbyTitle(cin(), fio.writetoDB());
            	menu();
            	mainEntry();
            } 
            else if(option.equalsIgnoreCase("g") || option.equalsIgnoreCase("Quit")) {
            	System.out.println("You have exited the program. Goodbye!");
                System.exit(1);
            } else {
            	System.out.println("Invalid command, try again!");
                menu();
                mainEntry(); //return to main menu
            }
    }
	
	public void close(){
		command.close();
	}
}

class newEntry{
	private String title, actor1, actor2, director, year, runtime;
	
	public String addInfo() throws IOException{
		System.out.println("\nNew Entry for Movie Database:");
		System.out.print("Enter title > ");
		title = keyboardInput.cin();
		if(title.length() >= 3){
			System.out.print("Enter actor1 > ");
			actor1 = keyboardInput.cin();
			System.out.print("Enter actor 2 > ");
			actor2 = keyboardInput.cin();
			System.out.print("Enter director > ");
			director = keyboardInput.cin();
			System.out.print("Enter year > ");
			year = keyboardInput.cin();
			System.out.print("Enter runtime > ");
			runtime = keyboardInput.cin();
		} else {
			System.out.println("Incorrect title format. Try again.");
			keyboardInput ki = new keyboardInput();
			ki.menu();
			ki.mainEntry();
		}
		
		String userEntry = title + "/" + actor1 + "/" + actor2 + "/" + director + "/" + year + "/" + runtime;
		System.out.println("Successfully added new entry!");
		
		return userEntry;
	}
}

class database{
	//titles = 0, actor 1 = 1, actor 2 = 2, director = 3, year = 4, runtime = 5
	public void searchbyActor(String input, String[][] moviesDB) {
		System.out.println();
		System.out.println("The actor '" + input + "' has been in the following feature films:");
		int none = 0;
		for(int i = 0; i < moviesDB[1].length; i++){
			if(moviesDB[1][i].equalsIgnoreCase(input)){
				String titlewithActor = moviesDB[0][i];
				System.out.println(titlewithActor);
			}
			else if(moviesDB[2][i].equalsIgnoreCase(input)){
				String titlewithActor = moviesDB[0][i];
				System.out.println(titlewithActor);
			} else {
				none++;
			}
		}
		if(none == moviesDB[1].length){
			System.out.println("// No titles found for actor //");
		}
	}
	
	public void searchbyYear(String input, String[][] moviesDB) {
		System.out.println();
		System.out.println("In the year '" + input + "', the following movies were released:");
		int none = 0;
		for(int i = 0; i < moviesDB[4].length; i++){
			if(moviesDB[4][i].equalsIgnoreCase(input)){
				String titlewithYear = moviesDB[0][i];
				System.out.println(titlewithYear);
			} else {
				none++;
			}
		}
		if(none == moviesDB[1].length){
			System.out.println("// No titles found for year //");
		}
	}
	
	public void searchbyRuntime(String input, String[][] moviesDB) {
		System.out.println();
		System.out.println("Theses movies were " + input + " mins long:");
		int none = 0;
		for(int i = 0; i < moviesDB[5].length; i++){
			if(moviesDB[5][i].equalsIgnoreCase(input)){
				String titlewithRuntime = moviesDB[0][i];
				System.out.println(titlewithRuntime);
			} else {
				none++;
			}
		}
		if(none == moviesDB[1].length){
			System.out.println("// No titles found for runtime //");
		}
	}
	
	public void searchbyDirector(String input, String[][] moviesDB) {
		System.out.println();
		System.out.println("The following movies were directed by '" + input +"':" );
		int none = 0;
		for(int i = 0; i < moviesDB[5].length; i++){
			if(moviesDB[3][i].equalsIgnoreCase(input)){
				String titlewithRuntime = moviesDB[0][i];
				System.out.println(titlewithRuntime);
			} else {
				none++;
			}
		}
		if(none == moviesDB[1].length){
			System.out.println("// No titles found for director //");
		}
	}
	
	public void searchbyTitle(String input, String[][] moviesDB) {
		System.out.println();
		int notitle = 0; String actorsIn = "", otherActorsIn = "", allActors = "", directorOf = "", yearMade = "", runtimeOf = "";
		for(int i = 0; i < moviesDB[0].length; i++){
			if(moviesDB[0][i].equalsIgnoreCase(input)){
				actorsIn = moviesDB[1][i];
				otherActorsIn = moviesDB[2][i];
				allActors = actorsIn + ", " + otherActorsIn;
				directorOf = moviesDB[3][i];
				yearMade = moviesDB[4][i];
				runtimeOf = moviesDB[5][i];
			} else{
				notitle++;
			}
		}
		if (notitle == moviesDB[0].length){
			System.out.println("// No titles found //");
		} else {
			System.out.println("Actors: " + allActors);
			System.out.println("Director: " + directorOf);
			System.out.println("Year: " + yearMade);
			System.out.println("Runtime: " + runtimeOf + " minutes");
		}
	}
}
