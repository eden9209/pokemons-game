package api;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import api.Edge;
import api.Node;
import api.DWGraph_DS;
import api.node_data;
import api.directed_weighted_graph;
import Server.Game_Server_Ex2;

import org.junit.jupiter.api.Test;

class WGraph_DSTest {

	  private static Random _rnd = null;

	    @Test
	    void nodeSize() {
	    	directed_weighted_graph g = new DWGraph_DS();
	    	node_data n0=new Node(0);
	    	node_data n1=new Node(1);
	    	node_data n2=new Node(1);

	        g.addNode(n0);
	        g.addNode(n1);
	        g.addNode(n2);

	        g.removeNode(2);
	        g.removeNode(1);
	        g.removeNode(1);
	        int s = g.nodeSize();
	        assertEquals(1,s);

	    }

	   
		@Test
		void initTest() {
			game_service game = Game_Server_Ex2.getServer(2); 
			String g = game.getGraph();
			DWGraph_DS gg = new DWGraph_DS();
			gg.init(g);
			assertEquals(gg.edgeSize(),22);
			assertEquals(gg.nodeSize(),11);
		}
	    
	    
	    @Test
	    void getV() {
	    	directed_weighted_graph g = new DWGraph_DS();
	    	node_data n0=new Node(0);
	    	node_data n1=new Node(1);
	    	node_data n2=new Node(2);
	    	node_data n3=new Node(3);

	        g.addNode(n0);
	        g.addNode(n1);
	        g.addNode(n2);
	        g.addNode(n3);
	        g.connect(0,1,1);
	        g.connect(0,2,2);
	        g.connect(0,3,3);
	        g.connect(0,1,1);
	        Collection<node_data> v = g.getV();
	        Iterator<node_data> iter = v.iterator();
	        while (iter.hasNext()) {
	        	node_data n = iter.next();
	            assertNotNull(n);
	        }
	    }

	  
	    
	    @Test
	    void removeNode() {
	    	//directed_weighted_graph g = new DWGraph_DS();
	    	DWGraph_DS g = new DWGraph_DS();
	    	
	    	node_data n7=new Node(0);
	    	node_data n8=new Node(1);
	    	node_data n9=new Node(2);
	    	node_data n10=new Node(3);

    		g.addNode(n7);
	        g.addNode(n8);
	        g.addNode(n9);
	        g.addNode(n10);
	        g.connect(0,1,1);
	        g.connect(0,2,2);
	        g.connect(0,3,3);
	        g.removeNode(4);
	        g.removeNode(0);
	        assertFalse(g.hasEdge(1,0));
	        int e = g.edgeSize();
	        assertEquals(0,e);
	        assertEquals(3,g.nodeSize());
	    }

	   	   
	    /**
	     * Generate a random graph with v_size nodes and e_size edges
	     * @param v_size
	     * @param e_size
	     * @param seed
	     * @return
	     */
	    public static directed_weighted_graph graph_creator(int v_size, int e_size, int seed) {
	    	directed_weighted_graph g = new DWGraph_DS();
	        _rnd = new Random(seed);
	        for(int i=0;i<v_size;i++) {
		    	node_data n0=new Node(i);
	            g.addNode(n0);
	        }
	        int[] nodes = nodes(g);
	        while(g.edgeSize() < e_size) {
	            int a = nextRnd(0,v_size);
	            int b = nextRnd(0,v_size);
	            int i = nodes[a];
	            int j = nodes[b];
	            double w = _rnd.nextDouble();
	            g.connect(i,j, w);
	        }
	        return g;
	    }
	    private static int nextRnd(int min, int max) {
	        double v = nextRnd(0.0+min, (double)max);
	        int ans = (int)v;
	        return ans;
	    }
	    private static double nextRnd(double min, double max) {
	        double d = _rnd.nextDouble();
	        double dx = max-min;
	        double ans = d*dx+min;
	        return ans;
	    }
	    /**
	     * Simple method for returning an array with all the node_data of the graph,
	     * Note: this should be using an Iterator<node_edge> to be fixed in Ex1
	     * @param g
	     * @return
	     */
	    private static int[] nodes(directed_weighted_graph g) {
	        int size = g.nodeSize();
	        Collection<node_data> V = g.getV();
	        node_data[] nodes = new node_data[size];
	        V.toArray(nodes); // O(n) operation
	        int[] ans = new int[size];
	        for(int i=0;i<size;i++) {ans[i] = nodes[i].getKey();}
	        Arrays.sort(ans);
	        return ans;
	    }

}
