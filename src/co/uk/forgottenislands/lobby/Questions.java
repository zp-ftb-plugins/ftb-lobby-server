package co.uk.forgottenislands.lobby;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Questions {
	
private Lobby plugin;
	
	public Questions(Lobby plugin){
		
		this.plugin = plugin;
	}
	
	public void askQuestion(Player player, Integer questionID){
		
		if(questionID == 1){
			
			player.sendMessage(ChatColor.BLUE + "=-=-=-=-= Welcome to" + ChatColor.BOLD + ChatColor.RED + " Forgotten Islands! " + ChatColor.RESET + ChatColor.BLUE + "=-=-=-=-=");
			player.sendMessage(ChatColor.BLUE + "This test will guide you through the steps to becoming a Citizen!");
			player.sendMessage("" + ChatColor.RED + "Be carefull! Three strikes and you will need to be reviewed manually!");
			player.sendMessage(ChatColor.GREEN + "Question 1 " + ChatColor.WHITE + "-" + ChatColor.BOLD + ChatColor.GREEN + " What is looting?");
			player.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "Type the number from one of the following answers into chat e.g. 2");
			player.sendMessage(ChatColor.RED + "1.) Looting is where players destroy anything they want and take it.");
			player.sendMessage(ChatColor.RED + "2.) Looting is where mobs and other entities destroy or steal blocks.");
			player.sendMessage(ChatColor.RED + "3.) Looting is where a player takes valueable blocks such as gold blocks from unprotected areas.");
			player.sendMessage(ChatColor.RED + "4.) Looting is where players take all a players items from their chests.");
		
		} else if(questionID == 2){
			
			player.sendMessage(ChatColor.GREEN + "Question 2 " + ChatColor.WHITE + "-" + ChatColor.BOLD + ChatColor.GREEN + " What is griefing?");
			player.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "Type the number from one of the following answers into chat e.g. 2");
			player.sendMessage(ChatColor.RED + "1.) Griefing is where players destroy anything they want and take it.");
			player.sendMessage(ChatColor.RED + "2.) Griefing is where mobs and other entities destroy or steal blocks.");
			player.sendMessage(ChatColor.RED + "3.) Griefing is where a player takes valueable blocks such as gold from unprotected areas.");
			player.sendMessage(ChatColor.RED + "4.) Griefing is where you steal another players dropped items.");
		
		} else if(questionID == 3){
			
			player.sendMessage(ChatColor.GREEN + "Question 3 " + ChatColor.WHITE + "-" + ChatColor.BOLD + ChatColor.GREEN + " What do we allow?");
			player.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "Type the number from one of the following answers into chat e.g. 2");
			player.sendMessage(ChatColor.RED + "1.) Looting and Griefing on the all of the servers.");
			player.sendMessage(ChatColor.RED + "2.) Griefing is allowed on Creative.");
			player.sendMessage(ChatColor.RED + "3.) Looting is allowed in unprotected areas on the Survival server.");
			player.sendMessage(ChatColor.RED + "4.) Griefing is allowed in all areas of the Survival server.");

		} else if(questionID == 4){
			
			player.sendMessage(ChatColor.GREEN + "Question 4 " + ChatColor.WHITE + "-" + ChatColor.BOLD + ChatColor.GREEN + " What do you do if you see someone hacking or find a bug in the game?");
			player.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "Type the number from one of the following answers into chat e.g. 2");
			player.sendMessage(ChatColor.RED + "1.) You ignore it and carry on playing the game.");
			player.sendMessage(ChatColor.RED + "2.) You contact Zackpollard by typing /mail Zackpollard (message).");
			player.sendMessage(ChatColor.RED + "3.) You try to scare the hacker off or exploit the bug/glitch you found.");
			player.sendMessage(ChatColor.RED + "4.) You tell others about the bug/glitch so they can benefit too.");
			
		} else if(questionID == 5){
			
			player.sendMessage(ChatColor.GREEN + "Question 5 " + ChatColor.WHITE + "-" + ChatColor.BOLD + ChatColor.GREEN + " What's allowed in the chat?");
			player.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "Type the number from one of the following answers into chat e.g. 2");
			player.sendMessage(ChatColor.RED + "1.) Anything goes in the chat, if it can be said, you can say it.");
			player.sendMessage(ChatColor.RED + "2.) You can broadcast IPs but no spamming or swearing.");
			player.sendMessage(ChatColor.RED + "3.) No spamming, consistent swearing/abuse or broadcasting other server IPs.");
			player.sendMessage(ChatColor.RED + "4.) Swearing and broadcasting IPs are allowed, but no spamming the chat.");
			
		} else if(questionID == 6){
			
			player.sendMessage(ChatColor.GREEN + "Question 6 " + ChatColor.WHITE + "-" + ChatColor.BOLD + ChatColor.GREEN + " Can I continuously kill other players or spawn kill them?");
			player.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "Type the number from one of the following answers into chat e.g. 2");
			player.sendMessage(ChatColor.RED + "1.) Yes, you can kill them whenever you like.");
			player.sendMessage(ChatColor.RED + "2.) No, you can only kill a player once then leave at least a 5 minute gap before doing so again, unless they attack you.");
			player.sendMessage(ChatColor.RED + "3.) No, you can only attack players that attack you first.");
			player.sendMessage(ChatColor.RED + "4.) Yes, you can kill people in spawn or at their home.");
		}	
	}
	
	public void wrongAnswer(Player player){
		
		Integer wrongs = 0;
		
		if(plugin.wrongAnswer.containsKey(player)){
			
			wrongs = plugin.wrongAnswer.get(player);
		}
		
		if(wrongs >= 3){
			
			player.sendMessage(ChatColor.RED + "You have been blocked from attempting to rankup as you failed three times! Please contact an admin to be ranked up manually.");
			plugin.getSaveFile().set("rankup.blocked." + player.getName(), true);
			plugin.saveSaveFile();
		} else {
			wrongs = wrongs + 1;
			player.sendMessage(ChatColor.RED + "That was the wrong answer! You have " + ChatColor.BOLD + (3 - wrongs) + ChatColor.RESET + ChatColor.RED + " attempts remaining!");
		}
	}
	
	public void answerQuestion(Player player, Integer questionID, String answer){
		
		player.sendMessage("1");
		
		if(questionID == 1){
			
			player.sendMessage("2");
			
			if (answer.equals("3")){
				
				player.sendMessage("3");
				
				plugin.questionTracker.put(player, plugin.questionTracker.get(player) + 1);
				this.askQuestion(player, plugin.questionTracker.get(player) + 1);
			} else {
				
				this.wrongAnswer(player);
			}
		} else if(questionID == 2){
			
			if (answer.equals("1")){
				
				plugin.questionTracker.put(player, plugin.questionTracker.get(player) + 1);
				this.askQuestion(player, plugin.questionTracker.get(player) + 1);
			} else {
				
				this.wrongAnswer(player);
			}
		} else if(questionID == 3){
			
			if (answer.equals("3")){
				
				plugin.questionTracker.put(player, plugin.questionTracker.get(player) + 1);
				this.askQuestion(player, plugin.questionTracker.get(player) + 1);
			} else {
				
				this.wrongAnswer(player);
			}
		} else if(questionID == 4){
			
			if (answer.equals("2")){
				
				plugin.questionTracker.put(player, plugin.questionTracker.get(player) + 1);
				this.askQuestion(player, plugin.questionTracker.get(player) + 1);
			} else {
				
				this.wrongAnswer(player);
			}
			
		} else if(questionID == 5){
			
			if (answer.equals("3")){
				
				plugin.questionTracker.put(player, plugin.questionTracker.get(player) + 1);
				this.askQuestion(player, plugin.questionTracker.get(player) + 1);
			} else {
				
				this.wrongAnswer(player);
			}
			
		} else if(questionID == 6){
			
			if (answer.equals("2")){
				
				plugin.questionTracker.put(player, plugin.questionTracker.get(player) + 1);
				this.askQuestion(player, plugin.questionTracker.get(player) + 1);
				this.rankUp(player);
			} else {
				
				this.wrongAnswer(player);
			}
		}
	}
	
	public void rankUp(Player player){
		
		Lobby.perms.playerAddGroup(player, "Citizen");
		Lobby.perms.playerRemoveGroup(player, "Visitor");
		player.sendMessage(ChatColor.GREEN + "Congratulations you are now a Citizen! Have fun!");
		player.teleport(player.getWorld().getSpawnLocation());
	}
}
