import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
import java.util.List;

import timemeasure.CPUTiming;

class RatioComparator implements Comparator<Item> {
	
	@Override
	public int compare(Item i1, Item i2) {
		return (i2.getPrice()/i2.getWeight()) - (i1.getPrice()/i1.getWeight());
	}
	
}


public class KnapsackSolver {

	public static void main(String[] args) {
		FileLoader loader = new FileLoader();
		List<Instance> instList = loader.loadFile();
		float totalRelativeError = 0, maxRelativeError = 0, relativeError;
		int optPrice, appPrice;

		//long startTime = CPUTiming.getCpuTime();
		for (Instance instance : instList) {
			
			bruteForceSolver(instance);
			optPrice = instance.getKnapsack().getPrice();
			ratioSolver(instance);
			appPrice = instance.getKnapsack().getPrice();
			relativeError = relativeError(optPrice, appPrice);
			totalRelativeError += relativeError;
			
			// Is current relative error greater than current max relative error?
			if (relativeError > maxRelativeError) {
				maxRelativeError = relativeError;
			}
			
		}
		//long cpuTime = CPUTiming.getCpuTime() - startTime;
		//System.out.println((float)cpuTime/(float) 50);
		System.out.println("total :"  + totalRelativeError/50);
		System.out.println("max: " + maxRelativeError);
	}
	
	// Solves the problem using brute force
	private static void bruteForceSolver(Instance instance) {
		int index, curWeight, bestPrice = 0, curPrice;
		
		// Taking every number from the interval (1,2^n) and try its binary form
		for (int i = 1; i < Math.pow(2, instance.getnSize()); i++) {
			BitSet bs = BitSet.valueOf(new long[]{i});
			curWeight = 0;
			curPrice = 0;
			index = -1;
			
			// Try to add every item with its bit set to 1
			while ((index = bs.nextSetBit(index + 1)) != -1) {
				curWeight += instance.getItemPool().getItems()[index].getWeight();
				curPrice += instance.getItemPool().getItems()[index].getPrice();
				
				if (curWeight > instance.getKnapsack().getLimit())
					break;
			}
			
			// Weight is under limit. Is price higher then current best?
			if (curPrice > bestPrice && curWeight <= instance.getKnapsack().getLimit()) {
				instance.getKnapsack().setItemsInBag(bs);
				instance.getKnapsack().setPrice(curPrice);
				instance.getKnapsack().setWeight(curWeight);
				bestPrice = curPrice;
			}
		}
	}
	
	// Solves the problem using weight/price ratio
	private static void ratioSolver(Instance instance) {
		Arrays.sort(instance.getItemPool().getItems(), new RatioComparator());
		
		for (Item item : instance.getItemPool().getItems()) {
			if (isUnderLimit(instance, item.getWeight())) {
				
				// Set new weight
				instance.getKnapsack().setWeight(
						instance.getKnapsack().getWeight() + item.getWeight());
				
				// Set corresponding bit in the vector
				instance.getKnapsack().getItemsInBag().set(item.getId());
				
				// Set new price
				instance.getKnapsack().setPrice(
						instance.getKnapsack().getPrice() + item.getPrice());
				
			}
		}
	}
	
	// If we add nWeight to current knapsack weight it will still be under limit?
	public static boolean isUnderLimit(Instance instance, int nWeight) {
		if ((instance.getKnapsack().getWeight() + 
				nWeight) <= instance.getKnapsack().getLimit())
			return true;
		
		return false;
	}
	
	// Compute the relative error
	private static float relativeError(int optPrice, int appPrice) {
		return Math.abs(((float)optPrice - (float)appPrice))/Math.abs((float)optPrice);
	}
}
