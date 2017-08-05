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
 * NOTE: THIS JAR FILE CONVERTS .SAV INTO .SRM
 *
 * @author 8BitWonder
 *
 */
public final class emeraldSavToSrm {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private emeraldSavToSrm() {
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
     * Convert .sav to .srm format by adding appropriate empty data.
     *
     * @param hexString
     *            String of hex values
     *
     * @return hexString
     */
    private static String savToSrm(String hexString) {

        int charsToAdd = 16384;

        //Loop to add appropriate data to hex String.
        for (int i = 0; i < charsToAdd; i++) {
            hexString += 'f';
        }

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

        //Initialize Path variable "savLocation" to .sav
        Path savLocation = Paths.get("SavToSrm\\Pokemon Emerald.sav")
                .toAbsolutePath().normalize();

        //Initialize String variable "srmLocation" to .srm
        String srmLocation = Paths.get("Pokemon Emerald.srm").toAbsolutePath()
                .normalize().toString();

        //Read all bytes from file and store in byte[] "data"
        byte[] data = readByte(savLocation);

        //Covert byte[] into String of hex values "hexString"
        String hexString = byteToHex(data);

        //Convert String of .sav into String of .srm "newHexString"
        String newHexString = savToSrm(hexString);

        //Convert new String of Hex values back into byte[] "newData"
        byte[] newData = hexToByte(newHexString);

        //Initialize FileOutputStream "out"
        FileOutputStream out = new FileOutputStream(srmLocation);

        //Output converted save file
        out.write(newData);

        //Close Scanner "in"
        in.close();
        //Close FileOutputStream "out"
        out.close();
    }

}
