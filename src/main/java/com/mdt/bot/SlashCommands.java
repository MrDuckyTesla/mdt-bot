package com.mdt.bot;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

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
}