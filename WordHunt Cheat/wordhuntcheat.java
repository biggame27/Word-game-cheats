package cheat;
import java.io.*;
import java.util.*;
public class wordhuntcheat {
	static TrieNode root = new TrieNode();
	static String board[] = new String[4];


	public static void main(String args[]) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader("dictionary.txt"));
		ArrayList<Pair> ans = new ArrayList<Pair>();
		make_trie(br);
		System.out.println("Input your 4x4 wordhunt board in like this:");
		System.out.println("casd");
		System.out.println("qdgs");
		System.out.println("vbds");
		System.out.println("aplk");
		System.out.println();
		
		Scanner in = new Scanner(System.in);
		board[0] = in.nextLine();
		board[1] = in.nextLine();
		board[2] = in.nextLine();
		board[3] = in.nextLine();
		boolean visited[][] = new boolean[4][4];
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				visited[i][j] = false;
			}
		}

		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				recurse(i, j, "", ("(" + i + ", " + j + ")"),  visited, root, ans);
			}
		}
		sort(0, ans.size()-1, ans);
		for(int i = 0; i < ans.size(); i++)
		       System.out.println(ans.size() - i + ": " + ans.get(i).first + "\n   " + ans.get(i).second +"\n\n");

	}
	static boolean compare(Pair first, Pair second) {
		return first.first.length() < second.first.length();
	}
	static void make_trie(BufferedReader br) throws IOException{
		String word = "";
		String input = "";
		while((input = br.readLine()) != null) {
			TrieNode curr = root;
			word = input;
			for(int i = 0; i < word.length(); i++) {
				String letter = word.substring(i, i+1);
				if(curr.children.get(letter) == null) {
					curr.children.put(letter, new TrieNode());
				}
				curr = curr.children.get(letter);
				if(i == word.length()-1) {
					curr.isFullWord = true;
				}
			}
		}
	}
	static void recurse(int row, int col, String word, String path, boolean visited[][], TrieNode node, ArrayList<Pair> ans) {
		if(row < 0 || row >= 4 || col < 0 || col >= 4 && visited[row][col])
			return;
		String letter = board[row].substring(col, col+1);
		if(node.children.get(letter) == null) {
			//System.out.println(word + letter);
			return;
		}
		word+=letter;
		visited[row][col] = true;
		if(word.length() > 3 && node.children.get(letter).isFullWord) {
			ans.add(new Pair(word, path));

		}
		ArrayList<Pair2> directions = new ArrayList<Pair2>();
		directions.add(new Pair2(new Pair1(1, 0), ", D"));
		directions.add(new Pair2(new Pair1(0, 1), ", R"));
		directions.add(new Pair2(new Pair1(-1, 0), ", U"));
		directions.add(new Pair2(new Pair1(1, -1), ", LD"));
		directions.add(new Pair2(new Pair1(-1, 1), ", RU"));
		directions.add(new Pair2(new Pair1(1, 1), ", RD"));
		directions.add(new Pair2(new Pair1(-1, -1), ", LU"));

		for(Pair2 elem : directions) {
			int x = elem.first.first;
			int y = elem.first.second;
			String d = elem.second;
			if(row + x >= 0 && row + x < 4 && col + y < 4 && col + y >=0) {
				if(!visited[row + x][col + y]) {
					recurse(row + x, col + y, word, path + d, visited, node.children.get(letter), ans);
				}
			}
		}
		visited[row][col] = false;
	}
	static int partition(int low, int high, ArrayList<Pair> ans)
    {
        int pivot = ans.get(high).len;
        int i = (low-1);
        for (int j=low; j<high; j++)
        {
            if (ans.get(j).len < pivot)
            {
                i++;
                Pair temp = ans.get(i);
                ans.set(i, ans.get(j));
                ans.set(j, temp);
            }
        }
        Pair temp = ans.get(i+1);
        ans.set(i+1, ans.get(high));
        ans.set(high, temp);
        return i+1;
    }
    static void sort(int low, int high, ArrayList<Pair> ans)
    {
        if (low < high)
        {
            int pi = partition(low, high, ans);
            sort(low, pi-1, ans);
            sort(pi+1, high, ans);
        }
    }


}

class TrieNode {
    HashMap<String, TrieNode> children = new HashMap<String, TrieNode>();
    boolean isFullWord;
    public TrieNode() {}
}
class Pair{
	String first;
	String second;
	int len;
	public Pair(String first, String second) {
		this.first = first;
		this.second = second;
		len = first.length();
	}

}
class Pair1{
	int first;
	int second;
	public Pair1(int n, int n1) {
		first = n;
		second = n1;
	}
}
class Pair2 {
	Pair1 first;
	String second;
	public Pair2(Pair1 pair, String second) {
		this.first = pair;
		this.second = second;
	}
}
