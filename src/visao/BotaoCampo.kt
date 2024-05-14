package visao

import modelo.Campo
import modelo.CampoEvento
import java.awt.Color
import java.awt.Font
import javax.swing.*

private val COR_BG_NORMAL = Color(184, 184, 184)
private val COR_BG_MARCACAO = Color(8, 179, 247)
private val COR_BG_EXPLOSAO = Color(189, 66, 68)
private val COR_BG_ABERTO = Color(121, 7, 199)
//private val SEM = ColorSpace(null)
private val COR_TXT_VERDE = Color(120, 252, 93)
private val IMG_BANDEIRA : Icon = ImageIcon("./src/img/bandeira-vermelha.png")
private val IMG_EXPLOSAO : Icon = ImageIcon("./src/img/bombear.png")


class BotaoCampo(private val campo: Campo) : JButton() {

    init {
        icon = null
        font = font.deriveFont(Font.BOLD)
        background = COR_BG_NORMAL
        isOpaque = true
        border = BorderFactory.createBevelBorder(0)
        addMouseListener(MouseCliqueListener(campo, { it.abrir() }, { it.alterarMarcacao() }))
        campo.onEvento(this::aplicarEstilo)
    }

    private fun aplicarEstilo(campo: Campo, evento: CampoEvento) {
        when(evento) {
            CampoEvento.EXPLOSAO -> aplicarEstiloExplodido()
            CampoEvento.ABERTURA -> aplicarEstiloAberto()
            CampoEvento.MARCACAO -> aplicarEstiloMarcado()
            else -> aplicarEstiloPadrao()
        }

        SwingUtilities.invokeLater {
            repaint()
            validate()
        }
    }

    private fun aplicarEstiloExplodido() {
        icon = IMG_EXPLOSAO
        background = COR_BG_EXPLOSAO
    }

    private fun aplicarEstiloAberto() {
        icon = null
        background = COR_BG_ABERTO
        border = BorderFactory.createLineBorder(Color.GRAY)

        foreground = when (campo.qtdeVizinhosMinados) {
            1 -> COR_TXT_VERDE
            2 -> Color.CYAN
            3 -> Color.YELLOW
            4, 5, 6 -> Color.RED
            else -> Color.PINK
        }

        text = if (campo.qtdeVizinhosMinados > 0) campo.qtdeVizinhosMinados.toString() else ""
    }

    private fun aplicarEstiloMarcado() {
        icon = IMG_BANDEIRA
        background = COR_BG_MARCACAO
        foreground = Color.BLACK
    }

    private fun aplicarEstiloPadrao() {
        icon = null
        background = COR_BG_NORMAL
        border = BorderFactory.createBevelBorder(0)
        text = ""
    }
}