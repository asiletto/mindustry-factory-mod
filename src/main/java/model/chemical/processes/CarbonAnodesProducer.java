package model.chemical.processes;

import adapter.Amount;
import adapter.ChemicalProcess;
import adapter.MeasureUnit;
import model.chemical.items.Carbon;
import model.chemical.items.CarbonAnodes;
import model.chemical.items.Energy;

public class CarbonAnodesProducer extends ChemicalProcess {
	//https://www.researchgate.net/figure/Exergy-analysis-of-the-current-industrial-process-for-primary-aluminum-production-with_fig5_234004241
	
	@Override
	public void init() {
		consumes.add(new Amount(new Energy(), 	1.0, 	MeasureUnit.MegaJoule));
		consumes.add(new Amount(new Carbon(),	1.0, 	MeasureUnit.KG));
		produces.add(new Amount(new CarbonAnodes(), 1.0, MeasureUnit.KG));
	}
	
}
