package challenge_mp;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


class PanelImagen extends JPanel {
	private Image img;
	private URL ruta_imagen;


  public PanelImagen(URL url) {
    this.ruta_imagen = url;
    img = new ImageIcon(url).getImage();
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