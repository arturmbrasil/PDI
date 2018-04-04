package pdi;

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
	
	public static Image desafio1(Image img) {
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
	
//	public static int[] histograma(Image img, int canal) {
//		int[] qt = new int [256];
//		PixelReader pr = img.getPixelReader();
//		int w = (int)img.getWidth();
//		int h =(int)img.getHeight();
//		for(int i = 0; i < w ; i++) {
//			for(int j = 0; j < h ; j++) {
//				if (canal == 1) 
//					qt[(int)(pr.getColor(i, j).getRed()*255)]++;
//				else if (canal == 2)
//					qt[(int)(pr.getColor(i, j).getGreen()*255)]++;
//				else
//					qt[(int)(pr.getColor(i, j).getBlue()*255)]++;
//				
//			}
//		}
//		return qt;
//	}
	
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
