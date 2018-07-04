package br.com.ia.qlearning.view;

public enum RecompensaStyle {

    INICIAl("estado-inicial"),
    INTRANSPONIVEL("estado-intransponivel"),
    CONVENCIONAL("estado-convencional"),
    OBJETIVO("estado-objetivo");

    private final String styleClass;

    private RecompensaStyle(final String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}