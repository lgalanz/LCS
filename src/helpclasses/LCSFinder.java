package helpclasses;

/**
 * Class LCSFinder implements the dynamic programming algorithm that searches
 * the longest common subsequence in the given sequences. The algorithm and
 * the pseudocode are describes in section 15.4 of the CLRS textbook.
 *
 */
public class LCSFinder {
    private String lcs = ""; // auxiliary field needed to restore the LCS based on the calculated array b
    private int printCount = 0; // operations count

    /**
     * Enum Direction is utilized in the array b that stores the history
     * of the search and enables tracing back through the matrix and identifying the LCS.
     */
    public enum Direction {
        UP,
        LEFT,
        DIAGONAL
    }

    /**
     * LCSLength method implements the dynamic programming algorithm that searches
     * the longest common subsequence in the given sequences.
     * @param stringX - first sequence
     * @param stringY - second sequence
     * @param seqXKey - S1, S2, S3 ... the key of the first sequence from the input file: needed for reporting
     * @param seqYKey - S1, S2, S3 ... the key of the second sequence from the input file: needed for reporting
     * @param outFilename - the name of the reporting file
     */
    public void LCSLength(String stringX, String stringY, String seqXKey, String seqYKey, String outFilename) {
        // operations count
        int count = 0;
        long startTime = System.nanoTime(); // track the time in which the algorithm runs

        int m = stringX.length(); // the length of the 1st sequence
        int n = stringY.length(); // the length of the 2nd sequence

        Direction[][] b = new Direction[m + 1][n + 1]; // the "table" where each cell stores the direction we can move from this cell
        int[][] c = new int[m + 1][n + 1]; // the "main" dynamic programming "table" with the length of the LCS at each iteration

        // Initialize the main table  - fill it with zeros
        for (int i = 0; i < m + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                c[i][j] = 0;
            }
        }

        // Iterating through the matrix with rows corresponding to the 1st sequence
        // and columns corresponding to the 2nd sequence
        for (int i = 0; i < m + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                // Edge case - we fill the very first row and column with zeros
                if (i == 0 || j == 0) {
                    c[i][j] = 0;
                }
                // If the characters at the position [i-1][j-1] are equal they belong to the LCS,
                // and we should move diagonal from the cell [i][j].
                // Otherwise, we move either up or left depending on which value was greater.
                else if (stringX.charAt(i - 1) == stringY.charAt(j - 1)) {
                    c[i][j] = c[i - 1][j - 1] + 1;
                    b[i][j] = Direction.DIAGONAL;

                    count++;
                }
                else if (c[i - 1][j] >= c[i][j - 1]) {
                    c[i][j] = c[i - 1][j];
                    b[i][j] = Direction.UP;

                    count++;
                }
                else {
                    c[i][j] = c[i][j - 1];
                    b[i][j] = Direction.LEFT;

                    count++;
                }
            }
        }

        long endTime = System.nanoTime(); // end time of the algorithm
        long lcsTime = endTime - startTime; // reporting the running time of the algorithm

        startTime = System.nanoTime(); // start time of the tracing back procedure
        lcs = ""; // drop the value in the lcs as it will be used by the printLCS method
        printCount = 0;
        printLCS(b, stringX, m, n);
        endTime = System.nanoTime(); // end time of the tracing back procedure

        long printTime = endTime - startTime; // reporting the running time of the tracing back procedure

        // Output the report
        IOHelper ioHelper = new IOHelper(outFilename);
        ioHelper.printToFile(seqXKey, seqYKey, stringX, stringY, c, b, lcs, lcsTime, printTime, count, printCount, true);
    }

    /**
     * printLCS restores the LCS based on the instructions provided in the array b
     * @param b - array of the instruction how we should move within the matrix to restore the LCS
     * @param stringX - we pass only one of the sequences as the LCS is contained in both of them
     * @param xLength - the length of the 1st sequence
     * @param yLength - the length of the 2nd sequence
     */
    private void printLCS(Direction[][] b, String stringX, int xLength, int yLength) {
        if (xLength == 0 || yLength == 0) {
            return;
        }

        printCount++;
        if (b[xLength][yLength] == Direction.DIAGONAL) {
            printLCS(b, stringX, xLength - 1, yLength - 1);
            lcs += stringX.charAt(xLength - 1);
        }
        else if (b[xLength][yLength] == Direction.UP) {
            printLCS(b, stringX, xLength - 1, yLength);
        }
        else {
            printLCS(b, stringX, xLength, yLength - 1);
        }
    }
}
