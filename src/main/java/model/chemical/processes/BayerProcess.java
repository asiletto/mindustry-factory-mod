package model.chemical.processes;

import adapter.Amount;
import adapter.ChemicalProcess;
import adapter.MeasureUnit;
import model.chemical.items.Alumina;
import model.chemical.items.Bauxite;
import model.chemical.items.CarbonDioxideGas;
import model.chemical.items.Energy;
import model.chemical.items.Limestone;
import model.chemical.items.RedMud;
import model.chemical.items.SodiumHydroxyde;
import model.chemical.items.Steam;
import model.chemical.items.Water;

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

		//waste
		produces.add(new Amount(new Energy(), 		26.49, 	MeasureUnit.MegaJoule));
		produces.add(new Amount(new RedMud(), 		2.0, 	MeasureUnit.KG));
		produces.add(new Amount(new Water(), 		11.57, 	MeasureUnit.KG));
		produces.add(new Amount(new Steam(), 		1.66, 	MeasureUnit.KG));
		produces.add(new Amount(new CarbonDioxideGas(), 			1.61, 	MeasureUnit.KG));

		//product
		produces.add(new Amount(new Energy(), 0.8, MeasureUnit.MegaJoule));
		produces.add(new Amount(new Alumina(), 1.93, MeasureUnit.KG));
	}
	
}
