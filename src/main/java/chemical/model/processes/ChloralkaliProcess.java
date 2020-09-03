package chemical.model.processes;

import chemical.model.items.CarbonAnodes;
import chemical.model.items.ChlorineGas;
import chemical.model.items.ElectrolysisCellMembrane;
import chemical.model.items.Energy;
import chemical.model.items.HydrogenGas;
import chemical.model.items.SodiumChloride;
import chemical.model.items.SodiumHydroxyde;
import chemical.model.items.Water;
import mindustry.model.Amount;
import mindustry.model.ChemicalProcess;
import mindustry.model.MeasureUnit;

public class ChloralkaliProcess extends ChemicalProcess {
	//https://en.wikipedia.org/wiki/Chloralkali_process
	//TODO: balance values
	
	@Override
	public void init() {
		consumes.add(new Amount(new Energy(), 	1.0, 	MeasureUnit.MegaJoule));
		
		consumes.add(new Amount(new SodiumChloride(),		1.0, 	MeasureUnit.KG));
		consumes.add(new Amount(new Water(),		1.0, 	MeasureUnit.KG));
		consumes.add(new Amount(new CarbonAnodes(),					0.1, 	MeasureUnit.KG));
		consumes.add(new Amount(new ElectrolysisCellMembrane(),		0.1, 	MeasureUnit.KG));

		//combined into 1 liquid "chloroalkali-process-input" input
		
		produces.add(new Amount(new HydrogenGas(), 1.0, MeasureUnit.KG));
		produces.add(new Amount(new ChlorineGas(), 1.0, MeasureUnit.KG));
		produces.add(new Amount(new SodiumHydroxyde(), 1.0, MeasureUnit.KG));
	}
	
}
