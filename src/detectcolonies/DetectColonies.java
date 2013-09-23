/*
 * Hamza Azeem
 * Tuesday April 2, 2013
 *
 * This application reads slide data from a file located in the same directory as this java file.
 * It uses two classes to read, analyze, and then display the size, respected location and color of the colonies on the slide to the user as the output.
 * This current class contains the main method which calls upon the Slide class to do the analysis of the slide data and then display the slide and its data to the user.
 * 
 */

package detectcolonies;

import java.io.*;

public class DetectColonies{

private static char[][] slideData;//global 2-dimensional char array to store each colony
    
    /*
     * Run main method
     */
    public static void main(String[] args){
	String grid = "slide.txt";
	populateArray(grid);
        displaySlide();//displays the slide read from the file
        displayColonies();//displays analysis of slide data file (colony co-ordinates, size and colour)
    }
	
	private static void populateArray(String file)
	{
            try
            {
		//Read file1.txt stream
		File slideFile = new File(file);
		FileReader in = new FileReader(slideFile);
		BufferedReader readSlide = new BufferedReader(in);
		
		//define and read length and width variables for locating colonies
		int length = Integer.parseInt(readSlide.readLine());
		int width = Integer.parseInt(readSlide.readLine());
		slideData = new char[length][width];//makes length and width the x and y indexes for 2d array
		
		/*
		 * Read in characters of each co-ordinate in Slide data into char array
		 */
		for (int row = 0; row < length; row++)
		{
                    for (int col = 0; col < width; col++)
                    {
                        slideData[row][col] = (char)readSlide.read();
                    }
                    readSlide.readLine(); //read past end-of-line
		}
				
		/*
		 * Close streams
		 */
		readSlide.close();
		in.close();
		 /*
		  * Catch clauses
		  */
            } catch (FileNotFoundException e) {
		System.err.println("File does not exist or could not be found.");
            } catch (IOException e){
		System.err.println("Problem reading file");
            }
        }
		
    /*
     * Print out Slide data to user
     */
    private static void displaySlide()
    {        
        for (int row = 0; row < slideData.length; row++){
            for (int col = 0; col < slideData[0].length; col++){
                System.out.print(slideData[row][col]);
            }
            System.out.println();
        }
    }
	
    /*
     * Displays analysis results for colonies' colour, size and location
     */
    private static void displayColonies()
    {        
        int size;//Counter for size of colonies
        for (int row = 0; row < slideData.length; row++)
        {
            for (int col = 0; col < slideData[0].length; col++)
            {
                //If any number (colour) but 0, then increase counter for respected colony colour
                if (slideData[row][col] == '1' || slideData[row][col] == '2' ||slideData[row][col] == '3' || slideData[row][col] == '4' || slideData[row][col] == '5' || slideData[row][col] == '6' || slideData[row][col] == '7' || slideData[row][col] == '8' || slideData[row][col] == '9')
                {
                    char colour = slideData[row][col];
                    size = collectCells(row, col, slideData[row][col]);//Passes row, col and 2D char array into Depth-First Search Algorithm to compute size of each colony
                    System.out.println("Colour: " + colour + " ~ Size: " + size + " ~ Location: (" + (row + 1)+ "," + (col + 1) + ")");//Added '+1' so that co-ordinates will start from 1 instead of 0
                }
            }
        }
    }
	
    /*
     * Recursive Algorithm (Depth-First Search) to determine colour and location of colonies
     */
    private static int collectCells(int row, int col, char colour)
    {
        if((row < 0) || (row >= slideData.length) || (col < 0) || (col >= slideData[0].length) || (slideData[row][col] != colour))
        {
            return(0);// if coordinate in question is not on grid or not the same colour, method stops counting
        } else {
            slideData[row][col] = 0;
            return(1 + collectCells(row+1, col, colour) + collectCells(row-1, col, colour) + collectCells(row, col+1, colour) + collectCells(row, col-1, colour));          
        }
    }	
}