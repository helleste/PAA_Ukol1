
public class ItemPool {
	
	private Item[] items;

	public Item[] getItems() {
		return items;
	}

	public void setItems(Item[] items) {
		this.items = items;
	}
	
	@Override
	public String toString() {
		String itemsData = "[\n";
		
		for (Item item : items) {
			itemsData += item.toString();
		}
		
		itemsData += "\n]";
		
		return itemsData;
	}

}
