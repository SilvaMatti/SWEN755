//authors : Pavithra and silva
import java.io.*;
import java.util.*;
//RMI imports
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
//
public class SensorProcessor extends HeartbeatReceiver{

	public String nameofsensor;
	public SensorProcessor()
	{
	}
	// Random nubmer generator for fault injection
	public static int randgen(int min, int max) {
		    Random ran = new Random();
		    int Num = ran.nextInt((max - min) + 1) + min;
		  	System.out.println("\n" + "Random number is " + Num );
		    return Num;
	}

	public static void main(String[] args) {

	        String host = (args.length < 1) ? null : args[0];
	        HeartbeatReceiver HBR = new HeartbeatReceiver();
			long setUpdatedTime = System.currentTimeMillis();
	        try {
	            Registry registry = LocateRegistry.getRegistry(host);
	            HeartBeat stub = (HeartBeat) registry.lookup("Hello");
	            HeartBeat stub1 = (HeartBeat) registry.lookup("Hell");
	            Date now;
				int a = randgen(0,3);
				boolean activeNode = true;
				//stub.sendMessage();
				stub1.sendMessage();
				while(activeNode)
				{
					try{

					if (a!=0)
					{
						//String response = stub.sendMessage();
						//String response1 = stub1.sendMessage();
						stub.sendMessage();
						stub1.sendMessage();
						setUpdatedTime = System.currentTimeMillis();
						a = randgen(0,3);
						int m= 3/a;
						HBR.checkAlive(setUpdatedTime);
					}

					}
					catch(Exception e){

						System.out.println("Failure Detected: Active sensor is dead!\n Switching to redundant node!");
						activeNode=false;

					}

					try{
					while(!activeNode)
					{

						setUpdatedTime = System.currentTimeMillis();
						stub1.sendMessage();
						HBR.checkAlive(setUpdatedTime);
					}}
					catch(Exception e){System.out.println("Failure Detected: Redundant sensor is dead!");}

				}//end while
	        }//try
	        catch (Exception e) {
	           System.out.println("The system is crashed!\nThere is no Backup option available!\nSwitch to manual mode!");
	        }//catch
    }//main
}//class
