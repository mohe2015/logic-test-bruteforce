
public class Field {
	
	int posX;
	int posY;
	
	FieldType[][] field;
	
	int foundCrosses;
	
	int directionX;
	int directionY;
	
	Field() {
		this.field = new FieldType[11][11];
		for (int x = 0; x < this.field.length; x++) {
			for (int y = 0; y < this.field[x].length; y++) {
				if (x == 5 && y == 5) {
					this.field[x][y] = FieldType.RED;
				} else if (x == 5 || y == 5) {
					this.field[x][y] = FieldType.GREEN;
				} else {
					this.field[x][y] = FieldType.EMPTY;
				}
			}
		}
	}
	
	void reinitialize() {
		this.field[5][0] = FieldType.GREEN_WITH_X;
		//this.field[5][this.field[5].length - 1] = FieldType.GREEN_WITH_X;
		this.field[0][5] = FieldType.GREEN_WITH_X;
		this.field[this.field.length - 1][5] = FieldType.GREEN_WITH_X;
		this.posX = 5;
		this.posY = this.field.length - 1;
		this.foundCrosses = 1;
		this.directionX = 0;
		this.directionY = -1;
	}
	
	@Override
	public String toString() {
		String field = "";
		for (int y = 0; y < this.field[0].length; y++) {
			for (int x = 0; x < this.field.length; x++) {
				if (posX == x && posY == y) {
					field += directionX == 1 ? "→" : directionX == -1 ? "←" : directionY == 1 ? "↓" : "↑";
				} else {
					field += this.field[x][y] == FieldType.GREEN ? "G" : this.field[x][y] == FieldType.RED ? "R" : this.field[x][y] == FieldType.GREEN_WITH_X ? "X": " ";
				}
			}
			field += "\n";
		}
		return field;
	}
}
