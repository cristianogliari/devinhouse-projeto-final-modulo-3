package br.com.devinhouse.backend.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.devinhouse.backend.entities.Assunto;
import br.com.devinhouse.backend.entities.Interessado;
import br.com.devinhouse.backend.entities.Processo;
import br.com.devinhouse.backend.repositories.AssuntoRepository;
import br.com.devinhouse.backend.repositories.InteressadoRepository;
import br.com.devinhouse.backend.repositories.ProcessoRepository;

@Service
public class ProcessoService {
	
	private static final Logger LOGGER = LogManager.getLogger(ProcessoService.class);
	
	@Autowired
	private ProcessoRepository processoRepository;
	
	@Autowired
	private AssuntoRepository assuntoRepository;
	
	@Autowired
	private InteressadoRepository interessadoRepository;
	
	private boolean verificaExistenciaDeProcesso(Integer id) {
		List<Processo> todosOsProcessos = processoRepository.findAll();
		boolean status = false;
		
		for (Processo each : todosOsProcessos) {
			if(id.equals(each.getId())) {
				status = true;
			}
		}
				
		return status;
	}
	
//	1 - Deverá haver um endpoint para criação de um processo;
	public Processo cadastrarProcesso(Processo obj) {
		char ativo = 'S';
		
		Assunto assuntoLocalizado = assuntoRepository.findById(obj.getCdassunto().getId()).get();
		Interessado interessadoLocalizado = interessadoRepository.findById(obj.getCdinteressado().getId()).get();
		
//		Não poderá ser cadastrado um novo processo com assuntos inesistentes no sistema
		if(assuntoLocalizado == null) {
			LOGGER.error("Nao foi localizado assunto com id: {}", obj.getCdassunto().getId());
			throw new RuntimeException("Assunto não localizado, favor informar um assunto válido.");
		}
		
//		Não poderá ser cadastrado um novo processo com interessados inesistentes no sistema;
		if(interessadoLocalizado == null) {
			LOGGER.error("Nao foi localizado interessado com id: {}", obj.getCdinteressado().getId());
			throw new RuntimeException("Interessado não localizado, favor informar um interessado válido.");
		}

//		Não poderá ser cadastrado um novo processo com assuntos inativos;
		if(assuntoLocalizado.getFlativo().charAt(0) == ativo) {
			obj.setCdassunto(assuntoLocalizado);
		} else {
			LOGGER.error("Tentou cadastrar processo com assunto inativo, assunto informado: {}", obj.getCdassunto().getDescricao());
			throw new RuntimeException("Assunto inativo, favor selecionar um assunto ativo.");
		}
				
//		Não poderá ser cadastrado um novo processo com interessados inativos;
		if(interessadoLocalizado.getFlativo().charAt(0) == ativo) {
			obj.setCdinteressado(interessadoLocalizado);	
		} else {
			LOGGER.error("Tentou cadastrar processo com interessado inativo, interessado informado: {}", obj.getCdinteressado().getNminteressado());
			throw new RuntimeException("Interessado inativo, favor selecionar um interassado ativo.");
		}
		
		obj.setChaveprocesso("XXXX 1/2000");
		Processo processoSalvo = processoRepository.save(obj);
		
//		Numero do processo igual ID
		processoSalvo.setNuprocesso(processoSalvo.getId());
		processoSalvo.setChaveprocesso(processoSalvo.getSgorgaosetor() + " " + processoSalvo.getNuprocesso() + "/" + processoSalvo.getNuano());
		
		return processoRepository.save(processoSalvo);
	}
		
//	2 - Deverá haver um endpoint para listagem de todos os processos, retornando todos os atributos de cada processo;
	public List<Processo> buscarTodosOsProcessos() {
		return processoRepository.findAll();
	}
	
//	3 - Deverá haver um endpoint para buscar um processo baseado na sua identificação única (ID);
	public List<Processo> buscarProcessoPorID(Integer id) {
		if(verificaExistenciaDeProcesso(id)) {
			List<Processo> todosProcessos = new ArrayList<Processo>();
			Processo processo = processoRepository.findById(id).get();
			todosProcessos.add(processo);
			
			return todosProcessos;
		} else {
			LOGGER.error("Nao localizou processo atraves da id: {}", id);
			throw new RuntimeException("Processo não localizado.");	
		}
	}	
	
//	4 - Deverá haver um endpoint para buscar um processo baseado no seu número de processo (CHAVEPROCESSO);
	public Processo buscarProcessoPorChaveProcesso(String termo) {
		return processoRepository.findByChaveprocesso(termo.toUpperCase());
	}
	
//	5 - Deverá haver um endpoint para buscar um ou mais processos baseado em seu interessado (CDINTERESSADO);
	public List<Processo> buscarProcessosPorInteressados(Integer termo) {
		Interessado interessadoLocalizado = interessadoRepository.findById(termo).get();
		List<Processo> processosLocalizados = processoRepository.findByCdinteressado(interessadoLocalizado);
	
		return processosLocalizados;	
	}
	
//	6 - Deverá haver um endpoint para buscar um ou mais processos baseado em seu assunto (CDASSUNTO);
	public List<Processo> buscarProcessosPorAssunto(Integer termo) {
		Assunto assuntoLocalizado = assuntoRepository.findById(termo).get();
		List<Processo> processosLocalizados = processoRepository.findByCdassunto(assuntoLocalizado);
		
		return processosLocalizados;
	}
	
//	7 - Deverá haver um endpoint para atualização de todos os atributos de um processo baseado na sua identificação única (ID);
	public Processo atualizarProcessoPorID(Integer id, Processo obj) {
		if (verificaExistenciaDeProcesso(id)) {
			char ativo = 'S';

			Processo processoEncontrado = processoRepository.findById(id).get();

			if(obj.getDescricao() != null) {
				processoEncontrado.setDescricao(obj.getDescricao());
			}
			if(obj.getCdassunto() != null) {
				Assunto assuntoLocalizado = assuntoRepository.findById(obj.getCdassunto().getId()).get();
				if(assuntoLocalizado.getFlativo().charAt(0) == ativo) {
					processoEncontrado.setCdassunto(assuntoLocalizado);
				} else {
					LOGGER.error("O assunto indicado esta inativo, assunto: {}", obj.getCdassunto().getDescricao());
					throw new RuntimeException("Assunto inativo, favor selecionar um assunto ativo.");
				}
			}
			if(obj.getCdinteressado() != null) {
				Interessado interessadoLocalizado = interessadoRepository.findById(obj.getCdinteressado().getId()).get();
				if(interessadoLocalizado.getFlativo().charAt(0) == ativo) {
					processoEncontrado.setCdinteressado(interessadoLocalizado);	
				} else {
					LOGGER.error("O interessado indicado esta inativo, interessado: {}", obj.getCdinteressado().getNminteressado());
					throw new RuntimeException("Interessado inativo, favor selecionar um interessado ativo.");
				}
			}
			return processoRepository.save(processoEncontrado);			
		} 
		LOGGER.error("Nao encontrou processo com ID {}", id);
		throw new RuntimeException("Processo não encontrado");
	}
	
//	8 - Deverá haver um endpoint para exclusão de um processo baseado na sua identificação única (ID);
	public List<Processo> removerProcessoPorID(Integer id) {
		if(verificaExistenciaDeProcesso(id)) {
			Processo processoFiltrado = processoRepository.findById(id).get();
			processoRepository.delete(processoFiltrado);
			
			return processoRepository.findAll();	
		} else {
			LOGGER.error("Tentou remover processo atraves da ID {}, que nao existe na base de dados", id);
			throw new RuntimeException("Processo não localizado");
		}
	}
}
