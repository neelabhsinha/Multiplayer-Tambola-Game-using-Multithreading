package participant;

//Interface to define skeleton of a participant ('Observer' as per observer pattern)
public interface Participant {
	public void update();
	public void start();
	public void gameOver();
	public String getPlayerName();
}