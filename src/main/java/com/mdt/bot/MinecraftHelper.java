package com.mdt.bot;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MinecraftHelper {
	
	private static Process minecraft;

	public static void start() throws IOException {
		if (minecraft == null || !minecraft.isAlive()) {
			ProcessBuilder runServer = new ProcessBuilder("java", "-jar", "paper.jar", "nogui").directory(new File("/home/nicol/minecraft/vanilla"));
			minecraft = runServer.start();
		}
	}
	
	public static int getPlayersNum() {
		try {
			URL url = new URL("http://127.0.0.1:8081/players");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			InputStream in = connection.getInputStream();
			System.out.println("HTTP: " + connection.getResponseCode());
			byte[] data = new byte[4];
			for (int i = 0; i < 4; i++) {data[i] = (byte) in.read();}
			in.close();	connection.disconnect();
			return ByteBuffer.wrap(data).getInt();
		} 
		catch (IOException e) {e.printStackTrace();} 
		return -1;
	}
	
	public static double getTPS() {
		try {
			URL url = new URL("http://127.0.0.1:8081/tps");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			InputStream in = connection.getInputStream();
			System.out.println("HTTP: " + connection.getResponseCode());
			byte[] data = new byte[8];
			for (int i = 0; i < 8; i++) {data[i] = (byte) in.read();}
			in.close();	connection.disconnect();
			return ByteBuffer.wrap(data).getDouble();
		} 
		catch (IOException e) {e.printStackTrace();} 
		return -1;
	}
	
	public static String getPlayersStr() {
		try {
			URL url = new URL("http://127.0.0.1:8081/playersStr");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			InputStream in = connection.getInputStream();
			System.out.println("HTTP: " + connection.getResponseCode());
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			int b; while ((b = in.read()) != -1) {buffer.write(b);}
			in.close();	connection.disconnect();
			return new String(buffer.toByteArray(), StandardCharsets.UTF_8);
		} 
		catch (IOException e) {e.printStackTrace();} 
		return "";
	}
	
	public static String getLog() throws IOException {
	    List<String> lines = Files.readAllLines(Paths.get("/home/nicol/minecraft/vanilla/logs/latest.log"));
	    return String.join("\n", lines.subList(Math.max(0, lines.size() - 20), lines.size()));
	}
	
	
	public static void openPort() throws IOException {
		new ProcessBuilder(
			   "sudo", "firewall-cmd", "--add-port=25565/tcp"
		).start();
	}
	
	public static void closePort() throws IOException {
		new ProcessBuilder(
			   "sudo", "firewall-cmd", "--remove-port=25565/tcp"
		).start();
	}
	
	public static void stop() throws IOException {
		if (minecraft.isAlive()) {
			minecraft.getOutputStream().write("stop\n".getBytes());
			minecraft.getOutputStream().flush();
		}
	}
	
	public static boolean isRunning() {
		if (minecraft == null) {return false;}
		return minecraft.isAlive();
	}

}
