# **_Project 1(Single Server, Key-Value Store (TCP and UDP))_**

## **TCP Server and Client Implementation:**
The TCPServer.java and TCPClient.java are two programs enabling communication between server and client using TCP/IP protocol. The TCP Server
listens for client connections on a given port and TCP Client connects to the server's IP address and given port. The server and client 
exchange data, allowing for reliable transmission of messages.

### TCPServer:
The TCPServer.java represents server-side of TCP communication.

##### Steps to execute:
1. Compile the TCPServer.java file in terminal:
Cmd: javac TCPServer.java

2. Run compiled TCPServer class:
Cmd: java TCPServer

3. Add valid port number on which you want the server to communicate.
e.g 80

4. Server will start and display message indicating IP address and port. It will then wait for client connections.



### TCPClient:
The TCPClient program represents client-side of the TCP/IP protocol. It connects to TCP server using server's IP address and 
port number. After connecting client can send messages to the server and receive responses.

### Steps to execute:
1. Compile  TCPClient.java file in terminal:
Cmd: javac TCPClient.java

2. Run compiled TCPClient class, adding the server's IP address and port number command-line arguments.
Cmd: java TCPClient <server-ip> <server-port>
Replace <server-ip> with the IP address of the server you want to connect to, and `<server-port>` with 
the corresponding port number.
e.g  java TCPClient localhost 80

3. The client establish connection with the server and will display connection status message.

4. After connecting select the operation you want to perform between client and server. The client will display the responses 
received from the server.
e.g select 1 for PUT operation

5. To terminate client program, simply close the client window.





---------------------------------------------------------------------------------------------------------------------------------------------------

## ** UDP Server and Client**
The UDPServer.java and UDPClient.java are two programs that enabling communication between server and client using UDP protocol.
The UDP Server listens for client connections on a given port and UDP Client connects to the server's IP address and given port. 
The server and client exchange data, allowing for reliable and faster transmission of messages.

## UDPServer
The UDPServer program represents the server-side of the UDP communication.

### Steps to execute:
1. Compile the UDPServer.java file in terminal:
Cmd: javac UDPServer.java

2. Run the compiled UDPServer class:
Cmd: java UDPServer

3. Add valid port number on which you want the server to communicate.
e.g 80

4. Server will start and display message indicating IP address and port. It will then wait for client connections.


#### UDPClient
The UDPClient program represents client-side of the UDP protocol. It connects to UDP server using server's IP address and
port number. After connecting client can send messages to the server and receive responses.

### Steps to execute:
1. Compile the UDPClient.java file in terminal:
Cmd: javac UDPClient.java

2. Run the compiled UDPClient class, adding the server's IP address and port number command-line arguments:
Cmd: java UDPClient <server-ip> <server-port>
Replace <server-ip> with the IP address of the server you want to connect to, and `<server-port>` with
the corresponding port number.
e.g  java UDPClient localhost 80

3. The client establish connection with the server and will display connection status message.

4. After connecting select the operation you want to perform between client and server. The client will display the responses
received from the server.
e.g select 1 for PUT operation

5. To terminate client program, simply close the client window.


