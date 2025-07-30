package br.com.dio.model;

public class Space {

    private Integer actual;
    private final int expected;
    private final boolean fixed;

    public Space(int expected, boolean fixed) {
        this.expected = expected;
        this.fixed = fixed;
        if(fixed){
            actual = expected;
        }
    }

    public Integer getActual() {
        return actual;
    }

    public void setActual(final Integer actual) {
        if(fixed) return;
        this.actual = actual;
    } // Mudando o valor do espaço, desde que não seja 'fixed'

    public void clearSpace(){
        setActual(null);
    } // Limpa o valor do espaço selecionado, desde que não seja 'fixed'

    public int getExpected() {
        return expected;
    }

    public boolean isFixed() {
        return fixed;
    }
}
