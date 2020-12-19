package gameClient;
import api.DWGraph_DS;
import api.Edge;
import api.directed_weighted_graph;
import api.edge_data;
import api.node_data;
import gameClient.util.Point3D;

import org.json.JSONException;
import org.json.JSONObject;

public class CL_Pokemon {
	
	static final double EPS = Math.pow(10, -7);
	private edge_data _edge;
	private double _value;
	private int _type;
	private Point3D location;
	private directed_weighted_graph g;
	private double min_dist;
	private int min_ro;
	private boolean taken = false;

	/*
	  Constructors
	 */
	
	public CL_Pokemon() {
		this.g = null;
		_edge = null;
		this.location = null;
		_value = 0;
		_type= 0;
	}
	
	
	public CL_Pokemon(Point3D p, int t, double v, double s, edge_data e) {
		_type = t;
	//	_speed = s;
		_value = v;
		set_edge(e);
		this.location = p;
		min_dist = -1;
		min_ro = -1;
	}
	
	public CL_Pokemon(DWGraph_DS g) {
		this();
		this.g = g;
	}
	
	public void init(directed_weighted_graph g)
	{
		this.g = g;
	}
	
//	public static CL_Pokemon init_from_json(String json) {
//		CL_Pokemon ans = null;
//		try {
//			JSONObject p = new JSONObject(json);
//			int id = p.getInt("id");
//
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//		}
//		return ans;
//	}
	
	/**
	 * initializing pok from json string
	 * @param pok_json - the json string
	 */
	public void initJson (String pok_json) {
		try {
			JSONObject line = new JSONObject(pok_json);
			JSONObject ttt = line.getJSONObject("Fruit");
			int value = ttt.getInt("value");
			int type = ttt.getInt("type");
			String pos = ttt.getString("pos");
			Point3D posP = new Point3D(pos);
			_value = value;
			_type = type;
			this.location = posP;
			
		} 
		catch (JSONException e) {e.printStackTrace();}
	}
	
	
	
	
//	public String toString() {
//		return "F:{v="+_value+", t="+_type+"}";
//		}
	
public String toString() {
	return "F:{v="+_value+", t="+_type+" , pos="+ this.location+"}";
	}
	
	
/*
* @return the edge that the agents need to pass to eat the pokemons
*/
	public edge_data getEdgePok() {
		for(node_data ni: g.getV()) {
			for(node_data nj: g.getV()) {
				Point3D niP = (Point3D) ni.getLocation();
				Point3D njP = (Point3D) nj.getLocation();
				if(ni == nj) continue;
				double destFruitniP = this.location.distance2D(niP);
				double destFruitnjP = this.location.distance2D(njP);
				double destniPnjP = niP.distance2D(njP);
				if(Math.abs(destFruitniP + destFruitnjP - destniPnjP) <= EPS) {
					int niKey = ni.getKey();
					int njKey = nj.getKey();
					int nextMin = Math.min(niKey, njKey);
					int nextMax = Math.max(niKey, njKey);
					if(getType() == 1) {
						Edge e = (Edge) g.getEdge(nextMin, nextMax);
						if(e != null)return e;
					}
					else {
						edge_data e = g.getEdge(nextMax, nextMin);
						if(e != null)return e;
					}
				}
			}
		}
		return null;
	}
	
	
	
	
	public edge_data get_edge() {
		return _edge;
	}

	public void set_edge(edge_data _edge) {
		this._edge = _edge;
	}

	public Point3D getLocation() {
		return this.location;
	}
	public int getType() {return _type;}
//	public double getSpeed() {return _speed;}
	public double getValue() {return _value;}

	public double getMin_dist() {
		return min_dist;
	}

	public void setMin_dist(double mid_dist) {
		this.min_dist = mid_dist;
	}

	public int getMin_ro() {
		return min_ro;
	}

	public void setMin_ro(int min_ro) {
		this.min_ro = min_ro;
	}
	
	public boolean isTaken() {
		return taken;
	}
	
	public void setTaken(boolean taken) {
		this.taken = taken;
	}
}