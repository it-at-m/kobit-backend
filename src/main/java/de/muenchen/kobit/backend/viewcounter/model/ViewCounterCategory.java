package de.muenchen.kobit.backend.viewcounter.model;

import lombok.Getter;

@Getter
public enum ViewCounterCategory {
    E_MAIL_SEND_COUNTER("Anzahl versendeter E-Mails"),
    PAGE_VISITED_COUNTER("Anzahl der Seitenaufrufe");


    private final String description;

    ViewCounterCategory(String description) {
        this.description = description;
    }
}
