package api;
import java.util.*;

import gameClient.util.Point3D;
/*
 * The implementation of node_data interface.
 * Contains HashMap data structure for all the edges that start at this node.
 * The key is the data of the node of the destination.
 * The value is the edge between this node and the destination.
 * for example :
 *  b.key, a->b || c.key , b->c   
*/
public class Node implements node_data {
/*
* @param Ni = The HashMap of the vertex 
* @param data = The key number of this vertex
* @param _weight = weight of this vertex for diajstra algo
* @param  _p = The point where the vertex stands
* @param _info = The information of the vertex for Graph_Algo
* @param _tag = The tag of the vertex for Graph_Algo
*/	
private HashMap<Integer, Edge> Ni = new HashMap<>(); //b.key,a->b     c.key,b->c
private int data;
private Point3D p;
private double weight;
private String info;
private int tag;
	
private static int count = 0;	
/*
 * Default Constructor Initializes all  the parameters
 */
public Node() {
this.data=count;
count++;
this.Ni=new HashMap<>();
this.tag=0;
this.info="";
this.weight=0;
}
public Node(int k)
{
	this.data=k	;
	this.Ni=new HashMap<>();
	this.tag=0;
	this.info="";
	this.weight=0;
}

public Node(int key, Point3D point3d) {
	this.data = key;
	this.p = new Point3D(point3d.x(), point3d.y());
	weight = 0;
	info = "";
	tag = 0;
	
}


/*
 * Constructor with all the parameters
 */
public Node(int d,Point3D p, double w, String s, int t) {
	this.data = d;
	this.p = new Point3D(p);
	this.weight=w;
	this.info=s;
	this.tag=t;
}





public int getKey()
{
return this.data;	
}
public geo_location getLocation() 
{
	return p;
}
/*
* Change this point to the parameter's point
*/
public void setLocation(geo_location p)
{
	if(p!=null)
		this.p=new Point3D(p);
	else
		System.out.println("There is no location!!!");
}
/* 
* return the HashMap    
*/
public HashMap<Integer,Edge> getEdgesOf(){
	return Ni;
}

/* I get the unique key of the node that i want to remove
* I remove from the HashMap of my node the entry of the unique key of the remove node  
*/
public void removeNode(node_data node)
{
	int k=node.getKey();
	if(this.Ni.containsKey(k)) {
	this.Ni.remove(k);
	}
	
}
public boolean hasNi(int key)
{
  return this.Ni.containsKey(key);
	     
}

/*
* This function add a Edge to the HashMap
*/
public void addEdge(Edge e) {
if(this.getKey() == e.getSrc())
{
this.Ni.put(e.getDest(), e);
}
else
	return;
 // System.out.println("Wrong edge!!!");
}

/*
* return all the key of the edges of this node
* the keyset of Ni of this node : is the dest-key  of his edge
*/
public List<Integer> getNi_k()
{
	 if (this.Ni != null)
	 {
	
	List<Integer> list=new ArrayList<Integer>();
	
	for (Integer key : this.Ni.keySet()) 
	{
		list.add(key);
	}
	return list;
	
	}

	 else {
	return null;
	 }
	 
}



public double getWeight()
{
return weight;	
}

public void setWeight(double w)
{
this.weight=w;	
}

public String getInfo()
{
return this.info;	
}

public void setInfo(String s)
{
this.info=s;	
}

public int getTag()
{
	return this.tag;	
}

public void setTag(int t)
{
	this.tag=t;	
}
	
}
