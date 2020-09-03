package mindustry.model;

public class Amount {

	public ChemicalElement element;
	public Double value;
	public MeasureUnit unit;
	
	public Amount(ChemicalElement element, Double value, MeasureUnit unit) {
		super();
		this.element = element;
		this.value = value;
		this.unit = unit;
	}
	public ChemicalElement getElement() {
		return element;
	}
	public void setElement(ChemicalElement element) {
		this.element = element;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public MeasureUnit getUnit() {
		return unit;
	}
	public void setUnit(MeasureUnit unit) {
		this.unit = unit;
	}
	
}
