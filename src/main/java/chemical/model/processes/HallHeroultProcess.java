package chemical.model.processes;

import chemical.model.items.Alumina;
import chemical.model.items.Aluminum;
import chemical.model.items.AluminumBathMaterial;
import chemical.model.items.CarbonAnodes;
import chemical.model.items.Energy;
import mindustry.model.Amount;
import mindustry.model.ChemicalProcess;
import mindustry.model.MeasureUnit;

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
