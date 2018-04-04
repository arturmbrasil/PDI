package pdi;

public class Pixel {
	public double red, blue, green;
	public int posL, posC; //linha e coluna

	public Pixel[] vizinhos3 = new Pixel[9];
	public Pixel[] vizinhosCruz = new Pixel[5];
	public Pixel[] vizinhosX = new Pixel[5];
	
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
		v[v.length-1] = this;

		for(Pixel p : v) {
			if(p != null) {
				mediaR += p.getRed();
				cont += 1;
			}
		}
		return (mediaR + this.getRed())/ (cont+1);
	}
	
	public double mediaBlue(Pixel[] v) {
		double mediaB = 0;
		int cont = 0;
		v[v.length-1] = this;

		for(Pixel p : v) {
			if(p != null) {
				mediaB += p.getBlue();
				cont += 1;
			}
		}
		return (mediaB + this.getBlue())/ (cont+1);
	}
	
	public double mediaGreen(Pixel[] v) {
		double mediaG = 0;
		int cont = 0;		
		v[v.length-1] = this;


		for(Pixel p : v) {
			if(p != null) {
				mediaG += p.getGreen();
				cont += 1;
			}
		}
		return (mediaG + this.getGreen())/ (cont+1);
	}
	
	public double medianaRed(Pixel[] v) {
		v[v.length-1] = this;
		ordenaVetorRed(v);
		int cont = 0;
		for(Pixel p : v) {
			if(p !=null)
				cont += 1;
		}
		return v[cont / 2].red;
	}
	
	public double medianaBlue(Pixel[] v) {
		v[v.length-1] = this;
		ordenaVetorBlue(v);
		int cont = 0;
		for(Pixel p : v) {
			if(p !=null)
				cont += 1;
		}
		return v[cont / 2].blue;
	}
	
	public double medianaGreen(Pixel[] v) {
		v[v.length-1] = this;
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
			for (int j = 0; j < v.length - 1; j++) {
				if(v[j] != null && v[j+1] != null) {
					if (v[j].red > v[j+1].red) { 
						aux = v[j]; 
						v[j] = v[j+1]; 
						v[j+1] = aux; 
					} 					
				}
				else if(v[j] == null && v[j+1] != null) {
					v[j] = v[i+1]; 
					v[j+1] = null; 
				}
			}
		}
	}
	
	public void ordenaVetorBlue(Pixel[] v) {
		Pixel aux = null;
		for (int i = 0; i < v.length; i++) { 
			for (int j = 0; j < v.length - 1; j++) {
				if(v[j] != null && v[j+1] != null) {
					if (v[j].blue > v[j+1].blue) { 
						aux = v[j]; 
						v[j] = v[j+1]; 
						v[j+1] = aux; 
					} 					
				}
				else if(v[j] == null && v[j+1] != null) {
					v[j] = v[i+1]; 
					v[j+1] = null; 
				}
			}
		}
	}
	
	public void ordenaVetorGreen(Pixel[] v) {
		Pixel aux = null;
		for (int i = 0; i < v.length; i++) { 
			for (int j = 0; j < v.length - 1; j++) {
				if(v[j] != null && v[j+1] != null) {
					if (v[j].green > v[j+1].green) { 
						aux = v[j]; 
						v[j] = v[j+1]; 
						v[j+1] = aux; 
					} 					
				}
				else if(v[j] == null && v[j+1] != null) {
					v[j] = v[i+1]; 
					v[j+1] = null; 
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
