/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.votacao;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.votacao.controller.PautaController;
import br.com.votacao.controller.VotacaoController;


public class VotacaoControllerTest extends VotacaoApiApplicationTests {
    private MockMvc mockMvc;

    @Autowired
    private VotacaoController votacaoController;
    
    @Autowired
    private PautaController pautaController;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(votacaoController).build();
    }

    @Test
    public void testPostVotacaoController() throws Exception {
    	
    	LocalDateTime dataInicio = LocalDateTime.now();    	
    	LocalDateTime dataFim = dataInicio.plusHours(2);
    	
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/pautas/v1/abrirVotacao")
                .content("{'idPauta': '1', 'abertura': '" + dataInicio +"', 'encerramento': '" + dataFim + "'}")
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    	
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/pautas/v1/votacao?minutes=1")
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testGetVotacoesController() throws Exception {
    	this.mockMvc.perform(MockMvcRequestBuilders.get("/api/votacoes/v1")).andExpect(MockMvcResultMatchers.status().isOk());

    	this.mockMvc.perform(MockMvcRequestBuilders.post("/api/pautas/v1/stop/SUSPENSA")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
}
