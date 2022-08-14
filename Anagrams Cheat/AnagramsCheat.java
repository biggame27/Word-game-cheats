package cheat;
import java.util.*;
import java.io.*;
public class AnagramsCheat {

	private static TrieNode root = new TrieNode();
	private static int anagramLength;
	private static String board[];
	private static ArrayList<Answer> ans = new ArrayList<>();

	public static void main(String args[]) throws IOException{
		Scanner in = new Scanner(new File("dictionary.txt"));
		Scanner in2 = new Scanner(System.in);
		makeTrie(in);
		System.out.print("Input the 6 letter anagrams puzzle:");
		String n = in2.nextLine();
		anagramLength = n.length();
		board = n.split("");
		boolean visited[] = new boolean[anagramLength];
		for(int i = 0; i < anagramLength; i++) {
			visited[i] = false;
		}
		for(int i = 0; i < anagramLength; i++) {
			recurse(i, String.valueOf(i+1), "", root, visited);
		}
		quickSort(0, ans.size()-1);
		for(int i = 0; i < ans.size(); i++) {
			System.out.println(ans.get(i).getWord() + " " + ans.get(i).getPath());
		}
		in.close();
		in2.close();
	}
	static void makeTrie(Scanner in) throws IOException {
		String word = "";
		while(in.hasNext()) {
			word = in.nextLine();
			TrieNode curr = root;
			//resets curr to root when there is a new word
			for(int i = 0; i < word.length(); i++) {
				String letter = word.substring(i, i+1);
				if(curr.getChildren().get(letter) == null) {
					curr.getChildren().put(letter, new TrieNode());
				}

				//Also resets curr and curr changes until the word is finished(next letter)
				//Assigning whole different new TrieNode
				curr = curr.getChildren().get(letter);
				//This specific letter or TrieNode is the end of the word
				if(i == word.length()-1)
					curr.setIFW(true);
			}
		}
	}
	static void recurse(int pos, String path, String word, TrieNode node, boolean[] visited) {
		if(pos < 0 || pos > anagramLength && visited[pos])
			return;
		String letter = board[pos];
		//checks if there is a valid word in the dictionary if you add the letter
		if(node.getChildren().get(letter) == null)
			return;
		word+= letter;
		visited[pos] = true;
		if(word.length() >= 3 && node.getChildren().get(letter).getIFW()) {
			ans.add(new Answer(word, path));
		}
		for(int i = 0; i < anagramLength; i++) {
			if(!visited[i]) {
				recurse(i, path + String.valueOf(i+1), word, node.getChildren().get(letter), visited);
			}
		}
		visited[pos] = false;
	}
	static void quickSort(int low, int high) {
		if(low < high) {
			int pivot = ans.get(high).getWord().length();
			int i = low;
			for(int j = low; j < high; j++) {
				if(ans.get(j).getWord().length() < pivot) {
					Answer temp = ans.get(i);
					ans.set(i, ans.get(j));
					ans.set(j, temp);
					i++;
				}
			}
			ans.add(i, ans.remove(high));
			quickSort(low, i-1);
			quickSort(i+1, high);
		}

	}


}
class TrieNode {
	private boolean isFullWord = false;
	private HashMap<String, TrieNode> children = new HashMap<>();
	public TrieNode(){}

	public boolean getIFW() {
		return isFullWord;
	}
	public void setIFW(boolean value) {
		isFullWord = value;
	}
	public HashMap<String, TrieNode> getChildren(){
		return children;
	}

}
class Answer{
	private String path = "";
	private String word = "";
	public Answer(String word, String path) {
		this.word = word;
		this.path = path;
	}
	public void addPath(String n) {
		path += n;
	}
	public String getPath() {
		return path;
	}
	public String getWord() {
		return word;
	}
}