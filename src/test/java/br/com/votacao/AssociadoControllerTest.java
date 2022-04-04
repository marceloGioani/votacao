/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.votacao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.votacao.controller.AssociadoController;


public class AssociadoControllerTest extends VotacaoApiApplicationTests {
    private MockMvc mockMvc;

    @Autowired
    private AssociadoController associadoController;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(associadoController).build();
    }

    @Test
    public void testGetAssociadosController() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/associados")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testPostAssociadoController() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/associados")
                .content("{'nome': 'Marcelo Giobani', 'cpf': '55758227706', 'ativo': 'true' }")
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
