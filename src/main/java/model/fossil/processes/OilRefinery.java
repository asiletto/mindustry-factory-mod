package model.fossil.processes;

import adapter.Amount;
import adapter.ChemicalProcess;
import adapter.MeasureUnit;
import model.fossil.items.CrudeOil;
import model.fossil.items.Diesel;
import model.fossil.items.Kerosene;
import model.fossil.items.NaturalGas;

public class OilRefinery extends ChemicalProcess {


	@Override
	public void init() {
		consumes.add(new Amount(new CrudeOil(), 6d, MeasureUnit.MOLES));
		
		produces.add(new Amount(new Diesel(), 2d, MeasureUnit.MOLES));
		produces.add(new Amount(new Kerosene(), 2d, MeasureUnit.MOLES));
		produces.add(new Amount(new NaturalGas(), 3d, MeasureUnit.MOLES));
	}
	
}
