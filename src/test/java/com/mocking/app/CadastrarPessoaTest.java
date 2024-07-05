package com.mocking.app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;


@ExtendWith(MockitoExtension.class)
public class CadastrarPessoaTest {

    @Mock
    private ApiDosCorreios apiDosCorreios;

    @InjectMocks
    private CadastrarPessoa cadastrarPessoa;

    @Test
    void validarDadosDeCadastro() {

        DadosLocalizacao dadosLocalizacao = new DadosLocalizacao("PE", "Recife", "Rua Equador", "apartamento", "Centro");

//        Mockito.when(apiDosCorreios.buscaDadosComBaseNoCep(anyString())).thenReturn(dadosLocalizacao);
        Mockito.when(apiDosCorreios.buscaDadosComBaseNoCep(anyString())).thenThrow(IllegalArgumentException.class);

        Pessoa pessoa = cadastrarPessoa.cadastrarPessoa("Danton", "321321", LocalDate.now(), "123123");

        assertEquals("Danton", pessoa.getName());
        assertEquals("321321", pessoa.getDocumento());
        assertEquals("PE", pessoa.getEndereco().getUf());
        assertEquals("apartamento", pessoa.getEndereco().getComplemento());
    }
    @Test
    void retornarExcecaoAoCadastrar() {

        new DadosLocalizacao("PE", "Recife", "Rua Equador", "apartamento", "Centro");

        doThrow(IllegalArgumentException.class)
                        .when(apiDosCorreios)
                        .buscaDadosComBaseNoCep(anyString());

        assertThrows(IllegalArgumentException.class,() -> cadastrarPessoa.cadastrarPessoa("Danton", "321321", LocalDate.now(), "123123"));

    }
}
