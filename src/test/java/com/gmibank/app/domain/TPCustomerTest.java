package com.gmibank.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gmibank.app.web.rest.TestUtil;

public class TPCustomerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TPCustomer.class);
        TPCustomer tPCustomer1 = new TPCustomer();
        tPCustomer1.setId(1L);
        TPCustomer tPCustomer2 = new TPCustomer();
        tPCustomer2.setId(tPCustomer1.getId());
        assertThat(tPCustomer1).isEqualTo(tPCustomer2);
        tPCustomer2.setId(2L);
        assertThat(tPCustomer1).isNotEqualTo(tPCustomer2);
        tPCustomer1.setId(null);
        assertThat(tPCustomer1).isNotEqualTo(tPCustomer2);
    }
}
