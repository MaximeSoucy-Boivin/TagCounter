/*
         TagCounter, a tag counter in a xml file
         Copyright (C) 2013 Maxime Soucy-Boivin

        This program is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        (at your option) any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the GNU General Public License
        along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ca.uqac.info.tag.Counter;

import org.xml.sax.*;
import org.xml.sax.helpers.*;


public class SaxTagHandlers extends DefaultHandler
{
	//Flags who help to know where the parsing is
	private boolean inTrace, inEvent, inVar, inTag;
	
	private String TagAnalyse = "";
	
	private int TagCounter = 0;
	//Buffer who help to get the informations
	private StringBuffer buffer;
	
	public SaxTagHandlers()
	{
		super();
	}
	
	public SaxTagHandlers(String Tag)
	{
		this();
		TagAnalyse = Tag;
	}
	
	/**
	* Set the value of the position for the variable inTrace
	* @param value can take true of false
	*/
    public void setInTrace(boolean value)
	{
		inTrace = value;
	}
		
	/**
	* Returns the value of the variable that contains inTrace at the precise moment when this function is called
	* @return true or false
	*/
	public boolean getInTrace()
	{
		return inTrace;
	}
		
	/**
	* Set the value of the position for the variable inEvent
	* @param value can take true of false
	*/
	public void setInEvent(boolean value)
	{
		inEvent = value;
	}
		
	/**
	* Returns the value of the variable that contains inEvent at the precise moment when this function is called
	* @return true or false
	*/
	public boolean getInEvent()
	{
		return inEvent;
	}
		
	/**
	* Set the value of the position for the variable inVar
	* @param value can take true of false
	*/
	public void setInVar(boolean value)
	{
		inVar = value;
	}
		
	/**
	* Returns the value of the variable that contains inVar at the precise moment when this function is called
	* @return true or false
	*/
	public boolean getInVar()
	{
		return inVar;
	}
	
	/**
	* Set the value of the position for the variable inTag
	* @param value can take true of false
	*/
	public void setInTag(boolean value)
	{
		inTag = value;
	}
		
	/**
	* Returns the value of the variable that contains inTag at the precise moment when this function is called
	* @return true or false
	*/
	public boolean getInTag()
	{
		return inTag;
	}
	
	public void addTagCounter(int value)
	{
		TagCounter +=value;
	}
	
	public int getTagCounter()
	{
		return TagCounter;
	}
	
	/**
	* Detects the opening of each tag in the file and perform the corresponding treatment
	*/
	public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException
	{
		if (qName.equals(TagAnalyse))
		{
			setInTag(true);
			addTagCounter(1);
		}
		else if(qName.equals("Trace"))
		{
			setInTrace(true);
		}
		else if(qName.equals("Event"))
		{
			setInEvent(true);
		}
		else if(qName.contains("p"))
		{
			setInVar(true);
		}
	}
	
	/**
	* Detects the closing of each tag in the file and perform the corresponding treatment
	*/
	public void endElement(String uri, String localName, String qName)throws SAXException
	{
		if(qName.equals("Trace"))
		{
			setInTrace(false);
			System.out.println("The number of " + TagAnalyse + " is " + getTagCounter());
		}
		else if(qName.equals("Event"))
		{
			setInEvent(false);
		}
		else if(qName.contains("p"))
		{
			setInVar(false);
		}
		else if (qName.equals(TagAnalyse)) 
		{
			setInTag(false);
		}          
	}
	
	/**
	* Function for detecting the characters to be treated
	*/
	public void characters(char[] ch,int start, int length)throws SAXException
	{
		String read = new String(ch,start,length);
		if(buffer != null) buffer.append(read);       
	}
	
	/**
	* Start of the Sax Parsing 
	*/
	public void startDocument() throws SAXException 
	{
		System.out.println("Sax parser use for parsing the xml file");
		System.out.println("-----------------------------------------------");
	}
	
	/**
	* End of the Sax Parsing 
	*/
	public void endDocument() throws SAXException 
	{
		System.out.println("-----------------------------------------------");
		System.out.println("End of the Sax parsing");
	}
}
