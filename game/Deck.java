package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Deck is responsible for giving cards to the players
public class Deck {
	//groups numCards number of cards and sends to the GameController to further assign to players
	//static so that object declaration is not needed
	public static List <Integer> generateDeck(int numCards) {
		List <Integer> card = new ArrayList <Integer> ();
		Random generateRandom = new Random();
		for(int i=0;i<numCards;i++) {
			card.add(generateRandom.nextInt(51));
		}
		return card;
	}
}
