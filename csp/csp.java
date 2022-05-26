import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;
public class CSP_CryptArithmetic {
  public static String word1;
	public static String word2;
	public static String addResult;
	public static Set<Character> letters;
	public static Vector<Solution> hexalSolutions;
	public static boolean[] isDigitVisited;
	public static List<Integer> digits;
	public static void main(String[] args) {
		readInput();
		if(isAlpha(word1) && isAlpha(word2) && isAlpha(addResult)) {
			if(lengthOk()) {
				breakToLetters();
				if(letters.size()>10) {
					System.out.println("\n --!-- I can't solve that... Im sorry... --!--");
					System.exit(0);
				}
				initialize();
				System.out.println("\n--> Decimal solutions found: \n");
        solveProblem(new ArrayList<Character>(letters));
				System.out.println("\n--> Hexal solutions found: \n");

				for(Solution s: hexalSolutions) {
					System.out.println(s.getFirst() +" + "+ s.getSecond() + " =  " +s.getResult());
				}
			}else {
				System.out.println("\n --!-- can't solve that... Im sorry... --!--");
				System.exit(0);
			}
		}else {
			System.out.println("\n --!-- The input must contain only letters --!--");
			System.exit(0);
		}
	}
	public static void solveProblem(List<Character> characters) {
		if(characters.size() == digits.size()) {
			constructSolution(characters);
		}
		for(int i=0; i<10; i++) {
			if(!isDigitVisited[i]) {

				isDigitVisited[i] = true;
				digits.add(i);
				solveProblem(characters);
				digits.remove(digits.size()-1);
				isDigitVisited[i] = false;

			}
		}		
	}
	public static void constructSolution(List<Character> characters ){
		HashMap<Character,Integer> map = new HashMap<Character,Integer>();
		for(int i=0;i<characters.size();i++) {
			map.put(characters.get(i), digits.get(i));
    }
		if(map.get(word1.charAt(0)) == 0 || map.get(word2.charAt(0)) == 0 || map.get(addResult.charAt(0)) == 0)
             return;
		String firstTerm = "", secondTerm = "", resTerm = "";
		for(char c: word1.toCharArray())
            firstTerm += map.get(c);
		for(char c: word2.toCharArray())
            secondTerm += map.get(c);
		for(char c: addResult.toCharArray())
            resTerm += map.get(c);
		if((Integer.parseInt(firstTerm) + Integer.parseInt(secondTerm)) == Integer.parseInt(resTerm)) {
			Solution s = new Solution(Integer.parseInt(convertBase(firstTerm,10,6)) , Integer.parseInt(convertBase(secondTerm,10,6)), Integer.parseInt(convertBase(resTerm,10,6)));
			hexalSolutions.add(s);
			System.out.println(firstTerm + " + " + secondTerm + " =  "+ resTerm + "   | Solution Mapping : " +map);
		}
	}
	public static void breakToLetters() {
		letters= new HashSet<Character>();
		for(char c: word1.toCharArray()) {
			letters.add(c);
		}
		for(char c: word2.toCharArray()) {
			letters.add(c);
		}
		for(char c: addResult.toCharArray()) {
			letters.add(c);
		}
	}
	public static void readInput() {
		Scanner in = new Scanner(System.in);
		System.out.print("--> Enter 1st word: ");
		word1 = in.next().toUpperCase();
		System.out.print("--> Enter 2nd word: ");
		word2 = in.next().toUpperCase();
		System.out.print("--> Enter result: ");
		addResult = in.next().toUpperCase();
		in.close();
	}
	public static boolean isAlpha(String in) {
		return in.matches("[A-Z]+");
	}
	public static void initialize() {
		digits = new ArrayList<Integer>();
		isDigitVisited = new boolean[10];	
		hexalSolutions = new Vector<Solution>();
	}
	public static boolean lengthOk() {
		if(addResult.length() > Math.max(word1.length(), word2.length())+1) {
			return false;
		}else {
			return true;
		}
	}
	public static String convertBase(String number, int sBase, int dBase) {
		return Integer.toString(Integer.parseInt(number, sBase),dBase);
	}

}
class Solution{
	private final int first;
	private final int second;
	private final int result;
	public Solution(int first, int second, int result) {
		this.first = first;
		this.second = second;
		this.result = result;
	}
    public int getFirst() { return first; }
    public int getSecond() { return second; }
    public int getResult() { return result; }
}
