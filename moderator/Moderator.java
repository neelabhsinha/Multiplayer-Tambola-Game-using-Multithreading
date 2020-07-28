package moderator;

import participant.Participant;

//Interface to define skeleton of a Moderator ('Subject', as per Observer patter)
public interface Moderator {
	public void startModerator();
	public void registerParticipant(Participant p);
	public void removeParticipant(Participant p);
	public void notifyObservers();
	public void gameOver();	
}
