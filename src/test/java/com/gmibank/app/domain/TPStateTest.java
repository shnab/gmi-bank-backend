package com.gmibank.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gmibank.app.web.rest.TestUtil;

public class TPStateTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TPState.class);
        TPState tPState1 = new TPState();
        tPState1.setId(1L);
        TPState tPState2 = new TPState();
        tPState2.setId(tPState1.getId());
        assertThat(tPState1).isEqualTo(tPState2);
        tPState2.setId(2L);
        assertThat(tPState1).isNotEqualTo(tPState2);
        tPState1.setId(null);
        assertThat(tPState1).isNotEqualTo(tPState2);
    }
}
