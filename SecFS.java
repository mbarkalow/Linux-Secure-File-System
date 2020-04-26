import java.util.*;
import java.io.*;

public class SecFS{

	public static void main(String[] args) throws IOException{

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
				System.out.println("**  1. list ## To get the list of files in SecFS          **");
				System.out.println("**  2. make dir_name ## To create a directory in SecFS    **");
				System.out.println("**  3. create file_name ## To create a file in SecFS      **");
				System.out.println("**  4. exit ## To disconnect from SecFS                   **");
				System.out.println("************************************************************");

			} else if(Split_Commands[0].equals("list")){
        System.out.println("Listing Directories and Files in SecFS...");
				System.out.println("************************************************************");
				File curDir = new File(".");
				File[] filesList = curDir.listFiles();
        for(File f : filesList){
          if(f.isDirectory())
            System.out.println(f.getName());
          if(f.isFile()){
            System.out.println("--" + f.getName());
          }
        }
      } else if(Split_Commands[0].equals("make")){
				String dirName = Split_Commands[1];
        System.out.println("Making directory in SecFS...");
				new File(dirName).mkdir();
				System.out.println("Made directory " + dirName + " in SecFS...");

      } else if(Split_Commands[0].equals("create")){
				String fname = Split_Commands[1];
				String command = "chmod 700 " + fname;
				System.out.println("COMMAND: " + command);
        System.out.println("Creating file in SecFS...");
				File file = new File(fname);
				if(file.createNewFile()){
					Process process = Runtime.getRuntime().exec(command);
          System.out.println("Created file " + fname + " in SecFS...");
        }else {
					System.out.println("File already exists");
				}

      }else if(Split_Commands[0].equals("exit")){
        System.out.println("Exiting SecFS console now...");
        System.exit(0);
      }
		}
	}

}
