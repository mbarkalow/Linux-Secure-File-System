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
        System.out.println("Exiting SecFS console now...");
        System.exit(0);
      }
		}
	}

}
