package pdi;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Pdi {
	
	public static Image canny(String path) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat image = Imgcodecs.imread(path);
		Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2GRAY);
		Imgproc.blur(image, image, new Size(3,3));
		Imgproc.Canny(image, image, 15, 45, 3, false);
		Mat dest = new Mat();
		Core.add(dest, Scalar.all(0), dest);
		image.copyTo(dest, image);
		MatOfByte mtb = new MatOfByte();
		Imgcodecs.imencode(".png", image, mtb);
		return new Image(new ByteArrayInputStream(mtb.toArray()));
	}
	
	
	public static Image laplace(String path) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat src = Imgcodecs.imread(path);

		Mat src_gray = new Mat(), dst = new Mat();
	    	int kernel_size = 3;
	    	int scale = 1;
	    	int delta = 0;
	    	int ddepth = CvType.CV_16S;
	    
	    	Imgproc.cvtColor( src, src_gray, Imgproc.COLOR_RGB2GRAY );
	    	Mat abs_dst = new Mat();
	    	Imgproc.Laplacian( src_gray, dst, ddepth, kernel_size, scale, delta, Core.BORDER_DEFAULT );
	    	// converting back to CV_8U
	    	Core.convertScaleAbs( dst, abs_dst );

		MatOfByte mtb = new MatOfByte();
		Imgcodecs.imencode(".png", abs_dst, mtb);
		return new Image(new ByteArrayInputStream(mtb.toArray()));

	}
	
	public static Image sobel(String path) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		Mat src = Imgcodecs.imread(path);
		Mat gray = new Mat();
		Imgproc.cvtColor( src, gray, Imgproc.COLOR_RGB2GRAY );
		//Imgproc.Sobel(gray, gray, gray.depth(), 1, 0, -1, 1.0, 0);
		//Imgproc.Sobel(gray, gray, gray.depth(), 1, 0);
		Imgproc.Sobel(gray, gray, gray.depth(), 2, 2);
	    
		Mat dest = new Mat();
		Core.add(dest, Scalar.all(0), dest);
		gray.copyTo(dest, gray);
		MatOfByte mtb = new MatOfByte();
		Imgcodecs.imencode(".png", gray, mtb);
		return new Image(new ByteArrayInputStream(mtb.toArray()));
	}
	
	public static Image erode(String path) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		Mat src = Imgcodecs.imread(path);
		
		Mat destination = new Mat(src.rows(),src.cols(),src.type());
        
        destination = src;

        int erosion_size = 5;
        
        Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new  Size(2*erosion_size + 1, 2*erosion_size+1));
        Imgproc.erode(src, destination, element);
        
        	Core.add(destination, Scalar.all(0), destination);
        	destination.copyTo(destination, src);
		MatOfByte mtb = new MatOfByte();
		Imgcodecs.imencode(".png", destination, mtb);
		return new Image(new ByteArrayInputStream(mtb.toArray()));
	}
	
	public static Image dilate(String path) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		Mat src = Imgcodecs.imread(path);
		
		Mat destination = new Mat(src.rows(),src.cols(),src.type());
        
        destination = src;

        int dilation_size = 5;
        
        Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new  Size(2*dilation_size + 1, 2*dilation_size+1));

        Imgproc.dilate(src, destination, element);
        
        	Core.add(destination, Scalar.all(0), destination);
        	destination.copyTo(destination, src);
		MatOfByte mtb = new MatOfByte();
		Imgcodecs.imencode(".png", destination, mtb);
		return new Image(new ByteArrayInputStream(mtb.toArray()));
	}
	
	
	
	public static Image questao1(Image img, Color corGrade, Integer distancia) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		int contador = distancia;
		for(int i=0; i<w; i++) {
			for(int j=0; j<h; j++) {
				if(i==contador-1) {
					Color cor = corGrade;
					pw.setColor(i, j, cor);
					if(j==h-1) {
						contador = contador + distancia;
					}
				}
				else {
					Color cor = pr.getColor(i, j);
					pw.setColor(i, j, cor);					
				}
			}
		}
		return wi;
	}
	

	public static Image questao2(Image img) { //Inverte segunda parte da imagem
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		int pedacoImg = h/2;
		
		Image imgInvertida = img;
		imgInvertida = rotacionar(imgInvertida);
		imgInvertida = rotacionar(imgInvertida);
		PixelReader prInvertida = imgInvertida.getPixelReader();

		for(int i=0; i<w; i++) {
			for(int j=0; j<=pedacoImg; j++) {
					//Primeira parte da imagem fica normal
					Color cor = pr.getColor(i, j);
					pw.setColor(i, j, cor);
			}
		}
		
		for(int i=0; i<w; i++) {
			for(int j=pedacoImg , k = 0; j<h; j++ , k++) {		
				//Inverte segunda parte da imagem
				Color cor = prInvertida.getColor(i, k);
				pw.setColor(i, j, cor);		
			}
		}
		
		return wi;
	}
	
	public static Boolean[] questao3(Double clickX, Double clickY, Double soltouX, Double soltouY, Image img) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		PixelReader pr = img.getPixelReader();
		Boolean cores[] = new Boolean[3];
		cores[0] = false; //red
		cores[1] = false; //blue
		cores[2] = false; //green
		
		for(int i=0; i<w; i++) {
			for(int j=0; j<h; j++) {
				
				Color cor = pr.getColor(i, j);
					
				if(i>=clickX & i <= soltouX) {
					if(j >= clickY & j <= soltouY ) {
						if(cor.getRed() == 1 & cor.getGreen() == 0 & cor.getBlue() == 0) {
							cores[0] = true; // Tem vermelho na parte selecionada
						}
						else if(cor.getRed() == 0 & cor.getGreen() == 1 & cor.getBlue() == 0) {
							cores[1] = true; // Tem verde na parte selecionada
						}
						else if(cor.getRed() == 0 & cor.getGreen() == 0 & cor.getBlue() == 1) {
							cores[2] = true; // Tem azul na parte selecionada
						}
					}
				}
				if(j>=clickY & j <= soltouY) {
					if(i > clickX & i < soltouX ) {
						
						if(cor.getRed() == 1 & cor.getGreen() == 0 & cor.getBlue() == 0) {
							cores[0] = true; // Tem vermelho na parte selecionada
						}
						else if(cor.getRed() == 0 & cor.getGreen() == 1 & cor.getBlue() == 0) {
							cores[1] = true; // Tem verde na parte selecionada
						}
						else if(cor.getRed() == 0 & cor.getGreen() == 0 & cor.getBlue() == 1) {
							cores[2] = true; // Tem azul na parte selecionada
						}
					}
				}
			
			}
		}
		
		return cores;
	}
	
	public static Image criaImgRGB() {
		int w = 200;
		int h = 300;
		WritableImage wi = new WritableImage(w, h);
		PixelWriter pw = wi.getPixelWriter();
		
		for(int i=0; i<w; i++) {
			for(int j=0; j<h; j++) {
				if(j<100) {
					Color cor = new Color(1, 0, 0, 1);
					pw.setColor(i, j, cor);					
				}else if(j<200) {
					Color cor = new Color(0, 1, 0, 1);
					pw.setColor(i, j, cor);										
				}else {
					Color cor = new Color(0, 0, 1, 1);
					pw.setColor(i, j, cor);					
				}
			}
		}
		
		return wi;
	}
	
	
	public static Image filtrosVertical(Image img) { //vertical
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		int pedacoImg = w/3;
		Double media;
		for(int i=0; i<w; i++) {
			for(int j=0; j<h; j++) {
				if (i>=0 && i<=pedacoImg) { //Tons de Cinza no primeiro 1/3 da imagem
					Color cor = pr.getColor(i, j);
					media = (cor.getRed() + cor.getGreen() + cor.getBlue()) / 3;
					Color corNova = new Color(media, media, media, cor.getOpacity());
					pw.setColor(i, j, corNova);					
				}
				else if(i>pedacoImg && i<=(pedacoImg*2)) { //Limiarizacao na segunda parte da imagem
					Color oldCor = pr.getColor(i, j);
					Color newCor = null;
					if(oldCor.getRed()>170.0/255.0) {
						newCor = new Color(1, 1, 1, oldCor.getOpacity());
					}else
						newCor = new Color(0, 0, 0, oldCor.getOpacity());
					pw.setColor(i, j, newCor);
				}
				else if(i>(pedacoImg*2)){ //Negativa na ultima parte da imagem
					Color cor = pr.getColor(i, j);
					Double red = 1 - cor.getRed();
					Double green = 1 - cor.getGreen();
					Double blue = 1 - cor.getBlue();
					Color corNova = new Color(red, green, blue, cor.getOpacity());
					pw.setColor(i, j, corNova);
				}
			}
		}
		return wi;
	}
	
	public static Image filtrosHorizontal(Image img) { //horizontal
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		int pedacoImg = h/3;
		Double media;
		for(int i=0; i<w; i++) {
			for(int j=0; j<h; j++) {
				if (j>=0 && j<=pedacoImg) { //Tons de Cinza no primeiro 1/3 da imagem
					Color cor = pr.getColor(i, j);
					media = (cor.getRed() + cor.getGreen() + cor.getBlue()) / 3;
					Color corNova = new Color(media, media, media, cor.getOpacity());
					pw.setColor(i, j, corNova);					
				}
				else if(j>pedacoImg && j<=(pedacoImg*2)) { //Limiarizacao na segunda parte da imagem
					Color oldCor = pr.getColor(i, j);
					Color newCor = null;
					if(oldCor.getRed()>170.0/255.0) {
						newCor = new Color(1, 1, 1, oldCor.getOpacity());
					}else
						newCor = new Color(0, 0, 0, oldCor.getOpacity());
					pw.setColor(i, j, newCor);
				}
				else if(j>(pedacoImg*2)){ //Negativa na ultima parte da imagem
					Color cor = pr.getColor(i, j);
					Double red = 1 - cor.getRed();
					Double green = 1 - cor.getGreen();
					Double blue = 1 - cor.getBlue();
					Color corNova = new Color(red, green, blue, cor.getOpacity());
					pw.setColor(i, j, corNova);
				}
			}
		}
		return wi;
	}
	
	public static Image adicao(Image img1, Image img2, Double pc1, Double pc2) {
		int w1 = (int)img1.getWidth();
		int h1 = (int)img1.getHeight();
		
		int w2 = (int)img2.getWidth();
		int h2 = (int)img2.getHeight();
		
		int w = Math.min(w1, w2);
		int h = Math.min(h1, h2);		
		
		PixelReader pr1 = img1.getPixelReader();
		PixelReader pr2 = img2.getPixelReader();
		pc1 = pc1/100;
		pc2 = pc2/100;
		
		WritableImage wi = new WritableImage(w, h);			
		PixelWriter pw = wi.getPixelWriter();
		for(int i=0; i<w; i++) {
			for(int j=0; j<h; j++) {
				Color cor1 = pr1.getColor(i, j);
				Color cor2 = pr2.getColor(i, j);
				
				Color corNova = new Color(cor1.getRed()*pc1 + cor2.getRed()*pc2, cor1.getGreen()*pc1 + cor2.getGreen()*pc2, cor1.getBlue()*pc1 + cor2.getBlue()*pc2, cor1.getOpacity());
				pw.setColor(i, j, corNova);
			}
		}

		return wi;
	}
	
	public static Image subtracao(Image img1, Image img2) {
		int w1 = (int)img1.getWidth();
		int h1 = (int)img1.getHeight();
		
		int w2 = (int)img2.getWidth();
		int h2 = (int)img2.getHeight();

		int w = Math.min(w1, w2);
		int h = Math.min(h1, h2);		
		
		PixelReader pr1 = img1.getPixelReader();
		PixelReader pr2 = img2.getPixelReader();
		
		WritableImage wi = new WritableImage(w, h);			
		PixelWriter pw = wi.getPixelWriter();
		for(int i=0; i<w; i++) {
			for(int j=0; j<h; j++) {
				Color cor1 = pr1.getColor(i, j);
				Color cor2 = pr2.getColor(i, j);
				double nRed = cor1.getRed() - cor2.getRed();
				double nGreen = cor1.getGreen() - cor2.getGreen();
				double nBlue = cor1.getBlue() - cor2.getBlue();
				if(nRed<0)
					nRed = nRed * (-1);
				if(nGreen<0)
					nGreen = nGreen * (-1);
				if(nBlue<0)
					nBlue = nBlue * (-1);
				Color corNova = new Color(nRed, nGreen, nBlue, cor1.getOpacity());
				pw.setColor(i, j, corNova);
			}
		}

		return wi;
	}
	
	public static Image inverte(Image img) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		WritableImage wi = new WritableImage(h, w);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		
		for(int i=0; i<w; i++) {
			for(int j=0; j<h; j++) {
				Color cor = pr.getColor(i, j);
				Color corNova = new Color(cor.getRed(), cor.getGreen(), cor.getBlue(), cor.getOpacity());
				pw.setColor(j, i, corNova);
			}
		}
		return wi;
	}
	
	public static Image desenho(Double clickX, Double clickY, Double soltouX, Double soltouY, Image img) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		
		for(int i=0; i<w; i++) {
			for(int j=0; j<h; j++) {
	
				Color cor = pr.getColor(i, j);
				pw.setColor(i, j, cor);
				
				if(i==clickX || i == soltouX) {
					if(j >= clickY & j <= soltouY ) {
						Color corNova = new Color(1, 0, 0, 1);
						pw.setColor(i, j, corNova);						
					}
				}
				if(j==clickY || j == soltouY) {
					if(i > clickX & i < soltouX ) {
						Color corNova = new Color(1, 0, 0, 1);
						pw.setColor(i, j, corNova);						
					}
				}
			
			}
		}
		return wi;
	}
	
	public static Image marcaFiltro(Double clickX, Double clickY, Double soltouX, Double soltouY, Image img) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		
		for(int i=0; i<w; i++) {
			for(int j=0; j<h; j++) {
				
				Color cor = pr.getColor(i, j);
				pw.setColor(i, j, cor);
				
				if(i>=clickX & i <= soltouX) {
					if(j >= clickY & j <= soltouY ) {
						cor = pr.getColor(i, j);
						Double red = 1 - cor.getRed();
						Double green = 1 - cor.getGreen();
						Double blue = 1 - cor.getBlue();
						Color corNova = new Color(red, green, blue, cor.getOpacity());
						pw.setColor(i, j, corNova);						
					}
				}
				if(j>=clickY & j <= soltouY) {
					if(i > clickX & i < soltouX ) {
						cor = pr.getColor(i, j);
						Double red = 1 - cor.getRed();
						Double green = 1 - cor.getGreen();
						Double blue = 1 - cor.getBlue();
						Color corNova = new Color(red, green, blue, cor.getOpacity());
						pw.setColor(i, j, corNova);						
					}
				}
			
			}
		}
		return wi;
	}
	
	
	public static Image aumentar(Image img) {
		int w = (int)img.getWidth() * 2;
		int h = (int)img.getHeight() * 2;
		
		int wOld = (int)img.getWidth();
		int hOld= (int)img.getHeight();
		
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		
		ArrayList<Color> cores = new ArrayList<Color>();
		for(int i=0; i<wOld; i++) {
			for(int j=0; j<hOld; j++) {
				Color cor = pr.getColor(i, j);
				cores.add(cor);
			}
		}
		
		int contador = 0;
		for(int i=0; i<w; i = i+2) {
			for(int j=0; j<h; j++) {
				if (contador<cores.size()) {
					double nred = cores.get(contador).getRed();
					double ngreen = cores.get(contador).getGreen();
					double nblue = cores.get(contador).getBlue();
					
					Color nova = new Color(nred,ngreen, nblue, 1.0);
					pw.setColor(i, j, nova);
					pw.setColor(i+1, j, nova);
						
					if(j%2!=0) {
						contador++;
					}
				}	
			}
		}
	
	
		return wi;
	}
	
	
	public static Image diminuir(Image img) { //diminui 40 px de altura e largura
		int w = (int)img.getWidth() - 40;
		int h = (int)img.getHeight() - 40;
		
		int wOld = (int)img.getWidth();
		int hOld= (int)img.getHeight();
		
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		
		ArrayList<Color> cores = new ArrayList<Color>();
		for(int i=20; i<wOld - 20; i++) {
			for(int j=20; j<hOld - 20; j++) {	
					Color cor = pr.getColor(i, j);
					cores.add(cor);
			}
		}
		
		int contador = 0;
		for(int i=0; i<w; i++) {
			for(int j=0; j<h; j++) {
				Color cor = cores.get(contador);
				pw.setColor(i, j, cor);					

				contador++;
			}
		}
		
	
		return wi;
	}
	
	
	public static Image borda3px(Image img) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		
		for(int i=0; i<w; i++) {
			for(int j=0; j<h; j++) {
				if(i<3 || j<3 || i>w-4 || j>h-4) {
					Color corNova = new Color(1, 0, 0, 1);
					pw.setColor(i, j, corNova);
				}else{
					Color cor = pr.getColor(i, j);
					//Color corNova = new Color(cor.getRed(), cor.getGreen(), cor.getBlue(), cor.getOpacity());
					pw.setColor(i, j, cor);					
				}
			}
		}
		
		return wi;
	}
	
	
	
	public static Image rotacionar(Image img) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		WritableImage wi = new WritableImage(h, w);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		
		for(int i=0; i<w; i++) {
			for(int j=0; j<h; j++) {
				Color cor = pr.getColor(i, j);
				Color corNova = new Color(cor.getRed(), cor.getGreen(), cor.getBlue(), cor.getOpacity());
				pw.setColor(h-1-j, i, corNova);
			}
		}
		
		return wi;
	}
	
	
	public static int quadradoOuCirculo(Image img) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		PixelReader pr = img.getPixelReader();

	
		for(int i=0; i<w; i++) {
			for(int j=0; j<h; j++) {
				if(pr.getColor(i, j).getRed() == 0 & pr.getColor(i, j).getGreen() == 0  & pr.getColor(i, j).getBlue() == 0 ) {//Achou o primeiro pixel preto
					//Se o pixel da direita e o pixel de baixo forem pretos então é um quadrado
					if((pr.getColor(i+1, j).getRed() == 0 & pr.getColor(i+1, j).getGreen() == 0  & pr.getColor(i+1, j).getBlue() == 0) & (pr.getColor(i, j+1).getRed() == 0 && pr.getColor(i, j+1).getGreen() == 0  && pr.getColor(i, j+1).getBlue() == 0)) {
						return 1;
					}
					else {
						return 2;
					}
				}
			}
		}		
		return 0;
	}
	
	public static int[] histograma(Image img, int canal) {
		int[] qt = new int [256];
		PixelReader pr = img.getPixelReader();
		int w = (int)img.getWidth();
		int h =(int)img.getHeight();
		for(int i = 0; i < w ; i++) {
			for(int j = 0; j < h ; j++) {
				if (canal == 1) 
					qt[(int)(pr.getColor(i, j).getRed()*255)]++;
				else if (canal == 2)
					qt[(int)(pr.getColor(i, j).getGreen()*255)]++;
				else
					qt[(int)(pr.getColor(i, j).getBlue()*255)]++;
				
			}
		}
		return qt;
	}
	
	public static int[] histogramaAc(int[] hist) {
		int[] ret = new int [hist.length];
		int v1 = hist[0];
			for(int i = 0; i < hist.length-1 ; i++) {
				ret[i] = v1;
				v1 += hist[i+1];
			}
		return ret;
	}
	
	public static Image equalizar(Image img) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		
		int[] histR = histograma(img, 1); //histograma red
		int[] histG = histograma(img, 2); //histograma green
		int[] histB = histograma(img, 3); //histograma blue
		int[] hacR = histogramaAc(histR); // histograma acumulado red
		int[] hacG = histogramaAc(histG); // histograma acumulado green
		int[] hacB = histogramaAc(histB); // histograma acumulado blue
		
		double n = w * h;
		
		//Calculo L
		int qtR = 255;
		int qtG = 255;
		int qtB = 255;
		
		for(int i = 0; i < 255; i++) {
			if(histR[i] == 0) 
				qtR--;
			if(histG[i] == 0) 
				qtG--;
			if(histB[i] == 0) 
				qtB--;
		}

		
		//Aplica a formula ((L - 1)/N) * HistAc
		for(int i=0; i<w; i++) {
			for(int j=0; j<h; j++) {
				Color cor = pr.getColor(i, j);

				double intensidadeR = cor.getRed() * 255;
				double intensidadeG = cor.getGreen() * 255;
				double intensidadeB = cor.getBlue() * 255;
				
				double red = (((qtR-1)/n) * hacR[(int) (intensidadeR)]) / 255;
				double green = (((qtG-1)/n) * hacG[(int) (intensidadeG)]) / 255;
				double blue = (((qtB-1)/n) * hacB[(int) (intensidadeB)]) / 255;
				
				Color corNova = new Color(red, green, blue, cor.getOpacity());
				pw.setColor(i, j, corNova);
			}
		}
		
		return wi;
		
	}

	
	public static int[] histogramaUnico(Image img) {
		int[] qt = new int [256];
		PixelReader pr = img.getPixelReader();
		int w = (int)img.getWidth();
		int h =(int)img.getHeight();
		for(int i = 0; i < w ; i++) {
			for(int j = 0; j < h ; j++) {
				qt[(int)(pr.getColor(i, j).getRed()*255)]++;
				qt[(int)(pr.getColor(i, j).getGreen()*255)]++;
				qt[(int)(pr.getColor(i, j).getBlue()*255)]++;
			}
		}
		return qt;
	}
	
	public static Image segmentar(Image img, int divisoes) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		
		int[] histR = histograma(img, 1); //histograma red
		int[] histG = histograma(img, 2); //histograma green
		int[] histB = histograma(img, 3); //histograma blue
		
		//int[] histU = histogramaUnico(img);
		
		int partHist = histR.length / divisoes;
		int controle = 0;
				
		ArrayList<Integer> maiorIntensidadeR = new ArrayList<Integer>();
		ArrayList<Integer> maiorIntensidadeG = new ArrayList<Integer>();
		ArrayList<Integer> maiorIntensidadeB = new ArrayList<Integer>();
		ArrayList<Integer> posicaoR = new ArrayList<Integer>();
		ArrayList<Integer> posicaoG = new ArrayList<Integer>();
		ArrayList<Integer> posicaoB = new ArrayList<Integer>();
				
		for (int a=0; a<5; a++) {
			maiorIntensidadeR.add(0);
			maiorIntensidadeG.add(0);
			maiorIntensidadeB.add(0);
			posicaoR.add(0);
			posicaoG.add(0);
			posicaoB.add(0);	
		}
		
		for(int i=0; i<256; i++) {
			if(i < partHist && controle < divisoes) {
				if(histR[i] > maiorIntensidadeR.get(controle)) {
					maiorIntensidadeR.set(controle, histR[i]);
					posicaoR.set(controle, i);
				}
				if(histG[i] > maiorIntensidadeG.get(controle)) {
					maiorIntensidadeG.set(controle, histG[i]);
					posicaoG.set(controle, i);
				}
				if(histB[i] > maiorIntensidadeB.get(controle)) {
					maiorIntensidadeB.set(controle, histB[i]);
					posicaoB.set(controle, i);
				}
			}
			else if(i > partHist) {
				partHist = partHist*2;
				controle++;
				i--;
			}
		}
		
		partHist = histR.length / divisoes;
		
		for(int i=0; i<w; i++) {
			for(int j=0; j<h; j++) {
				Color cor = pr.getColor(i, j);

				double intensidadeR = cor.getRed() * 255;
				double intensidadeG = cor.getGreen() * 255;
				double intensidadeB = cor.getBlue() * 255;
								
				//Retorna controle = parte da divisao do histograma
				int controleR = retornaControle(divisoes, partHist, (int)intensidadeR);
				int controleG = retornaControle(divisoes, partHist, (int)intensidadeG);
				int controleB = retornaControle(divisoes, partHist, (int)intensidadeB);
				
				double red = posicaoR.get(controleR)/255.0;
				double green = posicaoG.get(controleG)/255.0;
				double blue = posicaoB.get(controleB)/255.0;
				
				Color corNova = new Color(red, green, blue, cor.getOpacity());
				pw.setColor(i, j, corNova);
			}
		}
		return ruidos(wi, 3, true);
		//return wi;
	}
	
	public static int retornaControle(int divisoes, int partHist, int pos) {
		int controle = 0;
		for(int k=0; k<256; k++) {
			if(k < partHist && controle < divisoes) {
				if(pos < partHist) {
					k = 256;
				}
			}
			else if(k > partHist) {
				partHist = partHist*2;
				controle++;
				k--;
			}
		}
		return controle;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void getGrafico(Image img,BarChart<String, Number> grafico){
		CategoryAxis eixoX = new CategoryAxis();
		NumberAxis eixoY = new NumberAxis();
	    eixoX.setLabel("Canal");       
	    eixoY.setLabel("valor");
	    XYChart.Series vlr = new XYChart.Series();
	    vlr.setName("Intensidade");
	    int[] hist = histogramaUnico(img);
	    for (int i=0; i<hist.length; i++) {
	    	vlr.getData().add(new XYChart.Data(i+"", hist[i]));
		}
	  	grafico.getData().addAll(vlr);
	}
	
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public static void getGrafico(Image img,BarChart<String, Number> grafico){
//		CategoryAxis eixoX = new CategoryAxis();
//		NumberAxis eixoY = new NumberAxis();
//	    eixoX.setLabel("Canal");       
//	    eixoY.setLabel("valor");
//	    XYChart.Series vlrR = new XYChart.Series();
//	    vlrR.setName("R");
//	    int[] histR = histograma(img, 1);
//	    for (int i=0; i<histR.length; i++) {
//	    	vlrR.getData().add(new XYChart.Data(i+"", histR[i]));
//		}
//	    XYChart.Series vlrG = new XYChart.Series();
//	    vlrG.setName("G");
//	    int[] histG = histograma(img, 2);
//	    for (int i=0; i<histG.length; i++) {
//	    	vlrG.getData().add(new XYChart.Data(i+"", histG[i]));
//	    }
//	    XYChart.Series vlrB = new XYChart.Series();
//	    vlrB.setName("B");
//	    int[] histB = histograma(img, 3);
//	    for (int i=0; i<histB.length; i++) {
//	    	vlrB.getData().add(new XYChart.Data(i+"", histB[i]));
//	    }
//	    grafico.getData().addAll(vlrR,vlrG,vlrB);
//	}
	
	public static Image tonsCinzaArit(Image img) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		Double media;
		for(int i=0; i<w; i++) {
			for(int j=0; j<h; j++) {
				Color cor = pr.getColor(i, j);
				//Color corNova = new Color(cor.getRed()/2, cor.getGreen()/2, cor.getBlue()/2, cor.getOpacity()); //escurece img
				//Color corNova = new Color(cor.getBlue(), cor.getBlue(), cor.getBlue(), cor.getOpacity());
				media = (cor.getRed() + cor.getGreen() + cor.getBlue()) / 3;
				Color corNova = new Color(media, media, media, cor.getOpacity());
				pw.setColor(i, j, corNova);
			}
		}
		return wi;
	}

	public static Image tonsCinzaPond(Image img, Integer red, Integer green, Integer blue) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		Double media;
		for(int i=0; i<w; i++) {
			for(int j=0; j<h; j++) {
				Color cor = pr.getColor(i, j);
				media =( cor.getRed() * red + cor.getGreen() * green + cor.getBlue() * blue ) / 100;
				Color corNova = new Color(media, media, media, cor.getOpacity());
				pw.setColor(i, j, corNova);
			}
		}
		return wi;
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

	public static Image ruidos(Image img, int tipoVizinho, boolean isMediana) {
		int w = (int)img.getWidth()-1;
		int h = (int)img.getHeight()-1;
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();

		Color cor = null;
	
		
		for(int i=0; i<w; i++) {
			for(int j=0; j<h; j++) {
				cor = pr.getColor(i, j);
				Pixel px = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), i, j);
				
				//VIZINHOS 3X3
				if(tipoVizinho == 3) { 
					if(px.posC == 0 && px.posL == 0) {
						cor = pr.getColor(0, 1);
						px.vizinhos3[0] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), 0, 1);
						cor = pr.getColor(1, 0);
						px.vizinhos3[1] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), 1, 0);
						cor = pr.getColor(1, 1);
						px.vizinhos3[2] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), 1, 1);
					} else if(px.posC == 0 && px.posL == h) {
						cor = pr.getColor(0, h-1);
						px.vizinhos3[0] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), 0, h-1);
						cor = pr.getColor(1, h);
						px.vizinhos3[1] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), 1, h);
						cor = pr.getColor(1, h-1);
						px.vizinhos3[2] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), 1, h-1);
					} else if(px.posL == 0 && px.posC == w) {
						cor = pr.getColor(w-1, 0);
						px.vizinhos3[0] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), w-1, 0);
						cor = pr.getColor(w, 1);
						px.vizinhos3[1] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), w, 1);
						cor = pr.getColor(w-1, 1);
						px.vizinhos3[2] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), w-1, 1);
					} else if(px.posC == w && px.posL == h) {
						cor = pr.getColor(w, h-1);
						px.vizinhos3[0] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), w, h-1);
						cor = pr.getColor(w-1, h-1);
						px.vizinhos3[1] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), w-1, h-1);
						cor = pr.getColor(w-1, h);
						px.vizinhos3[2] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), w-1, h);
					} else if(px.posL == 0) { // CORRIGIDO - (COLUNA , LINHA) - SEMPRE ASSIM
						cor = pr.getColor(px.posC+1, 0);
						px.vizinhos3[0] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC+1, 0);
						cor = pr.getColor(px.posC-1, 0);
						px.vizinhos3[1] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC-1, 0);
						cor = pr.getColor(px.posC+1, 1);
						px.vizinhos3[2] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC+1, 1);
						cor = pr.getColor(px.posC, 1);
						px.vizinhos3[3] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC, 1);
						cor = pr.getColor(px.posC-1, 1);
						px.vizinhos3[4] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC-1, 1);
					} else if(px.posC == 0) {
						cor = pr.getColor(0, px.posL-1);
						px.vizinhos3[0] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), 0, px.posL-1);
						cor = pr.getColor(0, px.posL+1);
						px.vizinhos3[1] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), 0, px.posL+1);
						cor = pr.getColor(1, px.posL-1);
						px.vizinhos3[2] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), 1, px.posL-1);
						cor = pr.getColor(1, px.posL);
						px.vizinhos3[3] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), 1, px.posL);			
						cor = pr.getColor(1, px.posL+1);
						px.vizinhos3[4] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), 1, px.posL+1);
					} else if(px.posC == w) {
						cor = pr.getColor(w, px.posL-1);
						px.vizinhos3[0] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), w, px.posL-1);
						cor = pr.getColor(w, px.posL+1);
						px.vizinhos3[1] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), w, px.posL+1);
						cor = pr.getColor(w-1, px.posL-1);
						px.vizinhos3[2] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), w-1, px.posL-1);
						cor = pr.getColor(w-1, px.posL);
						px.vizinhos3[3] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), w-1, px.posL);			
						cor = pr.getColor(w-1, px.posL+1);
						px.vizinhos3[4] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), w-1, px.posL+1);
					} else if(px.posL == h) {
						cor = pr.getColor(px.posC+1, h);
						px.vizinhos3[0] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC+1, h);
						cor = pr.getColor(px.posC-1, h);
						px.vizinhos3[1] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC-1, h);
						cor = pr.getColor(px.posC+1, h-1);
						px.vizinhos3[2] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC+1, h-1);
						cor = pr.getColor(px.posC, h-1);
						px.vizinhos3[3] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC, h-1);
						cor = pr.getColor(px.posC-1, h-1);
						px.vizinhos3[4] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC-1, h-1);
					} else {
						cor = pr.getColor(px.posC, px.posL-1);
						px.vizinhos3[0] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC, px.posL-1);
						cor = pr.getColor(px.posC,px.posL+1);
						px.vizinhos3[1] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC,px.posL+1);
						cor = pr.getColor(px.posC+1, px.posL);
						px.vizinhos3[2] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC+1, px.posL);
						cor = pr.getColor(px.posC-1, px.posL);
						px.vizinhos3[3] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC-1, px.posL);
						cor = pr.getColor(px.posC-1, px.posL-1);
						px.vizinhos3[4] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC-1, px.posL-1);
						cor = pr.getColor(px.posC+1, px.posL-1);
						px.vizinhos3[5] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC+1, px.posL-1);
						cor = pr.getColor(px.posC+1, px.posL+1);
						px.vizinhos3[6] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC+1, px.posL+1);
						cor = pr.getColor(px.posC-1, px.posL+1);
						px.vizinhos3[7] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC-1, px.posL+1);
					}
					
					if(isMediana) {
						double red = px.medianaRed(px.vizinhos3);
						double blue = px.medianaBlue(px.vizinhos3);
						double green = px.medianaGreen(px.vizinhos3);
						Color corNova = new Color(red, green, blue, cor.getOpacity());
						pw.setColor(px.posC, px.posL, corNova);					
						
					}else {
						double red = px.mediaRed(px.vizinhos3);
						double blue = px.mediaBlue(px.vizinhos3);
						double green = px.mediaGreen(px.vizinhos3);
						Color corNova = new Color(red, green, blue, cor.getOpacity());
						pw.setColor(px.posC, px.posL, corNova);		
						
					}
					
					
				}
				//VIZINHOS CRUZ
				else if(tipoVizinho == 2) {
					if(px.posC == 0 && px.posL == 0) {
						cor = pr.getColor(0, 1);
						px.vizinhosCruz[0] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), 0, 1);
						cor = pr.getColor(1, 0);
						px.vizinhosCruz[1] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), 1, 0);
					} else if(px.posC == 0 && px.posL == h) {
						cor = pr.getColor(0, h-1);
						px.vizinhosCruz[0] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), 0, h-1);
						cor = pr.getColor(1, h);
						px.vizinhosCruz[1] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), 1, h);
					} else if(px.posL == 0 && px.posC == w) {
						cor = pr.getColor(w-1, 0);
						px.vizinhosCruz[0] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), w-1, 0);
						cor = pr.getColor(w, 1);
						px.vizinhosCruz[1] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), w, 1);
					} else if(px.posC == w && px.posL == h) {
						cor = pr.getColor(w, h-1);
						px.vizinhosCruz[0] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), w, h-1);
						cor = pr.getColor(w-1, h);
						px.vizinhosCruz[1] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), w-1, h);
					} else if(px.posL == 0) {
						cor = pr.getColor(px.posC+1, 0);
						px.vizinhosCruz[0] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC+1, 0);
						cor = pr.getColor(px.posC-1, 0);
						px.vizinhosCruz[1] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC-1, 0);
						cor = pr.getColor(px.posC, 1);
						px.vizinhosCruz[2] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC, 1);
					} else if(px.posC == 0) {
						cor = pr.getColor(0, px.posL-1);
						px.vizinhosCruz[0] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), 0, px.posL-1);
						cor = pr.getColor(0, px.posL+1);
						px.vizinhosCruz[1] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), 0, px.posL+1);
						cor = pr.getColor(1, px.posL);
						px.vizinhosCruz[2] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), 1, px.posL);			
					} else if(px.posC == w) {
						cor = pr.getColor(w, px.posL-1);
						px.vizinhosCruz[0] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), w, px.posL-1);
						cor = pr.getColor(w, px.posL+1);
						px.vizinhosCruz[1] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), w, px.posL+1);
						cor = pr.getColor(w-1, px.posL);
						px.vizinhosCruz[2] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), w-1, px.posL);			
					} else if(px.posL == h) {
						cor = pr.getColor(px.posC+1, h);
						px.vizinhosCruz[0] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC+1, h);
						cor = pr.getColor(px.posC-1, h);
						px.vizinhosCruz[1] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC-1, h);
						cor = pr.getColor(px.posC, h-1);
						px.vizinhosCruz[2] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC, h-1);
					} else {
						cor = pr.getColor(px.posC, px.posL-1);
						px.vizinhosCruz[0] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC, px.posL-1);
						cor = pr.getColor(px.posC,px.posL+1);
						px.vizinhosCruz[1] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC,px.posL+1);
						cor = pr.getColor(px.posC+1, px.posL);
						px.vizinhosCruz[2] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC+1, px.posL);
						cor = pr.getColor(px.posC-1, px.posL);
						px.vizinhosCruz[3] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC-1, px.posL);
					}
					
					if(isMediana) {
						double red = px.medianaRed(px.vizinhosCruz);
						double blue = px.medianaBlue(px.vizinhosCruz);
						double green = px.medianaGreen(px.vizinhosCruz);
						Color corNova = new Color(red, green, blue, cor.getOpacity());
						pw.setColor(px.posC, px.posL, corNova);					
						
					}else {
						double red = px.mediaRed(px.vizinhosCruz);
						double blue = px.mediaBlue(px.vizinhosCruz);
						double green = px.mediaGreen(px.vizinhosCruz);
						Color corNova = new Color(red, green, blue, cor.getOpacity());
						pw.setColor(px.posC, px.posL, corNova);		
						
					}
					
				}
				
				
				
				
				//VIZINHOS X
				else if(tipoVizinho == 1) {
					if(px.posC == 0 && px.posL == 0) {
						cor = pr.getColor(1, 1);
						px.vizinhosX[0] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), 1, 1);
					} else if(px.posC == 0 && px.posL == h) {
						cor = pr.getColor(1, h-1);
						px.vizinhosX[0] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), 1, h-1);
					} else if(px.posL == 0 && px.posC == w) {
						cor = pr.getColor(w-1, 1);
						px.vizinhosX[0] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), w-1, 1);
					} else if(px.posC == w && px.posL == h) {
						cor = pr.getColor(w-1, h-1);
						px.vizinhosX[0] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), w-1, h-1);
					} else if(px.posL == 0) { 
						cor = pr.getColor(px.posC+1, 1);
						px.vizinhosX[0] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC+1, 1);
						cor = pr.getColor(px.posC-1, 1);
						px.vizinhosX[1] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC-1, 1);
					} else if(px.posC == 0) {
						cor = pr.getColor(1, px.posL-1);
						px.vizinhosX[0] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), 1, px.posL-1);
						cor = pr.getColor(1, px.posL+1);
						px.vizinhosX[1] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), 1, px.posL+1);
					} else if(px.posC == w) {
						cor = pr.getColor(w-1, px.posL-1);
						px.vizinhosX[0] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), w-1, px.posL-1);
						cor = pr.getColor(w-1, px.posL+1);
						px.vizinhosX[1] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), w-1, px.posL+1);
					} else if(px.posL == h) {
						cor = pr.getColor(px.posC+1, h-1);
						px.vizinhosX[0] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC+1, h-1);
						cor = pr.getColor(px.posC-1, h-1);
						px.vizinhosX[1] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC-1, h-1);
					} else {
						cor = pr.getColor(px.posC-1, px.posL-1);
						px.vizinhosX[0] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC-1, px.posL-1);
						cor = pr.getColor(px.posC+1, px.posL-1);
						px.vizinhosX[1] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC+1, px.posL-1);
						cor = pr.getColor(px.posC+1, px.posL+1);
						px.vizinhosX[2] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC+1, px.posL+1);
						cor = pr.getColor(px.posC-1, px.posL+1);
						px.vizinhosX[3] = new Pixel(cor.getRed(), cor.getBlue(), cor.getGreen(), px.posC-1, px.posL+1);
					}
					
					if(isMediana) {
						double red = px.medianaRed(px.vizinhosX);
						double blue = px.medianaBlue(px.vizinhosX);
						double green = px.medianaGreen(px.vizinhosX);
						Color corNova = new Color(red, green, blue, cor.getOpacity());
						pw.setColor(px.posC, px.posL, corNova);					
						
					}else {
						double red = px.mediaRed(px.vizinhosX);
						double blue = px.mediaBlue(px.vizinhosX);
						double green = px.mediaGreen(px.vizinhosX);
						Color corNova = new Color(red, green, blue, cor.getOpacity());
						pw.setColor(px.posC, px.posL, corNova);		
						
					}
					
				}
			}
		}
		return wi;
	}

	
}
