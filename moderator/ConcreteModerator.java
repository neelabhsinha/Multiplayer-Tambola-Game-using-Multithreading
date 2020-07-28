package moderator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import game.Table;
import participant.Participant;

public class ConcreteModerator extends Thread implements Moderator {
	
	private List <Participant> participants; //list of participants
	private Table table; //shared data
	private volatile static ConcreteModerator gameModerator; //for singleton pattern
	
	private ConcreteModerator(Table table) { //Private Constructor that creates empty list of participants
		super("Moderator");
		this.table=table;
		this.participants = new ArrayList <Participant>();
	}
	private ConcreteModerator(List <Participant> participants, Table table) { //Private Constructor that takes in an ArrayList of participants
		super("Moderator");
		this.table=table;
		this.participants = participants;
	}
	
	public static ConcreteModerator createModerator (Table table) { //method to create singleton object without parameters
		if(gameModerator == null) {
			synchronized(ConcreteModerator.class) {
				if(gameModerator == null) {
					gameModerator = new ConcreteModerator(table);
				}
			}
		}
		return gameModerator;
	}
	public static ConcreteModerator createModerator (List <Participant> participants, Table table) { //method to create singleton object with participants
		if(gameModerator == null) {
			synchronized(ConcreteModerator.class) {
				if(gameModerator == null) {
					gameModerator = new ConcreteModerator(participants,table);
				}
			}
		}
		return gameModerator;
	}
	
	//Functions for observer pattern to add/remove participants
	@Override
	public void registerParticipant(Participant p) {
		participants.add(p);
	}

	@Override
	public void removeParticipant(Participant p) {
		int index= participants.indexOf(p);
		if(index>=0) {
			participants.remove(index);
		}	
	}

	@Override
	public void notifyObservers() { //the notify function which subject uses to notify observers
		this.table.notifyAll();
	}
	
	public int generateRandomNumber() {
		Random rand = new Random();
		return (rand.nextInt(51));
	}
	
	//Methods for multi-threading
	public void startModerator() {
		super.start();
	}
	public void gameOver() {
		try {
			this.join();
		} catch (InterruptedException e) {
			System.err.println("Error in Moderator Joining - " + e.getStackTrace());
		}
	}
	@Override
	public void run() {
		synchronized(table) { //take lock of table
			while(!this.table.isGameOver()) { //check if game is over
				this.table.setNewRound(false);
				this.table.setCheckedFlags(false);
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
					System.err.println("Error in moderator delay - " + e.getStackTrace());
				}
				int num = this.generateRandomNumber();
				this.table.insertValue(num); //insert number to table's list
				this.table.setNewRound(true); //declares new round open
				System.out.println("\nRound " + this.table.getCurrentround() + " Started...\n" + "Moderator generated " + num + "\n" + table);
				this.notifyObservers(); //calls the notifyObservers method
				while(this.table.playersPlaying()) {
					try {
						/*
						Disclaimer - this sleep is just for the display process. 
						It does not affect inter-thread communication, or the functioning of the code in any way.
						The code will function same, and faster if removed.
						Inter-thread communication is taken care by wait() and notifyAll() methods
						*/
						Thread.sleep(1000);
						this.table.wait(); //waits while other threads release the lock
					}
					catch(InterruptedException e) {
						System.err.println("Interrupted Exception in Moderator - " + e.getStackTrace());
					}
				}
			}
		}
	}
	
	@Override
	public String toString() {
		return "ConcreteModerator [participants=" + participants + "]";
	}	
}