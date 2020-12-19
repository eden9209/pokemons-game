package api;

import static org.junit.jupiter.api.Assertions.*;
import gameClient.util.Point3D;
import org.junit.jupiter.api.Test;

class Nodetest {


public Node n;
		
	@Test
	void testAddEdge() {
		n=new Node(3,new Point3D(5,-5));
		Node n2=new Node(4,new Point3D(6,-6));
		Edge e=new Edge(3,4,1.5);
		Edge e2=new Edge(5,4,2);//there is no node 5
		n.addEdge(e);
		n.addEdge(e2);
		
		assertEquals(n.getKey(),e.getSrc());
		assertEquals(n2.getKey(),e.getDest());
		assertEquals(n.getEdgesOf().containsKey(5),false);
	}

	@Test
	void testGetKey() {
		Node[] nodes=new Node[10];
		for(int i=0;i<10;i++) {
			nodes[i]=new Node(i,new Point3D(i,i));
		}
		for(int i=0;i<10;i++) {
			assertEquals(nodes[i].getKey(),i);
		}
	}
	
	@Test
	void testGetLocation() {
		Node[] nodes=new Node[10];
		for(int i=0;i<10;i++) {
			nodes[i]=new Node(i,new Point3D(2*i,-2*i));
		}
		for(int i=0;i<10;i++) {
			assertEquals(nodes[i].getLocation(),new Point3D(2*i,-2*i));
		}
	}
	
	@Test
	void testSetLocation() {
		Node[] nodes=new Node[10];
		for(int i=0;i<10;i++) {
			nodes[i]=new Node(i,new Point3D(2*i,-2*i));
			nodes[i].setLocation(new Point3D(i+1,i*8)); 
		}
		for(int i=0;i<10;i++) {
			assertNotEquals(nodes[i].getLocation(),new Point3D(2*i,-2*i));
			assertEquals(nodes[i].getLocation(),new Point3D(i+1,8*i));
		}	
	}
	
		
	@Test
	void testSetGetTag() {
		Node[] nodes=new Node[100];
		for (int i = 0; i < nodes.length; i++) {
			nodes[i]=new Node();
			nodes[i].setTag(i);
		}
		for (int i = 0; i < nodes.length; i++) {
			assertEquals(nodes[i].getTag(),i);
		}		
	}
	
	@Test
	void testGetEdgesOf() {
		Node[] nodes=new Node[100];
		n=new Node(0,new Point3D(0,0));
		nodes[0]=n;
		for (int i = 1; i < nodes.length; i++) {
			nodes[i]=new Node(i,new Point3D(i*5,i-2));
			Edge e=new Edge(0,i,2.5);
			nodes[0].addEdge(e);
		}		
		assertEquals(n.getEdgesOf().isEmpty(),false);
		assertEquals(n.getEdgesOf().size(),99);

	}
	

}
