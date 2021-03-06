package com.gmibank.app.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gmibank.app.web.rest.TestUtil;

public class TPAccountRegistrationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TPAccountRegistrationDTO.class);
        TPAccountRegistrationDTO tPAccountRegistrationDTO1 = new TPAccountRegistrationDTO();
        tPAccountRegistrationDTO1.setId(1L);
        TPAccountRegistrationDTO tPAccountRegistrationDTO2 = new TPAccountRegistrationDTO();
        assertThat(tPAccountRegistrationDTO1).isNotEqualTo(tPAccountRegistrationDTO2);
        tPAccountRegistrationDTO2.setId(tPAccountRegistrationDTO1.getId());
        assertThat(tPAccountRegistrationDTO1).isEqualTo(tPAccountRegistrationDTO2);
        tPAccountRegistrationDTO2.setId(2L);
        assertThat(tPAccountRegistrationDTO1).isNotEqualTo(tPAccountRegistrationDTO2);
        tPAccountRegistrationDTO1.setId(null);
        assertThat(tPAccountRegistrationDTO1).isNotEqualTo(tPAccountRegistrationDTO2);
    }
}
