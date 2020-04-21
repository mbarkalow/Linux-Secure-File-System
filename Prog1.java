import java.io.*;
import java.util.*;

public class Prog1
{
	private static String curDir = "Project";
	private static ProcessBuilder builder = new ProcessBuilder();
	private static File aliceDir = new File("/home/mark/Documents/cs419/Project/AliceFolder");
	private static File bashDir = new File("/bin/bash");
	
	private static ArrayList<String> stringCommands = new ArrayList();
	
	private static void listCommands()
	{
		System.out.println("You are able to create,edit, and delete files in the SecFS folder");
		System.out.println("The following are the Supported Commands");
		System.out.println("************************************************************");
		System.out.println("**  1. list ## To get the list of files in SecFS           **");
		System.out.println("**  2. create <filename> ## To create a new text file          **");
		System.out.println("**  3. delete <filename> ## To delete a new file         **");
		System.out.println("**  4. edit <filename> <program> ## To edit a file with the specified program         **");
		System.out.println("**  5. exit ## To disconnect from SecFS                **");
		System.out.println("**  5. help  ## To display the list of commands again             **");
		System.out.println("************************************************************");
	}
	
	private static void acceptCommands()
	{
		Scanner scan = new Scanner(System.in);
		while(true)
		{

			System.out.print("$> "); //Prompt
			String command = scan.nextLine();
     		String[] split_commands = command.split(" ");

			if(split_commands[0].equals("help"))
			{
				listCommands();
			}

			else if(split_commands[0].equals("exit"))
			{
        		System.out.println("Exiting console now...");
        		return;
			}
			else
			{
				executeCommand(split_commands);
			}
		}
	}
	
	private static String[] evaluateCommand(String[] x)
	{
		String buildArgs = "/bin/bashsplit-csplit";
		//String buildArgs = "";
		//String[] buildArgs 
		
		if(stringCommands.contains(x[0]) == false)
		{
			buildArgs = buildArgs.concat("invalid");
		}
		else
		{
			if(x.length == 1)
			{
				//return "ls";
				buildArgs = buildArgs.concat("ls");
				//buildArgs = buldArgs.concat(" ");
			}
			else if(x.length == 2)
			{
				if(x[0].equals("create"))
				{
					buildArgs = buildArgs.concat("touch " + x[1]);
				}
				else
				{
					buildArgs = buildArgs.concat("rm " + x[1]);
				}
				//buldArgs = buildArgs.concat(x[0]);
				//buildArgs = buildArgs.concat(" ");
				//buildArgs = buldArgs.concat(x[1]);
			}
			else if(x.length == 3)
			{
				buildArgs = buildArgs.concat(x[2] + " " + x[1]);
				/*buildArgs = buildArgs.concat(x[0]);
				buildArgs = buildArgs.concat(" ");
				buildArgs = buildArgs.concat(x[1]);
				buildArgs = buldArgs.concat(" ");
				buildArgs = buldArgs.concat(x[2]);*/
			}
			else
			{
					buildArgs = buildArgs.concat("invalid");
			}
		}
		String[] args = buildArgs.split("split");
		return args;
	}
	
	private static void executeCommand(String[] split_commands)
	{
		boolean validDir = false;
		String text;
		
		String[] args = evaluateCommand(split_commands);
		for(int i = 0; i < args.length; i++)
		{
			System.out.println("args[" + i + "] = " + args[i]);
		}
		if((args[2].equals("invalid")) == false)
		{
			try
			{
				String[] args1 = {"bash", "-c"};
				
				builder.command(args);
				Process process = builder.start();
				
				//Process process = Runtime.getRuntime().exec(args, null, aliceDir);
				
				// set up stdinput and stderror streams
				BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
				BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
				
				System.out.println("Standard output: ");
				while((text = input.readLine()) != null)
				{
					System.out.println(text);
				}
				
				// read errors from command
				System.out.println("Standard error: ");
				while((text = error.readLine()) != null)
				{
					System.out.println(text);
				}
				
				int exit = process.waitFor();
				if(exit != 0)
				{
					System.out.println("There was a problem");
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				System.out.println("Exception caught");
			}
		}
		else
		{
			System.out.println("Invalid command. Type help for list of commands.");
		}
	}
	
	public static void main(String [] args)
	{
		// setting directory of process
		builder.directory(aliceDir);
		
		stringCommands.add("list");
		stringCommands.add("create");
		stringCommands.add("delete");
		stringCommands.add("edit");
		
		String user;
		//getting user and storing
		user = System.getProperty("user.name");

		System.out.println("Connected.");
    	System.out.println("Welcome " + user);
		
		listCommands();
    	
		acceptCommands();
	}
}
