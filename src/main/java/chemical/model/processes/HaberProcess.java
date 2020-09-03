package chemical.model.processes;

import chemical.model.items.Ammonia;
import chemical.model.items.HydrogenGas;
import chemical.model.items.NitrogenGas;
import mindustry.model.Amount;
import mindustry.model.ChemicalProcess;
import mindustry.model.MeasureUnit;

public class HaberProcess extends ChemicalProcess {
	//https://en.wikipedia.org/wiki/Haber_process
	
	@Override
	public void init() {
		consumes.add(new Amount(new NitrogenGas(), 1d, MeasureUnit.MOLES));
		consumes.add(new Amount(new HydrogenGas(), 3d, MeasureUnit.MOLES));
		produces.add(new Amount(new Ammonia(), 2d, MeasureUnit.MOLES));
	}
	
}
