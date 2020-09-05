package model.chemical.processes;

import adapter.Amount;
import adapter.ChemicalProcess;
import adapter.MeasureUnit;
import model.chemical.items.Ammonia;
import model.chemical.items.HydrogenGas;
import model.chemical.items.NitrogenGas;

public class HaberProcess extends ChemicalProcess {
	//https://en.wikipedia.org/wiki/Haber_process
	
	@Override
	public void init() {
		consumes.add(new Amount(new NitrogenGas(), 1d, MeasureUnit.MOLES));
		consumes.add(new Amount(new HydrogenGas(), 3d, MeasureUnit.MOLES));
		produces.add(new Amount(new Ammonia(), 2d, MeasureUnit.MOLES));
	}
	
}
