package com.mdt.bot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import javax.security.auth.login.LoginException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MDT_BOT {
	
	private static String token;
	
    public static void main(String[] args) throws LoginException, IOException {
    	
    	token = new String(Files.readAllBytes(Paths.get("src/main/java/TOKEN.txt")));
    	
        JDABuilder.createDefault(
        			token,
        			GatewayIntent.GUILD_MESSAGES,
        			GatewayIntent.MESSAGE_CONTENT
                ).addEventListeners(new MessageListener()).build();
    }
}
