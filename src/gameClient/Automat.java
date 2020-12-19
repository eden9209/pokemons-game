package gameClient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.json.JSONObject;
import Server.Game_Server_Ex2;
import api.DWGraph_Algo;
import api.DWGraph_DS;
import api.directed_weighted_graph;
import api.game_service;
import gameClient.CL_Pokemon;
import api.edge_data;
import api.node_data;
import gameClient.util.Point3D;
import gameClient.util.StdDraw;
import api.game_service;


public class Automat {
	
	game_service game;
	public DWGraph_Algo ga;
	myGui mgg;
	int scenario;
	private String log;
	private Thread thread;
	private static long dt = 111;
	private static double EPS = 0.00001;
	
public Automat(int s, game_service game, myGui mgg, int id) {
	
		this.game = game;
		this.mgg = mgg;
		ga = mgg.ga;
		game = gameAutoScenario(s);
		mgg.initpokemons(game,ga.dg);
		mgg.initAgents(game,ga.dg);
		runAutoScenario(game);
	}
	
/**
 * starting the auto game
 * @param game
 */	

public void runAutoScenario(game_service game) {
	game.startGame();

	Long tmpTime = game.timeToEnd();
	
	moveRobotsThread();

	while(game.isRunning()) {
		StdDraw.enableDoubleBuffering();
		mgg.refreshDraw();
		mgg.draw_pokemons(game);
		mgg.drawAgent(game);

		if (tmpTime - game.timeToEnd() > 103L) {
			tmpTime = game.timeToEnd();
		}

		moveRobotsAuto(game);
		mgg.refreshElements(game,this.ga.dg);
		mgg.printScore(game);

		StdDraw.show();
	}

		mgg.displayFinalScore(game);

}

public synchronized void moveRobotsThread(){ 
	thread = new Thread(new Runnable() {

		@Override
		public synchronized void run() {
			while(game.isRunning()){
				if(game.isRunning()){
					log = game.move();
				}
				try {
					
					Thread.sleep(dt);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			thread.interrupt();
		}
	});
	thread.start();
}

/**
 * this func moves the agents by graph algorithms to get much pokemons as possible
 * @param game
 */
public void moveRobotsAuto(game_service game) {

	if(log != null){
		
		ArrayList<CL_Agent> botsToMove = new ArrayList<CL_Agent>();
		ArrayList<CL_Pokemon> fruitsWithoutBots = new ArrayList<CL_Pokemon>();
		Collection<Integer> robots = mgg.agents.keySet();
		for (Integer integer : robots) {
			CL_Agent b = mgg.agents.get(integer);
			if(b.getTrack() == null){
				botsToMove.add(b);
			}
		}
		Collection<Point3D> fruitSet = mgg.pokemons.keySet();
		for (Point3D point3d : fruitSet) {
			CL_Pokemon currF = mgg.pokemons.get(point3d);
			if(!currF.isTaken());{
				fruitsWithoutBots.add(currF);
			}
		}

		while(!botsToMove.isEmpty() && !fruitsWithoutBots.isEmpty())
		{
			int srcIndex = 0;
			CL_Agent SrcFrom = null;
			CL_Pokemon DestTo = null;
			int destIndex = 0;
			double dist = Integer.MAX_VALUE;
			/*
			 * find for each agent where to move with shortestpathdist method 
			 */
			for(int i = 0; i < botsToMove.size(); i++){
				for(int j = 0; j < fruitsWithoutBots.size(); j++){
					double tmp = ga.shortestPathDist(botsToMove.get(i).getNodeKey(), fruitsWithoutBots.get(j).get_edge().getSrc()) + fruitsWithoutBots.get(j).get_edge().getWeight();
					if(tmp < dist){
						srcIndex = i;
						destIndex = j;
						SrcFrom = botsToMove.get(i);
						DestTo = fruitsWithoutBots.get(j);
						dist = tmp;
					}
				}
			}

			// locate each agent near the pok
			SrcFrom.setPokPos(DestTo.getLocation());
            
			//find the path of the agent to go to the pok with shortestPath
			
			List<node_data> path = ga.shortestPath(SrcFrom.getNodeKey(), DestTo.get_edge().getSrc());
			path.add(ga.dg.getNode(DestTo.get_edge().getDest()));
			path.remove(0);
			SrcFrom.setTrack(path);
			botsToMove.remove(srcIndex);
			DestTo.setTaken(true);
			fruitsWithoutBots.remove(destIndex);
		}

		for (Integer integer : robots) {
			CL_Agent b = mgg.agents.get(integer);
			if(b.getTrack() != null){
				if(b.getNode().getLocation().distance2D(b.getLocation()) <= EPS){// if the robot is on the node he  to be on

					List<node_data> path = b.getTrack();

					game.chooseNextEdge(b.getID(), path.get(0).getKey());

					b.setNode(path.get(0));
					path.remove(0);

					if(path.size() == 0){
						b.setTrack(null);
					}
				}
			}
		}
	}
}

/**
 * returns the relevant game server to the game
 * @param s is the scenario number
 * @return
 */
public game_service gameAutoScenario(int s) {
	scenario = s;
	game = Game_Server_Ex2.getServer(s); // you have [0,23] games
	game.login(205656119);
	String g = game.getGraph();
	//System.out.println(g);
	//System.out.println(ga);
	this.ga.dg.init(g);
	mgg.initpokemons(game,this.ga.dg);
	//mgg.initAgents(game,this.ga.dg);
	mgg.drawDGraph();

	String info = game.toString();
	JSONObject line;
	int rs = 0;
	try {
		line = new JSONObject(info);
		JSONObject ttt = line.getJSONObject("GameServer");
		rs = ttt.getInt("pokemons");
	}catch (Exception e) {
		e.printStackTrace();
	}

	int i = 0;
	Collection<CL_Pokemon> f = mgg.pokemons.values();
	
/*locate the agents near the first pokemon on the list */
	
	for (CL_Pokemon fruit : f) {
		if(i >= rs)break;
		if(!fruit.isTaken()) {
			edge_data e = fruit.get_edge();
			int minNode = Math.min(e.getDest(), e.getSrc());
			int maxNode = Math.max(e.getDest(), e.getSrc());

			if(fruit.getType() == -1) {
				game.addAgent(maxNode);
			}else {
				game.addAgent(minNode);
			}
			fruit.setTaken(true);
			i++;
		}
	}

	return game;
}





}
