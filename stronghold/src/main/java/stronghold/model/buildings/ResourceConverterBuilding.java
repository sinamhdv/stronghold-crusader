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
			int verticalHeight, boolean isSelectable, int x, int y, int residents, int residentsCapacity,
			boolean hasWorkers, int ownerIndex, int turnOfBuild, ResourceType inputType, ResourceType outpuType,
			GroundType[] allowedGroundTypes, int productionCycleTurns, int outputsProducedCount, int inputsUsedCount) {
		super(maxHp, name, neededWorkers, width, height, verticalHeight, isSelectable, x, y, residents,
				residentsCapacity, hasWorkers, ownerIndex, turnOfBuild);
		this.inputType = inputType;
		this.outputType = outpuType;
		this.allowedGroundTypes = allowedGroundTypes;
		this.productionCycleTurns = productionCycleTurns;
		this.outputsProducedCount = outputsProducedCount;
		this.inputsUsedCount = inputsUsedCount;
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
	public GroundType[] getAllowedGroundTypes() {
		return allowedGroundTypes;
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
