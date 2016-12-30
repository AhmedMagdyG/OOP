package controller;

public class GameModel {
	
	private static GameModel gameModel;
	
	private GameModel(){
		
	}

	public void updateData() {
		// TODO Auto-generated method stub
		
	}

	public static GameModel getInstance() {
		if(gameModel == null){
			gameModel = new GameModel();
		}
		return gameModel;
	}
}
