import java.io.*;

import spy.Operative;
import client.*;
import common.*;

/**
 * This class constructs the UI for a chat client.  It implements the
 * chat interface in order to activate the display() method.
 * Warning: Some of the code here is cloned in ServerConsole 
 * 
 * @author Kevin Johnson 6017605;
 * @author ZhenHao Wu 6911292;
 * @version Dec 2014
 */
public class ClientConsole implements ChatIF 
{
  //Class variables *************************************************
  
  /**
   * The default port to connect on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Instance variables **********************************************
  
  /**
   * The instance of the client that created this ConsoleChat.
   */
   ChatClient client;
   Operative operative;

  
  //Constructors ****************************************************

  /**
   * Constructs an instance of the ClientConsole UI.
   *
   * @param host The host to connect to.
   * @param port The port to connect on.
   */
  public ClientConsole(String host, int port,Operative operative) 
  {
    try 
    {
      client= new ChatClient(host, port, this,operative);
    } 
    catch(IOException exception) 
    {
      System.out.println("Error: Can't setup connection!"
                + " Terminating client.");
      System.exit(1);
    }
  }

  
  //Instance methods ************************************************
  
  /**
   * This method waits for input from the console.  Once it is 
   * received, it sends it to the client's message handler.
   */
  public void accept(Operative operative) 
  {
    try
    {
      BufferedReader fromConsole = 
        new BufferedReader(new InputStreamReader(System.in));  
      String message;
      
      while (true) 
      {
    	System.out.println("Enter a message.");
        message = fromConsole.readLine();
        client.handleMessageFromClientUI(message,operative);
        
      }
    } 
    catch (Exception ex) 
    {
      System.out.println
        ("Unexpected error while reading from console!");
    }
  }

  /**
   * This method overrides the method in the ChatIF interface.  It
   * displays a message onto the screen.
   *
   * @param message The string to be displayed.
   */
  public void display(Object message) 
  {
    System.out.println("> " + message);
  }

  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of the Client UI.
   *
   * @param args[0] The host to connect to.
   */
  public static void main(String[] args) 
  {
    String host = "";
    int port = 0;  //The port number

    try
    {
      host = args[0];
    }
    catch(ArrayIndexOutOfBoundsException e)
    {
      host = "localhost";
    }
    
    BufferedReader fromConsole = 
            new BufferedReader(new InputStreamReader(System.in));  
    
    /*
     * Console greeting.
     */
    
    System.out.println("WELCOME TO SPYCHAT. \n --------------");
    System.out.println("\n This method handles any messages received from the client.");                        
    System.out.println("\n #Mission - returns missions of current operative. ");                                
    System.out.println("\n #CreateMission - Sends mission  with specified operative NOTE ARRAY LIST has 3 objects.");               
    System.out.println("\n #Validate - Sends operative to server to see if Operative is legit. ");                    
    System.out.println("\n #MissionComplete - Sends mission complete code. ");                         
    System.out.println("\n #CreateResource - Sends resouirce to be created by server." );                          
    System.out.println("\n #CreateOperative - sends and Operative to be created in the server server. ");
    System.out.println("\n Note third element in array list is arraylist of other operatives. ");                     
    System.out.println("\n #Quit sends operative and disconnect message to server. \n");                        
    
    String codeName = null;
	try {
		System.out.println("Enter code name: ");
		codeName = fromConsole.readLine();
	} catch (IOException e) {
		System.out.println("CodeName invalid. Session disconnected.");
	}
    String pWord = null;
	try {
		System.out.println("Enter password: ");
		pWord = fromConsole.readLine();
	} catch (IOException e) {
		System.out.println("Password invalid. Session disconnected.");
	}

    
    Operative operative = new Operative(codeName,pWord);
    
    
    ClientConsole chat= new ClientConsole(host, DEFAULT_PORT,operative);
   
    
    // Initially send operative credentials to server. 
    try {
		chat.client.handleMessageFromClientUI("#Validate",operative);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

   
    
    chat.accept(operative);  //Wait for console data
  }
}

