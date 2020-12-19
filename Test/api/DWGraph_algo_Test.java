package api;
import static org.junit.jupiter.api.Assertions.*;
import gameClient.util.Point3D;
import api.DWGraph_Algo;
import api.directed_weighted_graph;
import api.dw_graph_algorithms;
import java.util.List;
import Server.Game_Server_Ex2;
import api.directed_weighted_graph;
import api.edge_data;
import api.game_service;
import gameClient.util.StdDraw;

import org.junit.jupiter.api.Test;


class DWGraph_algo_Test {
	 @Test
	    void isConnected() {
		   directed_weighted_graph g0 = WGraph_DSTest.graph_creator(0,0,1);
		   dw_graph_algorithms ag0 = new DWGraph_Algo();
	        ag0.init(g0);
	        assertTrue(ag0.isConnected());
	  
	        g0 = WGraph_DSTest.graph_creator(1,0,1);
	        ag0 = new DWGraph_Algo();
	        ag0.init(g0);
	        assertTrue(ag0.isConnected());

	         g0 = WGraph_DSTest.graph_creator(2,0,1);
	        ag0 = new DWGraph_Algo();
	        ag0.init(g0);
	        assertFalse(ag0.isConnected());
	        
	     

	        g0 = WGraph_DSTest.graph_creator(10,30,1);
	        ag0.init(g0);
	        boolean b = ag0.isConnected();
	        assertTrue(b);
	    }

	    	 
	    @Test
	    void shortestPath() {
	    	directed_weighted_graph g0 = small_graph();
	    	dw_graph_algorithms ag0 = new DWGraph_Algo();
	        ag0.init(g0);
	        List<node_data> sp = ag0.shortestPath(0,10);
	        //double[] checkTag = {0.0, 1.0, 2.0, 3.1, 5.1};
	        int[] checkKey = {0, 1, 5, 7, 10};
	        int i = 0;
	        for(node_data n: sp) {
	        	//assertEquals(n.getTag(), checkTag[i]);
	        	assertEquals(n.getKey(), checkKey[i]);
	        	i++;
	        }
	    }
	    
	    /**
		 * returns the length of the shortest path between src to dest
		 * @param src - start node
		 * @param dest - end (target) node
		 * @return
		 */
		@Test
		void shortestPathDistTest() {
			DWGraph_Algo ga = new DWGraph_Algo();
			ga.dg.addNode(new Node(0,new Point3D(-50, -50), 0, "snED", 0));
			ga.dg.addNode(new Node(1,new Point3D(50, 50), 0, "rotze", 0));
			ga.dg.addNode(new Node(2,new Point3D(50, -50), 0, "shedstishvy", 0));
			ga.dg.addNode(new Node(3,new Point3D(-50, 50), 0, "lo", 0));
			ga.dg.addNode(new Node(4,new Point3D(0, -75), 0, "hal", 0));
			ga.dg.addNode(new Node(5,new Point3D(0, 75), 0, "hapaccnim", 0));
			ga.dg.addNode(new Node(6,new Point3D(0, 0), 0, "ya shramu&*^^%#@", 0));
			ga.dg.connect(0,3, 2);
			ga.dg.connect(3,5, 1);
			ga.dg.connect(5,1, 5);
			ga.dg.connect(1,2, 0);
			ga.dg.connect(2,4, 6);
			ga.dg.connect(4,0, 3);
			ga.dg.connect(6,4, 4);
			ga.dg.connect(6,5, 2.5);
			
			assertEquals(ga.shortestPathDist(0, 2),8);
			assertEquals(ga.shortestPathDist(6, 2),7.5);
			assertEquals(ga.shortestPathDist(6, 3),9);
		}
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    private directed_weighted_graph small_graph() {
	    	directed_weighted_graph g0 = WGraph_DSTest.graph_creator(11,0,1);
	        g0.connect(0,1,1);
	        g0.connect(0,2,2);
	        g0.connect(0,3,3);

	        g0.connect(1,4,17);
	        g0.connect(1,5,1);
	        g0.connect(2,4,1);
	        g0.connect(3, 5,10);
	        g0.connect(3,6,100);
	        g0.connect(5,7,1.1);
	        g0.connect(6,7,10);
	        g0.connect(7,10,2);
	        g0.connect(6,8,30);
	        g0.connect(8,10,10);
	        g0.connect(4,10,30);
	        g0.connect(3,9,10);
	        g0.connect(8,10,10);

	        return g0;
	    }
	
	
	
	
	
	
}
