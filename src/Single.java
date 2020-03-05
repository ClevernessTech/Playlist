class Single { //Single will take in all the elements provided in the csv file
    int position;
    String track;
    String artist;
    int streams;
    String url;
    Single next;

    Single(int ps, String st, String sa, int ss, String su){
        position = ps;
        track = st;
        artist = sa;
        streams = ss;
        url = su;
    }

    void displayLink()
    {
        System.out.printf("%d %-60s %-20s %-15d %-40s%n", position, track, artist, streams, url);
        //Formated with print f to maintain similar spacing in console window due to variances in track titles/artist names
    }
}//end of Single class

/* The List SortedArtists is composed of a series of artist names */
class SortedArtists<size> {
    private Single first;
    private Single last;

    SortedArtists() {
        first = null;
    }

    SortedArtists(Single[] linkArr) {
        first = null; // initialize list
        for (int j = 0; j < linkArr.length; j++)
            insertSorted(linkArr[j]);
    }

    private boolean isEmpty() {
        return (first == null);
    }

    private void insertSorted(Single k)//Sorts by artists name as be request in assignment
    { // make new link
        Single previous = null; // start at first
        Single current = first; // until end of list,
        while (current != null && k.track.compareTo(current.track) > 0) { // or key > current,
            previous = current;
            current = current.next; // go to next item
        }
        if (previous == null) // at beginning of list
            first = k; // first --> k
        else // not at beginning
            previous.next = k; // old prev --> k
        k.next = current; // k --> old current
    }

    public void insertFirst(int ps, String st, String sa, int ss, String su)//adds to top of the list, never used but kept for testing/flexibility
    { // make new link
        Single newLink = new Single(ps, st, sa, ss, su);
        newLink.next = first; // newLink --> old first
        first = newLink; // first --> newLink
    }

    private void insertLast(int ps, String st, String sa, int ss, String su)//adds to bottom of the list
    {
        Single newLink = new Single(ps, st, sa, ss, su);
        if (isEmpty()) // if empty list,
            first = newLink; // first --> newLink
        else
            last.next = newLink; // old last --> newLink
        last = newLink; // newLink <-- last
    }

    public Single deleteFirst() // delete first item, never used but kept for testing/flexibility
    { // (assumes list not empty)
        Single temp = first; // save reference to link
        first = first.next; // delete it: first-->old next
        return temp; // return deleted link
    }

    public void displayList() {
        Single current = first; // start at beginning of list
        while (current != null) // until end of list,
        {
            current.displayLink(); // print data
            current = current.next; // move to next link
            System.out.println("");
        }
        System.out.println("");
    }

    Single remove() // return & delete first link, used after creating a sorted Link list using a established array of data.
    { // (assumes non-empty list)
        Single temp = first; // save first
        first = first.next; // delete first
        return temp; // return value
    }
}