package com.mdt.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.nio.file.*;

public final class MDT_BOT {
	
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
	        			.addOption(OptionType.NUMBER, "b", "second number", true),
	        		new SubcommandData("random", "Generates a random number between first and second number")
	        			.addOption(OptionType.NUMBER, "a", "first number", true)
	        			.addOption(OptionType.NUMBER, "b", "second number", false)
	        	),
	        Commands.slash("image", "image related commands")
	        	.addSubcommands(
	        		new SubcommandData("pixel", "adds a pixel to image")
	        			.addOption(OptionType.INTEGER, "x", "x coord", true)
	        			.addOption(OptionType.INTEGER, "y", "y coord", true)
	        			.addOption(OptionType.INTEGER, "r", "red value", false)
	        			.addOption(OptionType.INTEGER, "g", "green value", false)
	        			.addOption(OptionType.INTEGER, "b", "blue value", false)
	        			.addOption(OptionType.INTEGER, "a", "alpha value", false),
	        		new SubcommandData("show", "shows image")
	        	),
	        Commands.slash("social", "text related commands")
	        	.addSubcommands(
	        		new SubcommandData("say", "sends a message on the bots behalf")
	        			.addOption(OptionType.STRING, "text", "message for the bot to send", true)
	        	)
	        	.addSubcommands(
		        	new SubcommandData("send", "sends a message on the bots behalf with user who used command visible")
		        		.addOption(OptionType.STRING, "text", "message for the bot to send", true)
		        )
	        	.addSubcommands(
			        new SubcommandData("reply", "replies to a message")
			        	.addOption(OptionType.STRING, "message-id", "id of the message for the bot to reply to", true)
			        	.addOption(OptionType.STRING, "text", "message for the bot to send", true)
	        	),
	        Commands.slash("gamble", "gambling related commands")
	        	.addSubcommands(
	        		new SubcommandData("coin-toss", "flips a coin heads or tails")
	        			.addOption(OptionType.NUMBER, "bet", "amount of money to gamble on", true)
	        			.addOption(OptionType.STRING, "call", "what you think the coin will land on", true)
	        			.addOption(OptionType.STRING, "secondary", "what you think the coin wont land on", false)
	        	)
	        	.addSubcommands(
		        		new SubcommandData("roulette", "spins the roulette wheel")
		        			.addOption(OptionType.NUMBER, "bet", "amount of money to gamble on", true)
		        			.addOption(OptionType.STRING, "call", "where you think the ball will land", true)
		        			.addOption(OptionType.STRING, "secondary", "where you dont think the ball will land", false)
		        			.addOption(OptionType.STRING, "special", "name of the special 2% win slot", false)
		        	)
        ).queue();
        
    }
}
