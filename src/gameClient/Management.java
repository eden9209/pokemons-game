package gameClient;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import gameClient.util.StdDraw;
import Server.Game_Server_Ex2;
import api.game_service;

/* 
 *This class show menu option for the user
 *Requirements:
 *The user must to press yes if he want to play
 *The user must to enter his id
 *The user need  pickScenario from 0-23 !!
  
 * If the user fill all the Requirements ,passing to Automat class with all his Parameters
 */


public class Management {
	
	private myGui mgg;
	private game_service game;

	public Management() {
		
		mgg = new myGui();
		game = mgg.game;
		
		ManagementGame();
	}
	
	/**
	 * menu option
	 */
	public void ManagementGame() {
		int w = YesOrNo(); //0 for no , 1 for yes
		if(w == 0) {
			System.out.println("Exiting game");
			System.exit(-2);
		}
		else if(w == -1) {
			return;
		}
		int id = pickID();
		StdDraw.id = id;
	
		int s = -1;
		while(s == -1) {
			s = pickScenario();
			if(s == -1)
				JOptionPane.showMessageDialog(null, "choose a valid scenario");
			else if (s == -2)
			{
				System.out.println("Exiting game");
				System.exit(-2);
			}
			
		}
	
		 {
			auto(s, id);
		}
	}
	
	
	/**
	 * run auto game
	 * @param s
	 */
	private void auto(int s, int id) {
		new Automat(s,game,mgg, id);
	}
	
	
	
	/**
	 * jframe of the option on the screen
	 * @return
	 */
	
		private int YesOrNo() {
		Object[] options = {"no",
		"yes"};
		int n = JOptionPane.showOptionDialog(null,
				"Are you sure , you want to play??",
				" this is a automatic game",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,     //do not use a custom Icon
				options,  //the titles of buttons
				options[0]); //default button title
		return n;
	}
	
		/**
		 * let the user pick scenario
		 * @return
		 */
		private int pickScenario() {
			JTextField SPDestField = new JTextField(5);
			JPanel SPEdgePanel = new JPanel();

			SPEdgePanel.add(new JLabel("scenario:"));
			SPEdgePanel.add(SPDestField);

			int SPEdgeRes = JOptionPane.showConfirmDialog(null, SPEdgePanel, 
					"Pick scenario (0 - 23)", JOptionPane.OK_CANCEL_OPTION);
			if (SPEdgeRes == JOptionPane.OK_OPTION) {
				try {

					int sce = Integer.parseInt(SPDestField.getText());
					if((sce <= 23 && sce >= 0) || sce == -1 || sce == -31) {
						return sce;
					}
					else return -1;

				}catch(Exception err) {
					return -1;
				}
			}
			return -2;	 //error happened or choose to cancel the game
		}
		
		
		private int pickID() {
			JTextField SPDestField = new JTextField(5);
			JPanel SPEdgePanel = new JPanel();

			SPEdgePanel.add(new JLabel("ID:"));
			SPEdgePanel.add(SPDestField);

			int SPEdgeRes = JOptionPane.showConfirmDialog(null, SPEdgePanel, 
					"What is your ID?", JOptionPane.OK_CANCEL_OPTION);
			if (SPEdgeRes == JOptionPane.OK_OPTION) {
				try {

					int id = Integer.parseInt(SPDestField.getText());
					if(true)
					{
						return id;
					}
					else return -1;

				}
				catch(Exception err) {
					return -1;
				}
			}
			return -2;	 //error happened or choose to cancel the game
		}

	
	

}
