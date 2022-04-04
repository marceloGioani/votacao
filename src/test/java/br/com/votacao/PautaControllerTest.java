/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.votacao;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.votacao.controller.PautaController;

public class PautaControllerTest extends VotacaoApiApplicationTests {
    private MockMvc mockMvc;

    @Autowired
    private PautaController pautaController;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(pautaController).build();
    }

    @Test
    public void testGetPautasController() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/pautas")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testPostPautaController() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/pautas/")
                .content("{'titulo': 'Pauta de Teste', 'descricao': 'Pauta criada para primeira votacao', 'situacao':'NOVA' }")
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
