package utils;

/*
 * Copyright (c) 2010-2019 Nathan Rajlich
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without
 *  restriction, including without limitation the rights to use,
 *  copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the
 *  Software is furnished to do so, subject to the following
 *  conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 *  OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 *  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 *  OTHER DEALINGS IN THE SOFTWARE.
 */
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.java_websocket.framing.Framedata;

public class SocketServer extends WebSocketServer {
    public static boolean isClientConnectedNow=false;

    public SocketServer(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
    }

    public SocketServer(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conn.send("Welcome to the server!"); //This method sends a message to the new client
//        broadcast("[new client connection]: " + handshake.getResourceDescriptor()); //This method sends a message to all clients connected
        broadcast("[new client connection]: " + conn.getRemoteSocketAddress().getAddress().getHostAddress()); //This method sends a message to all clients connected
        System.out.println("New Client "+conn.getRemoteSocketAddress().getAddress().getHostAddress() + " connected to Java Server!");
        isClientConnectedNow=true;
    }
    
    
    @Override
    public void onWebsocketPing(WebSocket conn,Framedata f){
        super.onWebsocketPing(conn, f);
        System.out.println(f.getOpcode()+" from "+conn.getLocalSocketAddress());
    }

    
    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        broadcast(conn + " has left the room!");
        System.out.println(conn + " has left the room!");
        isClientConnectedNow=false;
    }

    long t = 0;

    @Override
    public void onMessage(WebSocket conn, String message) {
        FactoryUtils.icbf.onMessageReceived(message);
        if (message.equals("stop")) {
            FactoryUtils.running.set(false);
            
        }
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
//		broadcast( message.array() );
        System.out.println(conn + " bak hele: " + message);
    }
    
    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
        if (conn != null) {
            // some errors like port binding failed may not be assignable to a specific websocket
        }
    }

    @Override
    public void onStart() {
//        System.out.println("Java WebSocket Server started!");
        setConnectionLostTimeout(0);
        setConnectionLostTimeout(100);
    }

}
