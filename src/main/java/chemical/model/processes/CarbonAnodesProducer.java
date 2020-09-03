package chemical.model.processes;

import chemical.model.items.Carbon;
import chemical.model.items.CarbonAnodes;
import chemical.model.items.Energy;
import mindustry.model.Amount;
import mindustry.model.ChemicalProcess;
import mindustry.model.MeasureUnit;

public class CarbonAnodesProducer extends ChemicalProcess {
	//https://www.researchgate.net/figure/Exergy-analysis-of-the-current-industrial-process-for-primary-aluminum-production-with_fig5_234004241
	
	@Override
	public void init() {
		consumes.add(new Amount(new Energy(), 	1.0, 	MeasureUnit.MegaJoule));
		consumes.add(new Amount(new Carbon(),	1.0, 	MeasureUnit.KG));
		produces.add(new Amount(new CarbonAnodes(), 1.0, MeasureUnit.KG));
	}
	
}
