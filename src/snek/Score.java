package snek;

public class Score {
	private int score;
	private String name;
	private String date;

	public Score(int score, String name, String date){
		this.score = score;
		this.name = name;
		this.date = date;
	}

	public int getScore(){
		return score;
	}

	@Override
	public String toString(){
		return score + "\t" + name  + "\t" + date;
	}
}
