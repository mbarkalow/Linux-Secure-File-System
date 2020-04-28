import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.*;
import java.nio.file.Path;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Prog1
{
	private static ProcessBuilder builder = new ProcessBuilder();
	private static String dir = System.getProperty("user.dir")+"/AliceFolder";
	private static Path pathVar = Paths.get(dir);
	private static File aliceDir = new File(dir);
	
	private static ArrayList<String> stringCommands = new ArrayList<String>();
	
	private static Scanner scan = new Scanner(System.in);
	
	/*
	 * This  method is called from the main method during the start of the program to let the user know
	 * what commands are supported in our system. It is also called if the user enters "help".
	 */
	private static void listCommands()
	{
		System.out.println("You are able to create,edit, and delete files in the AliceFolder");
		System.out.println("The following are the supported commands");
		System.out.println("************************************************************");
		System.out.println("**  1. list ## To get the list of files in AliceFolder           **");
		System.out.println("**  2. create <filename> ## To create a new text file          **");
		System.out.println("**  3. read <filename> ## To display contents of file          **");
		System.out.println("**  4. delete <filename> ## To delete a file         **");
		System.out.println("**  5. edit <filename> <program> ## To edit a file with the specified program         **");
		System.out.println("**  6. exit ## To disconnect from program                **");
		System.out.println("**  7. help  ## To display the list of commands again             **");
		System.out.println("************************************************************");
	}
	
	/*
	 * This method is called from the main method to sit in a while loop handling user input and sending it to 
	 * executeCommand until the command "exit" called which stops the program.
	 */
	private static void acceptCommands()
	{
		while(true)
		{
			System.out.print("$> ");
			String command = scan.nextLine();
     		String[] split_commands = command.split(" ");

			if(split_commands[0].equals("help"))
			{
				listCommands();
			}

			else if(split_commands[0].equals("exit"))
			{
				scan.close();
        		System.out.println("Exiting console now...");
        		return;
			}
			else
			{
				executeCommand(split_commands);
			}
		}
	}
	
	private static String readInput()
    {
        System.out.println("Please enter key to decrypt file");
        String key = scan.nextLine();
        return key;
        
    }
	
	private static String executeProcess(String[] command)
    {
            String text = "";
            String timeStamp = "";
            ProcessBuilder builder2 = new ProcessBuilder();
            builder2.directory(aliceDir);
            try
            {
                builder2.command(command);
                Process process = builder2.start();
                
                //Process process = Runtime.getRuntime().exec(args, null, aliceDir);
                
                // set up stdinput and stderror streams
                BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                
                
				timeStamp = input.readLine();
                while((text = input.readLine()) != null)
                {
                    //System.out.println(text);
                }
                
                // read errors from command
                
                while((text = error.readLine()) != null)
                {
                    //System.out.println(text);
                }
                
                int exit = process.waitFor();
                if(exit != 0)
                {
                    System.out.println("There was a problem");
                }
                return timeStamp;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println("Exception caught");
            }
            return timeStamp;
        
    }
	
	private static String getFileName(String arg)
    {
        String filename = "";
        int setter = 0;
        for(int i = 0; i < arg.length(); i++)
        {
			if(arg.charAt(i) == ' ')
			{
				setter = i + 1;
			}
			
			
        }
        for(int i = setter; i < arg.length(); i++)
        {
        
                
                    filename = filename.concat(Character.toString(arg.charAt(i)));
                
        }
        return filename;
        
    }
	
	/*
	 * This method takes user input and converts it to a string array that can be used 
	 * in Process Builder with real System calls.
	 */
	private static String[] evaluateCommand(String[] x)
	{
		String buildArgs = "/bin/bashsplit-csplit";
		
		/* If whatever the user inputted is not part of Program supported commands, let ExecuteCommand method know
		 * that the input is invalid.
		 */
		if(stringCommands.contains(x[0]) == false)
		{
			buildArgs = buildArgs.concat("invalid");
		}
		// Determine if user is trying to create, delete, or edit a file, or list all files in directory.
		else
		{
			if(x.length == 1)
			{
				if(x[0].equals("list")){
					buildArgs = buildArgs.concat("ls");
				}
				else{
					buildArgs = buildArgs.concat("invalid");
				}
			}
			else if(x.length == 2)
			{
				if(x[0].equals("create"))
				{
					buildArgs = buildArgs.concat("touch " + x[1]);
				}
				else if(x[0].equals("read"))
				{
					buildArgs = buildArgs.concat("cat " + x[1]);
				}
				else
				{
					if(x[0].equals("delete")){
						buildArgs = buildArgs.concat("rm " + x[1]);
					}
					else{
						buildArgs = buildArgs.concat("invalid");
					}
				}
			}
			else if(x.length == 3)
			{	
				if(x[0].equals("edit")){
					buildArgs = buildArgs.concat(x[2] + " " + x[1]);
				}else{
					buildArgs = buildArgs.concat("invalid");
				}
			}
			else
			{
					buildArgs = buildArgs.concat("invalid");
			}
		}
		// Create String array that can be executed in Process Builder and return.
		String[] newArgs = buildArgs.split("split");
		return newArgs;
	}
	
	/* This method is responsible for using Process Builder to execute commands and calling other methods to handle
	 * setting up encryption/decryption, and setting file permissions.
	 */
	private static void executeCommand(String[] split_commands)
	{
		CharSequence sequence = "touch";
		
		String outputText = "";
		String outputError = "";
		
        String originalTimeStamp = "";
        String file = "";
        String filePath = "";
		
        /* This method is called to evaluate user input and make sure it is valid.
         * evaluateCommand returns a string array that is ready to be used for Process Builder.
         * System calls are stored in commandArgs[2].
         */
		String[] commandArgs = evaluateCommand(split_commands);
		
		file = getFileName(commandArgs[2]);
        filePath = dir + "/";
        filePath = filePath.concat(file);
       
        File onlyFile = new File(dir + "/" + file);
        
        
		
		if((commandArgs[2].equals("invalid")) == false)
		{
			if((split_commands[0].contains("read") )&& (split_commands[0].charAt(0) =='r')&& (onlyFile.length() != 0)) {
				try
				{
					System.out.println("SECOND TESTING HERE");
					Cipher cipher;
					cipher = Cipher.getInstance("DES");
					String key = readInput();
					byte[] decryptKey = Base64.getDecoder().decode(key);
					SecretKey decodedKey = new SecretKeySpec(decryptKey,0,decryptKey.length,"DES");
					cipher.init(Cipher.DECRYPT_MODE, decodedKey);
					FileInputStream inputStream = new FileInputStream(filePath);
					byte[] encryptedBytes = new byte[(int) onlyFile.length()];
					inputStream.read(encryptedBytes);
					
					byte[] decryptedText = cipher.doFinal(encryptedBytes);
					String byteString = new String(decryptedText);
					System.out.println("contents of file: " + byteString);
					//FileOutputStream output = new FileOutputStream(filePath);
					//output.write(decryptedText);
					inputStream.close();
					//output.close();
				
				
				}
				catch(Exception e)
				{
					e.printStackTrace();
					System.out.println("Exception caught");
				}
				
			}
            
			if((split_commands[0].contains("edit") )&& (split_commands[0].charAt(0) =='e')&& (onlyFile.length() != 0)) {
				try
				{
					System.out.println("SECOND TESTING HERE");
					Cipher cipher;
					cipher = Cipher.getInstance("DES");
					String key = readInput();
					byte[] decryptKey = Base64.getDecoder().decode(key);
					SecretKey decodedKey = new SecretKeySpec(decryptKey,0,decryptKey.length,"DES");
					cipher.init(Cipher.DECRYPT_MODE, decodedKey);
					FileInputStream inputStream = new FileInputStream(filePath);
					byte[] encryptedBytes = new byte[(int) onlyFile.length()];
					inputStream.read(encryptedBytes);
					
					byte[] decryptedText = cipher.doFinal(encryptedBytes);
					FileOutputStream output = new FileOutputStream(filePath);
					output.write(decryptedText);
					inputStream.close();
					output.close();
				
				
				}
				catch(Exception e)
				{
					e.printStackTrace();
					System.out.println("Exception caught");
				}
				
			}
		if((split_commands[0].contains("edit")) && (split_commands[0].charAt(0) == 'e'))
        {
            String[] jeremyCommand = {"/bin/bash","-c","stat -c %y " + dir + "/" + file};
            originalTimeStamp = executeProcess(jeremyCommand);
        }
			// If the input from the user is valid, the following code is executed. Unless user is reading file.
			if(!commandArgs[2].contains("cat"))
			{
				try
				{
					// Setting up Process Builder.
					builder.command(commandArgs);
					Process process = builder.start();
					
					// set up stdinput and stderror streams
					BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
					BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
					
					// Standard output from process.
					System.out.println("Standard output: ");
					while((outputText = input.readLine()) != null)
					{
						System.out.println(outputText);
					}
					
					// Standard error from process.
					System.out.println("Standard error: ");
					while((outputError = error.readLine()) != null)
					{
						System.out.println(outputError);
					}
					
					// Waits for Process to terminate normally before continuing.
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
			
			if((split_commands[0].contains("edit")) && (split_commands[0].charAt(0) == 'e'))
			{
				try
				{
					String[] jeremyCommand = {"/bin/bash","-c","stat -c %y " + dir + "/" + file};
					generatingKey(jeremyCommand, originalTimeStamp, onlyFile, filePath);
				}
				catch(Exception e)
				{
					e.printStackTrace();
					System.out.println("Exception caught");
				}
			}
			
			// If user is creating a new file.
			if((commandArgs[2].contains(sequence)))
			{
				try
				{
					// Call method to set permissions on file.
					setPerms(commandArgs);
				}
				
				catch(Exception e)
				{
					e.printStackTrace();
					System.out.println("Exception caught");
				}
			}
		}
		else
		{
			System.out.println("Invalid command. Type help for list of commands.");
		}
	}
	
	private static void generatingKey(String[] jeremyCommand, String originalTimeStamp, File onlyFile, String filePath) throws Exception
    {
        String newTimeStamp;
		System.out.println("Waiting for user to save changes to file opened.....");
        do{

        newTimeStamp = executeProcess(jeremyCommand);


        }while(Objects.equals(originalTimeStamp,newTimeStamp));
        System.out.println("Changes are saved and file has been encrypted");
        if(!(Objects.equals(originalTimeStamp,newTimeStamp)))
        {


            KeyGenerator gen = KeyGenerator.getInstance("DES");
            SecretKey key = gen.generateKey();
            String printKey = Base64.getEncoder().encodeToString(key.getEncoded());
            System.out.println("this is your key to decrypt this file upon re opening: \n" + printKey);
            Cipher newCipher;
            newCipher = Cipher.getInstance("DES");
            newCipher.init(Cipher.ENCRYPT_MODE, key);

            FileInputStream inputStream = new FileInputStream(filePath);




            byte[] allBytes = new byte[(int) onlyFile.length()];
            inputStream.read(allBytes);

            byte[] encryptedBytes = newCipher.doFinal(allBytes);
            FileOutputStream output = new FileOutputStream(filePath);
            output.write(encryptedBytes);

            inputStream.close();
            output.close();

        }
    }
	
	private static void setPerms(String[] commandArgs) throws IOException
	{
		// Creating a set of PosixFilePermissions enums. Only add permissions to Owner of file which is Alice.
		Set<PosixFilePermission> listPerms = new HashSet<PosixFilePermission>();
		
		listPerms.add(PosixFilePermission.OWNER_READ);
		listPerms.add(PosixFilePermission.OWNER_WRITE);
		listPerms.add(PosixFilePermission.OWNER_EXECUTE);
		
		// retrieving the name of the file to be created from user input.
		int sub1 = 0;
		int sub2 = commandArgs[2].length();
		for(int i = 0; i < commandArgs[2].length(); i++)
		{
			if(commandArgs[2].charAt(i) == ' ')
			{
				sub1 = i + 1;
			}
		}
		
		// Using method setPosixFilePermissions from Files class to set permissions on file.
		String file = commandArgs[2].substring(sub1,sub2);
		String directory = dir;
		directory = directory.concat("/" + file);
		Files.setPosixFilePermissions(Paths.get(directory),listPerms);
	}
	
	public static void main(String [] args)
	{
		// setting directory of Process Builder which is where System calls will be executed.
		builder.directory(aliceDir);
		
		// ArrayList containing list of commands available to user.
		stringCommands.add("list");
		stringCommands.add("create");
		stringCommands.add("read");
		stringCommands.add("delete");
		stringCommands.add("edit");
		
		String user;
		user = System.getProperty("user.name");
		
		// Verifying the user is Alice.
		if(user.equalsIgnoreCase("alice"))
		{
			System.out.println("Connected.");
	    	System.out.println("Welcome " + user);
			
			if(!Files.exists(pathVar))
			{
				new File("AliceFolder").mkdir();
			}
			listCommands();
			
			acceptCommands();
		}
		else
		{
			System.out.println("Alice is the only user that can run this program.");
		}
	}
}