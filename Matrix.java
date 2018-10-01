
public class Matrix {
	
	public int[][] matrix;

	
	public Matrix(int[] list) {
		if(list.length!=12) {
			System.out.println("The original pitch row must contain 12 tones!");
		}
		else {
			this.matrix = new int[12][12];
			matrix[0]=list;
			for(int i=1; i<12; i++) {
				this.matrix[i][0]=12-this.matrix[0][i];
			}
			for(int y=1; y<12; y++) {
				for(int x=1; x<12; x++) {
					if(this.matrix[y][0]+this.matrix[0][x]<12) {
						this.matrix[y][x]=this.matrix[y][0]+this.matrix[0][x];
					}
					if(this.matrix[y][0]+this.matrix[0][x]>=12) {
						this.matrix[y][x]=this.matrix[y][0]+this.matrix[0][x]-12;
					}
				}
			}
			
		}
	}
	
	public void printMatrix() {
		for(int i=0; i<12; i++) {
			for(int j=0; j<12; j++) {
				System.out.print(matrix[i][j]+",");
			}
			System.out.println("");
		}
	}
}
