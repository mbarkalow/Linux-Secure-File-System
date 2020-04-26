import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.*;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Prog1
{
	private static ProcessBuilder builder = new ProcessBuilder();
	private static File aliceDir = new File("/home/alice/Documents/AliceFolder");
	private static String dir = "/home/alice/Documents/AliceFolder";
	//private static CharSequence sequence = "touch";
	
	private static ArrayList<String> stringCommands = new ArrayList<String>();
	
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
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter key to decrypt file");
        String key = scan.nextLine();
        scan.close();
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
                
                System.out.println("Standard output: ");
				timeStamp = input.readLine();
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
	
	private static String[] evaluateCommand(String[] x)
	{
		String buildArgs = "/bin/bashsplit-csplit";
		
		if(stringCommands.contains(x[0]) == false)
		{
			buildArgs = buildArgs.concat("invalid");
		}
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
		String[] newArgs = buildArgs.split("split");
		return newArgs;
	}
	
	private static void executeCommand(String[] split_commands)
	{
		CharSequence sequence = "touch";
		
		String outputText = "";
		String outputError = "";
		
        String originalTimeStamp = "";
        String file = "";
        String filePath = "";
		
		String[] commandArgs = evaluateCommand(split_commands);
		
		file = getFileName(commandArgs[2]);
        filePath = dir;
        filePath = filePath.concat(file);
        
		/*for(int i = 0; i < commandArgs.length; i++)
		{
			System.out.println("commandArgs[" + i + "] = " + commandArgs[i]);
		}
        for(int i = 0; i < args[2].length(); i++)
		{
				System.out.println("This is i: " + i + ":" + args[2].charAt(i));
		}*/
        
        File onlyFile = new File(dir + file);
        
        if((onlyFile.length() == 0) && (split_commands[0].contains("edit"))){
            
        }else if((split_commands[0].contains("edit") )&& (split_commands[0].charAt(0) =='e')) {
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
			System.out.println("TESTING HERE");
            
            String[] jeremyCommand = {"/bin/bash","-c","stat -c %y /home/jeremy/security/" + file};
			System.out.println("Original Time Stamp");
            originalTimeStamp = executeProcess(jeremyCommand);
        }
		
		if((commandArgs[2].equals("invalid")) == false)
		{
			try
			{
				builder.command(commandArgs);
				Process process = builder.start();
				
				// set up stdinput and stderror streams
				BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
				BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
				
				System.out.println("Standard output: ");
				while((outputText = input.readLine()) != null)
				{
					System.out.println(outputText);
				}
				
				// read errors from command
				System.out.println("Standard error: ");
				while((outputError = error.readLine()) != null)
				{
					System.out.println(outputError);
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
			
			if((split_commands[0].contains("edit")) && (split_commands[0].charAt(0) == 'e'))
			{
				try
				{
					String[] jeremyCommand = {"/bin/bash","-c","stat -c %y " + dir + file};
					generatingKey(jeremyCommand, originalTimeStamp, onlyFile, filePath);
				}
				catch(Exception e)
				{
					e.printStackTrace();
					System.out.println("Exception caught");
				}
			}
			
			if((commandArgs[2].contains(sequence)))
			{
				try
				{
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
        do{



        System.out.println("IN WHILE LOOP");
        newTimeStamp = executeProcess(jeremyCommand);


        }while(Objects.equals(originalTimeStamp,newTimeStamp));
        System.out.println("Out of while loop");
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
		Set<PosixFilePermission> listPerms = new HashSet<PosixFilePermission>();
		
		listPerms.add(PosixFilePermission.OWNER_READ);
		listPerms.add(PosixFilePermission.OWNER_WRITE);
		listPerms.add(PosixFilePermission.OWNER_EXECUTE);
		
		int sub1 = 0;
		int sub2 = commandArgs[2].length();
		for(int i = 0; i < commandArgs[2].length(); i++)
		{
			if(commandArgs[2].charAt(i) == ' ')
			{
				sub1 = i + 1;
			}
		}
		//String newCommand = "chmod 700 ";
		//newCommand = newCommand.concat(dir + "/");
		String file = commandArgs[2].substring(sub1,sub2);
		//newCommand = newCommand.concat("newfile.txt");
		String directory = dir;
		directory = directory.concat("/" + file);
		Files.setPosixFilePermissions(Paths.get(directory),listPerms);
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