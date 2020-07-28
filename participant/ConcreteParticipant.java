package participant;

import java.util.ArrayList;
import java.util.List;

import game.Table;

public class ConcreteParticipant extends Thread implements Participant {
	
	private List <Integer> hand; //cards in hand as given by the GameController
	private List <Integer> checkedCards; //cards that appeared in moderator's generated cards
	private int playerId; // ID of player as assigned by GameController
	private int checkedNumbers; //number of cards that appreared in moderator's generated cards
	private String name; //name of the player
	private Table table; //shared data
	
	//Constructor when player does not specify name
	public ConcreteParticipant(List <Integer> list, Table table, int id) {
		super("[No Name]");
		this.hand = list;
		this.playerId = id;
		this.setPlayerName("[No Name]");
		this.setCheckednumbers(0);
		this.table=table;
		this.checkedCards = new ArrayList <Integer> ();
	}
	//Constructor when name is specified
	public ConcreteParticipant(List<Integer> list, String name, Table table, int id) {
		super(name);
		this.hand = list;
		this.playerId = id;
		this.setPlayerName(name);
		this.setCheckednumbers(0);
		this.table=table;
		this.checkedCards = new ArrayList <Integer> ();
	}
	
	//Helpers
	private void findnum(int num) { //finds a number in hand, and if found, removes it from hand and adds to checkedNumbers
		int index = this.hand.indexOf(num);
		if(index>=0) {
			this.checkedCards.add(this.hand.get(index));
			this.hand.remove(index);
			this.setCheckednumbers(this.getCheckednumbers() + 1);
		}
	}
	
	//Getters and Setters
	public int getCheckednumbers() {
		return checkedNumbers;
	}

	private void setCheckednumbers(int checkednumbers) {
		this.checkedNumbers = checkednumbers;
	}
	@Override
	public String getPlayerName() {
		return this.name;
	}
	private void setPlayerName(String name) {
		this.name = name;
	}
	
	//update method as per observer pattern. Reads the shared data and does required tasks
	/*
	Note -Here, notifyObservers() allow run() to execute, and run() calls update(). So, although indirect, the actual task is
	same as how it happens in observer pattern
	*/
	@Override
	public void update() {
		if(!this.table.isGameOver()) {				
			int num = this.table.getLastNumber();
			this.findnum(num);
		}
		/*
		Disclaimer - this sleep is just for display process. 
		It does not affect inter-thread communication, or the functioning of the code in any way.
		The code will function same, and faster if removed.
		Inter-thread communication is taken care by wait() and notifyAll() methods
		*/
		try {
			Thread.sleep(1000);
		}
		catch(InterruptedException e) {
			System.out.println("Error in player delay - " + e.getStackTrace());
		}
		
		System.out.println(this);
		if(this.checkedNumbers==this.table.getwinThreshold())
			this.table.setWinner(playerId-1);
		this.table.setRoundPlayed(playerId-1);
		this.table.notifyAll();
	}
	
	//Methods for multi-threading
	@Override
	public void start() {
		super.start();
	}
	public void gameOver() {
		try {
			this.join();
		} catch (InterruptedException e) {
			System.err.println("Error in " + this.name + "Joining - " + e.getStackTrace());
		}
	}
	@Override
	public void run() {
		synchronized(table) {
			while(!this.table.isGameOver()) {
				while(!this.table.isNewRound() || this.table.getRoundPlayed(playerId-1)) {
					try {
						this.table.wait(); //waits while other thread has occupied the lock
					}
					catch(InterruptedException e) {
						System.err.println("Error in player waiting - " + e.getStackTrace());
					}
				}
				this.update(); //calls the update method to do the required process
			}
		}
	}

	@Override
	public String toString() {
		return "ConcreteParticipant [hand=" + hand + ", checkedCards=" + checkedCards + ", playerId=" + playerId
				+ ", checkedNumbers=" + checkedNumbers + ", name=" + name + "]";
	}	
}