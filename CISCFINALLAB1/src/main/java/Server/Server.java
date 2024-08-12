package Server;

import edu.sdccd.cisc191.User;
import edu.sdccd.cisc191.Workout;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 12345;
    private static final Map<String, User> users = new ConcurrentHashMap<>();
    private static final Map<String, Workout> workouts = new ConcurrentHashMap<>();
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(10);
    private static volatile boolean running = true;

    public static void main(String[] args) {
        startServer();
    }

    private static void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);
            while (running) {
                final Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                threadPool.execute(() -> processClient(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    public static void stopServer() {
        running = false;
    }

    private static void processClient(Socket clientSocket) {
        try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {

            String command = (String) in.readObject();
            if (command.equals("ADD_WORKOUT")) {
                Workout workout = (Workout) in.readObject();
                workouts.put(workout.getId(), workout);
                out.writeObject("WORKOUT_ADDED");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Map<String, User> getUsers() {
        return users;
    }

    public static void setUsers(Map<String, User> users) {
        Server.users.clear();
        Server.users.putAll(users);
    }
}