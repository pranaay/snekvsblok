package snek;

/**
 * Contains definitions for Score. Custom class to store scoring details.
 */
public class Score {
	private int score;
	private String name;
	private String date;

	/**
	 * Constructor to create a Score object.
	 * @param score Integer Score of the player.
	 * @param name String Name of the Player
	 * @param date String Date
	 */
	public Score(int score, String name, String date){
		this.score = score;
		this.name = name;
		this.date = date;
	}

	/**
	 * Returns the score from the object.
	 * @return Integer
	 */
	public int getScore(){
		return score;
	}

	/**
	 * Returns name from the object.
	 * @return String
	 */
	public String getName(){
		return name;
	}

	/**
	 * Returns date from the object.
	 * @return String
	 */
	public String getDate(){
		return date;
	}

	/**
	 * Method to print the object
	 * @return String
	 */
	@Override
	public String toString(){
		return score + "\t" + name  + "\t" + date;
	}
}
