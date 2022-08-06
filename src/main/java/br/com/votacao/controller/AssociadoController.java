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
import br.com.votacao.model.Associado;
import br.com.votacao.repository.AssociadoRepository;
import br.com.votacao.service.AssociadoService;


@RestController
@RequestMapping("/api/associados")
public class AssociadoController {
	
	@Autowired
	private AssociadoRepository repository;
	
	@Autowired
	private AssociadoService associadoService;
	
	@GetMapping("/{id}")
	@ResponseBody
	public Associado find(@PathVariable Long id) {
		Optional<Associado>  associado = repository.findById(id);
		return associado.get();
	}
	
	@GetMapping("/lista")
	@ResponseBody
	public Page<Associado> findAssociados(@PageableDefault(sort = "id", 
														   direction = Direction.DESC, 
														   page = 0, size = 10) Pageable pageable) {
		return repository.findAll(pageable);

	}
	
	
	@GetMapping("/lista/{nome}")
	@ResponseBody
	public List<Associado> findAssociados(@RequestParam String nome ){

		return repository.findByNome(nome);
		
	}
	
	@PostMapping
	public ResponseEntity<Object> createAssociado(@RequestBody @Valid Associado associado) {
		
		try {
				associado = associadoService.save(associado);
				return new ResponseEntity<>(associado, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseMsg(
                    HttpStatus.NOT_ACCEPTABLE,
                    "Cadastro de associado não permitido!", e.getMessage()), HttpStatus.NOT_ACCEPTABLE);

		}
	}
	
	@PutMapping
	public ResponseEntity<Associado> atualizar(@RequestBody @Valid Associado associado) {
		Optional<Associado>  upAssociado = repository.findById(associado.getId());
		
		if (upAssociado.isPresent()) {
			upAssociado = Optional.ofNullable(upAssociado.get().atualiza(associado));
			return ResponseEntity.ok(upAssociado.get());
		}
		
		return ResponseEntity.notFound().build();
		
		
	}
	
}
