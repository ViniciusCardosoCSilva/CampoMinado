import modelo.Tabuleiro
import modelo.TabuleiroEvento
import visao.PainelTabuleiro
import java.awt.Color
import java.awt.Font
import java.awt.Toolkit
import javax.swing.*
import javax.swing.border.EmptyBorder
import javax.swing.border.LineBorder

fun main(args: Array<String>) {
    TelaPrincipal()
}

class TelaPrincipal : JFrame() {

    private val tabuleiro = Tabuleiro(qtdeLinhas = 16, qtdeColunas = 30, qtdeMinas = 50)
    private val painelTabuleiro = PainelTabuleiro(tabuleiro)

    init {
        iconImage = Toolkit.getDefaultToolkit().getImage(javaClass.getResource("/img/bomb.jpg"))
        tabuleiro.onEvento(this::mostrarResultado)
        add(painelTabuleiro)

        setSize(690, 438)
        setLocationRelativeTo(null)
        defaultCloseOperation = EXIT_ON_CLOSE
        title = "Campo Minado"
        isVisible = true
    }

    private fun mostrarResultado(evento: TabuleiroEvento) {
        // Adiciona um Timer para esperar 5 segundos antes de mostrar o resultado
        val timer = Timer(5000) {
            SwingUtilities.invokeLater {
                TelaResultado(evento)
                tabuleiro.reiniciar()
                painelTabuleiro.repaint()
                painelTabuleiro.validate()
            }
        }
        timer.isRepeats = false
        timer.start()
    }
}

class TelaResultado(evento: TabuleiroEvento) : JFrame() {
    private var contentPane: JPanel? = null
    private var caminho_img = String();

    init{
        if(evento == TabuleiroEvento.DERROTA){
            caminho_img = "./src/img/derrota.jpg"
            telaResultado(Color(255, 0, 0), "Você perdeu!!!", caminho_img)
        } else {
            caminho_img = "./src/img/vitoria.jpg"
            telaResultado(Color(31, 66, 66), "Você venceu!!!", caminho_img)
        }
        setLocationRelativeTo(null)
    }

    fun telaResultado(cor_texto: Color, msg: String, caminho_img: String) {
        isVisible = true
        defaultCloseOperation = EXIT_ON_CLOSE
        setBounds(100, 100, 450, 300)
        contentPane = JPanel()
        contentPane!!.border = EmptyBorder(5, 5, 5, 5)

        setContentPane(contentPane)
        contentPane!!.layout = null

        val panel = JPanel()
        panel.background = Color(0, 0, 0)
        panel.border = LineBorder(Color(0, 0, 0))
        panel.setBounds(0, 0, 434, 261)
        contentPane!!.add(panel)
        panel.layout = null

        val labelMsg = JLabel(msg)
        labelMsg.foreground = cor_texto
        labelMsg.horizontalAlignment = SwingConstants.CENTER
        labelMsg.font = Font("Tahoma", Font.BOLD, 38)
        labelMsg.setBounds(10, 60, 414, 65)
        panel.add(labelMsg)

        val btnReiniciar = JButton("Reiniciar")
        btnReiniciar.font = Font("Tahoma", Font.PLAIN, 15)
        btnReiniciar.background = Color(0, 128, 0)
        btnReiniciar.addActionListener {
            extendedState = JFrame.ICONIFIED
        }
        btnReiniciar.setBounds(149, 160, 142, 27)
        panel.add(btnReiniciar)

        val lblNewLabel = JLabel("")
        lblNewLabel.horizontalAlignment = SwingConstants.CENTER
        lblNewLabel.icon = ImageIcon(caminho_img)
        lblNewLabel.setBounds(0, 0, 434, 261)
        panel.add(lblNewLabel)
    }
}
