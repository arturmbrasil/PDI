package braile;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class Controller {

	@FXML ImageView imgv1;
	@FXML Label lblPassos;

	private Image imagem1;
	private File f;
	private int passo;
	
	@FXML
	public void abreImg1() {
		f = selecionaImagem();
		if(f != null) {
			imagem1 = new Image(f.toURI().toString());
			imgv1.setImage(imagem1);
			imgv1.setFitWidth(imagem1.getWidth());
			imgv1.setFitHeight(imagem1.getHeight());
		}
		passo = 1;
	}
	
	@FXML
	public void proxPasso() {
		if (passo == 1) { //1 passo: Tons de Cinza
			lblPassos.setText("Passo 1: Tons de Cinza");
			imagem1 = Processamento.tonsCinza(f.getAbsolutePath());
			atualizaImg1();
			
			salvaTemp(imagem1);

		}
		if (passo == 2) { //2 passo: Limiarização 140
			lblPassos.setText("Passo 2: Limiarização 140");
			imagem1 = Processamento.limiarizacao(imagem1, 140.0/255);
			atualizaImg1();
			
			salvaTemp(imagem1);
		}
		
		if (passo == 3) { //3 passo: Eliminar Ruidos
			lblPassos.setText("Passo 3: Eliminar Ruidos");
			imagem1 = Processamento.ruidos("imagens/temp.png");
			atualizaImg1();
			
			salvaTemp(imagem1);
		}
		if (passo == 4) { //4 passo: Negativa
			lblPassos.setText("Passo 4: Negativa");
			imagem1 = Processamento.negativa(imagem1);
			atualizaImg1();
			
			salvaTemp(imagem1);
		}
		
		if (passo == 5) { //5 passo: Dilatação
			lblPassos.setText("Passo 5: Dilatação");
			imagem1 = Processamento.dilatacao("imagens/temp.png");
			atualizaImg1();

			salvaTemp(imagem1);
		}
		
		if (passo == 6) { //6 passo: Eliminar Bordas e marcar com linhas
			lblPassos.setText("Passo 6: Eliminar Bordas e marcar com linhas");
		}
		
		
		passo ++;
		
	}
	
	private void atualizaImg1() {
		imgv1.setImage(imagem1);
		imgv1.setFitWidth(imagem1.getWidth());
		imgv1.setFitHeight(imagem1.getHeight());
	}
	
	private void salvaTemp(Image img) {
		BufferedImage bImg = SwingFXUtils.fromFXImage(img, null);
		try { //Salva imagem temp
			File outputfile = new File("imagens/temp.png");
			ImageIO.write(bImg, "png", outputfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private File selecionaImagem() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens", "*.png", "*.PNG", "*.jpg", "*.JPG", "*.gif", "*.GIF", "*.bmp", "*.BMP"));
		fileChooser.setInitialDirectory(new File("imagens/"));
		File imgSelec = fileChooser.showOpenDialog(null);
		try {
			if (imgSelec != null) {
				return imgSelec;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
