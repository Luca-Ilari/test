package com.mycompany.app;

import static org.junit.Assert.assertTrue;

import org.example.App;
import org.example.SistemaBancario;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    public void testMain() {
        App.main(null);
    }

    @Test
    public void testSistemaBanc() {
        SistemaBancario.main(null);
    }

    @Test
    public void mainInstance() {
        App x = new App();
    }

}
