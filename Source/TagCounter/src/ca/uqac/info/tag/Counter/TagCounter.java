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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.cli.*;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class TagCounter {
	
	 /**
	 * Return codes
	 */
	 public static final int ERR_OK = 0;
	 public static final int ERR_ARGUMENTS = 4;

	 
	public static void main(final String[] args)
	{
		 // Parse command line arguments
		 Options options = setupOptions();
		 CommandLine c_line = setupCommandLine(args, options);
		 
		 String redirectionFile = "";
		 String tagAnalyse = "";
		 String inputFile = "";
		 
		 if (c_line.hasOption("h"))
		 {
		      showUsage(options);
		      System.exit(ERR_OK);
		 }
		 
		 //Contains a redirection file for the output
		 if(c_line.hasOption("redirection"))
		 {
				try 
				{
					redirectionFile = c_line.getOptionValue("redirection");
			    	PrintStream ps;
					ps = new PrintStream(redirectionFile);
					System.setOut(ps);
				} 
				catch (FileNotFoundException e) 
				{
					System.out.println("Redirection error !!!");
					e.printStackTrace();
				} 
		  }
		 
		 //Contains a tag
		 if(c_line.hasOption("Tag"))
		 {
			 tagAnalyse = c_line.getOptionValue("t");
		 }
		 else
		 {
		    System.err.println("No Tag in Arguments");
		    System.exit(ERR_ARGUMENTS);
		 }
		 
		//Contains a InputFile 
		 if(c_line.hasOption("InputFile"))
		 {
			 inputFile = c_line.getOptionValue("i");
		 }
		 else
		 {
		    System.err.println("No Input File in Arguments");
		    System.exit(ERR_ARGUMENTS);
		 }
		 
		 //Start of the program
		 System.out.println("-----------------------------------------------");
		 System.out.println("The count of the Tag is start !!!");
		 
		// Throw the Sax parsing for the file 
		try 
		{
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser;
			parser = factory.newSAXParser();
				
			File Tagfile = new File(inputFile);
			DefaultHandler manager = new SaxTagHandlers(tagAnalyse);
			parser.parse(Tagfile, manager);
		} 
		catch (ParserConfigurationException e) 
		{
			System.out.println("Parser Configuration Exception for Sax !!!");
			e.printStackTrace();
			
		} 
		catch (SAXException e) 
		{
			System.out.println("Sax Exception during the parsing !!!");
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			System.out.println("Input/Ouput Exception during the Sax parsing !!!");
			e.printStackTrace();
		}
	}

	 /**
	 * Sets up the options for the command line parser
	 * @return The options
	 */
	@SuppressWarnings("static-access")
	private static Options setupOptions()
	{
		Options options = new Options();
		Option opt;
		opt = OptionBuilder
				.withLongOpt("help")
				.withDescription(
						"Display command line usage")
						.create("h");
		options.addOption(opt);
		opt = OptionBuilder
				.withLongOpt("Tag")
				.withArgName("x")
				.hasArg()
				.withDescription(
						"The tag will be counted")
						.create("t");
		options.addOption(opt);
		opt = OptionBuilder
				.withLongOpt("InputFile")
				.withArgName("x")
				.hasArg()
				.withDescription(
						"Set the iput file to analyse")
						.create("i");
        options.addOption(opt);   
        opt = OptionBuilder
        		.withLongOpt("redirection")
        		.withArgName("x")
        		.hasArg()
        		.withDescription(
        				"Set the redirection file for the System.out")
        				.create("r");
         options.addOption(opt);
      return options;
	}

	/**
	 * Sets up the command line parser
	 * @param args The command line arguments passed to the class' {@link main}
	 * method
	 * @param options The command line options to be used by the parser
	 * @return The object that parsed the command line parameters
	*/
	private static CommandLine setupCommandLine(String[] args, Options options)
	{
	  CommandLineParser parser = new PosixParser();
	  CommandLine c_line = null;
	  try
	  {
	    // parse the command line arguments
	    c_line = parser.parse(options, args);
	  }
	  catch (org.apache.commons.cli.ParseException exp)
	  {
	    // oops, something went wrong
	    System.err.println("ERROR: " + exp.getMessage() + "\n");
	    System.exit(ERR_ARGUMENTS);    
	  }
	  return c_line;
	}

	/**
	 * Show the benchmark's usage
	 * @param options The options created for the command line parser
	 */
	private static void showUsage(Options options)
	{
	  HelpFormatter hf = new HelpFormatter();
	  hf.printHelp("java -jar TagCounter.jar [options]", options);
	}

}