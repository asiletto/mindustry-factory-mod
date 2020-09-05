package model.chemical.processes;

import adapter.Amount;
import adapter.ChemicalProcess;
import adapter.MeasureUnit;
import model.chemical.items.Ammonia;
import model.chemical.items.AmmoniumNitrate;
import model.chemical.items.NitricAcid;

public class AmmoniumNitratePlant extends ChemicalProcess {
	//https://en.wikipedia.org/wiki/Ammonium_nitrate
	
	@Override
	public void init() {
		consumes.add(new Amount(new NitricAcid(), 1d, MeasureUnit.MOLES));
		consumes.add(new Amount(new Ammonia(), 1d, MeasureUnit.MOLES));
		
		produces.add(new Amount(new AmmoniumNitrate(), 1d, MeasureUnit.MOLES));
	}
	
}
