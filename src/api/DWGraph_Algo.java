package api;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DWGraph_Algo implements dw_graph_algorithms
{
public DWGraph_DS dg;
	
/*
* Default constructor
*/
public DWGraph_Algo() {
this.dg = new DWGraph_DS();
	}

public DWGraph_Algo(directed_weighted_graph _graph)
{
		this.dg = new DWGraph_DS();
		init(_graph);
}
	
			
 public void init(directed_weighted_graph g)
 {
	 this.dg = (DWGraph_DS) g;
	 
 }
 
 
 public directed_weighted_graph getGraph()
 {
	 return dg; 
	 
 }

 public directed_weighted_graph copy()
 {
	 DWGraph_DS m = new DWGraph_DS();
	 Collection<node_data> node = this.dg.getV();
	 
	 Iterator<node_data> it = node.iterator();
	 Iterator<node_data> it2 = node.iterator();
	 
	 while (it2.hasNext())
	 {  Node n = new Node();
	   n = (Node) it2.next();
	   m.addNode(n);
	  }
	     	
	  while (it.hasNext()) {
	  Node n = new Node();
	  n = (Node) it.next();
	    		
	  Collection<edge_data> edge = this.dg.getE(n.getKey());
	  Iterator<edge_data> ite = edge.iterator();
	     		
	  while (ite.hasNext()) 
	  {
	   Edge ed =  (Edge) ite.next();
	     	
	    m.connect(n.getKey(), ed.getDest(),ed.getWeight());
	   }
	  
	   }
	    return m;
	 
 }
 
 /*BFS algorithm:
  * 1 step: if numbers of node in the graph is 0 or 1 the graph is considered to be connected ! 
  * 2 step: insert the first node to queue1 and after check his Neighbors ! 
  * 3 step: if we visit in node with (tag=0) we are increment the size_bfs and set node.tag to 1
  * 4 step: if dg.nodeSize()==size_bfs the graph is isConnected because we visit all the nodes
  * 5 step : in the end we Initialize all tags to zero for the next use of the BFS
 */         
 public boolean isConnected() {
	 if(this.dg.getV().size()==0)
	        return true;
	     	if(this.dg.getV().size() == 1 ) 
	    		return true;
	    	
	    	int size=this.dg.nodeSize();
	    	int size_bfs=0;
	    	
	    	Collection<node_data> node=this.dg.getV();
	    	Iterator<node_data> it=node.iterator();

	       LinkedList<node_data> queue1 = new LinkedList<node_data>(); 
	       node_data m=it.next();
	        queue1.add(m);
	        
	          while (!queue1.isEmpty())
	          { 
	        	
	        	  node_data u = queue1.remove(); 
	            Collection<node_data> edge=this.dg.getV(u.getKey());
	            Iterator<node_data> etr=edge.iterator();
	            
	            while(etr.hasNext()) 
	            {
	            	node_data n=etr.next(); 
	            
	            if(n.getTag()==0)
	            {
	            	size_bfs++;
	            	n.setTag(1);;
	                queue1.add(n);
	                                   
	           } 
	                      
	       }
	            
	          }
	          
	      	this.zeroTagsMaxWeight();
	         if(this.dg.nodeSize()==size_bfs)
	         return true;
	          else 
	         return false;
	        
	}
 
 
 
 private void zeroTagsMaxWeight()
 {
 		Collection<node_data> nodes = dg.getV();
 		
 		for (node_data node : nodes) {
 			Node n=(Node)node;
 			n.setTag(0);
 			n.setWeight(Integer.MAX_VALUE);
 			n.setInfo("");
 		}
 }
 
 /*
 * 
 * Diakstra algorithm:
 * note : this algorithm tell us the shortest path from ONE node to every other node
 * 
  * 1 step: use zeroTagsMaxWeight() -> all the tags of the nodes is 0 and their weight = max value 
  * 2 step: put the first node (src) to the ArrayList and set is weight to 0 !
  * 3 step: while the queue in not empty :
  * 4 step: remove the currNode from the queue and set his tag to 1 to not visit him again 
  * 5 step : examine the edge of currNode
  *  
  *  z - the currNode
  *  h - the destNode
  *  dstNodeW - the weight of h. 
  *  edge_dataW - the weight of the edge
  *  
  *  if(dstNodeW > z.getWeight() + edge_dataW)
  *  we update h Weight to the sum of: z.getWeight()+edge_data.get_weight (shortest path)
  *  
  *  now with the help of the getIndex we visit the vertex with the smallest known
  *  distance from the start vertex
  *  
 */
 private void diaksta(int src)
 {
     	  zeroTagsMaxWeight();
     	  ArrayList<node_data> nodes = new ArrayList<node_data>();
     	  nodes.add(dg.getNode(src));
     	  Node n=(Node)nodes.get(0);
     			  n.setWeight(0);
     			
     			  
     		while(!nodes.isEmpty())
     		{
     		node_data currNode = nodes.get(0);
     		Node z=(Node)currNode;
     		if(currNode.getTag() == 0)
     	    {
     		currNode.setTag(1);
     		nodes.remove(0);
     		Collection<edge_data> edges = dg.getE(currNode.getKey());
     	 for (edge_data edge_data : edges)
     	 {
     		node_data destNode = dg.getNode(edge_data.getDest());
     		Node h=(Node)destNode;			
     		double dstNodeW = h.getWeight();
     		double edge_dataW = edge_data.getWeight();
     		
     		if(dstNodeW > z.getWeight() + edge_dataW)
     		{
     		h.setWeight(z.getWeight()+edge_data.getWeight());
     		///for the : shortestPath function
     		destNode.setInfo(currNode.getKey() + "");
     		
     		if(destNode.getTag() == 0)
     		{ 
     			nodes.add(getIndex(nodes, h.getWeight()),destNode);
     		
     		}
     		
     		}
     	}   
     				
     	}
     			else
     			{
     				nodes.remove(0);
     			}
     		
      }
     		
  }

 /*
  * get the insert position in sorted array
  */	
 private int getIndex(ArrayList<node_data> nodes,double dstN)
 {
 	int minIn = 0;
 	int maxIn = nodes.size() - 1;
 	int mid = minIn + (maxIn - minIn) / 2;

 	while(minIn <= maxIn){
 		if(((Node) nodes.get(mid)).getWeight() == dstN)
 		{
 			return mid;
 		}
 		if(dstN > ((Node) nodes.get(mid)).getWeight()){
 			minIn = mid + 1;
 		}
 		if(dstN < ((Node) nodes.get(mid)).getWeight()){
 			maxIn = mid - 1;
 		}
 		mid = minIn + (maxIn - minIn) / 2 ;
 	}
 	return mid;
 }
 
 
 
 
public double shortestPathDist(int src, int dest)
{
	diaksta(src);
	Node ans =(Node) dg.getNode(dest);
	return ans.getWeight();
}

public List<node_data> shortestPath(int src, int dest)
{
	diaksta(src);
	node_data node = dg.getNode(dest);
	if(((Node) node).getWeight() >= Integer.MAX_VALUE) {
		return null;
	}
	List<node_data> ans = new ArrayList<node_data>();
	while(!node.getInfo().isEmpty()){
		ans.add(0, node);
		node = dg.getNode(Integer.parseInt(node.getInfo()));
	}
	ans.add(0, node);
	return ans;
	
	
}
 public boolean save(String file)
{
	 try{
			FileOutputStream file1 = new FileOutputStream(file); 
			ObjectOutputStream out = new ObjectOutputStream(file1);
			out.writeObject(this.dg);
			out.close(); 
			file1.close(); 

			System.out.println("Object has been serialized");
			return true;
		}   
		catch(IOException ex){
			System.out.println(ex.getMessage()); 
			return false;
		}
	 
}

public boolean load(String file)
{
	try{    
		FileInputStream file1 = new FileInputStream(file); 
		ObjectInputStream in = new ObjectInputStream(file1);
		this.dg = (DWGraph_DS)in.readObject();
		in.close(); 
		file1.close(); 
		System.out.println("Object has been deserialized");
		return true;
	} 

	catch(IOException | ClassNotFoundException ex) 
	{ 
		System.out.println("IOException is caught"); 
		return false;
	} 
	
}
public boolean equals(Object o)
{
	if(this==o) return true;
	if(o==null|| getClass()!=o.getClass()) return false;
	
	DWGraph_DS that =(DWGraph_DS) o;
	
	for(node_data m:this.dg.getV())
	{
		if(!that.nodes.containsKey(m.getKey()))
		{
			return false;
		}
	}
	
	return true;
}






}
