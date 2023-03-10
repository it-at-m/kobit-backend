package de.muenchen.kobit.backend.competence;

import de.muenchen.kobit.backend.competence.view.CompetenceView;

public enum Competence {

    // Conflict Points
    WORKPLACE_CONFLICT(
            "Arbeitsplatzkonflikt",
            "Das Aufeinanderprallen widerstreitender Auffassungen, Interessen oder Ähnlichem"
                    + " entstandenen schwierigen Situation, die zum Zerwürfnis führen kann."),
    MOBBING(
            "Mobbing",
            "Gezieltes Anfeinden, Schikanieren und Diskriminieren von Kolleg*innen untereinander"
                    + " oder durch Vorgesetzte mit dem Ziel der Ausgrenzung"),
    DISCRIMINATION(
            "Diskriminierung",
            "Herabwürdigung von Menschen oder eine Schlechterbehandlung ohne Grund"),
    SEXUAL_HARASSMENT(
            "Sexuelle Belästigung",
            "Jedes unerwünschte, sexuell bestimmte, körperliche, verbale oder nonverbale"
                + " Verhalten, das bezweckt oder bewirkt, dass die Würde der betreffenden Person"
                + " verletzt wird."),
    DOMESTIC_VIOLENCE(
            "Häusliche Gewalt",
            "Häusliche Gewalt ist jede Art körperlicher, seelischer oder sexueller Misshandlung"
                    + " zwischen Personen die innerhalb einer bestehenden oder im Zusammenhang mit"
                    + " einer früheren häuslichen Gemeinschaft oder Beziehung verübt, versucht oder"
                    + " angedroht wird."),
    PRIVATE_PROBLEMS(
            "Privates Problem",
            "Familiäres und partnerschaftliches Problem, schwere Erkrankung, Verlust,"
                    + " Überforderung im Alltag, Umbruchsituation."),
    HEALTH_PROBLEMS("Gesundheitliches Problem", ""),
    ANTI_DEMOCRACY(
            "Demokratiefeindlichkeit", "Einstellung/Haltung, die sich gegen Demokratie richtet."),

    // own role
    EMPLOYEE("Mitarbeiter*in", ""),
    EXECUTIVE("Führungskraft", ""),
    JUNIOR("Nachwuchskraft", ""),

    /**
     * Both are just used if there is a contact point, which is not responsible for employees or
     * executives but when they have problems with juniors.
     */
    EMPLOYEE_JUNIOR("", ""),
    EXECUTIVE_JUNIOR("", ""),

    // role of opposite
    OPPOSITE_EMPLOYEE("Kolleg*in", ""),
    OPPOSITE_JUNIOR("Nachwuchskraft", ""),
    OPPOSITE_EXECUTIVE("Führungskraft", ""),
    OPPOSITE_OTHER_TEAM("teamübergreifend", ""),

    // Stress
    STRESS_LOW("gering", ""),
    STRESS_MEDIUM("mittel", ""),
    STRESS_HIGH("hoch", ""),

    // Escalation Level
    ESCALATION_LOW("gering", ""),
    ESCALATION_MEDIUM("mittel", ""),
    ESCALATION_HIGH("hoch", ""),

    // reasons of discrimination
    ETHNIC_RACIAL(
            "Diskriminierung aufgrund ethnischer oder rassistischer Motive, Religion oder"
                    + " Weltanschauung, Alter",
            ""),
    DISABLED("Diskriminierung aufgrund Behinderung", ""),
    SEXUAL_IDENTITY("Diskriminierung aufgrund sexueller oder geschlechtlicher Identität", ""),

    // sexual discrimination
    EQUALITY("Gleichstellung Mann Frau", ""),
    LGBTIQ("LGBTIQ*", ""),

    // Health Issues
    ADDICTION("Suchtproblem", ""),
    PHYSICAL("körperliches Problem", ""),
    PSYCHOLOGICAL("psychisches Problem", "");

    public final String germanDescription;
    public final String shortDescription;

    Competence(String germanDescription, String shortDescription) {
        this.germanDescription = germanDescription;
        this.shortDescription = shortDescription;
    }

    public CompetenceView toCompetenceView() {
        return new CompetenceView(this, this.germanDescription, this.shortDescription);
    }
}
