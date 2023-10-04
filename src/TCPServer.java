import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

/**
 * TCPServer is a simple implementation of a TCP server. It listens for incoming TCP connections.
 */
public class TCPServer {

    static InputStream read;
    static OutputStream write;
    static Properties properties;

    /**
     * This method starts the TCPServer program.
     *
     * @param args takes port number as argument.
     * @throws Exception when error occurrs during execution.
     */
    public static void main(String[] args) throws Exception {

        System.out.print("Enter port Number: ");
        Scanner port = new Scanner(System.in);
        int PORT = port.nextInt();
        if (PORT > 65535) {
            throw new IllegalArgumentException("Invalid arguments!"
                    + "Please provide valid IP address and PORT number.");
        }

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            String start = getTimeStamp();
            System.out.println(start + " Server started on port: " + PORT);
            Socket clientSocket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());

            read = new FileInputStream("map.properties");
            properties = new Properties();
            properties.load(read);

            write = new FileOutputStream("map.properties");
            properties.store(write, null);

            while (true) {
                String input = dataInputStream.readUTF();
                requestLog(input, String.valueOf(clientSocket.getInetAddress()),
                        String.valueOf(clientSocket.getPort()));

                String result = performOperation(input.split(" "));
                responseLog(result);
                dataOutputStream.writeUTF(result);
                dataOutputStream.flush();
            }


        } catch (Exception e) {
            System.out.println(getTimeStamp() + " Error: " + e);
        }
    }


    /**
     * This method adds key-value pair to map and stores it in the properties file.
     *
     * @param key   key
     * @param value value associated with key
     * @return returns success message
     * @throws Exception when error occurrs during operation.
     */

    static String addToMap(String key, String value) throws Exception {
        properties.setProperty(key, value);
        properties.store(write, null);
        String result = "Inserted key \"" + key + "\" with value \"" + value + "\"";
        return result;
    }

    /**
     * This method deletes key-value pair from the map and updates the properties file.
     *
     * @param key deleted key
     * @return returns successful deletion message
     * @throws IOException when error occurrs during operation.
     */
    private static String deleteFromMap(String key) throws IOException {
        String result = "";
        if (properties.containsKey(key)) {
            properties.remove(key);
            properties.store(write, null);
            result = "Deleted key \"" + key + "\"" + " successfully!";
        } else {
            result = "Key not found.";
        }
        return result;
    }

    /**
     * This method Retrieves value associated with given key.
     *
     * @param key  key to retrieve the value
     * @return returns associated value
     * @throws IOException when error occurrs during operation.
     */
    private static String getFromMap(String key) throws IOException {
        try {
            String value = properties.getProperty(key);
            String result = value == null ?
                    "No value found for key \"" + key + "\""
                    : "Key: \"" + key + "\" ,Value: \"" + value + "\"";
            return result;
        } catch (Exception e) {
            throw new IOException("Error: " + e);
        }
    }



    /**
     * Helper Methods.
     */

    /**
     * Method to print Request messages.
     *
     * @param str  message string
     * @param ip   client IP address
     * @param port client port number
     */
    private static void requestLog(String str, String ip, String port) {
        System.out.println(getTimeStamp() + " Request from: " + ip + ":" + port + " -> " + str);
    }


    /**
     * Method to print Response messages.
     *
     * @param str message string
     */
    private static void responseLog(String str) {
        System.out.println(getTimeStamp() + " Response:  "
                + str + "\n");
    }


    /**
     * Method to process user request.
     *
     * @param input user request
     * @return returns the PUT/GET/DELETE operation
     * @throws IllegalArgumentException when invalid input entered
     */
    private static String performOperation(String[] input) throws IllegalArgumentException {
        try {
            if (input.length < 2) {
                throw new IllegalArgumentException("Insufficient arguments.");
            }

            String operation = input[0].toUpperCase();
            String key = input[1];

            switch (operation) {
                case "PUT": {

                    if (input.length < 3) {
                        throw new IllegalArgumentException(
                                "Value is missing for PUT operation.");
                    }

                    String value = input[2];
                    return addToMap(key, value);
                }
                case "DELETE": {
                    return deleteFromMap(key);
                }
                case "GET": {
                    return getFromMap(key);
                }
                default:
                    throw new IllegalArgumentException("Invalid operation: " + operation);
            }
        } catch (IllegalArgumentException e) {
            return "Bad Request: " + e.getMessage();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }


    /**
     * Method to return the current timestamp.
     *
     * @return returns current timestamp
     */
    private static String getTimeStamp() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.SSS");
        return "<Time Stamp: " + simpleDateFormat.format(new Date()) + ">";
    }

}