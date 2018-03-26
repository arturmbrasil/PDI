package pdi;

public class Pixel {
	public double red, blue, green;
	public int posL, posC; //linha e coluna

	public Pixel[] vizinhos3 = new Pixel[8];
	public Pixel[] vizinhosCruz = new Pixel[4];
	public Pixel[] vizinhosX = new Pixel[4];
	
	public Pixel(double red, double blue, double green, int posC, int posL) {
		super();
		this.red = red;
		this.blue = blue;
		this.green = green;
		this.posL = posL;
		this.posC = posC;
	}

	public Pixel(int posL, int posC) {
		super();
		this.posL = posL;
		this.posC = posC;
	}

	public double mediaRed(Pixel[] v) {
		double mediaR = 0;
		int cont = 0;
		for(Pixel p : v) {
			if(p != null) {
				mediaR += p.getRed();
				cont += 1;
			}
		}
		return mediaR / cont;
	}
	
	public double mediaBlue(Pixel[] v) {
		double mediaB = 0;
		int cont = 0;

		for(Pixel p : v) {
			if(p != null) {
				mediaB += p.getBlue();
				cont += 1;
			}
		}
		return mediaB / cont;
	}
	
	public double mediaGreen(Pixel[] v) {
		double mediaG = 0;
		int cont = 0;

		for(Pixel p : v) {
			if(p != null) {
				mediaG += p.getGreen();
				cont += 1;
			}
		}
		return mediaG / cont;
	}
	
	public double medianaRed(Pixel[] v) {
		ordenaVetorRed(v);
		int cont = 0;
		for(Pixel p : v) {
			if(p !=null)
				cont += 1;
		}
		return v[cont / 2].red;
	}
	
	public double medianaBlue(Pixel[] v) {
		ordenaVetorBlue(v);
		int cont = 0;
		for(Pixel p : v) {
			if(p !=null)
				cont += 1;
		}
		return v[cont / 2].blue;
	}
	
	public double medianaGreen(Pixel[] v) {
		ordenaVetorGreen(v);
		int cont = 0;
		for(Pixel p : v) {
			if(p !=null)
				cont += 1;
		}
		return v[cont / 2].green;
	}
	
	public void ordenaVetorRed(Pixel[] v) {
		Pixel aux = null;
		for (int i = 0; i < v.length; i++) { 
			for (int j = 0; j < v.length; j++) {
				if(v[i] != null && v[j] != null) {
					if (v[i].red < v[j].red) { 
						aux = v[i]; 
						v[i] = v[j]; 
						v[j] = aux; 
					} 					
				}
			}
		}
	}
	
	public void ordenaVetorBlue(Pixel[] v) {
		Pixel aux = null;
		for (int i = 0; i < v.length; i++) { 
			for (int j = 0; j < v.length; j++) {
				if(v[i] != null && v[j] != null) {
					if (v[i].blue < v[j].blue) { 
						aux = v[i]; 
						v[i] = v[j]; 
						v[j] = aux; 
					} 
				}
			}
		}
	}
	
	public void ordenaVetorGreen(Pixel[] v) {
		Pixel aux = null;
		for (int i = 0; i < v.length; i++) { 
			for (int j = 0; j < v.length; j++) {
				if(v[i] != null && v[j] != null) {
					if (v[i].green < v[j].green) { 
						aux = v[i]; 
						v[i] = v[j]; 
						v[j] = aux; 
					}
				}
			}
		}
	}
	

	public double getRed() {
		return red;
	}

	public void setRed(double red) {
		this.red = red;
	}

	public double getBlue() {
		return blue;
	}

	public void setBlue(double blue) {
		this.blue = blue;
	}

	public double getGreen() {
		return green;
	}

	public void setGreen(double green) {
		this.green = green;
	}

	public int getPosL() {
		return posL;
	}

	public void setPosL(int posL) {
		this.posL = posL;
	}

	public int getPosC() {
		return posC;
	}

	public void setPosC(int posC) {
		this.posC = posC;
	}
	
	
	
	
	
	
}
