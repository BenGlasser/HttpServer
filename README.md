#HTTP Server

a simple http server I made to learn RFC 2616

At this point the server has sever limitations and only handles `GET` requests.  It handles these requests in the most minimal way possible only returning the requested file resource and a `Content-Length` Entity Header.  the server is multithreaded adn an example of how to run it can be found in com.benglasser.http.Main

```
import java.util.concurrent.ExecutorService;

public class Main
{
  public static void main(String[] args) throws IOException
  {
    final int SERVER_PORT = 8000;
    final ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
    HttpServer myServer = new HttpServer(new ExecutorService(10), serverSocket);
    myServer.start();
    for (; ; ) ;
  }
}
```
