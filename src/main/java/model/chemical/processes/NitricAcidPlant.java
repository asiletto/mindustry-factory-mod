package model.chemical.processes;

import adapter.Amount;
import adapter.ChemicalProcess;
import adapter.MeasureUnit;
import model.chemical.items.NitricAcid;
import model.chemical.items.NitrogenDioxideGas;
import model.chemical.items.OxygenGas;
import model.chemical.items.Water;

public class NitricAcidPlant extends ChemicalProcess {
	//https://en.wikipedia.org/wiki/Nitric_acid#Production
	
	@Override
	public void init() {
		consumes.add(new Amount(new NitrogenDioxideGas(), 4d, MeasureUnit.MOLES));
		consumes.add(new Amount(new OxygenGas(), 1d, MeasureUnit.MOLES));
		consumes.add(new Amount(new Water(), 2d, MeasureUnit.MOLES));
		
		produces.add(new Amount(new NitricAcid(), 4d, MeasureUnit.MOLES));
	}
	
}
