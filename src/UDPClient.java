import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

/**
 * UDPClient is a simple implementation of UDP client.It sends requests to UDP server.
 */
public class UDPClient {

    static String key, value, request;
    static Scanner scanner;

    /**
     * This method starts the UDPClient program.
     *
     * @param args takes IP and port number as arguments.
     * @throws IOException when I/O error occurs.
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 2 || Integer.parseInt(args[1]) > 65535) {
            throw new IllegalArgumentException("Invalid argument! " +
                    "Please provide valid IP address and PORT number");
        }
        InetAddress serverIP = InetAddress.getByName(args[0]);
        int serverPort = Integer.parseInt(args[1]);
        scanner = new Scanner(System.in);

        try (DatagramSocket datagramSocket = new DatagramSocket()) {
            datagramSocket.setSoTimeout(10000);
            String start = getTimeStamp();
            System.out.println(start + " Client started on port number: ");

            while (true) {
                System.out.print("Operations: \n1. PUT\n2. GET\n3. DELETE\nPlease select operation: ");
                String operation = scanner.nextLine().trim();
                if (Objects.equals(operation, "1")) {
                    getKey();
                    getValue();
                    request = "PUT " + key + " , " + value;
                } else if (Objects.equals(operation, "2")) {
                    getKey();
                    request = "GET " + key;
                } else if (Objects.equals(operation, "3")) {
                    getKey();
                    request = "DELETE " + key;
                } else {
                    System.out.println("Invalid operation selected!");
                    continue;
                }

                requestLog(request);

                byte[] requestBuffer = request.getBytes();
                if (requestBuffer.length > 65507) {
                    System.out.println("Error: Request size exceeds maximum allowed limit!");
                    continue;
                }

                DatagramPacket requestPacket = new DatagramPacket(requestBuffer, requestBuffer.length,
                        serverIP, serverPort);
                datagramSocket.send(requestPacket);

                byte[] resultBuffer = new byte[512];
                DatagramPacket resultPacket = new DatagramPacket(resultBuffer, resultBuffer.length);

                try {
                    datagramSocket.receive(resultPacket);
                    String result = new String(resultBuffer);
                    responseLog(result);
                } catch (java.net.SocketTimeoutException e) {
                    System.out.println("Connection timed out!");
                }
            }
        } catch (UnknownHostException | SocketException e) {
            System.out.println(
                    "Invalid host or port number!");
        }
    }


    /**
     * This method gets key from the user.
     *
     * @throws IOException when error occurs during input reading.
     */
    private static void getKey() throws IOException {
        System.out.print("Enter key: ");
        key = scanner.nextLine();
    }

    /**
     *This method gets the value from user
     *
     * @throws IOException when error occurs during input reading.
     */
    private static void getValue() throws IOException {
        System.out.print("Enter Value: ");
        value = scanner.nextLine();
    }




    /**
     * Helper Methods.
     */


    /**
     * Method to print Request messages.
     *
     * @param str message string
     */
    private static void requestLog(String str) {
        System.out.println(getTimeStamp() +
                " Request -> " + str);
    }

    /**
     * Method to print Response messages.
     *
     * @param str message string
     */
    private static void responseLog(String str) {
        System.out.println(getTimeStamp() +
                " Response -> " + str + "\n");
    }

    /**
     * Method to return the current timestamp.
     *
     * @return returns current timestamp
     */
    private static String getTimeStamp() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.SSS");
        return "[Time Stamp: " + simpleDateFormat.format(new Date()) + "]";
    }
}
