package br.com.votacao.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.votacao.error.ResponseMsg;
import br.com.votacao.model.Pauta;
import br.com.votacao.model.dto.PautaDTO;
import br.com.votacao.model.dto.PautaResponse;
import br.com.votacao.repository.PautaRepository;
import br.com.votacao.service.PautaService;


@RestController
@RequestMapping("/api/pautas")
public class PautaController {

	@Autowired
	private PautaService service;
	
	@Autowired
	private PautaRepository repository;
	
	@GetMapping("/lista")
	@ResponseBody
	public Page<Pauta> findPautas(@PageableDefault(sort = "id", 
													  direction = Direction.DESC, 
													  page = 0, size = 10) Pageable pageable) {
		return repository.findAll(pageable);

	}

	
	@GetMapping("/lista/{titulo}")
	@ResponseBody
	public List<PautaResponse> findPautas(@RequestParam String titulo) {
		
		return repository.findByTitulo(titulo);
		
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	public ResponseEntity<Object> find(@PathVariable Long id) {
		try {
			PautaDTO pauta = service.findById(id);
			return new ResponseEntity<>(pauta, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseMsg(
                    HttpStatus.BAD_REQUEST,
                    "Problemas ao pesquisar a Pauta", e.getMessage()), HttpStatus.OK);
		}
	}
	
	@PostMapping
	public ResponseEntity<PautaDTO> createPauta(@RequestBody @Valid Pauta pauta) {
		pauta = repository.save(pauta);
		
		return new ResponseEntity<>(new PautaDTO(pauta), HttpStatus.CREATED);
		
	}
	
	@PutMapping
	public ResponseEntity<PautaDTO> updatePauta(@RequestBody @Valid Pauta pauta) {
		Optional<Pauta>  upPauta = repository.findById(pauta.getId());
		
		if (upPauta.isPresent()) {
			upPauta = Optional.ofNullable(pauta.atualiza(pauta));
			return ResponseEntity.ok(new PautaDTO(upPauta.get()));
		}
		
		return ResponseEntity.notFound().build();
		
		
	}
	
}
