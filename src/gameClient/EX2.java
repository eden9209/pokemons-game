package gameClient;
import Server.Game_Server_Ex2;
import api.directed_weighted_graph;
import api.edge_data;
import api.game_service;
import org.json.JSONException;
import org.json.JSONObject;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import gameClient.util.StdDraw;
import api.game_service;

/* Run the play from here !! 
 */


public class EX2 {
	static myGui mgg;

	public static void main(String[] a) {
		
		new Management(); 
		
		try {
		if(a[0]!= null && a[1]!=null)
		{
			int id=Integer.parseInt(a[0]);
			int s=Integer.parseInt(a[1]);
			mgg = new myGui();
			new Automat(s,null,mgg, id);

		}
		
		}
		
		 catch(Exception e)
		{
			System.out.println("you are dont use cmd command ");
		}
	}
}

