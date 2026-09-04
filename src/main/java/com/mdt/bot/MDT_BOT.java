package com.mdt.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class MDT_BOT {
	
	private static String token;
	private static ScheduledExecutorService scheduler =
		    Executors.newScheduledThreadPool(1);
	
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
	        		new SubcommandData("solve", "solves a given expression")
	        			.addOption(OptionType.STRING, "e", "Expression that you want solved", true),
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
		        ),
	        Commands.slash("minecraft", "minecraft server related commands")
	        	.addSubcommands(
	        		new SubcommandData("start", "starts the minecraft server"),
	        		new SubcommandData("players", "relays how many players are online"),
	        		new SubcommandData("tps", "relayes the current tps of the server"),
	        		new SubcommandData("status", "check if the server is running")
	        	)
	        	.addSubcommandGroups(
	        		new SubcommandGroupData("admin", "Operator only commands")
	        			.addSubcommands(
	        				new SubcommandData("log", "Operator command"),
	    	        		new SubcommandData("stop", "Operator command")
//	    	        		new SubcommandData("backup", "Operator command"),
//	    	        		new SubcommandData("restart", "Operator command"),
//	    	        		new SubcommandData("rollback", "Operator command")
	        			)
	        	)
		        
        ).queue();
        
        scheduler.scheduleAtFixedRate(() -> {
            if (MinecraftHelper.isRunning()) {
                if (MinecraftHelper.getPlayersNum() == 0) {
                    try {
                    	MinecraftHelper.stop();
						MinecraftHelper.closePort();
					} 
                    catch (IOException e) {
						System.out.println("Server couldnt stop.");
					}
                }
            }
        }, 0, 30, TimeUnit.MINUTES);
        
    }
}
