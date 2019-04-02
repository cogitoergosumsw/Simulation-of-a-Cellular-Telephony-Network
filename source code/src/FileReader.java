import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

// converting "PCS_TEST_DETERMINSTIC_1819S2.xls" file to .csv format for easier parsing of data
public class FileReader {
    private Scanner scanner;
    public FileReader(String fileName) throws FileNotFoundException {
        scanner = new Scanner(new File(fileName));
        scanner.useDelimiter(",");
    }

    public String[] readOneRow(){
        return scanner.nextLine().split(",");
    }

    public boolean hasNextLine(){
        return scanner.hasNext();
    }
}
