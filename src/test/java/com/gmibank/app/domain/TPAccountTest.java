package com.gmibank.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gmibank.app.web.rest.TestUtil;

public class TPAccountTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TPAccount.class);
        TPAccount tPAccount1 = new TPAccount();
        tPAccount1.setId(1L);
        TPAccount tPAccount2 = new TPAccount();
        tPAccount2.setId(tPAccount1.getId());
        assertThat(tPAccount1).isEqualTo(tPAccount2);
        tPAccount2.setId(2L);
        assertThat(tPAccount1).isNotEqualTo(tPAccount2);
        tPAccount1.setId(null);
        assertThat(tPAccount1).isNotEqualTo(tPAccount2);
    }
}
