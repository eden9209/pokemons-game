package api;
import java.io.Serializable;
import java.util.*;
import java.io.Serializable;
import org.json.JSONArray;
import org.json.JSONObject;
import gameClient.util.Point3D;

/**
* Contains HashMap data structure for all the nodes in the graph.
* The key is the data of the node.
* The value is the node.
* example: 1,a 2,b  .. |data,Node
*/
public class DWGraph_DS implements directed_weighted_graph ,Serializable 
{
/*
*@param nodes = The HashMap of the graph
*@param mc = Keep track of the amount of changes (add or remove of node and edge)
*@param edge_size = Keep track of the amount of Edges 
*/	
public HashMap<Integer, node_data> nodes;
private int mc;
private int edge_size;	

/*
* Default constructor
*/
public DWGraph_DS()
{
this.nodes=new HashMap<>();
this.mc=0;
this.edge_size=0;
}

/*
* Copy constructor (deep copy) 
*/
public DWGraph_DS(DWGraph_DS other) {
	nodes = new HashMap<>();
	for(node_data n: other.nodes.values()) {
		
	this.nodes.put(n.getKey(), n);
	}
	
	this.edge_size = other.edge_size;
	this.mc = other.mc;
}


		
public node_data getNode(int key)
{
return nodes.get(key)	;
}

public edge_data getEdge(int src, int dest)
{
	if(nodes.containsKey(src) && nodes.containsKey(dest)) {
		Node s=(Node) nodes.get(src);
		if(s.getEdgesOf().get(dest)!=null)
			return s.getEdgesOf().get(dest);
	}
	//System.out.println("src ot dst doesnt exist");
	return null;
}
	

public void addNode(node_data n)
{
	
	if(!this.nodes.containsKey(n.getKey())) 
	{
	mc++;
	this.nodes.put(n.getKey(),n);
		
	}
	else 
	{
		return;
		//System.out.println("Node already in the graph");
	}
}

/*
* connect and edge between node1 and node2 , if they exist in the graph
* if the edge already exists , the method simply update the weight of the edge.
* 
*/
public void connect(int src, int dest, double w) {

	if(nodes.containsKey(src) && nodes.containsKey(dest) && (src != dest)) {
		Node n = (Node)nodes.get(src);
		if(!n.getEdgesOf().containsKey(dest)) {
			Node s=(Node) nodes.get(src);
			Edge e=new Edge(src,dest,w);
			s.addEdge(e);
			mc++;//adding an edge
			edge_size++;
		}
	}
	else {
		System.out.println("src or dst doesnt exist OR src equals to dest");
	}
}

public boolean hasEdge(int node1, int node2)
{
	Node s=(Node)this.getNode(node1);
    return s.hasNi(node2);
	
} 

/*
* Return all the nodes in the HashMap 
*/ 
public Collection<node_data> getV()
{
	return this.nodes.values();	
}


/*
 * Return all the node_info that come out from this vertex (using the HashMap of this vertex).
*/   
public Collection<node_data> getV(int node_id)
{
	Collection<node_data> list=new ArrayList<node_data>();
	
	 if(nodes.containsKey(node_id)) 
	 {
	 Node m=(Node)this.nodes.get(node_id);
	 if(!m.getEdgesOf().isEmpty() )
	 {
	 for(int key: m.getEdgesOf().keySet())
	 {
		node_data z=getNode( key) ;
	    list.add(z);
	    
	 }
	 
	 }
	 
	 }
	 
	 return list;	
}


public Collection<edge_data> getE(int node_id)
{
Collection<edge_data> list=new ArrayList<edge_data>();
	
	if(this.nodes.containsKey(node_id))
	{
		Node n=(Node) this.nodes.get(node_id);
		list.addAll(n.getEdgesOf().values());
	}
	return list;
}

/*
* To remove a node we need to find all edges that connect to it.
* So the fast way is to check which node is edge of the removeNode and after delete from their hash_map this node.
* After deleting every edge, we remove the node and update the counters.
*/
public node_data removeNode(int key)
{
	if(this.nodes.containsKey(key))
	{
		Node n=(Node)getNode(key);
		
		List <Integer> m=n.getNi_k();
		
		if(m!=null)
		{
        for(int i=0;i<m.size();i++)			
		{
        	int z=m.get(i);
        	
		    Node h=(Node)getNode(z);
		    h.removeNode(n);
			edge_size--;
		      mc++;
		}
        
		}
		
		n.getEdgesOf().clear();
		this.nodes.remove(key);
		return n;
		
		}
 
	   else {
	//	System.out.println("key doesn't exist");
		return null;
	}	
	
}

public edge_data removeEdge(int src, int dest)
{
	if(this.nodes.containsKey(src) && this.nodes.containsKey(dest))

{
		Node n1 = (Node) this.nodes.get(src);
		Node n2 = (Node) this.nodes.get(dest);

		if(n1.getEdgesOf().containsKey(dest))
		{
			edge_size--;
			mc++;
			n1.getEdgesOf().remove(dest);
			return n1.getEdgesOf().remove(dest);


		}
		if(n2.getEdgesOf().containsKey(src))
		{
			edge_size--;
			mc++;
			n2.getEdgesOf().remove(src);
			return n2.getEdgesOf().remove(src);

		}
}
	
	else {
		return null;
	}
	return null;
}


/*Getters and Setters
*/
public int nodeSize()
{
	return this.nodes.size();
}

public int edgeSize()
{
	return edge_size;
}
public int getMC()
{
	return mc;
}

/* read from A0-A6 (exist in data folder) the Json string and convert it to build a graph 
*/

public void init(String jsonSTR) {
    try {
        final JSONObject graph = new JSONObject(jsonSTR);
        final JSONArray nodes = graph.getJSONArray("Nodes");
        final JSONArray edges = graph.getJSONArray("Edges");
        for (int i = 0; i < nodes.length(); ++i) {
            final int id = nodes.getJSONObject(i).getInt("id");
            final String pos = nodes.getJSONObject(i).getString("pos");
            final Point3D p = new Point3D(pos);
            this.addNode(new Node(id, p));
        }
        for (int i = 0; i < edges.length(); ++i) {
            final int s = edges.getJSONObject(i).getInt("src");
            final int d = edges.getJSONObject(i).getInt("dest");
            final double w = edges.getJSONObject(i).getDouble("w");
            this.connect(s, d, w);
        }
    }
    catch (Exception e) {
        e.printStackTrace();
    }
}
}







	

