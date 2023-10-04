import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * TCPClient is a simple implementation of a TCP client side. It sends requests to a TCP server.
 */

class TCPClient {

    static String key, value, request;
    static BufferedReader bufferedReader;

    /**
     * This method starts the TCPClient program.
     *
     * @param args takes Host name and port number as arguments.
     * @throws Exception when error occurrs during execution.
     */
    public static void main(String[] args) throws Exception {

        if (args.length != 2 || Integer.parseInt(args[1]) > 65535) {
            throw new IllegalArgumentException("Invalid arguments " +
                    "Please provide valid IP address and PORT number");
        }

        InetAddress serverIP = InetAddress.getByName(args[0]);
        int serverPort = Integer.parseInt(args[1]);

        try (Socket s = new Socket()) {
            int timeout = 10000;
            s.connect(new InetSocketAddress(serverIP, serverPort), timeout);

            DataInputStream dataInputStream = new DataInputStream(s.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(s.getOutputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String start = getTimeStamp();
            System.out.println(start + " Client started on port number: " + s.getPort());

            while (true) {
                System.out.print("Operations: \n1. PUT\n2. GET\n3. DELETE\nPlease select operation: ");
                String operation = bufferedReader.readLine().trim();
                if (Objects.equals(operation, "1")) {
                    getKey();
                    getValue();
                    request = "PUT " + key + " " + value;
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

                // Send request packet to the server
                sendPacket(dataOutputStream, request);

                // Receive response packet from the server
                String response = receivePacket(dataInputStream);

                if (response.startsWith("ERROR")) {
                    System.out.println("Received error response from the server side: " + response);
                } else {
                    responseLog(response);
                }
            }
        } catch (UnknownHostException | SocketException e) {
            System.out.println("Invalid host or port number!");
        } catch (SocketTimeoutException e) {
            System.out.println("Connection timed out, try again!");
        } catch (Exception e) {
            System.out.println("Exception occurred!" + e);
        }
    }

    /**
     * This method is to get key from user.
     *
     * @throws IOException when error occurs during input reading.
     */
    private static void getKey() throws IOException {
        System.out.print("Enter key: ");
        key = bufferedReader.readLine();
    }


    /**
     *This method is to get value from user.
     *
     * @throws IOException when error occurs during input reading.
     */
    private static void getValue() throws IOException {
        System.out.print("Enter Value: ");
        value = bufferedReader.readLine();
    }


    /**
     * Helper Methods
     */

    /**
     *Method to send packet to the server.
     *
     * @param outputStream output stream to write packet
     * @param packet       packet to send
     * @throws IOException when error occurs during input writing.
     */
    private static void sendPacket(DataOutputStream outputStream, String packet) throws IOException {
        outputStream.writeUTF(packet);
        outputStream.flush();
        requestLog(packet);
    }


    /**
     * Method to receive packet from the server.
     *
     * @param inputStream input stream to read packet
     * @return received packet
     * @throws IOException when error occurs during input reading.
     */
    private static String receivePacket(DataInputStream inputStream) throws IOException {
        return inputStream.readUTF();
    }



    /**
     *Method to print Request messages.
     *
     * @param str message string
     */
    private static void requestLog(String str) {
        System.out.println(getTimeStamp() + " Request: " + str);
    }




    /**
     *Method to print Response messages.
     *
     * @param str message string
     */
    private static void responseLog(String str) {
        System.out.println(getTimeStamp() + " Response: " + str + "\n");
    }



    /**
     *Method to return current timestamp.
     *
     * @return returns current timestamp
     */
    private static String getTimeStamp() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.SSS");
        return "[Time Stamp: " + simpleDateFormat.format(new Date()) + "]";
    }
}
