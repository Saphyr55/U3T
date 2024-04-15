package utours.ultimate;

import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;

public class PrototypeTest {

    static void displayInterfaceInformation(NetworkInterface networkInterface, PrintStream out) throws SocketException {
        out.printf("Display name: %s\n", networkInterface.getDisplayName());
        out.printf("Name: %s\n", networkInterface.getName());
        Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
            out.printf("InetAddress: %s\n", inetAddress);
        }
        out.print("\n");
    }

    @Test
    void prototypeTest() throws SocketException {
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface networkInterface : Collections.list(nets))
            displayInterfaceInformation(networkInterface, System.out);
    }

}
