/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.votacao.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.votacao.error.ResponseMsg;
import br.com.votacao.model.Voto;
import br.com.votacao.service.VotoService;


@RestController
@RequestMapping(path = "/api/votos")
public class VotoController {
    
    @Autowired
    VotoService service;
    
    @PostMapping
	public ResponseEntity<Object> votar(@RequestBody @Valid Voto voto) {
		
    	try {
			this.service.registrarVoto(voto);
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseMsg(
                    HttpStatus.NOT_MODIFIED,
                    "Erro ao ao regstrar o voto!", e.getMessage()), HttpStatus.OK);
		}
		
		return new ResponseEntity<>("Voto Confirmado", HttpStatus.CREATED);
		
		
	}
}