package com.mdt.bot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.FileUpload;

public class SlashCommands extends ListenerAdapter {

	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

		String cmd = event.getName();

		switch (cmd) {
		
			case "ping":
				event.reply("Pong").queue();
				break;
	
			case "math":
				mathHelper(event);
				break;
				
			case "image":
				imageHelper(event);
				break;
			
			case "social":
				socialHelper(event);
				break;
		}
	}

	private void mathHelper(SlashCommandInteractionEvent event) {
		String cmd = event.getSubcommandName();
		double a = event.getOption("a").getAsDouble(), b = event.getOption("b").getAsDouble();

		switch (cmd) {
		
			case "add":
				event.reply(a + " + " + b + " = " + (a+b)).queue();
				break;
				
			case "subtract":
				event.reply(a + " - " + b + " = " + (a-b)).queue();
				break;
				
			case "multiply":
				event.reply(a + " * " + b + " = " + (a*b)).queue();
				break;
				
			case "divide":
				event.reply(a + " / " + b + " = " + (a/b)).queue();
				break;
		}
	}
	
	private void imageHelper(SlashCommandInteractionEvent event) {
		String cmd = event.getSubcommandName(); BufferedImage img;
		try {img = ImageIO.read(new File("input.png"));} 
		catch (IOException e) {img = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);}
		int[] rgba = new int[4]; String[] rgbaS = new String[] {"r", "g", "b", "a"};
		for (int i = 0; i < 4; i++) {rgba[i] = event.getOption(rgbaS[i], 255, opt -> opt.getAsInt())%256;}
		
		switch (cmd) {
		
			case "pixel":
				Graphics2D gph = img.createGraphics();
				int x = event.getOption("x").getAsInt()%100, y = event.getOption("y").getAsInt()%101;
				gph.setColor(new Color(rgba[0], rgba[1], rgba[2], rgba[3]));
				gph.fillRect(x*10, y*10, 10, 10);
				try {
					ImageIO.write(img, "png", new File("input.png"));
					event.replyFiles(FileUpload.fromData(new File("input.png"))).queue();
				} catch (IOException e) {e.printStackTrace();}
				break;
			
			case "show":
				File file = new File("input.png");
				event.replyFiles(FileUpload.fromData(file, "input.png")).queue();
				break;
		}
	}
	
	private void socialHelper(SlashCommandInteractionEvent event) {
		String cmd = event.getSubcommandName();

		switch (cmd) {
		
			case "say":
				event.getChannel().sendMessage(event.getOption("text").getAsString()).queue();
				event.reply("Successfully sent message").setEphemeral(true).queue();
				break;
		}
	}
	
}