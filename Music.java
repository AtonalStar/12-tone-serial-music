import java.util.*;

public class Music {
	private static Scanner kb;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Please enter your original pitch row(Use C, C#, D, D#, E, F, F#, G, G#, A, A#, B and type 'Enter' everytime you type a tone):");
		kb = new Scanner(System.in);
		String[] origin = new String[12];
		for(int i=0; i<12; i++) {
			origin[i] = kb.nextLine();
		}
	//Check duplication
		Set<String> checkDup =new HashSet<String>();
		for(String s:origin) {
			checkDup.add(s);
		}
		if(checkDup.size()!=origin.length) {
			System.out.println("The original pitch row should not have duplication!");
		}
		
		//Generate the Clock Diagram model--The Clock Map
			String[] tones = {"C","C#","D","D#","E","F","F#","G","G#","A","A#","B"}; 
			HashMap<String,Integer> clockorder = new HashMap<String,Integer>();
			clockorder.put(origin[0], 0);
		
			int firstIndex = Arrays.asList(tones).indexOf(origin[0]); //Arrays.binarysearch cannot always work on unsorted array. 
			System.out.println("The firstIndex is: " + firstIndex);
			for(int i=0;i<12;i++) {
				if(i-firstIndex>=0) clockorder.put(tones[i],i-firstIndex);
				else clockorder.put(tones[i],i-firstIndex+12);
			}
		
			System.out.println("The Clock Map:");
			for (Map.Entry<String, Integer> entry : clockorder.entrySet()) {     
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}
		
		//Generate the Original Pitch Class
			int[] pitchClass = new int[12];
			for(int i=0;i<12;i++) {
				pitchClass[i]=clockorder.get(origin[i]);
			}
			System.out.println("The original row pitch class:");
			for(int i=0;i<12;i++) {
				System.out.print(pitchClass[i]+".");
			}
			System.out.println("");
		
		//Generate the Matrix
			System.out.println("The Matrix:");
			Matrix matrix = new Matrix(pitchClass);
			matrix.printMatrix();
	}

}

/*
	for(int i=0;i<12;i++) {

	}
*/


