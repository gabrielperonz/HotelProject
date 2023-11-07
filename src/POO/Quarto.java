package POO;

public class Quarto {

    private String tipoCama;
    private boolean aceitaAnimais;

    private String tipoQuarto;
    private boolean servicoDeQuarto;
    public String getTipoCama() {
        return tipoCama;
    }

    public void setTipoCama(String tipoCama) {
        this.tipoCama = tipoCama;
    }

    public boolean isAceitaAnimais() {
        return aceitaAnimais;
    }

    public void setAceitaAnimais(boolean aceitaAnimais) {
        this.aceitaAnimais = aceitaAnimais;
    }

    public String getTipoQuarto() {
        return tipoQuarto;
    }

    public void setTipoQuarto(String tipoQuarto) {
        this.tipoQuarto = tipoQuarto;
    }

    public boolean getServicoDeQuarto() {
        return servicoDeQuarto;
    }

    public void setServicoDeQuarto(boolean servicoDeQuarto) {
        this.servicoDeQuarto = servicoDeQuarto;
    }
}
