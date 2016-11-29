/*****
 * 
 * @author S. Tanimoto
 * This class offers a method for returning colors in a "progressive" sequence.
 * The colors at the beginning of the sequence contrast highly with each other, and later colors fill in other parts of the color space.
 * To use this, create an instance pc of ProgressiveColors, and then call the method: pc.progressiveColor(k).
 * For example, if k=0, then the color is [0, 0, 0] (black).  
 * If k=1, the color is [255,255,255] (white).
 * If k=2, the color is [255,0,0] (red).
 *
 * created Nov. 5, 2014.
 */
public class ProgressiveColors {
	static int NBITS = 24; // Size of the full color space, in bits per pixel.
	
	// Mapping used in procedure map3bits.
	static int[][] triples = { {0,0,0}, {1,1,1},{1,0,0},{0,1,1},{1,1,0},{0,0,1},{0,1,0},{1,0,1}};
	
	// A trivial constructor:
	public ProgressiveColors() { 
	}
	int[] reverseBits(int k) {
		int[] bits = new int[24];
		int quotient = k;
		for(int i=0; i<NBITS; i++){
			bits[i] = quotient % 2;
			quotient = quotient / 2;
		}
		return bits;
	}
	// Compute and return the kth progressive color as an array of 3 ints.
	int[] progressiveColor(int k) {
		int[] bits = reverseBits(k);
		int[] red_bits = {0,0,0,0,0,0,0,0};
		int[] green_bits = {0,0,0,0,0,0,0,0};
		int[] blue_bits = {0,0,0,0,0,0,0,0};
		for(int i=0; i<NBITS/3; i++) {
			int b0 = bits[i*3];
			int b1 = bits[i*3+1];
			int b2 = bits[i*3+2];
			int[] somebits = map3bits(b0, b1, b2);
			red_bits[i]=somebits[0];
			green_bits[i]=somebits[1];
			blue_bits[i]=somebits[2];
		}
		int red = fromBitsToNumber(red_bits);
		int green = fromBitsToNumber(green_bits);
		int blue = fromBitsToNumber(blue_bits);
				
		int[] rgb = {red, green, blue};
		return rgb;
	}
	// A sort of binary-to-decimal conversion, but treating bit 0 as a sign bit in an 8-bit 1's complement representation, 
	// taking "negative numbers" as 255 - x.
	int fromBitsToNumber(int[] bits){
		int sum=0;
		for(int i=1; i<bits.length; i++) {
			sum *= 2;
			sum += bits[i];
		}
		if (bits[0]==1) {
			sum = 255-sum;
		}
		return sum;
	}
	// Perform a transformation on 3 bits that helps keep successive colors contrasty.
	int[] map3bits(int b0, int b1, int b2) {
		int idx = b0+2*b1+4*b2;
		int[] rgb_bits = triples[idx];
		return rgb_bits;
	}
	// Main method is just for stand-alone testing.
	public static void main(String[] args) {
		ProgressiveColors pc = new ProgressiveColors();
		for(int i=0; i<16; i++) {
		   int[] c= pc.progressiveColor(i);
	       System.out.println("Color number "+i+": ");
	       for(int j = 0; j<3; j++) {
	    	   System.out.print(c[j]+" ");
	       }
	       System.out.println("");
		}
	}

}
