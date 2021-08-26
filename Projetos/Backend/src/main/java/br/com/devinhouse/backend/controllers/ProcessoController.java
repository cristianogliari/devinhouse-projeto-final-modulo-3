package br.com.devinhouse.backend.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.devinhouse.backend.entities.Processo;
import br.com.devinhouse.backend.services.ProcessoService;

@RestController
@RequestMapping(value = "/processos/v1", headers = "api-version=v1")
@CrossOrigin
public class ProcessoController {
	
	private static final Logger LOGGER = LogManager.getLogger(ProcessoController.class);
	
	@Autowired
	private ProcessoService service;
	
	//	1 - Deverá haver um endpoint para criação de um processo;
	@RolesAllowed("user-role")
	@RequestMapping(value = "/cadastrar", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public Processo criarProcesso(@RequestBody Processo obj) {
		LOGGER.info("Cadastrou o processo com descricao: {}", obj.getDescricao());
		return service.cadastrarProcesso(obj);
	}
	
	//	2 - Deverá haver um endpoint para listagem de todos os processos, retornando todos os atributos de cada processo;
	@RolesAllowed("user-role")
	@RequestMapping(value = "/buscar", method = GET, produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<Processo> buscarTodosOsProcessosController() {
		LOGGER.info("Buscou todos os processos");
		return service.buscarTodosOsProcessos();
	}	
	
	//	3 - Deverá haver um endpoint para buscar um processo baseado na sua identificação única (ID);
	@RolesAllowed("user-role")
	@RequestMapping(value = "/buscar/id/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<Processo> buscarProcessoPorIDController(@PathVariable Integer id) {
		LOGGER.info("Buscou o processo atraves da id: {}", id);
		return service.buscarProcessoPorID(id);
	}
		
	//	4 - Deverá haver um endpoint para buscar um processo baseado no seu número de processo (CHAVEPROCESSO);
	@RolesAllowed("user-role")
	@RequestMapping(value = "/buscar/chaveprocesso", method = GET, produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Processo buscarProcessoPorChaveProcessoController(@RequestParam("value") String termo) {
		LOGGER.info("Buscou o processo pela chave de processo: {}", termo);
		return service.buscarProcessoPorChaveProcesso(termo);
	}	
	
	//	5 - Deverá haver um endpoint para buscar um ou mais processos baseado em seu interessado (CDINTERESSADO);
	@RolesAllowed("user-role")
	@RequestMapping(value = "/buscar/interessado/id/{interessado}", method = GET, produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<Processo> buscarProcessosPorInteressadosController(@PathVariable("interessado") Integer termo) {
		LOGGER.info("Buscou os processos com interessado ID: {}", termo);
		return service.buscarProcessosPorInteressados(termo);
	}
	
	//	6 - Deverá haver um endpoint para buscar um ou mais processos baseado em seu assunto (CDASSUNTO);
	@RolesAllowed("user-role")
	@RequestMapping(value = "/buscar/assunto/id/{assunto}", method = GET, produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<Processo> buscarProcessosPorAssuntoController(@PathVariable("assunto") Integer termo) {
		LOGGER.info("Buscou os processos com o assunto ID: {}", termo);
		return service.buscarProcessosPorAssunto(termo);
	}
	
	//	7 - Deverá haver um endpoint para atualização de todos os atributos de um processo baseado na sua identificação única (ID);
	@RolesAllowed("user-role")
	@RequestMapping(value = "/atualizar/id/{id}", method = PUT, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Processo atualizarProcessoPorIDController(@PathVariable Integer id, @RequestBody Processo obj) {
		LOGGER.info("Atualizou o processo de ID {} com novas informacoes", id);
		return service.atualizarProcessoPorID(id, obj);
	}
	
	//	8 - Deverá haver um endpoint para exclusão de um processo baseado na sua identificação única (ID);
	@RolesAllowed("user-role")
	@RequestMapping(value = "/remover/id/{id}", method = DELETE, produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<Processo> removerProcessoPorIDController(@PathVariable Integer id) {
		LOGGER.info("Removeu o processo com ID: {}", id);
		return service.removerProcessoPorID(id);
	}
}
