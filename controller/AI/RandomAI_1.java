package AI;
import java.util.Random;
import model.Stadium;
import model.Player;

class RandomAI_1 extends PlayerType {
	Random randomgene;

	RandomAI_1(int numberEquip, Stadium stade) {
		super(numberEquip, stade);
		randomgene = new Random();
	}

	//return player number with ball
	public int ballNumber(){
		int number = -1;
		
		Player[] equip;
		if(equipNumber() == 0)
			equip = stadium.getSnowKids();
		else
			equip = stadium.getShadows();
		
		for(int check = 0; check != equip.length   &&   number == -1; check++){
			if(equip[check].getBallPossession())
				number = check;
		}
		
		return number;
	}

	//return true if playerOne can make pass at playerTwo
	public boolean canPass(Player playerOne, Player playerTwo){
		boolean can = false;
		
		if(stadium.pass(playerOne, playerTwo))
			can = stadium.pass(playerTwo, playerOne);
			
		return can;
	}
	
	//number of possibility pass
	public int passNumber(){
		int number = 0;
		
		Player[] equip;
		if(equipNumber() == 0)
			equip = stadium.getSnowKids();
		else
			equip = stadium.getShadows();
		
		int ball = ballNumber();
		
		for(int check = 0; check != equip.length; check++){
			if(check != ball   &&   canPass(equip[ball], equip[check]))
				number++;
		}
		
		return number;
	}
	
	//return true if player can move up
	public boolean canUp(Player player){
		boolean can = false;
		
		if(stadium.move(player,'U'))
			can = stadium.move(player,'D');
			
		return can;
	}

	//return true if player can move down	
	public boolean canDown(Player player){
		boolean can = false;
		
		if(stadium.move(player,'D'))
			can = stadium.move(player,'U');
			
		return can;
	}

	//return true if player can move left	
	public boolean canLeft(Player player){
		boolean can = false;
		
		if(stadium.move(player,'L'))
			can=stadium.move(player,'R');
		
		return can;
	}
	
	//return true if player can move right
	public boolean canRight(Player player){
		boolean can = false;
		
		if(stadium.move(player,'R'))
			can=stadium.move(player,'L');
		
		return can;
	}
	
	//return number of moves that can make player
	public int moveNumber(Player player){
		int number = 0;
		
		if(canUp(player))		number++;
		if(canDown(player))	number++;
		if(canLeft(player))		number++;
		if(canRight(player))	number++;
		
		return number;
	}
	
	//return true if player can move
	public boolean canMove(Player player){
		return moveNumber(player) != 0;
	}
	
	//return number of players can move
	public int movePlayerNumber(){
		int number = 0;
		
		Player[] equip;
		if(equipNumber() == 0)
			equip = stadium.getSnowKids();
		else
			equip = stadium.getShadows();
		
		int ball = ballNumber();
		
		for(int check = 0; check != equip.length; check++){
			if(check != ball   &&   canMove(equip[check]))
				number++;
		}
		
		return number;
	}

	@Override
	boolean timeOut() {
		int passes = 1; //number of passes remaining
		int moves = 2; //number of moves remaining
		int ball = ballNumber(); //number of player with ball
		int exMoveNumber = -1; //number of first move player
		int exMoveDirection = -1; //direction of first move player
		
		Player[] equip; //player list of this equip
		if(equipNumber() == 0)
			equip = stadium.getSnowKids();
		else
			equip = stadium.getShadows();

		int random1 = randomgene.nextInt(11); //choose: 0-end, 1..5-pass, 6..10-move
		int random2; //second random number
		
		while(random1 != 0   &&   (moves != 0  ||  passes != 0)){
			if(random1 < 6   &&   passes != 0   &&   passNumber() != 0){
				//pass
				//random player selection
				random1 = randomgene.nextInt(equip.length);
				
				while(random1 == ball   ||   !canPass(equip[ball],equip[random1]))
					random1 = (random1+1) % (equip.length); //not using random to haven't got infinity circle
				
				//act
				stadium.pass(equip[ball],equip[random1]);
				passes--;
				
			}else if(random1 > 5   &&   moves != 0   &&   movePlayerNumber() != 0){
				 //move
				 
				 if(exMoveNumber == -1   ||   movePlayerNumber() != 1   ||   ball == exMoveNumber   ||   moveNumber(equip[exMoveNumber]) != 1){
					//first move or second move and can move other of back
				 
					//random player selection
					random1 = randomgene.nextInt(equip.length);
					
					while(random1 == ball  ||   !canMove(equip[random1])   ||   (exMoveNumber != -1   &&   random1 == exMoveNumber   &&   moveNumber(equip[exMoveNumber]) == 1))
						random1 = (random1+1) % (equip.length); //not using random to haven't got infinity circle
					
					//random direction choose
					random2 = randomgene.nextInt(4);
					
					while((random2 == 0   &&   !canLeft(equip[random1]))   ||   (random2 == 1   &&   !canUp(equip[random1]))   ||   (random2 == 2   &&   !canRight(equip[random1]))   ||   (random2 == 3   &&   !canDown(equip[random1]))   ||   (random1 == exMoveNumber   &&   random2 == (exMoveDirection+2) % 4))
						random2= (random2+1) % 4; //not using random to haven't got infinity circle
					
					//act
					if(random2 == 0)
						stadium.move(equip[random1],'L');
					else if(random2==1)
						stadium.move(equip[random1],'U');
					else if(random2==2)
						stadium.move(equip[random1],'R');
					else
						stadium.move(equip[random1],'D');
				}
				
					moves--;
			}
						
			random1 = randomgene.nextInt(11); //nextAction
		}
		
		return true;
	}
	
	public static void main(String args[]){
		//tests
	}
}
