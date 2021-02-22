package task4;

import java.util.Comparator;

public class Tree {
	
	private Game root;
	private AsciiXorComparator comparator;
	private String[] database= {"Grand Theft Auto V","Rocket League","Minecraft","Hearthstone","Diablo III",
			"Red Dead Redemption II","Spider-Man","Fortnite ","Call of Duty: Black Ops","Overwatch ",
			"World of Warcraft","Counter-Strike: Global Offensive","Apex Legends","Cuphead","League of Legends",
			"God of War","Mortal Kombat 11","Call of Duty: WWII","Among Us","Assassin's Creed Odyssey",
			"The Elder Scrolls V: Skyrim","Far Cry 5","Destiny 2","Watch Dogs","Pokemon Sword&Shield",
			"The Witcher 3: Wild Hunt","Battlefield 4","Lego Worlds","Forza Horizon 4","Super Mario Odyssey"};
	
	
	public static void main(String args[]) {
        System.out.println("Ascii Compare:");
		Tree newTree = new Tree(new AsciiXorComparator());
		newTree.insertExamples(30);
		newTree.printInOrder(newTree.getRoot());
		System.out.println("----------------------");
		
		System.out.println("\nRating Compare:");
		Tree secondTree = new Tree();
		secondTree.insertExamples(30);
		secondTree.printInOrder(secondTree.getRoot());
		
	}
	
	
	public Tree(Comparator<Game> comp) {
		root = null;
		comparator = (AsciiXorComparator) comp;
	}
	
	public Tree() {
		root = null;
		comparator = null;
	}
	
	public Game getRoot() {
		return root;
	}
	public void insertExamples(Integer size) {
			for(int i = 0; i < size; i++) {
				insert(database[i]);
			}
	}	
	
	public void insert(String title) {
		if(root == null) {
			root = new Game(title);
			return;
		}
		Game node = new Game(title);
		Game head = root,parent = null;
		int result = 0;
		while(head != null) {
			if(comparator != null) {
				result = comparator.compare(head, node);
			}
			else {
				result = head.compareTo(node);
			}
			parent = head;
			if(result > 0)
				head = head.right;
			else if(result == 0) 
				return;
			else 
				head = head.left;
		}
		head = node;
		head.parent = parent;
		if(result > 0)
			parent.right = head;
		else if(result < 0)
			parent.left = head;
	}
	
	public void printInOrder(Game root) {
		if(root!=null){ 
			printInOrder(root.left);
			if(root == this.root) {
				System.out.print("Root:");
			}
			if(comparator == null) {
				System.out.println(" "+root.getTitle()+"   node_value: " +root.calculateRatings(root.getRatings())); 
			}
			else {
				System.out.println(" " + root.getTitle() + "   node_value: " + comparator.calculateAscii(root.getTitle()));
			}
            printInOrder(root.right); 
        }
	}
}


