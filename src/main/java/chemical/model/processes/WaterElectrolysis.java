package chemical.model.processes;

import chemical.model.items.Energy;
import chemical.model.items.HydrogenGas;
import chemical.model.items.OxygenGas;
import chemical.model.items.Water;
import mindustry.model.Amount;
import mindustry.model.ChemicalProcess;
import mindustry.model.MeasureUnit;

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
