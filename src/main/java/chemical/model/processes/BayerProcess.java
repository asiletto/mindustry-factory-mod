package chemical.model.processes;

import chemical.model.items.Alumina;
import chemical.model.items.Bauxite;
import chemical.model.items.CarbonDioxideGas;
import chemical.model.items.Energy;
import chemical.model.items.Limestone;
import chemical.model.items.SodiumHydroxyde;
import chemical.model.items.RedMud;
import chemical.model.items.Steam;
import chemical.model.items.Water;
import mindustry.model.Amount;
import mindustry.model.ChemicalProcess;
import mindustry.model.MeasureUnit;

public class BayerProcess extends ChemicalProcess {
	//https://en.wikipedia.org/wiki/Bayer_process
	//https://www.researchgate.net/figure/Exergy-analysis-of-the-current-industrial-process-for-primary-aluminum-production-with_fig5_234004241
	
	@Override
	public void init() {
		consumes.add(new Amount(new Energy(), 	2.73, 	MeasureUnit.MegaJoule));
		consumes.add(new Amount(new Bauxite(),	5.1, 	MeasureUnit.KG));
		consumes.add(new Amount(new SodiumHydroxyde(), 		0.09, 	MeasureUnit.KG));
		consumes.add(new Amount(new Limestone(), 0.06, 	MeasureUnit.KG));
		consumes.add(new Amount(new Water(), 	11.57, 	MeasureUnit.KG));
		consumes.add(new Amount(new Energy(), 	24.61, 	MeasureUnit.MegaJoule));

		waste.add(new Amount(new Energy(), 		26.49, 	MeasureUnit.MegaJoule));
		waste.add(new Amount(new RedMud(), 		2.0, 	MeasureUnit.KG));
		waste.add(new Amount(new Water(), 		11.57, 	MeasureUnit.KG));
		waste.add(new Amount(new Steam(), 		1.66, 	MeasureUnit.KG));
		waste.add(new Amount(new CarbonDioxideGas(), 			1.61, 	MeasureUnit.KG));
				
		produces.add(new Amount(new Energy(), 0.8, MeasureUnit.MegaJoule));
		produces.add(new Amount(new Alumina(), 1.93, MeasureUnit.KG));
	}
	
}
