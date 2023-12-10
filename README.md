# LongestCommon

LongestCommon is the Java program to identify the longest common subsequence between two sequences.

## Java requirements

The program is implemented with the latest version of Java as of 11/01/21.
That is **Java 17**.

## IDE

The program is implemented using IntelliJ IDEA 2021.2.2 (Community Edition).

## Installation

Compiled files are located in the directory .\LongestCommon\out\production\LCS.
The program requires one parameter: absolute paths to the input files.

**The input file** must be located in the input folder of the root directory (.\LongestCommon\input) and should contain a list of sequences in the format key = value (see example below).

**The output folder** must exist and be located in the root directory (.\LongestCommon\output).

The program can be run from *.\LongestCommon\out\production\LCS* using the following command:
```bash
.\LongestCommon\out\production\LCS> java LCS inputFileName
```

#### Example:

```bash
F:\!JHU\FA21\Algorithms\LongestCommon\out\production\LCS> java LCS default-input.txt
```

## Example of the input file format
```
S1 = ACCGGTCGACTGCGCGGAAGCCGGCCGAA
S2 = GTCGTTCGGAATGCCGTTGCTCTGTAAA
S3 = ATTGCATTGCATGGGCGCGATGCATTTGGTTAATTCCTCG
S4 = CTTGCTTAAATGTGCA
...
```
