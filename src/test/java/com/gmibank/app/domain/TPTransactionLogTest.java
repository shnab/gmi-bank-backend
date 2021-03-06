package com.gmibank.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gmibank.app.web.rest.TestUtil;

public class TPTransactionLogTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TPTransactionLog.class);
        TPTransactionLog tPTransactionLog1 = new TPTransactionLog();
        tPTransactionLog1.setId(1L);
        TPTransactionLog tPTransactionLog2 = new TPTransactionLog();
        tPTransactionLog2.setId(tPTransactionLog1.getId());
        assertThat(tPTransactionLog1).isEqualTo(tPTransactionLog2);
        tPTransactionLog2.setId(2L);
        assertThat(tPTransactionLog1).isNotEqualTo(tPTransactionLog2);
        tPTransactionLog1.setId(null);
        assertThat(tPTransactionLog1).isNotEqualTo(tPTransactionLog2);
    }
}
