
public class Item {
	private String name;
	private int x;
	private int y;
    private boolean ifCollected = false;

	public Item(String item_name, int i_x, int i_y) {
		this.name = item_name;
		this.x = i_x;
		this.y = i_y;
	}

	public String getName() {
		return name;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
    public boolean isIfCollected() {
        return ifCollected;
    }
    public void setIfCollected(boolean ifCollected, Player player) {
        this.ifCollected = ifCollected;
        this.x = player.getX();
        this.y = player.getY();
    }
}
