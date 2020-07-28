package game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import moderator.*;
import participant.*;

//The GameController's Job is to initiate the game and control it. It gives players the cards, assigns the moderator and runs the game
public class GameController {
	
	public static void main(String [] args) {
		int totalPlayers= 4; //decides total number of players
		int rounds = 10; //specify maximum number of rounds
		int winThreshold = 3; //specify criteria of winning, that is, number of strikes required to win the match
		int cardsPerPlayer = 10; //specify how many cards will one player hold
		
		//Initiation Process -
		Table table = new Table(rounds,totalPlayers,winThreshold);
		List <Participant> players = new ArrayList <Participant> ();
		for(int i=1;i<=totalPlayers;i++) {
			Participant temp = new ConcreteParticipant(Deck.generateDeck(cardsPerPlayer), "Player " + i,table,i);
			players.add(temp);
		}
		Moderator moderator = ConcreteModerator.createModerator(players,table);
		
		//Display Elements -
		System.out.println("Creating Game...");
		if(table!=null)
			System.out.println("Table Created with parameters - " + table);
		System.out.println("Player assigned and are as follows -");
		Iterator <Participant> itr = players.iterator();
		while(itr.hasNext()) {
			System.out.println(itr.next());
		}
		if(moderator!=null)
			System.out.println("Moderator Assigned...");
		System.out.println("\nStarting Game...");
		
		//Start threads -
		moderator.startModerator();
		for(Participant player:players) {
			player.start();
		}
		//It is a good practice to ensure that the main thread is the last to finish.
		//So, gameOver() executes the Thread.join() function for both moderator and players
		moderator.gameOver();
		for(Participant player:players) {
			player.gameOver();
		}
		
		//Display elements after game ends, also judges the winner based on table status
		System.out.println("\nGame Over");
		try {
			/*
			Disclaimer - this sleep is just for display process. 
			It does not affect inter-thread communication, or the functioning of the code in any way.
			The code will function same, and faster if removed.
			Inter-thread communication is taken care by wait() and notifyAll() methods
			*/
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(table.findWinner()<0)
			System.out.println("Sorry! No winner in this game.");
		else {
			int index = table.findWinner();
			Participant winner = players.get(index);
			System.out.println(winner.getPlayerName() + " is the winner!");
		}
	}
}