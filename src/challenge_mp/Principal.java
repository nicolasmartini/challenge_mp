package challenge_mp;

import java.awt.Cursor;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Store;
import javax.swing.JOptionPane;

/**
 * Clase encargada de procesar los correos electronicos y guardarlos en la base
 * de datos
 * 
 * @author N1C0
 *
 */
public class Principal {

	private Store store;
	private String key = "DevOps";
	private Operaciones op;
	private Login login;

	public Principal(Store store, Login l) {

		this.store = store;
		this.login = l;
		conectar_db();

		if (op != null) {
			procesar_correos();
		}
	}

	/*
	 * Se crea la conexion a la base de datos
	 */
	private void conectar_db() {
		ConexionMySQL c = new ConexionMySQL();
		if (c.Conectar("admin", "admin") != null) {
			System.out.println("Acceso a la bd");
			op = new Operaciones(c.getConnection());

		}

	}

	public void setStore(Store store) {

		this.store = store;

	}

	/**
	 * Se procesan todos los correos encontrados y si contiene la palabra clave se guardan en la bd
	 */
	private void procesar_correos() {
		
		String input = JOptionPane.showInputDialog("Ingrese palabra clave a buscar en los correos", "DevOps");

		if (input == null || input.trim().equals("")) {

			return;
		}
		
		key = input;

		login.setCursor(Cursor.getDefaultCursor().WAIT_CURSOR);

		Folder folder;
		try {

			folder = store.getFolder("INBOX");
			folder.open(Folder.READ_ONLY);

			// Se obtienen los mensajes.
			Message[] mensajes = folder.getMessages();

			System.out.println("mensajes " + mensajes.length);

			String from;
			String subject;
			Date date;
			String fecha;

			// Por cada mensaje se verifica si contiene en su asuento o cuerpo la palabra
			// buscada (DevOps)
			for (int i = 0; i < mensajes.length; i++) {
				subject = mensajes[i].getSubject();
				System.out.println(subject);
				if (subject.contains(key) || chequear_partes(mensajes[i])) {

					from = mensajes[i].getFrom()[0].toString();
					if (from.contains("<") && from.contains(">")) {
						from = (String) from.subSequence(from.indexOf("<") + 1, from.indexOf(">"));
					}
					date = mensajes[i].getSentDate();
					SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd");
					fecha = formatofecha.format(date);
					guardar_registro(fecha, from, subject);

				}

			}

			login.setCursor(Cursor.getDefaultCursor());

			folder.close(false);
			store.close();

			login.setVisible(true);

		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Guardar en la bd informacion sobre un correo
	 * 
	 * @param fecha fecha de recepcion del correo
	 * @param from remitente del correo
	 * @param subject asutno del correo
	 */
	private void guardar_registro(String fecha, String from, String subject) {

		op.guardar_registro(fecha, from, subject);
	}

	private boolean chequear_partes(Part parte) {

		try {
			if (parte.isMimeType("multipart/*")) {

				Multipart multi;
				multi = (Multipart) parte.getContent();

				for (int j = 0; j < multi.getCount(); j++) {

					if (multi.getBodyPart(j).isMimeType("text/*")) {

						if (parte.getContent().toString().contains(key)) {
							return true;
						}

					}
				}
			} else if (parte.isMimeType("text/*")) {

				if (parte.getContent().toString().contains(key)) {
					return true;
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

}
