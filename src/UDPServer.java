import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;

/**
 *UDPServer is a simple implementation of a UDP server. It listens for incoming UDP connections.
 */
public class UDPServer {
    static InputStream read;
    static OutputStream write;
    static Properties properties;

    /**
     * This method starts the UDPServer program.
     *
     * @param args takes port number as argument
     * @throws SocketException exception
     */
    public static void main(String[] args) throws Exception {

        System.out.print("Enter port Number: ");
        Scanner port = new Scanner(System.in);
        int PORT = port.nextInt();
        if ( PORT > 65535) {
            throw new IllegalArgumentException("Invalid Input!"
                    + "Please provide valid IP address and PORT number.");
        }

        try (DatagramSocket datagramSocket = new DatagramSocket(PORT)){

            String start = getTimeStamp();
            System.out.println(start + " Server started on port: " + PORT);
            byte[] requestBuffer = new byte[512];
            byte[] responseBuffer;

            read = new FileInputStream("map.properties");
            properties = new Properties();
            properties.load(read);
            write = new FileOutputStream("map.properties");
            properties.store(write, null);


            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(requestBuffer, requestBuffer.length);
                datagramSocket.receive(receivePacket);
                InetAddress client = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                String request = new String(receivePacket.getData(), receivePacket.getOffset(), receivePacket.getLength());
                requestLog(request, client.toString(), String.valueOf(clientPort));

                // Validate packet size
                if (receivePacket.getLength() > 512) {
                    errorLog("Received packet exceeds maximum allowed size.");
                    continue;
                }

                try {
                    String[] input = request.split(" ");
                    if (input.length < 2) {
                        throw new IllegalArgumentException("Malformed request!");
                    }
                    String result = performOperation(input);
                    responseLog(result);
                    responseBuffer = result.getBytes();

                } catch (IllegalArgumentException e) {
                    String errorMessage = e.getMessage();
                    errorLog(errorMessage);
                    responseBuffer = errorMessage.getBytes();
                }

                DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length,
                        client, clientPort);
                datagramSocket.send(responsePacket);
                requestBuffer = new byte[512];

            }
        } catch (Exception e) {
            errorLog("Invalid IP address and PORT number!");
        }
    }



    /**
     * This method adds the key-value pair to the map.
     *
     * @param key    key
     * @param value associated value
     * @return success message of the operation
     * @throws Exception when error occurrs during operation.
     */
    static String addToMap(String key, String value) throws Exception {
        properties.setProperty(key, value);
        properties.store(write, null);
        String result = "Inserted key \"" + key + "\" with value \"" + value + "\"";
        return result;
    }

    /**
     * This method delete the key from the map.
     *
     * @param key takes key
     * @return returns successful deletion message
     * @throws IOException when error occurrs during operation.
     */
    private static String deleteFromMap(String key) throws IOException {
        String result = "";
        if(properties.containsKey(key)) {
            properties.remove(key);
            properties.store(write, null);
            result = "Deleted key \"" + key + "\"" + " successfully!";
        }
        else {
            result = "Key not found.";
        }
        return result;
    }

    /**
     * This method Retrieves value associated with given key.
     *
     * @param key key to retrieve the value
     * @return returns associated value
     * @throws IOException when error occurrs during operation.
     */
    private static String getFromMap(String key) throws IOException {
        String value = properties.getProperty(key);
        String result = value == null ?
                "No value found for key \"" + key + "\"" : "Key: \"" + key + "\" ,Value: \"" + value + "\"";
        return result;
    }





    /**
     * Helper methods.
     */


    /**
     * Method to print Request messages.
     *
     * @param str    message string
     * @param ip   client IP address
     * @param port client port number
     */
    private static void requestLog(String str, String ip, String port) {
        System.out.println(getTimeStamp() + " Request from: " + ip + ":" + port  + " -> "+ str);
    }

    /**
     * Method to print Response messages.
     *
     * @param str message string
     */
    private static void responseLog(String str) { System.out.println(getTimeStamp() +
            " Response -> " + str + "\n");}

    /**
     * Method to print Error messages.
     *
     * @param err error message string
     */
    private static void errorLog(String err) { System.out.println(getTimeStamp() +
            " Error -> " + err);}

    /**
     * Method to return the current timestamp.
     *
     * @return the current timestamp
     */
    private static String getTimeStamp() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.SSS");
        return "<Time: " + simpleDateFormat.format(new Date()) + ">";
    }

    /**
     * Method to process user request
     * @param input user request
     * @return result of PUT/GET/DELETE operation
     * @throws IllegalArgumentException in case of invalid input
     */
    private static String performOperation(String[] input) throws IllegalArgumentException {
        try {
            String operation = input[0].toUpperCase();
            String key = "";
            int j = 0;
            for(int i = 1; i < input.length; i++) {
                if(Objects.equals(input[i], ",")) {
                    j = i;
                    break;
                }
                else key = key + input[i] + " ";
            }
            key = key.trim();

            switch (operation) {
                case "PUT": {
                    String value = "";
                    for(int i = j+1; i < input.length; i++) value = value + " " + input[i].trim();
                    value = value.trim();
                    return addToMap(key, value);
                }
                case "DELETE": {
                    return deleteFromMap(key);
                }
                case "GET": {
                    return getFromMap(key);
                }
                default:
                    throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            return "BAD REQUEST!:  Please view README.md to check available operations." + e;
        }

    }


}