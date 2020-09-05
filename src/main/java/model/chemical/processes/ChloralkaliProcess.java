package model.chemical.processes;

import adapter.Amount;
import adapter.ChemicalProcess;
import adapter.MeasureUnit;
import model.chemical.items.CarbonAnodes;
import model.chemical.items.ChlorineGas;
import model.chemical.items.ElectrolysisCellMembrane;
import model.chemical.items.Energy;
import model.chemical.items.HydrogenGas;
import model.chemical.items.SodiumChloride;
import model.chemical.items.SodiumHydroxyde;
import model.chemical.items.Water;

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
