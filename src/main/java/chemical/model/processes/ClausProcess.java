package chemical.model.processes;

import chemical.model.items.HydrogenSulfideGas;
import chemical.model.items.OxygenGas;
import chemical.model.items.Sulfur;
import chemical.model.items.Water;
import mindustry.model.Amount;
import mindustry.model.ChemicalProcess;
import mindustry.model.MeasureUnit;

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
