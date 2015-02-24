
public class main {
	
	public static void main(String[] arguments) {
		child1 child1 = new child1(5);
		System.out.println("variable1 = " + child1.variable1);
		System.out.println("and again = " + child1.getVariable1());
		child1.setVariable1(7);
		System.out.println("variable1 = " + child1.variable1);
		System.out.println("and again = " + child1.getVariable1());
	}

}
