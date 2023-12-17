import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(socket.getInputStream());
            Scanner userInput = new Scanner(System.in)
        ) {
            System.out.print("Enter your name: ");
            String name = userInput.nextLine();
            System.out.println("Welcome to the group chat, " + name + "!");
            writer.println(name + " has joined the chat.");

            Thread receiveThread = new Thread(() -> {
                while (true) {
                    String message = scanner.nextLine();
                    System.out.println(message);
                }
            });
            receiveThread.start();

            while (true) {
                String message = userInput.nextLine();
                writer.println(name + ": " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
