import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

class Reader {
    static BufferedReader reader;
    static StringTokenizer tokenizer;

    /** call this method to initialize reader for InputStream */
    static void init(InputStream input) {
        reader = new BufferedReader(
                     new InputStreamReader(input) );
        tokenizer = new StringTokenizer("");
    }

    /** get next word */
    static String next() throws IOException {
        while ( ! tokenizer.hasMoreTokens() ) {
            //TODO add check for eof if necessary
            tokenizer = new StringTokenizer(
                   reader.readLine() );
        }
        return tokenizer.nextToken();
    }

    static int nextInt() throws IOException {
        return Integer.parseInt( next() );
    }
	
    static double nextDouble() throws IOException {
        return Double.parseDouble( next() );
    }
    static long nextLong() throws IOException {
        return Long.parseLong( next() );
    }
   	
    
}

class SortById implements Comparator<Player>{
	
	
	@Override
	public int compare(Player p1,Player p2) {
		
		return p1.getID()-p2.getID();
	}
}

class SortByHP implements Comparator<Player>{
	
	@Override
	public int compare(Player p1, Player p2) {
		
		if(p1.getHP()>=p2.getHP()) {
			return 1;
		}
		return -1;
	}
}


abstract class Player{
	
	/* when we want to have some common methods but some other methods too
	 * which are different across different player. 
	 * Can,t use interface because there common methods can't be reused.
	*/
	
	protected double hp;
	
	private String name;
	
	protected int ID;
	
	public Player(int c, int p) {
		
		hp=c;
		name="Player"+Integer.toString(p);
		ID=p;
		
	
	
	}
	
	@Override
	public boolean equals(Object o1) {
		
		if(o1!=null && getClass()==o1.getClass()) {
			Player o=(Player) o1; //type casting
			return(ID==o.ID);
		}
		else {
			return false;
		}
		
		
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	
	
	
	
	public double getHP() {
		return hp;
	}
	
	
	
	public void setHP(double x) {
		
		if(hp+x<0) {
			hp=0;
			return;
		}
		hp+=x;
	}
	
	public int getID() {
		return this.ID;
	}
	
	
	public abstract int play(HashMap<Integer,Player> players);    //abstract method
	
	public abstract String name();//
	
	
	
	public int vote(HashMap<Integer,Player> h) {
		
		
		
		for(int e:h.keySet()) {
			
			if(e!=ID) {
				return e;
			}
		}
		
		return 0;
		
	}
}

class Mafia extends Player{
	
	
	public Mafia(int k,int p) {
		super(k,p);
	}
	
	@Override
	public int play(HashMap<Integer,Player> players) {
		
		ArrayList<Integer> avail=new ArrayList<Integer>();
		
		for(int e:players.keySet()) {
			if(players.get(e) instanceof Mafia) {
				continue;
				
			}
			else {
				avail.add(e);
			}
		}
		
		Collections.shuffle(avail);
		
		int chosen=avail.get(0);
		
		
		
		
		return chosen;
		
		
		
		
	}
	
	@Override
	public String name() {
		return  "Mafia";
	}
}
class Detective extends Player{
	
	
	public Detective(int k,int p) {
		super(k,p);
	}
	
	@Override
	public int play(HashMap<Integer,Player> players) {
		
		ArrayList<Integer> avail=new ArrayList<Integer>();
		
		for(int e:players.keySet()) {
			if(players.get(e) instanceof Detective) {
				continue;
				
			}
			else {
				avail.add(e);
			}
		}
		
		Collections.shuffle(avail);
		
		int chosen=avail.get(0);
		
		
		return chosen;
		
	}
	
	@Override
	public String name() {
		return  "Detective";
	}
	
}
class Healer extends Player{
	
	
	public Healer(int k,int p) {
		super(k,p);
	}
	

	@Override
	public int play(HashMap<Integer,Player> players) {
		
		ArrayList<Integer> avail=new ArrayList<Integer>();
		
		for(int e:players.keySet()) {
			
				avail.add(e);
			
		}
		
		Collections.shuffle(avail);
		
		int chosen=avail.get(0);
		
		players.get(chosen).setHP(500);
		
		return chosen;
		
		
		
		
		
		
		
	}
	
	@Override
	public String name() {
		return  "Healer";
	}
}
class Commoner extends Player{
	
	
	public Commoner(int k,int p) {
		super(k,p);
	}
	
	
	@Override
	public int play(HashMap<Integer,Player> h) {
		
		
		
		for(int e:h.keySet()) {
			
			if(e!=ID) {
				return e;
			}
		}
		
		return 0;
		
	}
	
	@Override
	public String name() {
		return  "Commoner";
	}
	
}



class Team<T>{                       //GENERIC PROGRAMMING
	
	private ArrayList<T> myList;
	
	public Team() {
		
		myList=new ArrayList<T>();
	}
	
	public ArrayList<T> getList() {
		
		return myList;
	}
	
	public void add(T ad) {
		myList.add(ad);
	}
	
	public void remove(T ad) {
		myList.remove(ad);
	}
	
	public T get(int idx) {
		return myList.get(idx);
	}
	
	public int size() {
		return myList.size();
	}
	
	public void preamble(Player User,Team<T> t) {

		System.out.println("You are "+User.toString()+".");
		System.out.println("You are "+User.name()+".");
		
		if(User instanceof Commoner) return;
		System.out.print("Other " + User.name() +"s are: [");
		for(int h=1;h<t.size();h++) {
			
			System.out.print(t.get(h).toString());
			
			if(h!=t.size()-1)
			System.out.print(",");
			
		}
		System.out.println("]");
		
		
		
		return;
	}
	
	
}



class PlayGame{
	
	private Team<Mafia> mafias;
	private Team<Detective> detectives ;
	private Team<Commoner> commoners ;
	private Team<Healer> healers ;
	
	private ArrayList<Player> playerA;  //for players, to be used in comparator
	
	private int n_mafias;
	private int n_rest;
	private int n;
	private int n_det;
	private int n_healer;
	
	HashMap<Integer,Player> players;
	
	public PlayGame(Team<Mafia> m,Team<Detective> d,Team<Commoner> c,Team<Healer> h,HashMap<Integer,Player> p,	ArrayList<Player> pp) {
		
		
		mafias=m;
		detectives=d;
		healers=h;
		commoners=c;
		players=p;
		playerA=pp;
		n=p.size();
		n_mafias=m.size();
		n_det=detectives.size();
		n_rest=p.size()-n_mafias;
		n_healer=h.size();
	}
	
	public void printHP() {
		System.out.println();
		for(int e: players.keySet()) {
			
			System.out.println(players.get(e).toString()+"  "+players.get(e).getHP());
		}
		System.out.println();
	}
	
	
	public int vote() {
		
		
		
		ArrayList<Integer> avail=new ArrayList<Integer>();
		
		for(int e:players.keySet()) {
			
				avail.add(e);
			
		}
		
		Collections.shuffle(avail);
		
		
		int chosen=avail.get(0);

		return chosen;
		
		
		
		
		
		
	}
	
	public int voteLoop() {
		ArrayList<Integer> votes=new ArrayList<Integer>();
		
		int x=0;
		
		while(x==0) {
		
		for(int e: players.keySet()) {
			
			Player p=players.get(e);
			
			votes.add(p.vote(players));
			
			
			
			
		}
		
		
		Collections.sort(votes);
		
		x=votes.get(votes.size()-1);
		
		for(int i=0;i<votes.size()-1;i++) {
			
			if(votes.get(i)==x) {
				x=0;
				break;
			}
				
		}
		}
		return x;
	}
	
	
	public Player damages(int chosen) {
		Player p=players.get(chosen);
		
		double totalHP=0;
		
		int totalGZero=0;
		
		int tot=0;

		for(int e : players.keySet()) {
			Player x=players.get(e);
			if(x instanceof Mafia) {
				tot++;
				totalHP+=x.getHP();
				if(x.getHP()>0) {
					totalGZero++;
				}
			}
		}
		
		if(totalHP<=0) {
			return p;
		}
		double init=p.getHP();
		
		double rAmt=0; 
		
		if(totalGZero!=0)
		rAmt= init/(double)totalGZero;
		

		int i=1;
		for(int e:players.keySet()) {
			Player x=players.get(e);
			if(x instanceof Mafia) {
				double pp=rAmt-x.getHP();
				if(x.getHP()<rAmt) {
					
				
					x.setHP(-x.getHP());
					
					if(tot-i!=0)
					rAmt+=pp/(tot-i);
					
				}
				else {
					x.setHP(-rAmt);
					
				}
				i++;
			}
		}

		if(totalHP>=p.getHP()) {
			
			p.setHP(-p.getHP());
			
		}
		else {
			p.setHP(-totalHP);
		}
		
		return p;
	}
	
	public void printRemaining() {
		
		Collections.sort(playerA,new SortById());
		
		int n=playerA.size();
		
		System.out.print(n+" players are remaining: ");
		
		for(int i=0;i<playerA.size();i++) {
			System.out.print(playerA.get(i).toString());
			
			if(i!=playerA.size()-1)
				System.out.print(", ");
		}
		System.out.println(" are alive.");
		
	}
	
	public int check(int x,HashMap<Integer,Player> players)throws IOException {
		
		if(!players.containsKey(x)) {
			
			while(!players.containsKey(x)) {
				
				System.out.print("The player has either died or voted out. Choose another player: ");
				x=Reader.nextInt();
			}
			
			
		}
		return x;
	}
	
	
	
	
	public void gameOver(int flag) {
		System.out.println("Game Over.");
		System.out.println((flag==1)?"The mafias have won.":"The mafias have lost");
		
		for(int i=0;i<mafias.size();i++) {
			System.out.print(mafias.get(i).toString());
			
			if(i!=mafias.size()-1)
			System.out.print(",");
		}
		System.out.println(" were Mafias.");
		
		for(int i=0;i<detectives.size();i++) {
			System.out.print(detectives.get(i).toString());
			
			if(i!=detectives.size()-1)
			System.out.print(",");
		}
		System.out.println(" were Detectives.");
		for(int i=0;i<healers.size();i++) {
			System.out.print(healers.get(i).toString());
			
			if(i!=healers.size()-1)
			System.out.print(",");
		}
		System.out.println(" were Healers.");
		for(int i=0;i<commoners.size();i++) {
			System.out.print(commoners.get(i).toString());
			
			if(i!=commoners.size()-1)
			System.out.print(",");
		}
		System.out.println(" were Commoners.");
		
	}
	
	public void playAsMafia(Player User)throws IOException {

		int round=1;
		
		int flag=0;
		
		int userOut=0; 
		
		while(n_rest!=n_mafias) {
			
			
			if(n_mafias==0) {
				break;
			}
			
			//System.out.println(n_mafias+" "+n_rest);
			
			System.out.println("Round "+round+":");
			
			printRemaining();
			
			int target=500000;
			
			if(userOut==0) {
			
			
				System.out.print("Choose a target: ");
				
				target=Reader.nextInt();
				
				target=check(target,players);

				
				while(players.get(target) instanceof Mafia) {
				
			
					System.out.println("You cannot target a Mafia. Choose a player to target: ");
					target=Reader.nextInt();
					target=check(target,players);
				
				
				}
				
				
			}
			
			else {
			System.out.println("Mafias have chosen their target.");
			
			
			for(int e: players.keySet()) {
				Player d=players.get(e);
				if(d instanceof Mafia) {
					target=d.play(players);
					break;
				}
				
			}

			}
			
			Player t=damages(target);
			

			int test=100000;
			
			
			
			if(n_det<=0 ) {
				
				System.out.println("Detectives have chosen a player to test");
				
				
				
			}
			else{
				for(int e: players.keySet()) {
					Player d=players.get(e);
					if(d instanceof Detective) {
						test=d.play(players);
						break;
					}
					
				}
				
				System.out.println("Detectives have chosen a player to test");
			}
			
			

			
			int chosen=10000;
			if(n_healer<=0) {
				System.out.println("Healers have chosen someone to heal.");
			}
			
		
			
			
			else {
				System.out.println("Healers have chosen someone to heal.");
				for(int e: players.keySet()) {
					Player d=players.get(e);
					if(d instanceof Healer) {
						chosen=d.play(players);
						break;
					}
					
				}
				//chosen = heal();
			}
			
			
			System.out.println("--End of Actions--");
			

			
			int die=0;
			
			
			
			if(n_healer>0) {
			if(chosen!=t.getID() && t.getHP()<=0) {
				
				n_rest--;
				
				if(t instanceof Detective) n_det--;
				if(t instanceof Healer) n_healer--;
				
				if(t.equals(User)) {
					userOut=1;
				}

				
				int x=t.getID();
				players.remove(x);
				playerA.remove(t);
				die++;
			}
			

			
			
			else if(t.getHP()<=0) {
				
				n_rest--;
				if(t instanceof Detective) n_det--;
				if(t instanceof Healer) n_healer--;
				if(t.equals(User)) {
					userOut=1;
				}

				int x=t.getID();
				players.remove(x);
				playerA.remove(t);
				die++;
			}
			}
			else 
				{if(t.getHP()<=0) 
				{
				n_rest--;
				if(t instanceof Detective) n_det--;
				if(t instanceof Healer) n_healer--;
				if(t.equals(User)) {
					userOut=1;
				}
//				if(t.getID()==User.getID()) {   ///use object .equals here
//					userOut=1;
//				}
				int x=t.getID();
				players.remove(x);
				playerA.remove(t);
				die++;}
				
			}
			

			if(die>0) {
				System.out.println(t.toString()+" has died.");
			}
			else {
				System.out.println("No one died.");
			}
			//System.out.println(test);
			
			if(n_mafias==n_rest ) {
//				flag++;
//				break;
				if(players.containsKey(test)) {
					if(players.get(test) instanceof Mafia) {
						
					}
				}
				else {
					flag++;
					break;
				}
			}
			
			if(n_det>=0) {
			
			if(players.containsKey(test)) {	
			if(players.get(test) instanceof Mafia ) {
				
				//System.out.println("hii");
				
				n_mafias--;
				
				
				//System.out.println(test);
				Player y=players.get(test);
				if(y.equals(User)) {
					userOut=1;
				}
//				if(y.getID()==User.getID()) {
//					userOut++;
//				}
				players.remove(test);
				playerA.remove(y);
				System.out.println(y.toString()+" has been voted out.");
			}
			else {
				if(userOut==0) {
					System.out.print("Select a person to vote out: ");
					int z=Reader.nextInt();
					z=check(z,players);}
					int pl=vote();
					Player y=players.get(pl);
//					if(y.getID()==User.getID()) {
//						userOut++;
//					}
					
					if(y.equals(User)) {
						userOut=1;
					}
					if(y instanceof Detective) n_det--;
					if(y instanceof Healer) n_healer--;
					players.remove(pl);
					playerA.remove(y);
					System.out.println(y.toString()+" has been voted out.");
					
					if(y instanceof Mafia) n_mafias--;
					else n_rest--;
				
			}
			}
			else {
				
				if(userOut==0) {
				System.out.print("Select a person to vote out: ");
				int z=Reader.nextInt();
				z=check(z,players);}
				int pl=vote();
				Player y=players.get(pl);
//				if(y.getID()==User.getID()) {
//					userOut++;
//				}
				
				if(y.equals(User)) {
					userOut=1;
				}
				if(y instanceof Detective) n_det--;
				if(y instanceof Healer) n_healer--;
				players.remove(pl);
				playerA.remove(y);
				System.out.println(y.toString()+" has been voted out.");
				
				if(y instanceof Mafia) n_mafias--;
				else n_rest--;
				
				
			}
			}
			else {
				
				if(userOut==0) {
				System.out.print("Select a person to vote out: ");
				int z=Reader.nextInt();
				z=check(z,players);}
				int pl=vote();
				Player y=players.get(pl);
//				if(y.getID()==User.getID()) {
//					userOut++;
//				}
				if(y.equals(User)) {
					userOut=1;
				}
				if(y instanceof Detective) n_det--;
				if(y instanceof Healer) n_healer--;
				players.remove(pl);
				playerA.remove(y);
				System.out.println(y.toString()+" has been voted out.");
				
				if(y instanceof Mafia) n_mafias--;
				else n_rest--;
				
			}
			round++;
			
			if(n_rest==n_mafias) {
				
				flag++;
				break;
			}
			
			System.out.println();
			
			
			
			
			
			
			
			
		}
		
		System.out.println();
		
		gameOver(flag);
		
	}
	
	public void playAsDetective(Player User)throws IOException {
		
		int round=1;
		
		int flag=0;
		
		int userOut=0; 
		
		
		
		while(n_rest!=n_mafias) {
			
			if(n_mafias==0) {
				break;
			}

			
			System.out.println("Round "+round+":");
			
			printRemaining();
			
			int target=500000;

			
			System.out.println("Mafias have chosen their target.");
			for(int e: players.keySet()) {
				Player d=players.get(e);
				if(d instanceof Mafia) {
					target=d.play(players);
					break;
				}
				
			}
			
			
			
			Player t=damages(target);
			

			
			int test=1000;
			
			if(n_det<=0 ) {
				
				System.out.println("Detectives have chosen a player to test");
				
			}
			
			else if(userOut==1) {
				for(int e: players.keySet()) {
					Player d=players.get(e);
					if(d instanceof Detective) {
						test=d.play(players);
						break;
					}
					
				}
				System.out.println("Detectives have chosen a player to test");
			}
			
			else {
			System.out.print("Choose a target to test: ");
			
			test=Reader.nextInt();
			
			test=check(test,players);

			
			while(players.get(test) instanceof Detective) {
			
		
				System.out.println("You cannot test a detective. Choose a player to test: ");
				test=Reader.nextInt();
				test=check(test,players);
			
			
			}
			}
			
			
			if(players.get(test) instanceof Mafia) System.out.println(players.get(test).toString()+" is a mafia.");
			else System.out.println(players.get(test).toString()+" is not a mafia.");

			
			int chosen=50000;
			
			if(n_healer<=0) {
				System.out.println("Healers have chosen someone to heal");
			}
			
			else {
				for(int e: players.keySet()) {
					Player d=players.get(e);
					if(d instanceof Healer) {
						chosen=d.play(players);
						break;
					}
					
				}
				//chosen=heal();
				System.out.println("Healers have chosen someone to heal");
			}
			
			
			
			System.out.println("--End of Actions--");

			int die=0;
			
			
			
			if(n_healer>0) {
			if(chosen!=t.getID() && t.getHP()<=0) {
				
				n_rest--;
				
				if(t instanceof Detective) n_det--;
				if(t instanceof Healer) n_healer--;
				if(t.equals(User)) {
					userOut=1;
				}
//				if(t.getID()==User.getID()) {   ///use object .equals here
//					userOut=1;
//				}
//				
				int x=t.getID();
				players.remove(x);
				playerA.remove(t);
				die++;
			}
			
			
			//System.out.println(t.getHP());
			
			
			else if(t.getHP()<=0) {
				
				n_rest--;
				if(t instanceof Detective) n_det--;
				if(t instanceof Healer) n_healer--;
//				if(t.getID()==User.getID()) {   ///use object .equals here
//					userOut=1;
//				}
				if(t.equals(User)) {
					userOut=1;
				}
				int x=t.getID();
				players.remove(x);
				playerA.remove(t);
				die++;
			}
			}
			else 
				{if(t.getHP()<=0) 
				{
				n_rest--;
				if(t instanceof Detective) n_det--;
				if(t instanceof Healer) n_healer--;
//				if(t.getID()==User.getID()) {   ///use object .equals here
//					userOut=1;
//				}
				if(t.equals(User)) {
					userOut=1;
				}
				int x=t.getID();
				players.remove(x);
				playerA.remove(t);
				die++;}
				
			}
			if(n_mafias==n_rest ) {
//				flag++;
//				break;
				if(players.containsKey(test)) {
					if(players.get(test) instanceof Mafia) {
						
					}
				}
				else {
					flag++;
					break;
				}
			}
//			
			if(die>0) {
				System.out.println(t.toString()+" has died.");
			}
			else {
				System.out.println("No one died.");
			}
			//System.out.println(test);
			
			if(n_det>=0) {
			if(players.containsKey(test)) {	
			if(players.get(test) instanceof Mafia ) {
				
				//System.out.println("hii");
				
				n_mafias--;
				
				
				//System.out.println(test);
				Player y=players.get(test);
//				if(y.getID()==User.getID()) {
//					userOut++;
//				}
				if(y.equals(User)) {
					userOut=1;
				}
				players.remove(test);
				playerA.remove(y);
				System.out.println(y.toString()+" has been voted out.");
			}
			else {
				if(userOut==0) {
					System.out.print("Select a person to vote out: ");
					int z=Reader.nextInt();
					z=check(z,players);}
					int pl=vote();
					Player y=players.get(pl);
//					if(y.getID()==User.getID()) {
//						userOut++;
//					}
					if(y.equals(User)) {
						userOut=1;
					}
					
					if(y instanceof Detective) n_det--;
					if(y instanceof Healer) n_healer--;
					players.remove(pl);
					playerA.remove(y);
					System.out.println(y.toString()+" has been voted out.");
					
					if(y instanceof Mafia) n_mafias--;
					else n_rest--;
				
			}
			}
			else {
				
				if(userOut==0) {
				System.out.print("Select a person to vote out: ");
				int z=Reader.nextInt();
				z=check(z,players);}
				int pl=vote();
				Player y=players.get(pl);
//				if(y.getID()==User.getID()) {
//					userOut++;
//				}
				if(y.equals(User)) {
					userOut=1;
				}
				
				if(y instanceof Detective) n_det--;
				if(y instanceof Healer) n_healer--;
				players.remove(pl);
				playerA.remove(y);
				System.out.println(y.toString()+" has been voted out.");
				
				if(y instanceof Mafia) n_mafias--;
				else n_rest--;
				
				
			}
			}
			else {
				
				if(userOut==0) {
				System.out.print("Select a person to vote out: ");
				int z=Reader.nextInt();
				z=check(z,players);}
				int pl=vote();
				Player y=players.get(pl);
//				if(y.getID()==User.getID()) {
//					userOut++;
//				}
				if(y.equals(User)) {
					userOut=1;
				}
				
				if(y instanceof Detective) n_det--;
				if(y instanceof Healer) n_healer--;
				players.remove(pl);
				playerA.remove(y);
				System.out.println(y.toString()+" has been voted out.");
				
				if(y instanceof Mafia) n_mafias--;
				else n_rest--;
				
			}
			round++;
			
			if(n_rest==n_mafias) {
				flag++;
			}
			
			System.out.println();
			
			
			
			
			
			
			
			
		}
		
		System.out.println();
		gameOver(flag);
		
	}
	
public void playAsHealer(Player User)throws IOException {
		
		int round=1;
		
		int flag=0;
		
		int userOut=0; 
		
		
		
		while(n_rest!=n_mafias) {
			
			//System.out.println(n_rest+" "+n_mafias);
			
			if(n_mafias==0) {
				break;
			}
			
			//System.out.println(n_mafias+" "+n_rest);
			
			System.out.println("Round "+round+":");
			
			printRemaining();
			
			int target=500000;
			
			
			
			
			
			System.out.println("Mafias have chosen their target.");
			for(int e: players.keySet()) {
				Player d=players.get(e);
				if(d instanceof Mafia) {
					target=d.play(players);
					break;
				}
				
			}
			
			
			
			Player t=damages(target);
			
//			System.out.println("                    "+t.getID() );

			
			int test=100000;
			
			
			
			if(n_det<=0 ) {
				
				System.out.println("Detectives have chosen a player to test");
				
				
				
			}
			else{
				for(int e: players.keySet()) {
					Player d=players.get(e);
					if(d instanceof Detective) {
						test=d.play(players);
						break;
					}
					
				}
				//test=test();
				System.out.println("Detectives have chosen a player to test");
			}

			
			int chosen=10000;
			if(n_healer<=0) {
				System.out.println("Healers have chosen someone to heal.");
			}
			
			else if(userOut==1) {
				for(int e: players.keySet()) {
					Player d=players.get(e);
					if(d instanceof Healer) {
						chosen=d.play(players);
						break;
					}
					
				}
				//chosen=heal();
				System.out.println("Healers have chosen someone to heal");
				
			}
			
			
			else {
			System.out.println("Choose a player to heal:");
			
			chosen=Reader.nextInt();
			
			chosen=check(chosen,players);
			
			
			players.get(chosen).setHP(500);
			}
			
			
			
			
			
			
			System.out.println("--End of Actions--");
			
			int die=0;
			
			
			
			if(n_healer>0) {
			if(chosen!=t.getID() && t.getHP()<=0) {
				
				n_rest--;
				
				if(t instanceof Detective) n_det--;
				if(t instanceof Healer) n_healer--;

				if(t.equals(User)) {
					userOut=1;
				}
				
				int x=t.getID();
				players.remove(x);
				playerA.remove(t);
				die++;
			}

			else if(t.getHP()<=0) {
				
				n_rest--;
				if(t instanceof Detective) n_det--;
				if(t instanceof Healer) n_healer--;

				if(t.equals(User)) {
					userOut=1;
				}
				int x=t.getID();
				players.remove(x);
				playerA.remove(t);
				die++;
			}
			}
			else 
				{if(t.getHP()<=0) 
				{
				n_rest--;
				if(t instanceof Detective) n_det--;
				if(t instanceof Healer) n_healer--;

				if(t.equals(User)) {
					userOut=1;
				}
				int x=t.getID();
				players.remove(x);
				playerA.remove(t);
				die++;}
				
			}
			
			if(n_mafias==n_rest ) {
//				flag++;
//				break;
				if(players.containsKey(test)) {
					if(players.get(test) instanceof Mafia) {
						
					}
				}
				else {
					flag++;
					break;
				}
			}
			
			if(die>0) {
				System.out.println(t.toString()+" has died.");
			}
			else {
				System.out.println("No one died.");
			}
			//System.out.println(test);
			
			
			
			if(n_det>=0) {
			if(players.containsKey(test)) {	
			if(players.get(test) instanceof Mafia ) {
				
				//System.out.println("hii");
				
				n_mafias--;
				
				
				//System.out.println(test);
				Player y=players.get(test);
//				if(y.getID()==User.getID()) {
//					userOut++;
//				}
				if(y.equals(User)) {
					userOut=1;
				}
				players.remove(test);
				playerA.remove(y);
				System.out.println(y.toString()+" has been voted out.");
			}
			else {
				if(userOut==0) {
					System.out.print("Select a person to vote out: ");
					int z=Reader.nextInt();
					z=check(z,players);}
					int pl=vote();
					Player y=players.get(pl);
//					if(y.getID()==User.getID()) {
//						userOut++;
//					}
					if(y.equals(User)) {
						userOut=1;
					}
					
					if(y instanceof Detective) n_det--;
					if(y instanceof Healer) n_healer--;
					players.remove(pl);
					playerA.remove(y);
					System.out.println(y.toString()+" has been voted out.");
					
					if(y instanceof Mafia) n_mafias--;
					else n_rest--;
			}
			}
			else {
				
				if(userOut==0) {
				System.out.print("Select a person to vote out: ");
				int z=Reader.nextInt();
				z=check(z,players);}
				int pl=vote();
				Player y=players.get(pl);
//				if(y.getID()==User.getID()) {
//					userOut++;
//				}
				if(y.equals(User)) {
					userOut=1;
				}
				
				if(y instanceof Detective) n_det--;
				if(y instanceof Healer) n_healer--;
				players.remove(pl);
				playerA.remove(y);
				System.out.println(y.toString()+" has been voted out.");
				
				if(y instanceof Mafia) n_mafias--;
				else n_rest--;
				
				
			}
			}
			else {
				if(userOut==0) {
				System.out.print("Select a person to vote out: ");
				int z=Reader.nextInt();
				z=check(z,players);}
				int pl=vote();
				Player y=players.get(pl);
//				if(y.getID()==User.getID()) {
//					userOut++;
//				}
				
				if(y.equals(User)) {
					userOut=1;
				}
				if(y instanceof Detective) n_det--;
				if(y instanceof Healer) n_healer--;
				players.remove(pl);
				playerA.remove(y);
				System.out.println(y.toString()+" has been voted out.");
				
				if(y instanceof Mafia) n_mafias--;
				else n_rest--;
				
			}
			round++;
			
			if(n_rest==n_mafias) {
				flag++;
			}
			
			System.out.println();
			
			
			
			
			
			
			
			
		}
		
		System.out.println();
		gameOver(flag);
		
	}
	
public void playAsCommoner(Player User)throws IOException {
	
	int round=1;
	
	int flag=0;
	
	int userOut=0; 
	
	
	
	while(n_rest!=n_mafias) {
		
		
		if(n_mafias==0) {
			break;
		}

		System.out.println("Round "+round+":");
		
		printRemaining();
		
		int target=50000;
		
		
		
		
		
		System.out.println("Mafias have chosen their target.");
		for(int e: players.keySet()) {
			Player d=players.get(e);
			if(d instanceof Mafia) {
				target=d.play(players);
				break;
			}
			
		}
		
		
		
		Player t=damages(target);
		
//		System.out.println("                    "+t.getID() );

		
		int test=100000;
		
		
		
		if(n_det<=0 ) {
			
			System.out.println("Detectives have chosen a player to test");
			
			
			
		}
		else{
			for(int e: players.keySet()) {
				Player d=players.get(e);
				if(d instanceof Detective) {
					test=d.play(players);
					break;
				}
				
			}
			//test=test();
			System.out.println("Detectives have chosen a player to test");
		}
		
		

		
		int chosen=10000;
		if(n_healer<=0) {
			System.out.println("Healers have chosen someone to heal.");
		}
		
	
		
		
		else {
			System.out.println("Healers have chosen someone to heal.");
			for(int e: players.keySet()) {
				Player d=players.get(e);
				if(d instanceof Healer) {
					chosen=d.play(players);
					break;
				}
				
			}
			//chosen = heal();
		}
		
		
		
		System.out.println("--End of Actions--");
		
		int die=0;
		
		
		
		if(n_healer>0) {
		if(chosen!=t.getID() && t.getHP()<=0) {
			
			n_rest--;
			
			if(t instanceof Detective) n_det--;

			if(t.equals(User)) {
				userOut=1;
			}
			
			int x=t.getID();
			players.remove(x);
			playerA.remove(t);
			die++;
		}
		

		
		
		else if(t.getHP()<=0) {
			
			n_rest--;
			if(t instanceof Detective) n_det--;
			if(t instanceof Healer) n_healer--;

			if(t.equals(User)) {
				userOut=1;
			}
			int x=t.getID();
			players.remove(x);
			playerA.remove(t);
			die++;
		}
		}
		else 
			{
			if(t.getHP()<=0) {
			
			n_rest--;
			if(t instanceof Detective) n_det--;
			if(t instanceof Healer) n_healer--;
//			if(t.getID()==User.getID()) {   ///use object .equals here
//				userOut=1;
//			}
			if(t.equals(User)) {
				userOut=1;
			}
			int x=t.getID();
			players.remove(x);
			playerA.remove(t);
			die++;}
			
		}
		if(n_mafias==n_rest ) {
//			flag++;
//			break;
			if(players.containsKey(test)) {
				if(players.get(test) instanceof Mafia) {
					
				}
			}
			else {
				flag++;
				break;
			}
		}
		if(die>0) {
			System.out.println(t.toString()+" has died.");
		}
		else {
			System.out.println("No one died.");
		}
		//System.out.println(test);
		
		if(n_det>=0) {
		
		if(players.containsKey(test)) {	
		if(players.get(test) instanceof Mafia ) {
			
			//System.out.println("hii");
			
			n_mafias--;
			
			
			//System.out.println(test);
			Player y=players.get(test);
//			if(y.getID()==User.getID()) {
//				userOut++;
//			}
			if(y.equals(User)) {
				userOut=1;
			}
			players.remove(test);
			playerA.remove(y);
			System.out.println(y.toString()+" has been voted out.");
		}
		else {
			if(userOut==0) {
				System.out.print("Select a person to vote out: ");
				int z=Reader.nextInt();
				z=check(z,players);}
				int pl=vote();
				Player y=players.get(pl);
//				if(y.getID()==User.getID()) {
//					userOut++;
//				}
				if(y.equals(User)) {
					userOut=1;
				}
				
				if(y instanceof Detective) n_det--;
				if(y instanceof Healer) n_healer--;
				players.remove(pl);
				playerA.remove(y);
				System.out.println(y.toString()+" has been voted out.");
				
				if(y instanceof Mafia) n_mafias--;
				else n_rest--;
		}
		}
		else {
			
			if(userOut==0) {
			System.out.print("Select a person to vote out: ");
			int z=Reader.nextInt();
			z=check(z,players);}
			int pl=vote();
			Player y=players.get(pl);
//			if(y.getID()==User.getID()) {
//				userOut++;
//			}
			if(y.equals(User)) {
				userOut=1;
			}
			
			if(y instanceof Detective) n_det--;
			if(y instanceof Healer) n_healer--;
			players.remove(pl);
			playerA.remove(y);
			System.out.println(y.toString()+" has been voted out.");
			
			if(y instanceof Mafia) n_mafias--;
			else n_rest--;
			
			
		}
		}
		else {
			if(userOut==0) {
			System.out.print("Select a person to vote out: ");
			int z=Reader.nextInt();
			z=check(z,players);}
			int pl=vote();
			Player y=players.get(pl);

			if(y.equals(User)) {
				userOut=1;
			}
			if(y instanceof Detective) n_det--;
			if(y instanceof Healer) n_healer--;
			players.remove(pl);
			playerA.remove(y);
			System.out.println(y.toString()+" has been voted out.");
			
			if(y instanceof Mafia) n_mafias--;
			else n_rest--;
			
		}
		round++;
		
		if(n_rest==n_mafias) {
			flag++;
		}
		
		System.out.println();

	}
	
	System.out.println();
	gameOver(flag);
	
}
	
	
	
}


public class Game{
	
	
	
	public static void main(String[] args) throws IOException {
		
		Reader.init(System.in);
		
		System.out.println("Welcome to Mafia");
		System.out.print("Enter Number of players: ");
		
		Reader.init(System.in);
		int n=Reader.nextInt();
		
		while(n<6) {
			System.out.println("Number of player must be atleast 6.");
			System.out.print("Enter no. of players: ");
			n=Reader.nextInt();
		}
		System.out.println();

		 ArrayList<Player> playerA=new ArrayList<Player>();
		 
		 Team<Mafia> mafias=new Team<Mafia>();
		 Team<Detective> detectives=new Team<Detective>();
		 Team<Healer> healers=new Team<Healer>();
		 Team<Commoner> commoners=new Team<Commoner>();
		 
		 HashMap<Integer,Player> players=new HashMap<Integer,Player>();
		 
		 ArrayList<Integer> r=new ArrayList<Integer>();
		 for(int i=1;i<=n;i++) {
			 r.add(i);
		 }
		 
		 Collections.shuffle(r);;
		 
		int i=0;
		
		for(int j=0;j<n/5;j++) {
			
			Mafia m=new Mafia(2500,r.get(i));
			players.put(r.get(i), m);
			
			i++;
		
			mafias.add(m);
			
			
			
			playerA.add(m);
			
			//System.out.println(m.toString());
		
		}
		
		//System.out.println();
		
		for(int j=0;j<n/5;j++) {
			Detective d=new Detective(800,r.get(i));
			players.put(r.get(i), d);
			i++;
			detectives.add(d);
		
			playerA.add(d);
			//System.out.println(d.toString());
			
		}
		
		//System.out.println();
		
		for(int j=0;j<(int)Math.max(1, n/10);j++) {
			Healer h=new Healer(800,r.get(i));
			players.put(r.get(i), h);
			i++;
			healers.add(h);
			
			playerA.add(h);
		//System.out.println(h.toString());
		}
		
		//System.out.println();
		
		int x=n-n/5-n/5-(int)Math.max(1, n/10);
		
		for(int j=0;j<x;j++) {
			
			Commoner c=new Commoner(1000,r.get(i));
			players.put(r.get(i), c);
			i++;
			commoners.add(c);
			
			playerA.add(c);
			//System.out.println(c.toString());
		}
		
		System.out.println();
		PlayGame game=new PlayGame(mafias,detectives,commoners,healers,players,playerA);
		
		System.out.println("Choose a Character");
		System.out.println("1) Mafia");
		System.out.println("2) Detective");
		System.out.println("3) Healer");
		System.out.println("4) Commoner");
		System.out.println("5) Assign Randomly");
		
		int p=Reader.nextInt();
		
	
		
		if(p==1) {   
			
			Player User=mafias.get(0);
			mafias.preamble(User, mafias);
			game.playAsMafia(User);
			
		}
		
		else if(p==2) {
		
			Player User=detectives.get(0);
			detectives.preamble(User,detectives);
			game.playAsDetective(User);
			
		}
		
		else if(p==3) {
			
			Player User=healers.get(0);
			healers.preamble(User,healers);
			game.playAsHealer(User);
			
		}
		else if(p==4) {
			
			Player User=commoners.get(0);
			commoners.preamble(User,commoners);
			game.playAsCommoner(User);
			
		}
		
		else if(p==5) {
			Random ran=new Random();
			
			int f=ran.nextInt(4-1+1)+1;
			
			if(f==1) {
				Player User=mafias.get(0);
				mafias.preamble(User, mafias);
				game.playAsMafia(User);
			}
			else if(f==2) {
				Player User=detectives.get(0);
				detectives.preamble(User,detectives);
				game.playAsDetective(User);
				
			}
			else if(f==3) {
				Player User=healers.get(0);
				healers.preamble(User,healers);
				game.playAsHealer(User);
				
			}
			else if(f==4) {
				Player User=commoners.get(0);
				commoners.preamble(User,commoners);
				game.playAsCommoner(User);
				
			}
			
			
			
		}

		
	
	}
}
