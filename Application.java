import java.io.*;
import java.util.*;

public class Application {
	
		private static Scanner input = new Scanner(System.in);
		private static Scanner input2 = new Scanner(System.in);
		private static TreeMap<String, Integer> tableID = new TreeMap<String, Integer>();
		private static TreeMap<String, String> tableLocation = new TreeMap<String, String>();
		private static ArrayList<String> usernameList = new ArrayList<>();

		public static void main(String[] args) throws IOException {
			makeFileID();
			makeFileLocation();
			makeTableID();
			makeTableLocation();
			runIntro();
		}
		
		public static void runIntro() throws IOException {
			System.out.println("Welcome to UoL!");
			System.out.print("Are you looking to register a new item (register): ");
			String response = input.nextLine();
			if(response.toLowerCase().equals("register")) {
				runRegistration();
			} else {
				runFailedResponse(response);
			}
		}
		
		public static void runRegistration() throws IOException {
			System.out.print("Please create a username: ");
			String username = input.nextLine();
			if(tableID.containsKey(username)) {
				runFailedRegistrationDuplicateName();
			}else {
				System.out.print("Please enter the ID number of your device: ");
				Integer ID = input.nextInt();
				if(tableID.containsValue(ID)) {
					runFailedRegistrationDuplicateID(username);
				}else {
					runSuccessfulRegistration(username, ID);
				}
			}
		}
		
		
//Success		
		
		public static void runSuccessfulReport(String username) throws IOException {
			System.out.println("The last location your device was seen in was" + tableLocation.get(username));
		}

		public static void runSuccessfulRegistrationDuplicateID(String username) throws IOException {
			System.out.print("Please enter the ID number of your device: ");
			int ID = input.nextInt();
			if(tableID.containsValue(ID)) {
				runFailedRegistrationDuplicateID(username);
			}else {
				runSuccessfulRegistration(username, ID);
			}
		}
		
		public static void runAddLocation(String username) throws IOException{
			System.out.print("Where are you currently located: ");
			String location = input2.nextLine();
	        File file = new File("C:Account-Location.txt");
        	PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
			Scanner currLine = new Scanner(file);
			tableLocation.put(username, location);
			output.println("{" + username + "=" + location + "}");
			output.close();
			currLine.close();
		}
		
		public static void runSuccessfulRegistration(String username, Integer ID) throws IOException {
	        File file = new File("C:Account-ID.txt");
        	PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
			Scanner currLine = new Scanner(file);
			tableID.put(username, ID);
			usernameList.add(username);
			output.println("{" + username + "=" + ID + "}");
			runAddLocation(username);
			output.close();
			currLine.close();
		}
		
//Failed
		public static void runFailedResponse(String response) throws IOException {
			System.out.println("I'm sorry you've entered an invalid input.");
			System.out.println("If you'd like to register a new item type register: ");
			response = input.nextLine();
			if(response.toLowerCase().equals("register")) {
				runRegistration();
			}else {
				runFailedResponse(response);
			}
		}
		
		public static void runFailedReportName() throws IOException{
			System.out.println("It seems your username is not in use, please enter a valid username: ");
			String username = input.nextLine();
			if(tableID.containsKey(username)) {
				runFailedReportName();
			}else {
				runSuccessfulReport(username);
			}
			
		}
		
		
		public static void runFailedRegistrationDuplicateName() throws IOException {
			System.out.println("It seems your username is taken, please select a new one: ");
			String username = input.nextLine();
			if(tableID.containsKey(username)) {
				runFailedRegistrationDuplicateName();
			}else {
				runSuccessfulRegistrationDuplicateID(username);
			}
		}
		
		public static void runFailedRegistrationDuplicateID(String username) throws IOException {
			System.out.println("It seems your ID hasalready been activated, please re-enter your ID: ");
			Integer ID = input.nextInt();
			if(tableID.containsValue(ID)) {
				runFailedRegistrationDuplicateID(username);
			}else {
				runSuccessfulRegistration(username, ID);
			}
		}
		
//Important Backend functionality
		
		public static TreeMap<String, String> getTableLocation() throws IOException {
			makeTableLocation();
			return tableLocation;
		}
		
		public static TreeMap<String, Integer> getTableID() throws IOException {
			makeTableID();
			return tableID;
		}
		
		public static ArrayList<String> getUsernameList() throws IOException {
			return usernameList;
		}
		
		public static void makeFileID() {
			try {
		        File files = new File("C:Account-ID.txt");
		        if (!files.exists()) {
		            files.createNewFile();
		        }

		        PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(files, true)));
				output.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
		
		public static void makeFileLocation() {
			try {
		        File files = new File("C:Account-Location.txt");
		        if (!files.exists()) {
		            files.createNewFile();
		        }

		        PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(files, true)));
				output.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
		
		public static void makeTableID() throws IOException {
	        File file = new File("C:Account-ID.txt");
	        PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
			Scanner currLine = new Scanner(file);
			String str = "";
			while(currLine.hasNext()) {
				String accountName = "";
				String productID = "";
				str = "" + currLine.next();
				for(int i = 0; i < str.length()-1; i++) {
					if(str.charAt(i) >= 65 && str.charAt(i) <= 122) {
						accountName = accountName + "" + str.charAt(i);
					}
					if((str.charAt(i) >= 48 && str.charAt(i) <= 57)) {
						productID = productID + "" + str.charAt(i);
					}
					if(str.charAt(i) == '}') {
						break;
					}
				}
				Integer ID = Integer.parseInt(productID);
				tableID.put(accountName, ID);
			}
			output.close();
			currLine.close();
		}
		
		public static void makeTableLocation() throws IOException {
	        File file = new File("C:Account-Location.txt");
	        PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
			Scanner currLine = new Scanner(file);
			String str = "";
			while(currLine.hasNext()) {
				String accountName = "";
				String location = "";
				str = "" + currLine.nextLine();
				for(int i = 0; i < str.indexOf('='); i++) {
					if(str.charAt(i) >= 65 && str.charAt(i) <= 122) {
						accountName = accountName + "" + str.charAt(i);
					}
				}
				for(int i = str.indexOf('=')+1;i < str.length()-1; i++) {
					if(str.charAt(i) != '}') {
						location = location + "" + str.charAt(i);
					}else {
						break;
					}
				}
				usernameList.add(accountName);
				tableLocation.put(accountName, location);
			}
			output.close();
			currLine.close();
		}
}

{
   public static void main(String[] args)
   {
      int dieSides = 6;
	   int trash = 0, boot = 1, smallFish = 3, mediumFish = 4, largeFish = 6, rareFish = 10; //each assigned points
	   int point1 = 0, point2 = 0, point3 = 0, point4 = 0, point5 = 0, point6 = 0;	
	   double total = 0;
      int rolls;
      char choice;
	   String fishs;
      int round;

      Scanner input = new Scanner(System.in);
      //die public class
      Die die = new Die(dieSides);
      
	   System.out.println("Welcome to the Fishing Game Simulator");
      

	//loop for the each side of the Die
	   do
	   {
		   die.roll();
         rolls = die.getValue();

		   if (rolls == 1)
		   {
			    fishs = "Trash";
			   total += trash;	//adds the points of each type of fish
			   round = trash;
			   point1++;	//increment of the points
            System.out.println("You have caugh a:"  + fishs);

		   }
		   else if (rolls == 2)
		   {
		   	 fishs = "Boot";
		   	total += boot;
		   	round = boot;
		   	point2++;
            System.out.println("You have caugh a:"  + fishs);

		   }
		   else if (rolls == 3)
		   {
		   	fishs = "Small Fish";
		   	total += smallFish;
		   	round = smallFish;
		   	point3++;
            System.out.println("You have caugh a:"  + fishs);

		   }
		  else  if (rolls == 4)
		   {
		   	 fishs = "Medium Fish";
		   	total += mediumFish;
		   	round = mediumFish;
		   	point4++;
            System.out.println("You have caugh a:"  + fishs);

		   }
		   else if (rolls == 5)
		   {
		   	 fishs = "Large Fish";
	      	total += largeFish;
   			round = largeFish;
   			point5++;
            System.out.println("You have caugh a:"  + fishs);

   		}
   		else if (rolls == 6)
   		{
   			 fishs = "Rare Fish";
   			total += rareFish;   
     			round = rareFish;
   			point6++;
            System.out.println("You have caugh a:"  + fishs);

   	   }
                     
         choice = JOptionPane.showInputDialog("Do you wish to continue Yes(y) or No(n): ").toUpperCase().charAt(0);
                  
       }//IF user wants to stop playing the total points will be displayed
        while (choice != 'n'&& choice != 'N');
         {
            if (choice == 'n' || choice == 'N')
		      {
               System.out.println("\n");
               System.out.println("THE FINAL RESULT OF THE GAME!");
               System.out.println("Your final total score is: " + total);
            }
            if (total <= 25)
            {
               System.out.println("You failed to get enough points. Better luck next time!!!");
            }
            if (total >= 25)
            {
               System.out.println("Good Job. You did well fisherman!");
            }
         }
      }
}