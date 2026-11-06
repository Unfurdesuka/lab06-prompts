import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

/**
 * SISTEMA PRINCIPAL: SNAKE GAME (CMD TACTICAL EDITION)
 * ARQUITECTURA: MVC (Model - View - Controller)
 * ARCHIVO ÚNICO. CERO DEPENDENCIAS.
 */
public class SnakeGame {
    public static void main(String[] args) {
        GameController controller = new GameController();
        controller.iniciarSistema();
    }
}

// ==========================================
// ENUMS DE ESTADO Y DIRECCIÓN
// ==========================================
enum EstadoJuego {
    JUGANDO,
    GANADO,
    PERDIDO
}

enum Direccion {
    ARRIBA,
    ABAJO,
    IZQUIERDA,
    DERECHA
}

// ==========================================
// CLASE DE COORDENADAS (DATOS PUROS)
// ==========================================
class Punto {
    private int x;
    private int y;

    public Punto(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public boolean esIgual(Punto otro) {
        return this.x == otro.getX() && this.y == otro.getY();
    }
}

// ==========================================
// MODELO (LÓGICA Y DATOS ENCAPSULADOS)
// ==========================================
class GameModel {
    private final int ANCHO = 20;
    private final int ALTO = 10;
    private final int META_MANZANAS = 10;

    private LinkedList<Punto> cuerpoSerpiente;
    private Punto manzana;
    private int cantidadActual;
    private EstadoJuego estado;
    private Direccion direccionActual;

    public GameModel() {
        cuerpoSerpiente = new LinkedList<>();
        // Posición inicial segura en el centro
        cuerpoSerpiente.add(new Punto(ANCHO / 2, ALTO / 2));
        setCantidadActual(0);
        setEstado(EstadoJuego.JUGANDO);
        setDireccionActual(Direccion.DERECHA);
        generarManzana();
    }

    // --- GETTERS Y SETTERS ESTRUCTURALES ---

    public int getCantidadActual() { return cantidadActual; }
    public void setCantidadActual(int cantidadActual) { this.cantidadActual = cantidadActual; }

    public EstadoJuego getEstado() { return estado; }
    public void setEstado(EstadoJuego estado) { this.estado = estado; }

    public boolean getVivo() { return this.estado == EstadoJuego.JUGANDO; }

    public Direccion getDireccionActual() { return direccionActual; }
    public void setDireccionActual(Direccion direccionActual) { this.direccionActual = direccionActual; }
    
    public LinkedList<Punto> getCuerpoSerpiente() { return cuerpoSerpiente; }
    public Punto getManzana() { return manzana; }
    
    public int getAncho() { return ANCHO; }
    public int getAlto() { return ALTO; }

    // --- LÓGICA INTERNA ---

    private void generarManzana() {
        Random rand = new Random();
        boolean posicionValida = false;
        int nuevoX = 0, nuevoY = 0;

        while (!posicionValida) {
            nuevoX = rand.nextInt(ANCHO - 2) + 1;
            nuevoY = rand.nextInt(ALTO - 2) + 1;
            posicionValida = true;

            for (Punto p : cuerpoSerpiente) {
                if (p.getX() == nuevoX && p.getY() == nuevoY) {
                    posicionValida = false;
                    break;
                }
            }
        }
        this.manzana = new Punto(nuevoX, nuevoY);
    }

    public void actualizarMovimiento() {
        if (!getVivo()) return;

        Punto cabeza = cuerpoSerpiente.getFirst();
        int nuevaX = cabeza.getX();
        int nuevaY = cabeza.getY();

        switch (direccionActual) {
            case ARRIBA: nuevaY--; break;
            case ABAJO: nuevaY++; break;
            case IZQUIERDA: nuevaX--; break;
            case DERECHA: nuevaX++; break;
        }

        Punto nuevaCabeza = new Punto(nuevaX, nuevaY);

        // Verificación de colisión de muros
        if (nuevaX <= 0 || nuevaX >= ANCHO - 1 || nuevaY <= 0 || nuevaY >= ALTO - 1) {
            setEstado(EstadoJuego.PERDIDO);
            return;
        }

        // Verificación de colisión consigo misma
        for (Punto p : cuerpoSerpiente) {
            if (p.esIgual(nuevaCabeza)) {
                setEstado(EstadoJuego.PERDIDO);
                return;
            }
        }

        cuerpoSerpiente.addFirst(nuevaCabeza);

        // Verificación de captura de manzana
        if (nuevaCabeza.esIgual(manzana)) {
            setCantidadActual(getCantidadActual() + 1);
            if (getCantidadActual() >= META_MANZANAS) {
                setEstado(EstadoJuego.GANADO);
            } else {
                generarManzana();
            }
        } else {
            // Si no come, la cola avanza (se elimina el último segmento)
            cuerpoSerpiente.removeLast();
        }
    }
}

// ==========================================
// VISTA (RENDERIZADO EN CMD)
// ==========================================
class GameView {
    public void renderizar(GameModel modelo) {
        limpiarPantalla();
        
        System.out.println("==================================");
        System.out.println("  OPERACION: SERPIENTE TACTICA");
        System.out.println("  MANZANAS: " + modelo.getCantidadActual() + " / 10");
        System.out.println("==================================");

        for (int y = 0; y < modelo.getAlto(); y++) {
            for (int x = 0; x < modelo.getAncho(); x++) {
                if (y == 0 || y == modelo.getAlto() - 1 || x == 0 || x == modelo.getAncho() - 1) {
                    System.out.print("#"); // Muro perimetral
                } else if (modelo.getManzana().getX() == x && modelo.getManzana().getY() == y) {
                    System.out.print("@"); // Manzana
                } else {
                    boolean esSerpiente = false;
                    boolean esCabeza = false;
                    
                    if (modelo.getCuerpoSerpiente().getFirst().getX() == x && modelo.getCuerpoSerpiente().getFirst().getY() == y) {
                        esSerpiente = true;
                        esCabeza = true;
                    } else {
                        for (Punto p : modelo.getCuerpoSerpiente()) {
                            if (p.getX() == x && p.getY() == y) {
                                esSerpiente = true;
                                break;
                            }
                        }
                    }

                    if (esCabeza) System.out.print("O");
                    else if (esSerpiente) System.out.print("o");
                    else System.out.print(" ");
                }
            }
            System.out.println();
        }
        
        System.out.println("==================================");
        if (modelo.getEstado() == EstadoJuego.JUGANDO) {
            System.out.println("CONTROLES: W (Arriba), S (Abajo), A (Izquierda), D (Derecha)");
            System.out.print("Ingrese direccion y presione ENTER (o solo ENTER para avanzar): ");
        }
    }

    private void limpiarPantalla() {
        // Enfoque a prueba de fallos para limpiar terminales sin secuencias ANSI.
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
    }

    public void mostrarMensajeFinal(EstadoJuego estado) {
        if (estado == EstadoJuego.GANADO) {
            System.out.println("\n[!] PROTOCOLO COMPLETADO CON EXITO.");
            System.out.println("===============================");
            System.out.println("           YOU WIN!            ");
            System.out.println("===============================");
        } else {
            System.out.println("\n[X] FALLO CATASTROFICO. COLISION DETECTADA.");
            System.out.println("===============================");
            System.out.println("          GAME OVER            ");
            System.out.println("===============================");
        }
    }
}

// ==========================================
// CONTROLADOR (GESTIÓN DE I/O Y BUCLE)
// ==========================================
class GameController {
    private GameModel modelo;
    private GameView vista;
    private Scanner scanner;

    public GameController() {
        this.modelo = new GameModel();
        this.vista = new GameView();
        this.scanner = new Scanner(System.in);
    }

    public void iniciarSistema() {
        while (modelo.getVivo()) {
            vista.renderizar(modelo);
            procesarEntrada();
            modelo.actualizarMovimiento();
        }
        vista.renderizar(modelo); // Render final
        vista.mostrarMensajeFinal(modelo.getEstado());
        scanner.close();
    }

    private void procesarEntrada() {
        String input = scanner.nextLine().trim().toLowerCase();
        
        if (input.length() > 0) {
            char comando = input.charAt(0);
            Direccion actual = modelo.getDireccionActual();
            
            // Prevenir suicidio al invertir dirección inmediatamente
            switch (comando) {
                case 'w':
                    if (actual != Direccion.ABAJO) modelo.setDireccionActual(Direccion.ARRIBA);
                    break;
                case 's':
                    if (actual != Direccion.ARRIBA) modelo.setDireccionActual(Direccion.ABAJO);
                    break;
                case 'a':
                    if (actual != Direccion.DERECHA) modelo.setDireccionActual(Direccion.IZQUIERDA);
                    break;
                case 'd':
                    if (actual != Direccion.IZQUIERDA) modelo.setDireccionActual(Direccion.DERECHA);
                    break;
            }
        }
        // Si el input está vacío (solo se presionó Enter), la serpiente mantiene su rumbo táctico.
    }
}