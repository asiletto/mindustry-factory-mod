package model.chemical.processes;

import adapter.Amount;
import adapter.ChemicalProcess;
import adapter.MeasureUnit;
import model.chemical.items.Alumina;
import model.chemical.items.Aluminum;
import model.chemical.items.AluminumBathMaterial;
import model.chemical.items.CarbonAnodes;
import model.chemical.items.Energy;

public class HallHeroultProcess extends ChemicalProcess {
	//https://www.researchgate.net/figure/Exergy-analysis-of-the-current-industrial-process-for-primary-aluminum-production-with_fig5_234004241
	
	@Override
	public void init() {
		
		consumes.add(new Amount(new Energy(), 				15.43, 	MeasureUnit.MegaJoule));
		consumes.add(new Amount(new Energy(), 				56.24, 	MeasureUnit.MegaJoule));
		consumes.add(new Amount(new CarbonAnodes(),			0.45, 	MeasureUnit.KG));
		consumes.add(new Amount(new AluminumBathMaterial(),	0.07, 	MeasureUnit.KG));
		consumes.add(new Amount(new Alumina(),				1.93, 	MeasureUnit.KG));
		
		waste.add(new Amount(new Energy(), 		151.39, 	MeasureUnit.MegaJoule));
		
		produces.add(new Amount(new Energy(), 	30.00, MeasureUnit.MegaJoule));
		produces.add(new Amount(new Aluminum(),	1.0, 	MeasureUnit.KG));

	}
	
}
