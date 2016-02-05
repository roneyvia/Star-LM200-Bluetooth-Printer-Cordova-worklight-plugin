package org.phonegap.plugin;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import android.content.Context;

import com.starmicronics.stario.PortInfo;
import com.starmicronics.stario.StarIOPort;
import com.starmicronics.stario.StarIOPortException;
import com.starmicronics.stario.StarPrinterStatus;

public class StarBTPrint extends CordovaPlugin {
	StarIOPort port = null;
	String messageCommand;
	String portName;
	String macAddress;
	
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		
		if (action.equals("searchBT")) {
			try {
				messageCommand = args.getString(0);
				serachBT(callbackContext, messageCommand);
			} catch (StarIOPortException e) {
				 System.out.println("============== CATCH 1 at serachBT");
				e.printStackTrace();
			}
			return true;
		} 
		
		else if(action.equals("startPrnt")){
			try {
				messageCommand = args.getString(0);
				startPrnt(callbackContext, messageCommand);
			} catch (StarIOPortException e) {
				 System.out.println("============== CATCH 1 at startPrnt");
				e.printStackTrace();
			}
			return true;
		}
		
		return false;
	}
	
	
	public void serachBT(CallbackContext callbackContext, String messageCommand) throws StarIOPortException{
		//The following would be an actual usage of searchPrinter:
		try
		{
			List<PortInfo> portList = StarIOPort.searchPrinter("BT:");
			for (PortInfo port : portList) {
				portName =  port.getPortName();
				macAddress = port.getMacAddress();
				
				System.out.println("============== portName at serachBT=="+portName);
				System.out.println("==============  at serachBT=="+macAddress);
			}
			
			JSONArray json = new JSONArray();
			json.put(portName);
			json.put(macAddress);
			//callbackContext.success(json);
			startPrnt(callbackContext, messageCommand); 
		}
		catch (StarIOPortException e)
		{
		 System.out.println("============== CATCH 2 at serachBT");
		 callbackContext.error("Failuer");
		}
		
	}
	
	public void startPrnt(CallbackContext callbackContext, String messageCommand) throws StarIOPortException{
		try
		{		
			
			String portSettings = "portable;d127";
			Context context = null;
		    port = StarIOPort.getPort(portName, portSettings, 10000, context);
			//Start checking the completion of printing
			StarPrinterStatus status = port.beginCheckedBlock();
			//Printing
			//byte[] command = PrinterFunctions.createPrintData(paperWidthInch);
			
			byte[] command = messageCommand.getBytes();
			System.out.println("==============command.length== "+command.length);
			port.writePort(command, 0, command.length);
			//End checking the completion of printing
			status = port.endCheckedBlock();
			if (status.offline == true) {
			//If true, then the printer is offline.
				 System.out.println("============== Printer is offline at startPrnt");
			}
			System.out.println("============== Message Before Byte== "+messageCommand);
			System.out.println("============== Message After Byte== "+command);
			
			
		//	callbackContext.success("Success StartPrnt = "+command);
		}
		catch (StarIOPortException e)
		{
			System.out.println("============== Catch 2 Exception at startPrnt=="+e);
			 callbackContext.error("Error 1");
		}
		finally
		{
			if (port != null)
			{
				try
				{
				StarIOPort.releasePort(port);
				callbackContext.success("Success Finally");
				}
				catch (StarIOPortException e) {
					System.out.println("============== Catch 2 Exception at startPrnt=="+e);
					 callbackContext.error("Error 2");
					}
			}
			
			
		}
	}
	
}
