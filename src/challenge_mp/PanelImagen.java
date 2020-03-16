package challenge_mp;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


class PanelImagen extends JPanel {
	private Image img;
	private String ruta_imagen;


  public PanelImagen(String ruta_imagen) {
    this.ruta_imagen = ruta_imagen;
    img = new ImageIcon(ruta_imagen).getImage();
    Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
    setPreferredSize(size);
    setMinimumSize(size);
    setMaximumSize(size);
    setSize(size);
    setLayout(null);
  }

  public void paintComponent(Graphics g) {
    g.drawImage(img, 0, 0, null);
  }

}