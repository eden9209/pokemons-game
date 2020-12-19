package gameClient;

import gameClient.CL_Pokemon;
import gameClient.CL_Agent;
import api.edge_data;
import api.game_service;
import api.directed_weighted_graph;
import api.node_data;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.json.JSONException;
import org.json.JSONObject;
import Server.Game_Server_Ex2;
import api.DWGraph_Algo;
import api.DWGraph_DS;
import api.Node;
import gameClient.util.Point3D;
import gameClient.util.StdDraw;
import api.game_service;

/*
* This class draw graphs using stdDraw
*/ 
public class myGui {
	
public DWGraph_Algo ga;
public HashMap<Integer, CL_Agent> agents;
public HashMap<Point3D, CL_Pokemon> pokemons;
public game_service game;	


final double xMax = 35.216;
final double xMin = 35.1835;
final double yMax = 32.11;
final double yMin = 32.1;


/*
* Default constructor
*/
public myGui() {
	ga = new DWGraph_Algo();
	this.agents = new HashMap<>();
	this.pokemons = new HashMap<>();
}

/*
* Copy constructor using the init function from Graph_Algo class
*/

public myGui(directed_weighted_graph g) {

	this.ga = new DWGraph_Algo();
	ga.init(g);
}	
	
/*
 * Add a node to the drawing using addNode function from DGraph
 */
public void addNode(Node a) {
  ga.dg.addNode(a);
}	

/*
* Draw the nodes
* @param x = the x of the node location (point) 
* @param y = the y of the node location (point)
* @param abs = the number of the node
*/
public void drawNodes() {
	try {
		Collection<node_data> n=ga.dg.getV();
		if(n != null && n.size() > 0) {
			for (node_data a:n) {
				double x=a.getLocation().x();
				double y=a.getLocation().y();
				StdDraw.setPenRadius(0.03);
				StdDraw.setPenColor(StdDraw.BLUE);//nodes in blue
				StdDraw.point(x,y);
				StdDraw.setPenColor(StdDraw.BLACK);
				String abs = a.getKey()+"";
				StdDraw.text(x,y,abs);
			}
		}

	}catch(Exception e) {
		System.err.println("No nodes to draw");
	}
}


/*
* Draw the edges
* @param allNodes = A collection of all the nodes
* @param allEdgesOfNode = A collection of all the edges that came out of the parameter's node
* @param Sx = the x of the source location
* @param Sy = the y of the source location
* @param Dx = the x of the destination location
* @param Dy = the y of the destination location
* @param arrowX = the x of the "arrow" point location
* @param arrowY = the y of the "arrow" point location
* @param te = the string of the weight number
*/
public void drawEdges() {
	try {
		Collection<node_data> allNodes = ga.dg.getV();
		if(allNodes != null && allNodes.size() > 0) {
			for(node_data n:allNodes) {
				Collection<edge_data> allEdgesOfNode = ga.dg.getE(n.getKey());
				if(allEdgesOfNode != null && allEdgesOfNode.size() > 0) {
					for(edge_data edges:allEdgesOfNode) {
						double Sx = ga.dg.getNode(edges.getSrc()).getLocation().x();
						double Sy = ga.dg.getNode(edges.getSrc()).getLocation().y();
						double Dx = ga.dg.getNode(edges.getDest()).getLocation().x();
						double Dy = ga.dg.getNode(edges.getDest()).getLocation().y();

						StdDraw.setPenRadius(0.005);
						StdDraw.setPenColor(StdDraw.ORANGE);//paint the line between the nodes in orange
						StdDraw.line(Sx,Sy,Dx,Dy);

						StdDraw.setPenRadius(0.01);
						StdDraw.setPenColor(StdDraw.RED);

						double arrowX= (Dx*8+Sx)/9;
						double arrowY= (Dy*8+Sy)/9;
						StdDraw.point(arrowX,arrowY);

						double tmp = edges.getWeight();
						String dou = String.format("%.4g%n", tmp);

						String te = dou+"";

						StdDraw.setPenRadius(0.1);
						StdDraw.setPenColor(Color.BLACK);

						double newX= (Dx*4+Sx)/5;
						double newY= (Dy*4+Sy)/5;

						StdDraw.text(newX, newY, te);
					}
				}
			}
		}

	}catch(Exception e) {
		System.err.println("No edges to Draw");
	}
}

/*
* This function open a window and calls to drawNode and drawEdge
*/
public void drawDGraph() {
	try {
		if(ga.dg.getV() != null) {
			StdDraw.setGui(this);
			setPageSize();
			drawEdges();
			drawNodes();
		}
	}catch(Exception e){
		System.err.println("Nothing to draw");
	}
}

/**
 * set the page size
 */
private void setPageSize() {
	final double fixScale = 0.0015;
	StdDraw.setCanvasSize(1200 , 600 );
	StdDraw.setXscale(xMin - fixScale, xMax + fixScale);
	StdDraw.setYscale(yMin - fixScale, yMax + fixScale);
}

/**
 * refresh > Draw graph
 */
public void refreshDraw() {
	StdDraw.clear();
	drawEdges();
	drawNodes();
}

/**
 * draw the pokemons in the HashMap
 * @param game
 */
public void draw_pokemons(game_service game) {

	Collection<CL_Pokemon> P_list = pokemons.values();
	for (CL_Pokemon P : P_list) {
		int type = P.getType();
		Point3D pos = new Point3D(P.getLocation());
		
		if(type == -1) {
			StdDraw.picture(pos.x(), pos.y(), "yellowPok.png", 0.0007, 0.0007);
		}else if(type == 1) {
			StdDraw.picture(pos.x(), pos.y(), "greenPok.jpg", 0.0007, 0.0007);
		}
	}
}

/**
 * draw the agents in the HashMap
 * @param game
 */
public void drawAgent(game_service game) {

	Collection<CL_Agent> A_list = agents.values();
	for (CL_Agent ag : A_list) {
		Point3D pos = new Point3D(ag.getLocation());
		String file = "redAgent.png";
		
			try {
			StdDraw.picture(pos.x(), pos.y(), file, 0.0007, 0.0007);
		}catch (Exception e) {
			StdDraw.circle(pos.x(), pos.y(), .0015 * 0.3);
		}

	}
}

/**
 * while the game is running, it shows the current score on the game window
 * @param game
 */
public void printScore(game_service game) {
	String results = game.toString();
	long t = game.timeToEnd();
	try {
		int scoreInt = 0;
		int movesInt = 0;
		JSONObject score = new JSONObject(results);
		JSONObject ttt = score.getJSONObject("GameServer");
		scoreInt = ttt.getInt("grade");
		movesInt = ttt.getInt("moves");
		String countDown = "Time: " + t / 1000;
		String scoreStr = "Score: " + scoreInt;
		String movesStr = "Moves: " + movesInt;
		double tmp1 = xMax - xMin;
		double tmp2 = yMax - yMin;

		StdDraw.setPenRadius(0.05);
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.text(xMin+tmp1 / 1.05 , yMin + tmp2 / 0.90, countDown);
		StdDraw.text(xMin+tmp1 / 1.05 , yMin + tmp2 / 0.95, movesStr);
		StdDraw.text(xMin+tmp1 / 1.05 , yMin + tmp2, scoreStr);
		
	}catch (Exception e) {
		System.err.println("Failed to print score");
	}
}

/**
 * update the pokemons and agents in the list 
 * @param game
 */

public void refreshElements(game_service game, directed_weighted_graph g) {
	initpokemons(game,g);
	initAgents(game,g);
}


/**
 * when game over, prints the final score
 * @param game
 */
public void displayFinalScore(game_service game){

	int scoreInt = 0;
	int movesInt = 0;
	try {
		String results = game.toString();
		System.out.println("Game Over: " + results);

		JSONObject score = new JSONObject(results);
		JSONObject ttt = score.getJSONObject("GameServer");
		scoreInt = ttt.getInt("grade");
		movesInt = ttt.getInt("moves");
		String endGame = "Youre score is: " + scoreInt + "\n"
				+ "Amount of moves:   " + movesInt	;

		JOptionPane.showMessageDialog(null, endGame);
	}
	catch (Exception e) {
		e.getMessage();
	}
}

/**
 * initialize the pokemons of the game
 * @param game
 */
public void initpokemons(game_service game, directed_weighted_graph g) {
	
	String f_list = game.getPokemons();
	ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(f_list);
	
	if(pokemons != null)
	{
		pokemons.clear();
	}
	else pokemons = new HashMap<>();

	for(int a = 0;a<cl_fs.size();a++) {
		
		cl_fs.get(a).init(g);		
		edge_data e = cl_fs.get(a).getEdgePok();
		if(e != null) {
			cl_fs.get(a).set_edge(cl_fs.get(a).getEdgePok());
			pokemons.put(cl_fs.get(a). getLocation(),cl_fs.get(a));
		}
		else
		{
			System.out.println("could not find edge to pokemon");
		}

	}
}

/**
 * initialize the agents of the game
 * @param game
 */
public void initAgents(game_service game, directed_weighted_graph gg) {
	
	String a_list =game.getAgents();
	
	List<CL_Agent>  cl_as =Arena.getAgents(a_list, gg);
	
	if(agents != null)
	{
		agents.clear();
	}
	else agents = new HashMap<>();
	for(int a = 0;a<cl_as.size();a++) {
		
		cl_as.get(a).init(gg);	
		
		agents.put(cl_as.get(a).getID(),cl_as.get(a));
		
	}
	
}
	

}
