import helpclasses.*;

import java.util.List;

/**
 * Provides the driver program for Longest common subsequence project. The Introduction
 * to Algorithms course textbook provides the description of the different approaches
 * to ....
 *
 * @author Larisa Galanzovskaia
 * @version 0.1 2021-11-21
 */
public class LCS {
    //private static final String INPUT_PATH = System.getProperty("user.dir") + "/input/";
    private static final String INPUT_PATH = "../../../input/";

    public static void main(String[] args) {
        // Check if the input file was provided
        if (args.length == 0) {
            System.out.println("main: Missing arguments: please enter the names of the input file.");
            System.exit(1);
        }

        IOHelper ioHelper = new IOHelper();
        List<String> data = ioHelper.read(INPUT_PATH + args[0]);

        if (data.size() < 2) {
            System.out.println("main: Please ensure that the input file contains at least 2 sequences");
            System.exit(1);
        }

        // Running pairwise comparisons of the sequences
        for(int i = 0; i < data.size() - 1; i++) {
            for(int j = i + 1; j < data.size(); j++) {
                String stringX = data.get(i);
                String stringY = data.get(j);

                // The strings are in the format S1 = TTTT
                // So, we split the string by "=": the first part is the key,
                // the second is the actual sequence. If there is no "=",
                // or any part before "=" or after "=" is missing, throw an error
                // and terminate the program.
                if(stringX.contains("=") && stringY.contains("=")
                        && stringX.indexOf("=") + 1 < stringX.length()
                        && stringY.indexOf("=") + 1 < stringY.length()) {

                    String seqXKey = stringX.substring(0, stringX.indexOf("=")).trim();
                    String seqYKey = stringY.substring(0, stringY.indexOf("=")).trim();

                    stringX = stringX.substring(stringX.indexOf("=") + 1).trim();
                    stringY = stringY.substring(stringY.indexOf("=") + 1).trim();

                    // Verifying the string format
                    if(stringX.length() > 0 && stringY.length() >0
                            && seqXKey.matches("^S[0-9]+") && seqYKey.matches("^S[0-9]+")
                            && stringX.matches("[A-Z ]+") && stringY.matches("[A-Z ]+")) {

                        LCSFinder lcsFinder = new LCSFinder();

                        // Figure out the filename for reporting
                        String outFilename = args[0].replaceAll(".txt", "");
                        outFilename = outFilename.replaceAll("-input", "");
                        outFilename += "-report";

                        lcsFinder.LCSLength(stringX, stringY, seqXKey, seqYKey, outFilename);
                    }
                    else {
                        System.out.println("main: Wrong format of the data. Please ensure that data has the following format:");
                        System.out.println("S1 = STRING1");
                        System.out.println("S2 = STRING2");
                        System.out.println("S3 = STRING3");
                        System.out.println("and so on.");
                        System.exit(1);
                    }
                }
                else {
                    System.out.println("main: Wrong format of the data. Please ensure that data has the following format:");
                    System.out.println("S1 = STRING1");
                    System.out.println("S2 = STRING2");
                    System.out.println("S3 = STRING3");
                    System.out.println("and so on.");
                    System.exit(1);
                }
            }
        }
    }
}
