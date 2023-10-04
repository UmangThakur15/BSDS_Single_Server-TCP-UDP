# **_Project 1(Single Server, Key-Value Store (TCP and UDP))_**

## **TCP Server and Client Implementation:**
The TCPServer.java and TCPClient.java are two programs enabling communication between server and client using TCP/IP protocol. The TCP Server
listens for client connections on a specified port and TCP Client connects to the server's IP address and port. The server and client 
exchange data through TCP sockets, allowing for reliable and ordered transmission of messages.

### TCPServer:
The TCPServer.java represents server-side of TCP communication. It creates a server socket and listens for incoming client connections on
given port. Once a client connects, the server can send and receive messages from the client.

##### Steps to execute:
1. Compile the TCPServer.java file using the Java compiler in terminal:
Cmd: javac TCPServer.java

2. Run the compiled TCPServer class, providing the desired port number as a command-line argument:
Cmd: java TCPServer

3. Add the `<port>` number on which you want the server to listen for incoming connections.

4. The server will start and display message indicating the IP address and port it is listening on. It will then wait for client connections.



### TCPClient:
The TCPClient program represents the client-side of the TCP communication. It connects to a TCP server using the server's IP address and port number.
Once connected, the client can send messages to the server and receive responses.

### Steps to execute:
1. Compile the TCPClient.java file using the Java compiler:
Cmd: javac TCPClient.java

2. Run the compiled TCPClient class, providing the server's IP address and port number as command-line arguments:
Cmd: java TCPClient <server-ip> <server-port>
Replace `<server-ip>` with the IP address (In our case `localhost`) of the server you want to connect to, and `<server-port>` with 
the corresponding port number.

3. The client will attempt to establish a connection with the server. If successful, it will display a message indicating the connection status.

4. Once connected, you can enter messages to send to the server. The client will display the responses received from the server.

5. To terminate the client program, simply close the client window or use the appropriate termination command.

---------------------------------------------------------------------------------------------------------------------------------------------------

## ** UDP Server and Client**
The UDP Server and Client are two Java programs that enable communication between a server and a client using the UDP protocol.
The UDP Server listens for client connections on a specified port, while the UDP Client connects to the server's IP address and port. 
The server and client exchange data through UDP Datagram sockets, allowing for faster transmission of messages.

## UDPServer
The UDPServer program represents the server-side of the UDP communication. It creates a datagram socket and listens for incoming
client connections on a specified port. Once a client connects, the server can send and receive messages from the client.

### Steps to execute:
1. Compile the UDPServer.java file using the Java compiler:
Cmd: javac UDPServer.java

2. Run the compiled UDPServer class, providing the desired port number as a command-line argument:
Cmd: java UDPServer

3. Add the `<port>` number on which you want the server to listen for incoming connections.

4. The server will start and display a message indicating the IP address and port it is listening on. It will then wait for client connections.


#### UDPClient
The UDPClient program represents the client-side of the UDP communication. It connects to a UDP server using the server's 
IP address and port number. Once connected, the client can send messages to the server and receive responses.

### Steps to execute:
1. Compile the UDPClient.java file using the Java compiler:
Cmd: javac UDPClient.java

2. Run the compiled UDPClient class, providing the server's IP address and port number as command-line arguments:
Cmd: java UDPClient <server-ip> <server-port>

Replace `<server-ip>` with the IP address (In our case `localhost`) of the server you want to connect to, and `<server-port>` with the
corresponding port number.

3. The client will attempt to establish a connection with the server. If successful, it will display a message indicating the connection status.

4. Once connected, you can enter messages to send to the server. The client will display the responses received from the server.

5. To terminate the client program, simply close the client window or use the appropriate termination command.


