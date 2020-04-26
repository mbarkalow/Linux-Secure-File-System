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

		//this is creating the root folder in which SecFS operates
		//if it already exists, the program just ignores this
		new File("SecFS").mkdir();
		String command = "chmod 700 " + "SecFS";
		Process process = Runtime.getRuntime().exec(command);

		while(true){

			System.out.print("$> "); //Prompt
			String Command = Scan.nextLine();
      			String[] Split_Commands = Command.split(" ");

			if(Split_Commands[0].equals("help")){

				System.out.println("The following are the Supported Commands");
				System.out.println("************************************************************");
				System.out.println("**  1. list (dir_name)	## To get the list of files in SecFS or specified directory  **");
				System.out.println("**  2. make dir_name ## To create a directory in SecFS    **");
				System.out.println("**  3. create file_name ## To create a file in SecFS      **");
				System.out.println("**  4. remove file_name ## To remove a file in SecFS      **");
				System.out.println("**  5. write file_name ## To write in a file in SecFS     **");
				System.out.println("**  6. read file_name ## To read a file in SecFS	      **");
				System.out.println("**  7. exit ## To disconnect from SecFS                   **");
				System.out.println("************************************************************");

			} else if(Split_Commands[0].equals("list")){
				String dirName;
				if (Split_Commands.length == 2) {
					dirName = "SecFS/" + Split_Commands[1] + "/";
				} else {
					dirName = "SecFS/";
				}
				File curDir = new File(dirName);
				File[] filesList = curDir.listFiles();
        			for(File f : filesList){
          				if(f.isDirectory())
            					System.out.println(f.getName());
          				if(f.isFile())
            					System.out.println("--" + f.getName());
        			}
      			} else if(Split_Commands[0].equals("make")){ //To create a directory in SecFS
				String dirName = "SecFS/" + Split_Commands[1];
				String comm = "chmod 700 " + dirName;
        			System.out.println("Making directory in SecFS...");
				new File(dirName).mkdir();
				Process proc = Runtime.getRuntime().exec(comm);
				System.out.println("Made directory " + dirName + " in SecFS...");
      			} else if(Split_Commands[0].equals("create")){ //To create a file in SecFS
				String fname = "SecFS/" + Split_Commands[1];
				String comm = "chmod 700 " + fname;
        			System.out.println("Creating file in SecFS...");
				File file = new File(fname);
				if(file.createNewFile()){
					Process proc = Runtime.getRuntime().exec(comm);
          				System.out.println("Created file " + fname + " in SecFS...");
        			}else {
					System.out.println("File already exists");
				}
      			}else if(Split_Commands[0].equals("remove")){ //To remove a file in SecFS
				String fname = "SecFS/" + Split_Commands[1];
				String comm = "rm " + fname;
				Process proc = Runtime.getRuntime().exec(comm);
        			System.out.println("Removed file " + fname + " from SecFS...");
      			}else if(Split_Commands[0].equals("write")){ //To write in a file in SecFS
				//THIS DOESNT WORK YET. CAN USE NOTEPAD METHOD PROVIDED BY JON
				// String fname = "SecFS/" + Split_Commands[1];
				// String comm = "vi " + fname;
				// Process proc = Runtime.getRuntime().exec(comm);
      			}else if(Split_Commands[0].equals("read")){ //To read a file in SecFS
				String fname = "SecFS/" + Split_Commands[1];
				String comm = "cat " + fname;
				Process proc = Runtime.getRuntime().exec(comm);
				BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        			String line = "";
        			while((line = reader.readLine()) != null) {
          				System.out.print(line + "\n");
        			}
      			}else if(Split_Commands[0].equals("exit")){ //To disconnect from SecFS
        			System.out.println("Exiting SecFS console now...");
        			System.exit(0);
      			}
		}

	}

}
