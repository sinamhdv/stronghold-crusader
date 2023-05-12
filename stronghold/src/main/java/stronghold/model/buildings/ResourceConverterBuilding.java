package stronghold.model.buildings;

import stronghold.model.ResourceType;
import stronghold.model.StrongHold;
import stronghold.model.map.GroundType;

public class ResourceConverterBuilding extends Building {
	private ResourceType inputType;
	private ResourceType outputType;
	private final GroundType[] allowedGroundTypes;
	private int productionCycleTurns;
	private int outputsProducedCount;
	private int inputsUsedCount;

	public ResourceConverterBuilding(int maxHp, String name, int neededWorkers, int width, int height,
			int verticalHeight, boolean isSelectable, int x, int y, int residentsCapacity, int ownerIndex,
			ResourceType inputType, ResourceType outputType, GroundType[] allowedGroundTypes, int productionCycleTurns,
			int outputsProducedCount, int inputsUsedCount) {
		super(maxHp, name, neededWorkers, width, height, verticalHeight, isSelectable, x, y, residentsCapacity,
				ownerIndex);
		this.inputType = inputType;
		this.outputType = outputType;
		this.allowedGroundTypes = allowedGroundTypes;
		this.productionCycleTurns = productionCycleTurns;
		this.outputsProducedCount = outputsProducedCount;
		this.inputsUsedCount = inputsUsedCount;
	}

	private ResourceConverterBuilding(ResourceConverterBuilding model, int x, int y, int ownerIndex) {
		super(model, x, y, ownerIndex);
		this.inputType = model.inputType;
		this.outputType = model.outputType;
		this.allowedGroundTypes = model.allowedGroundTypes;
		this.productionCycleTurns = model.productionCycleTurns;
		this.outputsProducedCount = model.outputsProducedCount;
		this.inputsUsedCount = model.inputsUsedCount;
	}

	@Override
	public Building generateCopy(int x, int y, int ownerIndex) {
		return new ResourceConverterBuilding(this, x, y, ownerIndex);
	}

	public ResourceType getInputType() {
		return inputType;
	}
	public void setInputType(ResourceType inputType) {
		this.inputType = inputType;
	}
	public ResourceType getOutputType() {
		return outputType;
	}
	public void setOutputType(ResourceType outpuType) {
		this.outputType = outpuType;
	}
	public boolean isGroundTypeAllowed(GroundType groundType) {
		for (GroundType type : allowedGroundTypes)
			if (type == groundType)
				return true;
		return false;
	}
	public int getProductionCycleTurns() {
		return productionCycleTurns;
	}
	public void setProductionCycleTurns(int productionCycleTurns) {
		this.productionCycleTurns = productionCycleTurns;
	}
	public int getOutputsProducedCount() {
		return outputsProducedCount;
	}
	public void setOutputsProducedCount(int outputsProducedCount) {
		this.outputsProducedCount = outputsProducedCount;
	}
	public int getInputsUsedCount() {
		return inputsUsedCount;
	}
	public void setInputsUsedCount(int inputsUsedCount) {
		this.inputsUsedCount = inputsUsedCount;
	}

	public void performConversion() {
		if ((StrongHold.getCurrentGame().getPassedTurns() - getTurnOfBuild()) % getProductionCycleTurns() != 0)
			return;
		if (getOwner().getResourceCount(inputType) < getInputsUsedCount())
			return;
		if (outputType != null)
			getOwner().increaseResource(outputType, getOutputsProducedCount());
		if (inputType != null)
			getOwner().decreaseResource(inputType, getInputsUsedCount());
	}
}
