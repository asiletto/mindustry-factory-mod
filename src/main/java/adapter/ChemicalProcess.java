package adapter;

import java.util.ArrayList;
import java.util.List;

public abstract class ChemicalProcess {
	//https://en.wikipedia.org/wiki/Category:Chemical_processes
	
	public ChemicalProcess() {
		init();
	}
	
	public List<Amount> produces = new ArrayList<Amount>();
	public List<Amount> consumes = new ArrayList<Amount>();
	public List<Amount> waste = new ArrayList<Amount>();
	
	public abstract void init();
	
	public List<Amount> getProduces() {
		return produces;
	}
	public void setProduces(List<Amount> produces) {
		this.produces = produces;
	}
	public List<Amount> getConsumes() {
		return consumes;
	}
	public void setConsumes(List<Amount> consumes) {
		this.consumes = consumes;
	}

	public List<Amount> getWaste() {
		return waste;
	}

	public void setWaste(List<Amount> waste) {
		this.waste = waste;
	}
	
}
