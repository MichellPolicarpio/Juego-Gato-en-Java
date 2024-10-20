/*
                       Juego del Gato - Tres en Raya
     Michell Alexis Policarpio Moran - zS21002379 - UV - FIEE - POO II
*/

//importamos librerias clave pa' la creacion de interfaz grafica, eventos y manipulacion pa' archivos
import javax.swing.*;  //para la creación de la interfaz gráfica (ventanas, botones, diálogos)
import java.awt.*;  //para el diseño de los componentes gráficos (como Layouts y fuentes)
import java.awt.event.*;  //para manejar eventos (acciones de usuario, como clics en botones)
import java.io.*;  //para la entrada/salida de archivos
import java.time.LocalDateTime;  //para poder usar fechas y horas actuales de cuando se gana
import java.time.format.DateTimeFormatter;  //se usa para formatear la fecha y hora en un formato especifico

//clase principal del Juego del Gato
public class JuegoGato extends JFrame implements ActionListener{
    //botones del menu principal
    private JButton iniciarJuegoButton;
    private JButton modificarNombresButton;
    private JButton verHistorialButton;
    private JButton creditosButton;
    private JButton ayudaButton;
    private JButton salirButton;
    
    //declarativa de los dos jugadores principales
    private static String jugadorX = "Jugador X";
    private static String jugadorO = "Jugador O";
    
    //declaro componentes clave para el funcionamiento del juego 
    private JButton[][] botones;
    private boolean turnoX;
    private JFrame frameJuego;

    //metodo constructor del menu principal
    public JuegoGato() {
        pedirNombreJugadores(); //Al iniciar el programa, se piden al usuario ingresar nombre de los jugadores
        
        //configuración básica de la ventana
        setTitle("JUEGO DEL GATO");
        setSize(400, 300);
        setLocationRelativeTo(null); // Centrar la ventana del menú
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 1));

        //titulo estilizado
        JLabel tituloLabel = new JLabel("********** JUEGO DEL GATO **********", SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Serif", Font.BOLD, 18));
        add(tituloLabel);

        //intanciamos botones del menu
        iniciarJuegoButton = new JButton("Iniciar Juego");
        modificarNombresButton = new JButton("Modificar Nombres de Jugadores");
        verHistorialButton = new JButton("Ver Historial de Ganadores");
        creditosButton = new JButton("Créditos del Creador");
        ayudaButton = new JButton("Ayuda");
        salirButton = new JButton("Salir del juego");

        //se agregan los escuchadores de eventos a los botones para cuando el usuario hace clic en algo, etc
        iniciarJuegoButton.addActionListener(this);
        modificarNombresButton.addActionListener(this);
        verHistorialButton.addActionListener(this);
        creditosButton.addActionListener(this);
        ayudaButton.addActionListener(this);
        salirButton.addActionListener(this);

        //se añaden los botones del menu
        add(iniciarJuegoButton);
        add(modificarNombresButton);
        add(verHistorialButton);
        add(creditosButton);
        add(ayudaButton);
        add(salirButton);
    }

    //metodo para solicitar los nombres de los jugadores usando un único cuadro de diálogo
    private void pedirNombreJugadores() {
        // Crear el panel para los campos de texto
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        
        // Crear los campos de texto y sus etiquetas
        JLabel labelX = new JLabel("Nombre Jugador X:");
        JLabel labelO = new JLabel("Nombre Jugador O:");
        JTextField campoX = new JTextField(15);
        JTextField campoO = new JTextField(15);
        
        // Añadir los componentes al panel
        panel.add(labelX);
        panel.add(campoX);
        panel.add(labelO);
        panel.add(campoO);
        
        // Mostrar el diálogo
        int resultado = JOptionPane.showConfirmDialog(this, panel, 
                "Ingrese nombres de jugadores", JOptionPane.OK_CANCEL_OPTION);
        
        // Procesar el resultado
        if (resultado == JOptionPane.OK_OPTION) {
            String nombreX = campoX.getText().trim();
            String nombreO = campoO.getText().trim();
            
            // Asignar nombres, usando valores por defecto si están vacíos
            jugadorX = nombreX.isEmpty() ? "Jugador X" : nombreX;
            jugadorO = nombreO.isEmpty() ? "Jugador O" : nombreO;
        } else {
            // Si el usuario cancela, usar nombres por defecto
            jugadorX = "Jugador X";
            jugadorO = "Jugador O";
        }
    }

    //metodo que asigan acciones de los botones del menu principal
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == iniciarJuegoButton){
            iniciarJuego();
        } else if (e.getSource() == modificarNombresButton){
            modificarNombres();
        } else if (e.getSource() == verHistorialButton){
            mostrarHistorial();
        } else if (e.getSource() == creditosButton){
            mostrarCreditos();
        } else if (e.getSource() == ayudaButton){
            mostrarAyuda();
        } else if (e.getSource() == salirButton){
            System.exit(0);
        }
    }

    //metodo que inicia e implementa logica del juego
    private void iniciarJuego(){
        frameJuego = new JFrame("Juego del Gato");
        frameJuego.setSize(300, 300);
        frameJuego.setLocationRelativeTo(null); // Centrar la ventana del menú   
        frameJuego.setLayout(new GridLayout(3, 3)); //layout para el tablero 3x3
        
        //inicializacion del tablero de los botones
        botones = new JButton[3][3];
        turnoX = true; //siempre iniciara el jugador x primero el juego.

        //creacion de los botones para el tablero
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                botones[i][j] = new JButton("");
                botones[i][j].setFont(new Font("Arial", Font.BOLD, 40));
                //se añade acción a cada botón del tablero
                botones[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JButton botonClickeado = (JButton) e.getSource();
                        if (botonClickeado.getText().equals("")){
                            if (turnoX) {
                                botonClickeado.setText("X");
                            } else {
                                botonClickeado.setText("O");
                            }
                            turnoX = !turnoX;
                            
                            //verifica si hay ganador o empate
                            int[] lineaGanadora = hayGanador();
                            if (lineaGanadora != null) {
                                resaltarLineaGanadora(lineaGanadora);
                                final String ganador = turnoX ? jugadorO : jugadorX;
                                // Usar Timer para mostrar el mensaje después de 2 segundos
                                Timer timer = new Timer(1000, new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        JOptionPane.showMessageDialog(frameJuego, "¡" + ganador + " ha ganado!");
                                        guardarHistorial(ganador);
                                        frameJuego.dispose();
                                    }
                                });
                                timer.setRepeats(false);
                                timer.start();
                            } else if (tableroLleno()) {
                                Timer timer = new Timer(2000, new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        JOptionPane.showMessageDialog(frameJuego, "¡Empate!");
                                        guardarHistorial("Empate");
                                        frameJuego.dispose();
                                    }
                                });
                                timer.setRepeats(false);
                                timer.start();
                            }
                        }
                    }
                });
                frameJuego.add(botones[i][j]);
            }
        }

        frameJuego.setVisible(true); //declaro mostrar la ventana del tablero
    }

    // Método para verificar si hay un ganador - ahora retorna un array con la información de la línea ganadora
    private int[] hayGanador() {
        // Comprobación de filas
        for (int i = 0; i < 3; i++) {
            if (botones[i][0].getText().equals(botones[i][1].getText()) &&
                botones[i][0].getText().equals(botones[i][2].getText()) &&
                !botones[i][0].getText().equals("")) {
                return new int[]{i, 0, i, 1, i, 2}; // Fila i
            }
        }
        
        // Comprobación de columnas
        for (int i = 0; i < 3; i++) {
            if (botones[0][i].getText().equals(botones[1][i].getText()) &&
                botones[0][i].getText().equals(botones[2][i].getText()) &&
                !botones[0][i].getText().equals("")) {
                return new int[]{0, i, 1, i, 2, i}; // Columna i
            }
        }
        
        // Comprobación de diagonal principal
        if (botones[0][0].getText().equals(botones[1][1].getText()) &&
            botones[0][0].getText().equals(botones[2][2].getText()) &&
            !botones[0][0].getText().equals("")) {
            return new int[]{0, 0, 1, 1, 2, 2}; // Diagonal principal
        }
        
        // Comprobación de diagonal secundaria
        if (botones[0][2].getText().equals(botones[1][1].getText()) &&
            botones[0][2].getText().equals(botones[2][0].getText()) &&
            !botones[0][2].getText().equals("")) {
            return new int[]{0, 2, 1, 1, 2, 0}; // Diagonal secundaria
        }
        
        return null; // No hay ganador
    }

    // Método para resaltar la línea ganadora
    private void resaltarLineaGanadora(int[] linea) {
        Color colorGanador = new Color(50, 205, 50); // Verde lima
        botones[linea[0]][linea[1]].setBackground(colorGanador);
        botones[linea[2]][linea[3]].setBackground(colorGanador);
        botones[linea[4]][linea[5]].setBackground(colorGanador);
        
        // Asegurar que el color de fondo sea visible
        botones[linea[0]][linea[1]].setOpaque(true);
        botones[linea[2]][linea[3]].setOpaque(true);
        botones[linea[4]][linea[5]].setOpaque(true);
    }

    //metodo para verificar si el tablero esta lleno (pa´ identificar empates)
    private boolean tableroLleno(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (botones[i][j].getText().equals("")){
                    return false;
                }
            }
        }
        return true;
    }

    //metodo para modificar nombre de los jugadores - ahora usa la misma ventana única
    private void modificarNombres() {
        // Crear el panel para los campos de texto
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        
        // Crear los campos de texto y sus etiquetas
        JLabel labelX = new JLabel("Nuevo nombre Jugador X:");
        JLabel labelO = new JLabel("Nuevo nombre Jugador O:");
        JTextField campoX = new JTextField(jugadorX, 15);
        JTextField campoO = new JTextField(jugadorO, 15);
        
        // Añadir los componentes al panel
        panel.add(labelX);
        panel.add(campoX);
        panel.add(labelO);
        panel.add(campoO);
        
        // Mostrar el diálogo
        int resultado = JOptionPane.showConfirmDialog(this, panel, 
                "Modificar nombres de jugadores", JOptionPane.OK_CANCEL_OPTION);
        
        // Procesar el resultado
        if (resultado == JOptionPane.OK_OPTION) {
            String nombreX = campoX.getText().trim();
            String nombreO = campoO.getText().trim();
            
            // Asignar nuevos nombres, usando valores por defecto si están vacíos
            jugadorX = nombreX.isEmpty() ? "Jugador X" : nombreX;
            jugadorO = nombreO.isEmpty() ? "Jugador O" : nombreO;
        }
    }

    //metodo que muestra el historial de partidas guardadas en el archivo "historial.txt".
    //lee cada línea del archivo y las acumula en un StringBuilder.
    //luego, muestra el contenido en un cuadro de diálogo.
    private void mostrarHistorial() {
        try (BufferedReader reader = new BufferedReader(new FileReader("historial.txt"))) {
            String linea;
            StringBuilder historial = new StringBuilder();
            while ((linea = reader.readLine()) != null) {
                historial.append(linea).append("\n");
            }
            JOptionPane.showMessageDialog(this, historial.toString(), "Historial de Partidas", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo leer el historial.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //metodo que imprime creditos del creador de esxte programa :p
    private void mostrarCreditos() {
        setLocationRelativeTo(null); // Centrar la ventana del menú  // Misma posición para la ventana de créditos
        String creditos = "Creador del Juego:\n- Michell Alexis Policarpio Moran\n- zS21002379 - 7° Semestre \n- Ingenieria en Informática - FIEE - UV \n- Paradigmas de programación II \n - Juego del Gato - Tres en Raya";
        JOptionPane.showMessageDialog(this, creditos, "Créditos", JOptionPane.INFORMATION_MESSAGE);
    }

    //metodo de informacion del programa o del juego
    private void mostrarAyuda() {
        setLocationRelativeTo(null); // Centrar la ventana del menú  // Misma posición para la ventana de ayuda
        String ayuda = "Este es un juego de gato.\n"
                + "1.- Iniciar un nuevo juego.\n"
                + "2.- Modificar nombres de jugadores.\n"
                + "3.- Ver el historial de ganadores.\n"
                + "Para jugar, haga clic en los cuadros para colocar X u O.";
        JOptionPane.showMessageDialog(this, ayuda, "Ayuda", JOptionPane.INFORMATION_MESSAGE);
    }

    //método que guarda el resultado de una partida en el archivo "historial.txt".
    //registra si la partida fue un empate o si hubo un ganador, junto con la fecha y hora.
    //el resultado se añade al archivo sin borrar el contenido anterior.
    private void guardarHistorial(String resultado) {
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy - HH:mm:ss");
        String fechaFormateada = fechaHoraActual.format(formatter);

        try (PrintWriter writer = new PrintWriter(new FileWriter("historial.txt", true))){
            if (resultado.equals("Empate")) {
                writer.println("RESULTADO: Empate");
            } else {
                writer.println("GANADOR: " + resultado);
            }
            writer.println("FECHA Y HORA: " + fechaFormateada);
            writer.println("-------------------------------------");
        } catch (IOException ex){
            //si ocurre un error al guardar el historial, se muestra un mensaje de error.
            JOptionPane.showMessageDialog(this, "No se pudo guardar el historial.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
