package it.polito.tdp.yelp.model;

import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	
	private Graph<Business, DefaultWeightedEdge> grafo;
	private List<Business> vertici;
	private Map<String, Business> vIdMap;
	
	public List<String> getAllCities(){
		YelpDao dao = new YelpDao();
		return dao.getAllCity();
	}
	
	public String creaGrafo(String city, Year anno) {
		this.grafo = new SimpleDirectedWeightedGraph<Business, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		YelpDao dao = new YelpDao();
		this.vertici = dao.getBusinessCY(city, anno);
		this.vIdMap = new HashMap<>();
		for(Business b : this.vertici){
			this.vIdMap.put(b.getBusinessId(), b);
		}
		
		
		Graphs.addAllVertices(this.grafo, this.vertici);
		return String.format("Grafo con #v: %d e #a: %d\n", this.grafo.vertexSet().size(), this.grafo.edgeSet().size());
		
		List<Adiacenza> archi = dao.calcolaArchi(city, anno);
		
		for(Adiacenza a : archi) {
			Graphs.addEdge(this.grafo, this.vIdMap.get(a.getId1()), this.vIdMap.get(a.getId2()), a.getPeso());
		}
	}
	
}
