package model.chemical.processes;

import adapter.Amount;
import adapter.ChemicalProcess;
import adapter.MeasureUnit;
import model.chemical.items.Energy;
import model.chemical.items.HydrogenGas;
import model.chemical.items.OxygenGas;
import model.chemical.items.Water;

public class WaterElectrolysis extends ChemicalProcess {
	//https://en.wikipedia.org/wiki/Electrolysis_of_water
	
	@Override
	public void init() {
		consumes.add(new Amount(new Energy(), 0.474, MeasureUnit.MegaJoule));
		consumes.add(new Amount(new Water(), 2d, MeasureUnit.MOLES));
		produces.add(new Amount(new OxygenGas(), 1d, MeasureUnit.MOLES));
		produces.add(new Amount(new HydrogenGas(), 2d, MeasureUnit.MOLES));
	}
	
}
