package br.com.votacao.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.votacao.error.ResponseMsg;
import br.com.votacao.model.Votacao;
import br.com.votacao.model.dto.VotacaoDto;
import br.com.votacao.repository.VotacaoRepository;
import br.com.votacao.service.VotacaoService;

@RestController
@RequestMapping ("/api/votacoes")
public class VotacaoController {

	@Autowired
	private VotacaoService service;
	
	@Autowired
	private VotacaoRepository repository;

	@GetMapping ("/v1")
	@ResponseBody
	public Page<VotacaoDto> findVotacao(@PageableDefault(sort = "id", direction = Direction.DESC, 
										page = 0, size = 10) Pageable pageable) {
		Page<Votacao> votacoes = null;
		votacoes = repository.findAll(pageable);
		
		return VotacaoDto.convert(votacoes);
	}

	@GetMapping ("/v1/status/{id}")
	@ResponseBody
	public ResponseEntity<Object> statusVotacao(@PathVariable long id) {
		try {
			VotacaoDto vtc = service.statusVotacao(id);
			return new ResponseEntity<>(vtc, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseMsg(
                    HttpStatus.OK,
                    "Votação não encontrada", "Não existe uma votação criada para de código: " + id), HttpStatus.OK);
		}
		
	}
	
	@PostMapping ("/v1/abrirVotacao")
	public ResponseEntity<Object> criarVotacao(@RequestBody @Valid Votacao votacao)  {
		try {
			votacao.validaTempo();
			return new ResponseEntity<>(service.abreVotacao(votacao), HttpStatus.CREATED);
			
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseMsg(
                    HttpStatus.OK,
                    "Erro ao ciar a votação!", e.getMessage()), HttpStatus.OK);
		}
		
	}

}
