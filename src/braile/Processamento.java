package braile;

import java.io.ByteArrayInputStream;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.photo.Photo;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Processamento {

	public static Image tonsCinza(String path) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat image = Imgcodecs.imread(path);
		Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2GRAY);
		Mat dest = new Mat();
		Core.add(dest, Scalar.all(0), dest);
		image.copyTo(dest, image);
		MatOfByte mtb = new MatOfByte();
		Imgcodecs.imencode(".png", image, mtb);
		return new Image(new ByteArrayInputStream(mtb.toArray()));
	}
	
	public static Image limiarizacao(Image img, Double limiar) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		for(int i=0; i<w; i++) {
			for(int j=0; j<h; j++) {
				Color oldCor = pr.getColor(i, j);
				Color newCor = null;
				if(oldCor.getRed()>limiar) {
					newCor = new Color(1, 1, 1, oldCor.getOpacity());
				}else
					newCor = new Color(0, 0, 0, oldCor.getOpacity());
				pw.setColor(i, j, newCor);
			}
		}
		return wi;
	}
	
	public static Image ruidos(String path) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat image = Imgcodecs.imread(path);
		Photo.fastNlMeansDenoising(image, image); 
		Mat dest = new Mat();
		Core.add(dest, Scalar.all(0), dest);
		image.copyTo(dest, image);
		MatOfByte mtb = new MatOfByte();
		Imgcodecs.imencode(".png", image, mtb);
		return new Image(new ByteArrayInputStream(mtb.toArray()));
	}
	
	public static Image negativa(Image img) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		for(int i=0; i<w; i++) {
			for(int j=0; j<h; j++) {
				Color cor = pr.getColor(i, j);
				Double red = 1 - cor.getRed();
				Double green = 1 - cor.getGreen();
				Double blue = 1 - cor.getBlue();
				Color corNova = new Color(red, green, blue, cor.getOpacity());
				pw.setColor(i, j, corNova);
			}
		}
		return wi;
	}
	
	public static Image dilatacao(String path) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		Mat src = Imgcodecs.imread(path);
		
		Mat destination = new Mat(src.rows(),src.cols(),src.type());
        
        destination = src;
        
        Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new  Size(2, 2));

        Imgproc.dilate(src, destination, element);
        
        	Core.add(destination, Scalar.all(0), destination);
        	destination.copyTo(destination, src);
		MatOfByte mtb = new MatOfByte();
		Imgcodecs.imencode(".png", destination, mtb);
		return new Image(new ByteArrayInputStream(mtb.toArray()));
	}
	
	public static Image erode(String path) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		Mat src = Imgcodecs.imread(path);
		
		Mat destination = new Mat(src.rows(),src.cols(),src.type());
        
        destination = src;
        
        Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new  Size(3, 3));

        Imgproc.erode(src, destination, element);
        
        	Core.add(destination, Scalar.all(0), destination);
        	destination.copyTo(destination, src);
		MatOfByte mtb = new MatOfByte();
		Imgcodecs.imencode(".png", destination, mtb);
		return new Image(new ByteArrayInputStream(mtb.toArray()));
	}
		
}

