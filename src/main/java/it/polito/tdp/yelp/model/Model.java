package it.polito.tdp.yelp.model;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	
	private YelpDao dao;
	private Graph<Business, DefaultWeightedEdge> grafo;
	
	

	public Model() {
		super();
		this.dao = new YelpDao();
	}

	public void creaGrafo(int anno, String city) {
		
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);

		Graphs.addAllVertices(grafo, this.dao.getVertici(anno, city));
		
		Map<String, Business> mappaBusiness = new HashMap<>();
		for(Business b: this.dao.getVertici(anno, city)) {
			mappaBusiness.put(b.getBusinessId(), b);
		}
		
		List<Arco> archi = this.dao.getArchi(anno, city, mappaBusiness);
		
		for(Arco a: archi)
			System.out.println(a.toString());
		System.out.println("\n");
		for(Arco a: archi) {
			if(a.getPeso()<0) {
				this.grafo.addEdge(a.getB1(), a.getB2());
				this.grafo.setEdgeWeight(a.getB1(), a.getB2(), -a.getPeso());
				System.out.println(a.getB1()+"   "+a.getB2()+"   "+-a.getPeso());
			}
			if(a.getPeso()>0) {
				this.grafo.addEdge(a.getB2(), a.getB1());
				this.grafo.setEdgeWeight(a.getB2(), a.getB1(), a.getPeso());
				System.out.println(a.getB2()+"   "+a.getB1()+"   "+a.getPeso());
			}
		}
		
	}
	/*
	 * cerco il business che ha somma in meno somma out max
	 */
	public Business migliore() {
		
		Business best = null;
		List<Best> lista = new LinkedList<>();
		
		
		
		for(Business b: this.grafo.vertexSet()) {
			Double diff = 0.0;
			Double in = 0.0;
			Double out = 0.0;
			for(DefaultWeightedEdge e: this.grafo.incomingEdgesOf(b)) {
				in +=this.grafo.getEdgeWeight(e);
			}
			for(DefaultWeightedEdge e: this.grafo.outgoingEdgesOf(b)) {
				out +=this.grafo.getEdgeWeight(e);
			}
			diff = in-out;
			lista.add(new Best(b, diff));
			System.out.println("\n" + b.toString() + "   " + diff);
		}
		
		Double max = 0.0;
		for(Best be: lista) {
			if(be.getVal()>max)
				max = be.getVal();
		}
		
		for(Best be: lista) {
			if(be.getVal()==max)
				best = be.getB();
		}
		
		return best;
	}

	public List<String> getCity() {
		return this.dao.getCity();
	}
	
	public int getNumVertici() {
		return this.grafo.vertexSet().size();
	}

	public int getNumArchi() {
		return this.grafo.edgeSet().size();
	}
	
	
}
