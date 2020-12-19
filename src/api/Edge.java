package api;

/*
 * The implementation of edge_data interface.
 * regular class (no data structure).
 */
public class Edge implements edge_data {
/* 	
*@param src = The vertex that the edge came out of.
*@param dst = The vertex that the edge came in to. 
*@param weight = The weight of the Edge, For Graph_Algo	
*@param info = Edge's information	
*@param tag =  Edge's tag, For Graph_Algo	
*/	
private int src;
private int dst;
private double weight;
private String info;
private int tag;	
/*
 * Constructor with some parameters (not all)
 */
public Edge(int s,int d,double w) {
	this.src=s;  
	this.dst=d;
	this.weight=w;
	this.info="";
	this.tag=0;
}

/*
 * Copy constructor
 */
public Edge(Edge other) {
	this(other.src,other.dst,other.weight);
}




/*
 * Getters && Setters
 * 
 */
public int getSrc()
{
return this.src;	
}
public int getDest()
{
return this.dst;	
}

public double getWeight() 
{
return this.weight;	
}

public void setWeight(double w) {
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
public String toString()
{
	
return this.src + " --> " + this.dst;
	
}
	
}
