package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	List<Portion> porzioniVertici;
	SimpleWeightedGraph<String, DefaultWeightedEdge> grafo;
	
	FoodDao dao;
	
	public Model() {
		dao=new FoodDao();
	}
	
	public void creaGrafo(int calorie) {
		
		porzioniVertici=new ArrayList<>();
		porzioniVertici=this.getAllVertici(calorie);
		
		//tra la lista, i vertici sono solo i nomi delle porzioni e non gli id (che potrebbero essere 
		//molteplici per lo stesso tipo di porzioni)
		
		grafo= new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		for(Portion p: porzioniVertici) {
			if(!grafo.containsVertex(p.getPortion_display_name())) {
				grafo.addVertex(p.getPortion_display_name());
			}
		}
		
		
		System.out.println("\n\nGRAFO CREATO con "+grafo.vertexSet().size()+" vertici:");
		System.out.println(grafo);
		
	}
	
	public List<Portion> getAllVertici(int calorie){
		return dao.listAllPortionsCalories(calorie);
		
	}
	
	
	
}
