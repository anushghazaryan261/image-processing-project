import ij.plugin.filter.PlugInFilter;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import ij.process.ColorProcessor;
import ij.IJ;
import java.awt.Color;

public class Low_Resolution implements PlugInFilter {

	ImagePlus inputImage;
	String title;

	public int setup(String args, ImagePlus im) {
		title = im.getShortTitle();
		inputImage = im;
		return DOES_RGB;
	}

	public void run(ImageProcessor inputIP) {
		int height = inputIP.getHeight();
		int width = inputIP.getWidth();
		int col, row;
		Color color;

		ImageProcessor outputIP = new ColorProcessor(width/5, height/5);

		for(col = 0; col < width/5 ; col++) {
			for(row = 0; row < height/5; row++) {
				int x = col*5, y = row*5;
				int r=0, g=0, b=0;
				for(int x1 = 0; x1 < 5; x1++) {
					for(int y1 = 0; y1 < 5; y1++) {
						color = new Color(inputIP.getPixel(x+x1, y+y1));
						r += color.getRed();
						g += color.getGreen();
						b += color.getBlue();
					}
				}
				int[] values = {r/25,g/25,b/25};
				outputIP.putPixel(col,row,values);
			}
		}
		(new ImagePlus("Low_resolution_" + title, outputIP)).show();
	}
}
