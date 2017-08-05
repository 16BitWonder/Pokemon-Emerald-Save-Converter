import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;

/**
 * Pokemon Emerald Save Conversion Tool
 *
 * Can convert 128kb .sav to 136kb .srm and back.
 *
 * NOTE: THIS JAR FILE CONVERTS .SRM INTO .SAV
 *
 * @author 16BitWonder
 *
 */
public final class emeraldSrmToSav {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private emeraldSrmToSav() {
    }

    /**
     * Fill a byte[] from a file at specified path (file).
     *
     * @param file
     *            The path to the file being read
     *
     * @throws IOException
     * @return temporary
     */
    private static byte[] readByte(Path file) throws IOException {

        byte[] temporary = Files.readAllBytes(file);

        return temporary;
    }

    /**
     * Convert byte[] into a String of Hex values.
     *
     * @param data
     *            byte[] of read information from a file.
     *
     * @return temporary
     */
    private static String byteToHex(byte[] data) {

        java.lang.String temporary = DatatypeConverter.printHexBinary(data);

        return temporary;
    }

    /**
     * Convert String of Hex values into byte[].
     *
     * @param hexString
     *            String of hex values
     *
     * @return temporary
     */
    private static byte[] hexToByte(String hexString) {

        byte[] temporary = DatatypeConverter.parseHexBinary(hexString);

        return temporary;
    }

    /**
     * Convert .srm to .sav format by removing appropriate empty data.
     *
     * @param hexString
     *            String of hex values
     *
     * @return hexString
     */
    private static String srmToSav(String hexString) {

        int charsToRemove = 16384;

        //Update hexString as a substring of itself, removing the excess data
        hexString = hexString.substring(0, hexString.length() - charsToRemove);

        return hexString;
    }

    /**
     * Print String of Hex values as they would appear in a hex editor, this
     * method is purely for debugging purposes. Although it will be included in
     * the release it will not be used.
     *
     * @param hexString
     *            String of hex values
     */
    private static void printHexData(String hexString) {

        //Print out all characters within hexChars
        for (int i = 0; i < hexString.length(); i++) {

            //Make a space every two chars
            if ((i % 2 == 0) && (i != 0)) {
                System.out.print(' ');
            }

            //Go down a line after every 16 chars
            if ((i % 32 == 0) && (i != 0)) {
                System.out.println();
            }

            System.out.print(hexString.charAt(i));
        }
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     *
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        //Initialize Scanner "in"
        Scanner in = new Scanner(System.in);

        //Initialize Path variable "srmLocation" to .srm
        Path srmLocation = Paths.get("SrmToSav\\Pokemon Emerald.srm")
                .toAbsolutePath().normalize();

        //Initialize String variable "savLocation" to .sav
        String savLocation = Paths.get("Pokemon Emerald.sav").toAbsolutePath()
                .normalize().toString();

        //Read all bytes from file and store in byte[] "data"
        byte[] data = readByte(srmLocation);

        //Covert byte[] into String of hex values "hexString"
        String hexString = byteToHex(data);

        //Convert String of .srm into String of .sav "newHexString"
        String newHexString = srmToSav(hexString);

        //Convert new String of Hex values back into byte[] "newData"
        byte[] newData = hexToByte(newHexString);

        //Initialize FileOutputStream "out"
        FileOutputStream out = new FileOutputStream(savLocation);

        //Output converted save file
        out.write(newData);

        //Close Scanner "in"
        in.close();
        //Close FileOutputStream "out"
        out.close();
    }

}
