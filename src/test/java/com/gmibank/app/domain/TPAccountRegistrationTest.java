package com.gmibank.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gmibank.app.web.rest.TestUtil;

public class TPAccountRegistrationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TPAccountRegistration.class);
        TPAccountRegistration tPAccountRegistration1 = new TPAccountRegistration();
        tPAccountRegistration1.setId(1L);
        TPAccountRegistration tPAccountRegistration2 = new TPAccountRegistration();
        tPAccountRegistration2.setId(tPAccountRegistration1.getId());
        assertThat(tPAccountRegistration1).isEqualTo(tPAccountRegistration2);
        tPAccountRegistration2.setId(2L);
        assertThat(tPAccountRegistration1).isNotEqualTo(tPAccountRegistration2);
        tPAccountRegistration1.setId(null);
        assertThat(tPAccountRegistration1).isNotEqualTo(tPAccountRegistration2);
    }
}
