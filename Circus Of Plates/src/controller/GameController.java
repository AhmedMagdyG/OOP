package controller;

public class GameController {

	private static GameController gameController;
	
	private GameController(){
		
	}
	
	public static GameController getInstance() {
		if(gameController == null){
			gameController = new GameController();
		}
		return gameController;
	}
	
	public void newGame(){
		System.out.println("NEW GAME CLICk");
	}

	public void save() {
		System.out.println("SAVE GAME");
	}

	public void quit() {
		// TODO Auto-generated method stub
		
	}

	public void pauseGame() {
		// TODO Auto-generated method stub
		
	}

	public void resumeGame() {
		// TODO Auto-generated method stub
		
	}

	public void load() {
		// TODO Auto-generated method stub
		
	}

	public void ChangeSettings() {
		// TODO Auto-generated method stub
		
	}

}
