/* Starting code for Assignment 2
 * Student: Jose Soto
 * CISC 3130-MY9
 * Brooklyn College, Spring 2020
 *
 * Used the opencsv-5.1.jar to do the CSV parsing this time when inputting the csv files, Apache commons-lang3-3.9.jar dependency is required.
 * Started with last weeks code as a base for gathering the data, with the use of Opencsv to get around the issue of other commas appearing in
 * the CSV that threw errors when just using the splitline command.
 *
 *Program reads through the created csvfiles directory first to count the number of files, then takes that counter and multiplies it by the
 * known size of the data in each csv file to obtain the total number of slots the queue will need.  I didn't go with merging because I couldn't
 * really see how to get it going with 2 different queues.
 *
 * readFiles method in playlist essentially does alot of what last weeks main method did, just with the added if loop that will iterate for each file
 * in the directory.  An array of type Single is created in method to return to main that will have all parsed data for each file, in this case 5.
 * The file counter from main is passed in so an array of 1000 slots is created to hold all the data but if a file is added/removed it's flexible enough
 * to keep going, although using a data set from spotify that DOESN'T use 200 files would cause an error.  Could be mitigated by just iterating through the entire loop
 * with a counter variable and using that.
 *
 * I didn't finish the whole assignment though, it will output the playlist but I didn't add a menu to see(or peek) at the current top track, pop single tracks etc.  Couldn't
 * see a ways about it and the assignment is due today.
 */
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class Playlist<size> {
        public static void main(String[] arg){
            int numFiles = 0, size = 200; //counter is for incrementing in while loop, size is size of data from the file

            System.out.println("Here is the playlist you requested.  The following weeks files were used in creating this for you.");
            File folder = new File("csvfiles");//creates an object folder whose pathname is the reference path for folder csvfiles in project
            File[] listOfFiles = folder.listFiles();//creates an array of file names
            for (File file : listOfFiles) {//will iterate as long
                if (file.isFile()) {
                    System.out.println(file);
                    numFiles++;//counts number of files in directory
                }
            }
            int trueSize = size * numFiles;//creates new variable so instead of merging queues, can create 1 long queue since de-duping wasn't necessarily required
            Queue<Single> q = new Queue<Single>(trueSize);
            Single[] linkArray = new Single[trueSize]; //array of type Single to hold values that are being inserted into linked list artistNames during loop

            linkArray = readFiles(trueSize);
            /*the for loop is run once to count the number of files in the directory, then that value is multiplied by size since we
            * know how many tracks are in 1 csv file to create a queue of that length*/

            SortedArtists sortedList = new SortedArtists(linkArray);//creates a sorted linked list by track using the returned array from readFiles
            for(int j = 0; j<trueSize; j++){
                linkArray[j] = sortedList.remove(); //deletes first line from beginning of sorted link and saves it into array, making it sorted
            }
            for(int j = 0; j<trueSize; j++){
                q.insert("'" + linkArray[j].track + "'" + " by " + linkArray[j].artist);
            }//feeds the linked list into linkArray overwriting it, then feeds linkArray into the queue q for manipulation afterwards

            while( !q.isEmpty() ){
                String n = q.remove();
                System.out.print(n);
                System.out.print("\n ");
            }//prints the queue to screen
        }//end of main method

    public static Single[] readFiles(int trueSize){
        int counter = 0;
        Single[] dataArray = new Single[trueSize]; //array of type Single to hold values that are being inserted into linked list artistNames during loop

        File folder = new File("csvfiles");
            File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {//will iterate this if loop for as long as there are files in the csvfiles directory in the project folder
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
        return dataArray;//returns the array information to main
    }//end of readFiles method
}//end of Playlist class










