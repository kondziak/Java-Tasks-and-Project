package task4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Game implements Comparable<Game>{

	private String title;
	private List<Double>ratings;
	public Game left,right,parent;
	
	public Game(String title) {
		this.title = title;
		left = null;
		right = null;
		parent = null;
		ratings = new ArrayList<Double>();
		createRatings(title.length());
	}
	
	public String getTitle() {
		return title;
	}
	
	public List<Double> getRatings(){
		return ratings;
	}
	
	public void createRatings(Integer listSize) {
		Random r = new Random();
		for(int i = 0; i < listSize; i++) {
			Double number = r.nextDouble() * 10;
			ratings.add(number);
		}
	}
	
	public Double calculateRatings(List<Double>ratings) {
		Double sum = 0.0;
		for(Double i : ratings )
			sum += i;

		return sum/ratings.size();
	}
	
	@Override
	public int compareTo(Game rate) {
		Double firstSum = calculateRatings(ratings);
		Double secondSum = calculateRatings(rate.getRatings());
		
		if(firstSum.equals(secondSum)) return 0;
		else if(secondSum > firstSum) return 1;
		return -1;
	}
}
class AsciiXorComparator implements Comparator<Game>{
	
	public int calculateAscii(String title) {
		int count = 0;
		for(int i = 0; i < title.length(); i++) 
			count += (int)(title.charAt(i));
	
		return count ^ title.length();
	}
	
	@Override
	public int compare(Game firstGame, Game secondGame) {
		if(firstGame.getTitle().equals(secondGame.getTitle()))	return 0;
		
		Integer firstResult = calculateAscii(firstGame.getTitle());
		Integer secondResult = calculateAscii(secondGame.getTitle());
		if(secondResult > firstResult) return 1;
		else if(firstResult > secondResult) return  -1;
	
		return secondGame.getTitle().compareTo(firstGame.getTitle());
	
	}
}