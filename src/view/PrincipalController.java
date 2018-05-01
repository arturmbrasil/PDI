package view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pdi.Pdi;

public class PrincipalController {
	
	@FXML Label lblR;
	@FXML Label lblG;
	@FXML Label lblB;
	
	@FXML ImageView imgv1;
	@FXML ImageView imgv2;
	@FXML ImageView imgv3;
	
	@FXML TextField tfRed;
	@FXML TextField tfGreen;
	@FXML TextField tfBlue;
	@FXML TextField tfSegmentacao;
	
	@FXML TextField pcimg1;
	@FXML TextField pcimg2;
	
	@FXML Slider slider;
	
	@FXML ToggleGroup vizinho;
	
	@FXML RadioButton cruz;
	@FXML RadioButton x;
	@FXML RadioButton tres;
	
	private Image imagem1, imagem2, imagem3;
	private double clicoux, clicouy, soltoux, soltouy;
	
	@FXML
	public void gerarHistograma(ActionEvent event) {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("HistogramaModal.fxml"));
			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.setTitle("Histograma");
			stage.initOwner(((Node)event.getSource()).getScene().getWindow());
			stage.show();			
			
			HistogramaModalController controller = (HistogramaModalController) loader.getController();
			if(imagem1 != null) 
				Pdi.getGrafico(imagem1, controller.hist1);
			if(imagem2 != null) 
				Pdi.getGrafico(imagem2, controller.hist2);
			if(imagem3 != null) 
				Pdi.getGrafico(imagem3, controller.hist3);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void equalizarHistograma() {
		imagem3 = Pdi.equalizar(imagem1);
		atualizaImg3();
	}
	
	@FXML
	public void adicao() {
		if(pcimg1.getText().isEmpty() || pcimg2.getText().isEmpty()) {
			exibeMsg("Erro", "Valores Inválidos", "A soma dos valores deve ser igual a 100", AlertType.ERROR);
			return;
		}
		Double somaTxts = Double.parseDouble(pcimg1.getText()) + Double.parseDouble(pcimg2.getText());
		if((somaTxts != 100)) {
			exibeMsg("Erro", "Valores Inválidos", "A soma dos valores deve ser igual a 100", AlertType.ERROR);
			return;
		} else {
		imagem3 = Pdi.adicao(imagem1, imagem2, Double.parseDouble(pcimg1.getText()), Double.parseDouble(pcimg2.getText()));
		atualizaImg3();
		}
	}
	
	@FXML
	public void subtracao() {
		imagem3 = Pdi.subtracao(imagem1, imagem2);
		atualizaImg3();
	}
	
	@FXML
	public void segmentacao() {
		imagem3 = Pdi.segmentar(imagem1, Integer.parseInt(tfSegmentacao.getText()));
		atualizaImg3();
	}
	
	@FXML
	public void inverter() {
		imagem3 = Pdi.inverte(imagem1);
		atualizaImg3();
	}
	
	@FXML
	public void rotacionar() {
		imagem3 = Pdi.rotacionar(imagem1);
		atualizaImg3();
	}
	
	@FXML
	public void borda3px() {
		imagem3 = Pdi.borda3px(imagem1);
		atualizaImg3();
	}
	
	@FXML
	public void aumentar() {
		imagem3 = Pdi.aumentar(imagem1);
		atualizaImg3();
	}
	
	@FXML
	public void diminuir() {
		imagem3 = Pdi.diminuir(imagem1);
		atualizaImg3();
	}
	
	@FXML 
	public void ruidosMedia() {
		if(tres.isSelected()) {
				imagem3 = Pdi.ruidos(imagem1, 3, false);
		}else if(cruz.isSelected()) {
			imagem3 = Pdi.ruidos(imagem1, 2, false);
		}else if(x.isSelected()) {
			imagem3 = Pdi.ruidos(imagem1, 1, false);
		}
			
		atualizaImg3();
	}
	
	@FXML 
	public void ruidosMediana() {
		if(tres.isSelected()) {
			imagem3 = Pdi.ruidos(imagem1, 3, true);
		}else if(cruz.isSelected()) {
			imagem3 = Pdi.ruidos(imagem1, 2, true);
		}else if(x.isSelected()) {
			imagem3 = Pdi.ruidos(imagem1, 1, true);
		}
	
		atualizaImg3();
	}


	
	@FXML
	public void tonsCinza() {
		if(tfRed.getText().isEmpty() && tfGreen.getText().isEmpty() && tfBlue.getText().isEmpty()) {
			imagem3 = Pdi.tonsCinzaArit(imagem1);
			atualizaImg3();
			return;
		}

		Integer somaTxts = Integer.parseInt(tfRed.getText()) + Integer.parseInt(tfGreen.getText()) + Integer.parseInt(tfBlue.getText());
		if(somaTxts != 100) {
			exibeMsg("Erro", "Valores Inválidos", "A soma dos valores deve ser igual a 100", AlertType.ERROR);
		} else {
			imagem3 = Pdi.tonsCinzaPond(imagem1, Integer.parseInt(tfRed.getText()), Integer.parseInt(tfGreen.getText()), Integer.parseInt(tfBlue.getText()));
			atualizaImg3();
			return;
		}
	
	}
	
	@FXML
	public void limiarizacao() {
		imagem3 = Pdi.limiarizacao(imagem1, slider.getValue()/255);
		atualizaImg3();
	}
	
	@FXML
	public void negativa() {
		imagem3 = Pdi.negativa(imagem1);
		atualizaImg3();
	}
	
	@FXML
	public void desafio1() {
		imagem3 = Pdi.desafio1(imagem1);
		atualizaImg3();
	}
	
	private void atualizaImg3() {
		imgv3.setImage(imagem3);
		imgv3.setFitWidth(imagem3.getWidth());
		imgv3.setFitHeight(imagem3.getHeight());
	}



	@FXML
	public void abreImg1() {
		File f = selecionaImagem();
		if(f != null) {
			imagem1 = new Image(f.toURI().toString());
			imgv1.setImage(imagem1);
			imgv1.setFitWidth(imagem1.getWidth());
			imgv1.setFitHeight(imagem1.getHeight());
		}
	}
	
	@FXML
	public void abreImg2() {
		File f = selecionaImagem();
		if(f != null) {
			imagem2 = new Image(f.toURI().toString());
			imgv2.setImage(imagem2);
			imgv2.setFitWidth(imagem2.getWidth());
			imgv2.setFitHeight(imagem2.getHeight());
		}
	}
	
	public void rastrearImg(MouseEvent evt) {
		ImageView imgview = (ImageView) evt.getTarget();
		if(imgview.getImage() != null) {
			verificaCor(imgview.getImage(), (int)evt.getX(), (int)evt.getY());
		}
	}
	
	@FXML
	public void clicou(MouseEvent evt) {
		clicoux = evt.getX();
		clicouy = evt.getY();
	}
	
	@FXML
	public void soltou(MouseEvent evt) {
		soltoux = evt.getX();
		soltouy = evt.getY();
		ImageView iv = (ImageView) evt.getTarget();
		if(iv.getImage() != null) {
			imagem3 = Pdi.desenho(clicoux, clicouy, soltoux, soltouy, imagem1);
			atualizaImg3();
		}
	}
	
	
	@FXML
	private void limpaRGB() {
		lblR.setText("R: 000");
		lblG.setText("G: 000");
		lblB.setText("B: 000");
	}
	
	@FXML
	public void salvar() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens", "*.png"));
		fileChooser.setInitialDirectory(new File("/Users/arturbrasil/Documents/imgs"));
		if (imagem3 != null) {
			File file = fileChooser.showSaveDialog(null);
			BufferedImage bImg = SwingFXUtils.fromFXImage(imagem3, null);
			try {
				ImageIO.write(bImg, "PNG", file);
				exibeMsg("Salvar imagem", "Imagem salva com sucesso.", null, AlertType.CONFIRMATION);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			exibeMsg("Salvar imagem", "Não é possível salvar imagem.", "Não há nenhuma imagem modificada.", AlertType.ERROR);
		}
	}
	
	private void exibeMsg(String titulo, String cabecalho, String msg, AlertType tipo) {
		Alert alert = new Alert(tipo);
		alert.setTitle(titulo);
		alert.setHeaderText(cabecalho);
		alert.setContentText(msg);
		alert.showAndWait();
	}
	
	private File selecionaImagem() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens", "*.png", "*.PNG", "*.jpg", "*.JPG", "*.gif", "*.GIF", "*.bmp", "*.BMP"));
		//fileChooser.setInitialDirectory(new File("caminho do arquivo"));
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
	
	private void verificaCor(Image img, int x, int y) {
		try {
				Color cor = img.getPixelReader().getColor(x, y);
				lblR.setText("R: "+(int) (cor.getRed()*255));
				lblG.setText("G: "+(int) (cor.getGreen()*255));
				lblB.setText("B: "+(int) (cor.getBlue()*255));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
