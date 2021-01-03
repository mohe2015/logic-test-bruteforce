import java.util.Arrays;
import java.util.Scanner;

public class Main {
	
	static Scanner s = new Scanner(System.in);
	
	static void bruteforceF0(Codeline[] f0, Codeline[] f1, int index) {
		if (index == f0.length) {
			bruteforceF1(f0, f1, 0);
		} else {
			for (Condition condition : Condition.values()) {
				for (Action action : Action.values()) {
					f0[index].condition = condition;
					f0[index].action = action;
					bruteforceF0(f0, f1, index + 1);
				}
			}
		}
	}
	
	static void bruteforceF1(Codeline[] f0, Codeline[] f1, int index) {
		if (index == f1.length) {
			emulate(f0, f1);
		} else {
			for (Condition condition : Condition.values()) {
				for (Action action : Action.values()) {
					f1[index].condition = condition;
					f1[index].action = action;
					bruteforceF1(f0, f1, index + 1);
				}
			}
		}
	}
	
	static Field field = new Field();
	
	static void emulate(Codeline[] f0, Codeline[] f1) {
		boolean debug = false;//Math.random() < 0.00000001;
		
		if (debug) {
			System.out.println("--------------------------------");
		}
		
		try {
			field.reinitialize();
			Codeline[] pcf = f0;
			int pc = 0;

			for (int i = 0; i < 1000; i++) {
				if (debug) {
					System.out.println("f0: " + Arrays.toString(f0));
					System.out.println("f1: " + Arrays.toString(f1));
					System.out.println("pcf: " + Arrays.toString(pcf));
					System.out.println("pc: " + pc + " pci: " + pcf[pc]);
					System.out.println(field);
					
					String input = s.nextLine();
					if (input.equals("s")) {
						return;
					}
				}
				
				switch (pcf[pc].condition) {
					case UNCONDITIONAL: {
						break;
					}
					case GREEN: {
						if (field.field[field.posX][field.posY] != FieldType.GREEN && field.field[field.posX][field.posY] != FieldType.GREEN_WITH_X) {
							pc++;
							continue;
						}
						break;
					}
					case RED: {
						if (field.field[field.posX][field.posY] != FieldType.RED) {
							pc++;
							continue;
						}
						break;
					}
				}
				switch (pcf[pc].action) {
					case CALL_F0: {
						pcf = f0;
						pc = 0;
						break;
					}
					case CALL_F1: {
						pcf = f1;
						pc = 0;
						break;
					}
					case FORWARD: {
						field.posX += field.directionX;
						field.posY += field.directionY;
						
						if (field.field[field.posX][field.posY] == FieldType.GREEN_WITH_X) {
							field.field[field.posX][field.posY] = FieldType.GREEN;
							field.foundCrosses++;
							if (field.foundCrosses == 4) {
								System.out.println("found solution "+Arrays.toString(f0)+" "+Arrays.toString(f1));
								return;
								//System.exit(0);
							}
						}
						if (field.field[field.posX][field.posY] == FieldType.EMPTY) {
							return;
						}
						pc++;
						break;
					}
					case LEFT: {
						// -1  0 ->  0  1
						//  1  0 ->  0 -1
						//  0 -1 -> -1  0
						//  0  1 ->  1  0
						
						if (field.directionX == 0) {
							field.directionX = field.directionY;
							field.directionY = 0;
						} else if (field.directionY == 0) {
							field.directionY = -field.directionX;
							field.directionX = 0;
						}
						
						pc++;
						break;
					}
					case RIGHT: {
						// -1  0 ->  0 -1
						//  1  0 ->  0  1
						//  0 -1 ->  1  0
						//  0  1 -> -1  0
						
						if (field.directionX == 0) {
							field.directionX = -field.directionY;
							field.directionY = 0;
						} else if (field.directionY == 0) {
							field.directionY = field.directionX;
							field.directionX = 0;
						}
						
						pc++;
						break;
					}
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			
		}
	}

	public static void main(String[] args) {
		bruteforceF0(new Codeline[] { new Codeline(), new Codeline(), new Codeline(), new Codeline() }, new Codeline[] { new Codeline(), new Codeline(), new Codeline(), new Codeline() }, 0);
	}
}
