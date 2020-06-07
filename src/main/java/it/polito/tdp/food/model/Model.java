package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	List<Portion> porzioniVertici;
	SimpleWeightedGraph<String, DefaultWeightedEdge> grafo;
	List<Adiacenza> adiacenze;
	List<String> soluzione;
	int pesoBest;
	
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
		
		adiacenze=new ArrayList<>();
		adiacenze=this.getAllAdiacenze();
		
		for(Adiacenza a:adiacenze) {
			if(grafo.containsVertex(a.getP1())&&grafo.containsVertex(a.getP2())) {
				//essendo non orientato, anche se mettiamo l'arco al contrario non viene creato
				Graphs.addEdge(grafo, a.getP1(), a.getP2(), a.getPeso());
			}
		}
		
		System.out.println("\n\nGRAFO CREATO con "+grafo.edgeSet().size()+" archi:");
		for(DefaultWeightedEdge e: grafo.edgeSet()) {
			System.out.println(grafo.getEdgeSource(e)+" - "+grafo.getEdgeTarget(e)+" -> peso "+grafo.getEdgeWeight(e));
		}
		
	}
	
	public List<Portion> getAllVertici(int calorie){
		return dao.listAllPortionsCalories(calorie);
		
	}
	
	public List<Adiacenza> getAllAdiacenze(){
		return dao.listAllAdiacenze();
	}
	
	public List<String> restituisciVertici(){
		List<String> vertici=new ArrayList<>();
		for(String v: grafo.vertexSet()) {
			vertici.add(v);
		}
		vertici.sort(null);
		return vertici;
		
	}
	
	public List<VicinoPeso> rstituisciVicini(String v){
		List<VicinoPeso> vicini=new ArrayList<>();
		for(String vicino: Graphs.neighborListOf(grafo, v)) {
			vicini.add(new VicinoPeso(vicino, (int) grafo.getEdgeWeight(grafo.getEdge(v, vicino))));
		}
		return vicini;
	}
	
	public String trovaPercorso(String partenza, int passi) {
		List<String> parziale=new ArrayList<>();
		parziale.add(partenza);
		soluzione=new ArrayList<>();
		pesoBest=0;
		//se devo cercare un cammino con n-archi, i vertici devono essere uno in piu'
		//System.out.println("\n\nPASSI: "+passi);
		ricorsione(parziale,passi+1);
		
		String ritorna="CAMMINO COSTRUITO CON PESO TOTALE "+pesoBest+" CHE CONTIENE I VERTICI: \n\n";
		for(String p: soluzione) {
			ritorna=ritorna+p+"\n";
		}
		
		return ritorna;
		
	}
	
	public void ricorsione(List<String> parziale, int n) {
		//caso terminale
		if(parziale.size()==n) {
			//devo calcolare il peso del percorso e confrontarlo con il migliore
			int pesoParziale=calcolaPesoPercorso(parziale);
			//System.out.println("\n\nPASSI: "+n);
			System.out.println("\n\nTrovato parziale con peso: "+pesoParziale);
			for(String p:parziale) {
				System.out.println(p);
			}
			
			if(pesoParziale>pesoBest) {
				soluzione=new ArrayList<>(parziale);
				pesoBest=pesoParziale;
			}
			
		}
		
		//scorro i vicini dell'ultimo elemento aggiunto
		for(String v:Graphs.neighborListOf(grafo, parziale.get(parziale.size()-1))) {
			if(!parziale.contains(v)) {
				//se non era un vertice che avevo gia' considerato lo metto in parziale
				parziale.add(v);
				ricorsione(parziale,n);
				parziale.remove(parziale.size()-1);
			}
		}
		
		
	}
	
	public int calcolaPesoPercorso(List<String> parziale) {
		int pesoCammino=0;
		for(int i=0;i<parziale.size()-1;i++) {
			pesoCammino=pesoCammino+(int) grafo.getEdgeWeight(grafo.getEdge(parziale.get(i), parziale.get(i+1)));
		}
		return pesoCammino;
	}
	
	
}
