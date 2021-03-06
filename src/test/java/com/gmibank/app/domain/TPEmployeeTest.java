package com.gmibank.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gmibank.app.web.rest.TestUtil;

public class TPEmployeeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TPEmployee.class);
        TPEmployee tPEmployee1 = new TPEmployee();
        tPEmployee1.setId(1L);
        TPEmployee tPEmployee2 = new TPEmployee();
        tPEmployee2.setId(tPEmployee1.getId());
        assertThat(tPEmployee1).isEqualTo(tPEmployee2);
        tPEmployee2.setId(2L);
        assertThat(tPEmployee1).isNotEqualTo(tPEmployee2);
        tPEmployee1.setId(null);
        assertThat(tPEmployee1).isNotEqualTo(tPEmployee2);
    }
}
