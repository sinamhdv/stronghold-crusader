package stronghold.model.buildings;

import stronghold.model.Government;
import stronghold.model.ResourceType;
import stronghold.model.StrongHold;
import stronghold.model.map.GroundType;

public class ResourceConverterBuilding extends Building {
	private ResourceType inputType;
	private ResourceType outpuType;
	private GroundType groundType;
	private int productionRate;
	private int productionNumber;
	private int usageRate;
	public ResourceConverterBuilding(int maxHp, int x, int y, Government owner, String name, int neededWorkers,
			int terunOfBuild, ResourceType inputType, ResourceType outpuType, GroundType groundType, int productionRate,
			int productionNumber, int usageRate) {
		super(maxHp, x, y, owner, name, neededWorkers, terunOfBuild);
		this.inputType = inputType;
		this.outpuType = outpuType;
		this.groundType = groundType;
		this.productionRate = productionRate;
		this.productionNumber = productionNumber;
		this.usageRate = usageRate;
	}
	public ResourceType getInputType() {
		return inputType;
	}
	public ResourceType getOutpuType() {
		return outpuType;
	}
	public GroundType getGroundType() {
		return groundType;
	}
	public int getProductionRate() {
		return productionRate;
	}
	public int getProductionNumber() {
		return productionNumber;
	}
	public void setInputType(ResourceType inputType) {
		this.inputType = inputType;
	}
	public void setOutpuType(ResourceType outpuType) {
		this.outpuType = outpuType;
	}
	public void setGroundType(GroundType groundType) {
		this.groundType = groundType;
	}
	public void setProductionRate(int productionRate) {
		this.productionRate = productionRate;
	}
	public void setProductionNumber(int productionNumber) {
		this.productionNumber = productionNumber;
	}
	public void performConversion(){
		if((StrongHold.getCurrentGame().getPassedTurns() - getTurnOfBuild()) % productionRate != 0) return;
		else if(outpuType == ResourceType.NULL) {
			getOwner().decreaseResource(inputType, usageRate);
			return;
		}
		else if(inputType == ResourceType.NULL) {
			getOwner().increaseResource(outpuType, productionNumber);
			return;
		}
		getOwner().decreaseResource(inputType, usageRate);
		getOwner().increaseResource(outpuType, productionNumber);
	}
}
