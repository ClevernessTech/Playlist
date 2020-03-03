/* Starting code for Assignment 1 Version 2
 * Student: Jose Soto
 * CISC 3130-MY9
 * Brooklyn College, Spring 2020
 *
 * Used the opencsv-5.1.jar to do the CSV parsing when inputting the csv files, which have had the titles edited out
 * Apache commons-lang3-3.9.jar dependency is required.
 */
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;


/* The List Playlist is composed of a series of artist names */
class Playlist<size> {


        public static void main(String[] arg){
            String csvFile = "src/csv/regional-global-weekly-2020-02-14--2020-02-21.csv";
            int counter = 0, numFiles = 0, size = 200; //counter is for incrementing in while loop, size is size of data from the file

            File folder = new File("csvfiles");//creates an object folder whose pathname is the reference path for folder csvfiles in project
            File[] listOfFiles = folder.listFiles();//creates an array of file names
            for (File file : listOfFiles) {//will iterate as long
                if (file.isFile()) {
                    numFiles++;
                }
            }
            int trueSize = size * numFiles;
            Queue<Single> q = new Queue<Single>(trueSize);
            Single[] linkArray = new Single[trueSize]; //array of type Single to hold values that are being inserted into linked list artistNames during loop

            linkArray = readFiles(csvFile, trueSize);
            /*the for loop is run once to count the number of files in the directory, then that value is multiplied by size since we
            * know how many tracks are in 1 csv file to create a queue of that length*/

            SortedArtists sortedList = new SortedArtists(linkArray);;
            for(int j = 0; j<trueSize; j++){
                linkArray[j] = sortedList.remove(); //deletes first line from beginning of sorted link and saves it into array, making it sorted
            }

            for(int j = 0; j<trueSize; j++){
                q.insert("'" + linkArray[j].track + "'" + " by " + linkArray[j].artist);
            }
            while( !q.isEmpty() )    // remove and display
            {                            //    all items
                String n = q.remove();  // (40, 50, 60, 70, 80)
                System.out.print(n);
                System.out.print("\n ");
            }


        }//end of main method

    public static Single[] readFiles(String csvFile, int trueSize){
        int counter = 0;
        Single[] dataArray = new Single[trueSize]; //array of type Single to hold values that are being inserted into linked list artistNames during loop

        File folder = new File("csvfiles");
            File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                try {
                    CSVReader reader = new CSVReader(new FileReader(file));
                    reader.skip(2); //CSVReader method to skip certain number of lines before reading data
                    String[] inputLine;

                    while ((inputLine = reader.readNext()) != null) {
                /*Similar to last weeks program, The tracks position in the weekly chart, its name, its artist, its number of streams,
                and its spotify url are in the first/second/third/fourth/fifth position after the read line is split by CSVReader which
                takes care of all the misc. commas that may appear in tracks/artists in the csv file. Used created setter methods to feed
                the data into the linked list*/
                        int tempPosition = Integer.parseInt(inputLine[0]);
                        String tempTrack = inputLine[1];
                        String tempArtist = inputLine[2];
                        int tempStreams = Integer.parseInt(inputLine[3]);
                        String tempUrl = inputLine[4];
                        dataArray[counter] = new Single(tempPosition, tempTrack, tempArtist, tempStreams, tempUrl);/*stores a object
                         * of Single into current position of linkArray*/
                        counter++;
                    }
                    reader.close();//stops reading file after while loop
                }catch (IOException | CsvValidationException e) {
                    e.printStackTrace();
                }catch(NumberFormatException ex) {//Error handling incase wrong data is run through parseInt
                    System.out.println("Error. Please input a valid number :-");
                }
            }
        }
        return dataArray;
    }//end of readFiles method

}//end of Playlist class










