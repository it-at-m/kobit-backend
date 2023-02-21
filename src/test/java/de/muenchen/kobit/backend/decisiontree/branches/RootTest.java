package de.muenchen.kobit.backend.decisiontree.branches;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RootTest {

    private Root root;

    @BeforeEach
    void init() {
        root = new Root();
    }

    @Test
    void getRootOptionsTest() {
        var result = root.getRootOptions();
        assertThat(result.getCompetence()).isEqualTo(null);
        assertThat(result.getAnswerOptions().size()).isEqualTo(8);
        assertThat(result.getQuestion()).isEqualTo("Worum geht es?");
    }
}
