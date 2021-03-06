/* Copyright (c) 2011, SaferMobile/MobileActive - https://safermobile.org */
/* See LICENSE for licensing information */

package org.safermobile.clear.micro.sms;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.microedition.io.Connector;
import javax.wireless.messaging.Message;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;

import org.safermobile.micro.utils.Logger;

public class SMSManager implements Runnable {

	private MessageConnection connection;
	private Vector  listeners;
	private boolean stop;
	private int 	port;
	
	//private static Hashtable _instances;
	private final static String PROTOCOL = "sms://";
	
	/**
	 * Creates a SMS Server listening on
	 * the specified port.
	 * 
	 * @param port server port.
	 */
	private SMSManager(int port) {
		this.listeners = new Vector();
		this.port = port;
	}
	
	/*
	public static synchronized SMSManager getInstance (int port) throws IOException
	{
		if (_instances == null)
			_instances = new Hashtable();
		
		String key = "" + port;
		
		SMSManager smsServer;
		
		if (_instances.containsKey(key))
		{
			smsServer = (SMSManager)_instances.get(key);
			Logger.debug("SMSManager", "found exist SMSManager on port: " + key);
		}
		else
		{
			smsServer = new SMSManager (port);
			smsServer.start();
			Logger.debug("SMSManager", "created new SMSManager on port: " + key);

			_instances.put(key, smsServer);
		}
		
		return smsServer;
	}*/
	
	public static void sendSMSAlert (String phoneNumber, String message) throws InterruptedIOException, IOException
	{
		
			/*
			MessageConnection sender = (MessageConnection) Connector.open(address);
			
		    //creates a new TextMessage
		    TextMessage textMessage = (TextMessage)connection.newMessage(MessageConnection.TEXT_MESSAGE);
		    textMessage.setAddress(PROTOCOL + address + ":" + port);
		    textMessage.setPayloadText(message);
		    connection.send(textMessage);*/
		    
		    String url = "sms://" +  phoneNumber;
		    MessageConnection connection =
		    	(MessageConnection) Connector.open(url);
		    	TextMessage msg = (TextMessage) connection.newMessage(
		    	MessageConnection.TEXT_MESSAGE);
		    	msg.setPayloadText(message);
		    	connection.send(msg);
		    	connection.close();
		 
	}
	
	/**
	 * Starts the server.
	 * 
	 * @throws IOException - Any connection related error.
	 */
	public void start() throws IOException {
		Thread t = new Thread(this);
		t.start();
	}
	
	/**
	 * Stops the server.
	 */
	public void stop() {
		this.stop = true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		/*
		try {
			this.connection = (MessageConnection) Connector.open(PROTOCOL + ':' + this.port);
			
			while (!stop) {
				try {
					Message message = this.connection.receive();
					
					Enumeration enumeration = this.listeners.elements();
					while (enumeration.hasMoreElements()) {
						SMSListener listener = (SMSListener) enumeration.nextElement();
						listener.messageReceived(message);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			this.connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	/**
	 * Adds a listener for new messages.
	 * 
	 * @param listener target listener.
	 */
	public void addListener(SMSListener listener) {
		if (!this.listeners.contains(listener)) {			
			this.listeners.addElement(listener);
		}
	}
	
	/**
	 * Removes a listener for new messages.
	 * 
	 * @param listener target listener.
	 */
	public void removeListener(SMSListener listener) {
		this.listeners.removeElement(listener);
	}
	
}
