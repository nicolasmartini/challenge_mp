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
import javax.management.monitor.Monitor;


public class Principal {
	

	private Store store;
	private String key = "Agua";
	private Operaciones op;
	private Login login;
	
	public Principal (Store store,Login l) {
		
		this.store = store;
		this.login = l;
		conectar_db();
		
		if (op!= null) {
			procesar_correos();
		}
	}
	
	 private void conectar_db() {
		 ConexionMySQL c = new ConexionMySQL();
	     if (c.Conectar("admin", "admin")!= null){
	    	 System.out.println("Acceso a la bd");
	    	 op = new Operaciones(c.getConnection());
	    	 
	     }
		
	}

	public void setStore(Store store) {
		 
		 this.store = store;
		 
	 }


	private void procesar_correos() {
		Folder folder;
		try {
			folder = store.getFolder("INBOX");
			folder.open(Folder.READ_ONLY);
			
            // Se obtienen los mensajes.
            Message[] mensajes = folder.getMessages();

            String from;
            String subject;
            Date date;
            String fecha;
          
            login.setCursor(Cursor.getDefaultCursor().WAIT_CURSOR);

            for (int i = mensajes.length-20; i < mensajes.length; i++)
            {
            	subject = mensajes[i].getSubject();
            	System.out.println(subject);
            	if (subject.contains(key) || chequear_partes(mensajes[i])) {
            		
            		from = mensajes[i].getFrom()[0].toString();
            		if (from.contains("<") && from.contains(">")) {
            			from = (String) from.subSequence(from.indexOf("<")+1,from.indexOf(">"));
            		}
            		date = mensajes[i].getSentDate();
            		SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd");
            		fecha=formatofecha.format(date);    
            		
            		System.out.println(fecha);
            		System.out.println(from);
            		
            		
            		guardar_registro(fecha,from,subject);
            		
            	}                
          
            }
            
            login.setCursor(Cursor.getDefaultCursor());

            folder.close(false);
            store.close();
			
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
		
	}

	private void guardar_registro(String fecha, String from, String subject) {
		
			op.guardar_registro(fecha, from, subject);
	}

	private boolean chequear_partes(Part parte) {
        // Si es multipart, se analiza cada una de sus partes recursivamente.
        try {
			if (parte.isMimeType("multipart/*")) {
			
			    Multipart multi;
			    multi = (Multipart) parte.getContent();
			    
			    for (int j = 0; j < multi.getCount(); j++)
			    {
			    	
			    	if (multi.getBodyPart(j).isMimeType("text/*")) {
			    		
			    	 	if (parte.getContent().toString().contains(key)){
		               		return true;
		               	}
			    		
			    	}
			    }
			}else if (parte.isMimeType("text/*")) {            
               
               	if (parte.getContent().toString().contains(key)){
               		return true;
               	}
                
            }
		} catch (MessagingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return false;
	}

//	/**
//     * main de la clase.
//     * @param args Se ignoran
//     */
//    public static void main(String[] args)
//    {
//      // Se obtiene la Session
//        Properties prop = new Properties();
//        prop.setProperty("mail.pop3.starttls.enable", "false");
//        prop.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        prop.setProperty("mail.pop3.socketFactory.fallback", "false");
//        prop.setProperty("mail.pop3.port", "995");
//        prop.setProperty("mail.pop3.socketFactory.port", "995");
//        Session sesion = Session.getInstance(prop);
//        // sesion.setDebug(true);
//
//        try
//        {
//          // Se obtiene el Store y el Folder, para poder leer el
//          // correo.
//            Store store = sesion.getStore("pop3");
//            store.connect("pop.gmail.com", "nicolasarielmartini@gmail.com", "n1c0l4589");
//            Folder folder = store.getFolder("INBOX");
//            folder.open(Folder.READ_ONLY);
//
//            // Se obtienen los mensajes.
//            Message[] mensajes = folder.getMessages();
//
//            // Se escribe from y subject de cada mensaje
//            for (int i = 0; i < mensajes.length; i++)
//            {
//                System.out.println(
//                    "From:" + mensajes[i].getFrom()[0].toString());
//                System.out.println("Subject:" + mensajes[i].getSubject());
//                
//                // Se visualiza, si se sabe como, el contenido de cada mensaje
//                //analizaParteDeMensaje(mensajes[i]);
//            }
//
//            folder.close(false);
//            store.close();
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }

//    /**
//     * Metodo recursivo.
//     * Si la parte que se pasa es compuesta, se extrae cada una de las subpartes y
//     * el metodo se llama a si mismo con cada una de ellas.
//     * Si la parte es un text, se escribe en pantalla.
//     * Si la parte es una image, se guarda en un fichero y se visualiza en un JFrame.
//     * En cualquier otro caso, simplemente se escribe el tipo recibido, pero se
//     * ignora el mensaje.
//     *
//     * @param unaParte Parte del mensaje a analizar.
//     */
//    private static void analizaParteDeMensaje(Part unaParte)
//    {
//        try
//        {
//          // Si es multipart, se analiza cada una de sus partes recursivamente.
//            if (unaParte.isMimeType("multipart/*"))
//            {
//                Multipart multi;
//                multi = (Multipart) unaParte.getContent();
//
//                for (int j = 0; j < multi.getCount(); j++)
//                {
//                    analizaParteDeMensaje(multi.getBodyPart(j));
//                }
//            }
//            else
//            {
//              // Si es texto, se escribe el texto.
//                if (unaParte.isMimeType("text/*"))
//                {
//                    System.out.println("Texto " + unaParte.getContentType());
//                    System.out.println(unaParte.getContent());
//                    System.out.println("---------------------------------");
//                }
//                else
//                {
//                  // Si es imagen, se guarda en fichero y se visualiza en JFrame
//                    if (unaParte.isMimeType("image/*"))
//                    {
//                        System.out.println(
//                            "Imagen " + unaParte.getContentType());
//                        System.out.println("Fichero=" + unaParte.getFileName());
//                        System.out.println("---------------------------------");
//
//                        salvaImagenEnFichero(unaParte);
//                        visualizaImagenEnJFrame(unaParte);
//                    }
//                    else
//                    {
//                      // Si no es ninguna de las anteriores, se escribe en pantalla
//                      // el tipo.
//                        System.out.println(
//                            "Recibido " + unaParte.getContentType());
//                        System.out.println("---------------------------------");
//                    }
//                }
//            }
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Presupone que unaParte es una foto adjunta a un correo.
//     * Recoge la imagen y la visualiza en un JFrame
//     *
//     * @param unaParte Parte de un correo correspondiente a una imagen.
//     *
//     * @throws IOException 
//     * @throws MessagingException 
//     */
//    private static void visualizaImagenEnJFrame(Part unaParte)
//        throws IOException, MessagingException
//    {
//        JFrame v = new JFrame();
//        ImageIcon icono = new ImageIcon(
//                ImageIO.read(unaParte.getInputStream()));
//        JLabel l = new JLabel(icono);
//        v.getContentPane().add(l);
//        v.pack();
//        v.setVisible(true);
//    }
//
//    /**
//     * Supone que unaParte corresponde a una imagen de un fichero y que
//     * getFileName() esta relleno.
//     * Salva la imagen en d:\getFileName().
//     *
//     * @param unaParte Parte de un correo correspondiente a una imagen.
//     *
//     * @throws FileNotFoundException 
//     * @throws MessagingException 
//     * @throws IOException 
//     */
//    private static void salvaImagenEnFichero(Part unaParte)
//        throws FileNotFoundException, MessagingException, IOException
//    {
//        FileOutputStream fichero = new FileOutputStream(
//                "d:/" + unaParte.getFileName());
//        InputStream imagen = unaParte.getInputStream();
//        byte[] bytes = new byte[1000];
//        int leidos = 0;
//
//        while ((leidos = imagen.read(bytes)) > 0)
//        {
//            fichero.write(bytes, 0, leidos);
//        }
//    }
//
//	
////	public static void main(String []args) {
////		
////		principal p = new principal();
////		p.conectar();
////		
////	}
	

}
