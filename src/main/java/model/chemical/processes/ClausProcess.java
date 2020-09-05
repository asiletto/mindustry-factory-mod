package model.chemical.processes;

import adapter.Amount;
import adapter.ChemicalProcess;
import adapter.MeasureUnit;
import model.chemical.items.HydrogenSulfideGas;
import model.chemical.items.OxygenGas;
import model.chemical.items.Water;
import model.common.Sulfur;

public class ClausProcess extends ChemicalProcess {
	//https://en.wikipedia.org/wiki/Claus_process
	
	@Override
	public void init() {
		consumes.add(new Amount(new HydrogenSulfideGas(), 6d, MeasureUnit.MOLES));
		consumes.add(new Amount(new OxygenGas(), 3d, MeasureUnit.MOLES));
		
		produces.add(new Amount(new Sulfur(), 3d, MeasureUnit.MOLES));
		produces.add(new Amount(new Water(), 6d, MeasureUnit.MOLES));
	}
	
}
