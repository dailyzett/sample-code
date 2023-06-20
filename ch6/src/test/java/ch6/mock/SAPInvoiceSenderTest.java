package ch6.mock;

import ch6.arguments.InvoiceToSapInvoiceConverter;
import ch6.arguments.SapInvoice;
import ch6.stub.Invoice;
import ch6.stub.InvoiceFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SAPInvoiceSenderTest {

    private final InvoiceFilter filter = mock(InvoiceFilter.class);
    private final SAP sap = mock(SAP.class);
    private final SAPInvoiceSender sender = new SAPInvoiceSender(filter, sap);
    private final InvoiceToSapInvoiceConverter converter = new InvoiceToSapInvoiceConverter();

    @Test
    void sendToSap() {
        Invoice mauricio = new Invoice("Mauricio", 20);
        Invoice frank = new Invoice("Frank", 99);

        List<Invoice> invoices = Arrays.asList(mauricio, frank);
        when(filter.lowValueInvoices()).thenReturn(invoices);

        sender.sendLowValuedInvoices();

        verify(sap).send(mauricio);
        verify(sap).send(frank);
    }

    @Test
    void noLowValueInvoices() {
        List<Invoice> invoices = emptyList();
        when(filter.lowValueInvoices()).thenReturn(invoices);

        sender.sendLowValuedInvoices();

        verify(sap, never()).send(any(Invoice.class));
    }

    // you may want to add more tests here.
    @Test
    void sendSapInvoiceToSap() {
        Invoice kimsejun = new Invoice("kimsejun", 20);

        List<Invoice> list = List.of(kimsejun);
        when(filter.lowValueInvoices()).thenReturn(list);

        SapInvoice result = converter.convert(kimsejun);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getClass(), SapInvoice.class);
    }
}
