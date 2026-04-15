package com.mdt.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import javax.security.auth.login.LoginException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MDT_BOT {
	
	private static String token;
	
    public static void main(String[] args) throws LoginException, IOException, InterruptedException {
    	
    	token = new String(Files.readAllBytes(Paths.get(".secret/TOKEN.txt")));
    	
        JDA bot = JDABuilder.createDefault(
        			token,
        			GatewayIntent.GUILD_MESSAGES,
        			GatewayIntent.MESSAGE_CONTENT,
        			GatewayIntent.GUILD_MESSAGE_REACTIONS
                ).addEventListeners(new MessageHandler(), new SlashCommands()).build();
        
        bot.awaitReady();
        
        bot.updateCommands().addCommands(
	        Commands.slash("ping", "Replies with pong"),
	        		
	        Commands.slash("math", "math related commands")
	        	.addSubcommands(
	        		new SubcommandData("add", "Adds two numbers together")
	        			.addOption(OptionType.NUMBER, "a", "first number", true)
	        			.addOption(OptionType.NUMBER, "b", "second number", true),
	        		new SubcommandData("subtract", "Subtracts the frist number from the second")
		    			.addOption(OptionType.NUMBER, "a", "first number", true)
		    			.addOption(OptionType.NUMBER, "b", "second number", true),
		    		new SubcommandData("multiply", "Multiplies two numbers together")
	        			.addOption(OptionType.NUMBER, "a", "first number", true)
	        			.addOption(OptionType.NUMBER, "b", "second number", true),
	        		new SubcommandData("divide", "Divides the frist number by the second")
	        			.addOption(OptionType.NUMBER, "a", "first number", true)
	        			.addOption(OptionType.NUMBER, "b", "second number", true)
	        	),
	        Commands.slash("image", "image related commands")
	        	.addSubcommands(
	        		new SubcommandData("pixel", "adds a pixel to image")
	        			.addOption(OptionType.INTEGER, "x", "x coord", true)
	        			.addOption(OptionType.INTEGER, "y", "y coord", true)
	        			.addOption(OptionType.INTEGER, "r", "red value", false)
	        			.addOption(OptionType.INTEGER, "g", "green value", false)
	        			.addOption(OptionType.INTEGER, "b", "blue value", false)
	        			.addOption(OptionType.INTEGER, "a", "alpha value", false)
	        	),
	        	Commands.slash("social", "text related commands")
	        		.addSubcommands(
	        			new SubcommandData("say", "sends a message on the bots behalf")
	        				.addOption(OptionType.STRING, "text", "message for the bot to send", true)
	        		)
        ).queue();
        
    }
}
