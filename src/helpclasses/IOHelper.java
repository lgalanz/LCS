package helpclasses;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/*******************************
 * IOHelper reads input from the file
 *
 * Methods:
 *      public List<String> read() - reads tokens from the file into the list
 *
 * @author Larisa Galanzovskaia
 * @version 0.1 2021-10-30
 */
public class IOHelper {
    private String outFilename = "report";

    public IOHelper(String filename) {
        this.outFilename = filename;
    }

    public IOHelper() {  }

    /*********
     * Reads the input file into a list since we don't know initially
     * how many elements the file contain. Therefore, the List is an
     * appropriate structure to store the input data.
     */
    public List<String> read(String inputFile) {
        // Check whether the file exists
        if(!Files.exists(Paths.get(inputFile)))
        {
            System.out.println("IOHelper.read: Please make sure that the input file exists: " + inputFile);
            System.exit(1);
        }

        List<String> inputData = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String currentLine = "";
            while((currentLine = reader.readLine()) != null) {
                inputData.add(currentLine);
            }
            reader.close();
            
            if(inputData.isEmpty()) {
                System.out.println("IOHelper.read: Please ensure that the input file is not empty: " + inputFile);
                System.exit(1);
            }
        } catch (IOException exception) {
            System.out.println("IOHelper.read Error: " + exception);
            System.exit(1);
        }
        return inputData;
    }

    /**
     * printToFile prints the report of the latest comparison
     * and accepts all the parameters relevant for the reporting
     * @param seqXKey
     * @param seqYKey
     * @param stringX
     * @param stringY
     * @param c
     * @param b
     * @param lcs
     * @param lcsTime
     * @param printTime
     * @param count
     * @param printCount
     * @param append - true if the output should be appended to the existing file
     */
    public void printToFile(String seqXKey, String seqYKey,
                            String stringX, String stringY,
                            int[][] c, LCSFinder.Direction[][] b, String lcs,
                            long lcsTime, long printTime,
                            int count, int printCount, boolean append) {
        try {
            //private final String outputPath = System.getProperty("user.dir") + "/output/";
            String outputPath = "../../../output/";
            PrintWriter printWriter = new PrintWriter(new FileWriter(outputPath + outFilename + ".txt", append));

            printWriter.printf("Sequence #%s | Length %d\n", seqXKey, stringX.length());
            for(int i = 0; i < stringX.length(); i++) {
                printWriter.printf("%2s", stringX.charAt(i));
            }
            printWriter.println();
            printWriter.println();
            printWriter.printf("Sequence #%s | Length %d\n", seqYKey, stringY.length());
            for(int i = 0; i < stringY.length(); i++) {
                printWriter.printf("%2s", stringY.charAt(i));
            }
            printWriter.println();
            printWriter.println();
            printWriter.printf("Longest Common Subsequence | Length %d\n", lcs.length());
            for(int i = 0; i < lcs.length(); i++) {
                printWriter.printf("%2s", lcs.charAt(i));
            }
            printWriter.println();
            printWriter.println();
            printWriter.printf("Longest Common Subsequence Runtime: %d\n", lcsTime);
            printWriter.printf("Print LCS Runtime: %d\n", printTime);
            printWriter.printf("Individual comparisons count: %d\n", count);
            printWriter.printf("Printing iterations count: %d\n", printCount);

            printWriter.println();

            // Print out the matrix of LCS counts
            printWriter.printf("%3s |%3s", " ", " ");
            for(int i = 0; i < stringY.length(); i++) {
                printWriter.printf("%3s", stringY.charAt(i));
            }
            printWriter.print("\n------");
            for(int i = 0; i < stringY.length() + 1; i++) {
                printWriter.print("---");
            }
            printWriter.printf("\n%3s |", " ");
            for(int i = 0; i < stringY.length() + 1; i++) {
                printWriter.printf("%3d", c[0][i]);
            }
            printWriter.print("\n");
            for(int i = 0; i < stringX.length(); i++) {
                printWriter.printf("%3s |", stringX.charAt(i));
                printWriter.printf("%3d", c[i + 1][0]);

                for(int j = 0; j < stringY.length(); j++) {
                    printWriter.printf("%3d", c[i + 1][j + 1]);
                }
                printWriter.print("\n");
            }

            printWriter.println();
            printWriter.println();

            // Print out the matrix of directions
            printWriter.printf("%3s |", " ");
            for(int i = 0; i < stringY.length(); i++) {
                printWriter.printf("%3s", stringY.charAt(i));
            }
            printWriter.print("\n---");
            for(int i = 0; i < stringY.length() + 1; i++) {
                printWriter.print("---");
            }
            printWriter.print("\n");
            for(int i = 0; i < stringX.length(); i++) {
                printWriter.printf("%3s |", stringX.charAt(i));

                for(int j = 0; j < stringY.length(); j++) {
                    if(b[i + 1][j + 1] == LCSFinder.Direction.DIAGONAL) {
                        printWriter.printf("%3s", "\\");
                    }
                    if(b[i + 1][j + 1] == LCSFinder.Direction.UP) {
                        printWriter.printf("%3s", "|");
                    }
                    if(b[i + 1][j + 1] == LCSFinder.Direction.LEFT) {
                        printWriter.printf("%3s", "<-");
                    }
                }
                printWriter.print("\n");
            }
            printWriter.print("\n######################################################################\n\n");
            printWriter.close();
        } catch (IOException e) {
            System.out.println("IOHelper.printToFile Error: " + e);
            System.exit(1);
        }
    }
}
