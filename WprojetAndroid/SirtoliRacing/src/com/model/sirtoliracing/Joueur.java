package com.model.sirtoliracing;

public class Joueur implements Comparable<Joueur>{

	private String name;
	private int score;
	
	public Joueur(String name, int score)
	{
		this.name=name;
		this.score=score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public int compareTo(Joueur j) {
		if(this.score<j.score)
		{
			return -1;
		}
		if(this.score>j.score)
		{
			return 1;
		}
		else
		{
		return 0;
		}
		
	}
	
	
}
