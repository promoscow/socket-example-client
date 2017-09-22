package ru.xpendence.sockets;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] ar) {
        /** Порт сервера. */
        int serverPort = 6666;
        /** IP сервера. */
        String address = "127.0.0.1";

        try {
            /**
             * Альтернативный (и более предпочтительный) способ создания адреса.
             * Создаём отдельный класс, который будет содержать в себе адрес сервера (127.0.0.1).
             */
            InetAddress ipAddress = InetAddress.getByName(address);
            System.out.println("Any of you heard of a socket with IP address " + address + " and port " + serverPort + "?");
            /** Создаём сокет, используя адрес и порт. */
            Socket socket = new Socket(ipAddress, serverPort);
            System.out.println("Yes! I just got hold of the program.");

            /** Входной и выходной потоки сокета. Одинаковый алгоритм для сервера и клиента. */
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            /** Data- (Input / Output) -Stream лучше подходит для обработки тестовых сообщений. */
            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            String line = null;
            System.out.println("Type in something and press enter. Will send it to the server and tell ya what it thinks.");
            System.out.println();

            while (true) {
                /**
                 * 1. Принимаем строку через BufferedReader.
                 * 2. writeUTF() — отправка серверу.
                 * 3. Очистка кэша.
                 * 4. Ждём ответа от сервера и печатаем её.
                 */
                line = keyboard.readLine(); // ждем пока пользователь введет что-то и нажмет кнопку Enter.
                System.out.println("Sending this line to the server...");
                out.writeUTF(line); // отсылаем введенную строку текста серверу.
                out.flush(); // заставляем поток закончить передачу данных.
                line = in.readUTF(); // ждем пока сервер отошлет строку текста.
                System.out.println("The server was very polite. It sent me this : " + line);
                System.out.println("Looks like the server is pleased with us. Go ahead and enter more lines.");
                System.out.println();
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}
