package com.gmibank.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gmibank.app.web.rest.TestUtil;

public class TPCountryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TPCountry.class);
        TPCountry tPCountry1 = new TPCountry();
        tPCountry1.setId(1L);
        TPCountry tPCountry2 = new TPCountry();
        tPCountry2.setId(tPCountry1.getId());
        assertThat(tPCountry1).isEqualTo(tPCountry2);
        tPCountry2.setId(2L);
        assertThat(tPCountry1).isNotEqualTo(tPCountry2);
        tPCountry1.setId(null);
        assertThat(tPCountry1).isNotEqualTo(tPCountry2);
    }
}
