import java.util.*;
import java.io.*;

public class SecFS{

	public static void main(String[] args){

		String user;
		//getting user and storing
		user = System.getProperty("user.name");

		System.out.println("Connected.");
    System.out.println("Welcome " + user);
		Scanner Scan = new Scanner(System.in);
		while(true){

			System.out.print("$> "); //Prompt
			String Command = Scan.nextLine();
      		String[] Split_Commands = Command.split(" ");

			if(Split_Commands[0].equals("help")){

				System.out.println("The following are the Supported Commands");
				System.out.println("************************************************************");
				System.out.println("**  1. list ## To get the list of files in SecFS           **");
				System.out.println("**  2. exit ## To disconnect from SecFS                    **");
				System.out.println("************************************************************");

			}

			else if(Split_Commands[0].equals("exit")){
				System.out.println(" hi Exiting SecFS console now...");
				System.exit(0);
			}
			  
			else if(Split_Commands[0].equals("list")){
				String s = null;
				try {
					
				// run the Unix "ps -ef" command
					// using the Runtime exec method:
					Process p = Runtime.getRuntime().exec("ping www.codejava.net");
					
					BufferedReader stdInput = new BufferedReader(new 
						 InputStreamReader(p.getInputStream()));
		
					BufferedReader stdError = new BufferedReader(new 
						 InputStreamReader(p.getErrorStream()));
		
					// read the output from the command
					System.out.println("Here is the standard output of the command:\n");
					while ((s = stdInput.readLine()) != null) {
						System.out.println(s);
					}
					// read any errors from the attempted command
					System.out.println("Here is the standard error of the command (if any):\n");
					while ((s = stdError.readLine()) != null) {
						System.out.println(s);
					}
					
					System.exit(0);
				}
				catch (IOException e) {
					System.out.println("exception happened - here's what I know: ");
					e.printStackTrace();
					System.exit(-1);
				}
	  		}

		}


	}

}
