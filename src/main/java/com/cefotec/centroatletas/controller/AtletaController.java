package com.cefotec.centroatletas.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cefotec.centroatletas.domain.Atleta;
import com.cefotec.centroatletas.domain.AtletaIMC;
import com.cefotec.centroatletas.service.AtletaIMCService;
import com.cefotec.centroatletas.service.AtletaService;

@Controller
public class AtletaController {

	@Autowired
	AtletaService atletaService;

	@Autowired
	AtletaIMCService imcService;

	@RequestMapping("/")
	public String home(Model model) {
		return "index";
	}

	@RequestMapping(value = "/insertar", method = RequestMethod.GET)
	public String insertarPage(Model model) {
		model.addAttribute(new Atleta());
		return "insertar";
	}

	@RequestMapping(value = "/insertar", method = RequestMethod.POST)
	public String insertarAction(Atleta atleta, BindingResult result, Model model) {
		atletaService.save(atleta);
		AtletaIMC imc = new AtletaIMC();

		double tempIMC = atleta.getPeso() / (atleta.getEstatura() * atleta.getEstatura());
		Double truncatedDouble = BigDecimal.valueOf(tempIMC)
				.setScale(3, RoundingMode.HALF_UP).doubleValue();

		imc.setIMC(truncatedDouble);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();
		
		imc.setFecha(dtf.format(now));
		imc.setAtleta(atleta);
		imcService.save(imc);
		return "index";
	}

	@RequestMapping("/listar")
	public String listar(Model model) {
		model.addAttribute("atletas", atletaService.getAll());
		model.addAttribute(new Atleta());
		return "listar";
	}
	
	@RequestMapping(value = "/listar", method = RequestMethod.POST)
	public String listar(Model model, Atleta atleta) {
		List<Atleta> serachedData = atletaService.find(atleta.getNombre());
		model.addAttribute("atletas", serachedData);
		model.addAttribute(new Atleta());
		return "listar";
	}

	@RequestMapping("/listarIMC/{id}")
	public String listarIMC(Model model, @PathVariable long id) {
		Optional<Atleta> atleta = atletaService.get(id);
		model.addAttribute("historialIMC", atleta.get().getHistorialIMC());
		model.addAttribute("nombreAtleta", atleta.get().getNombre() + " " + atleta.get().getPrimerApellido());
		return "listarIMC";
	}

	@RequestMapping("/verDetalle/{id}")
	public String verDetalleAtleta(Model model, @PathVariable long id) {
		Optional<Atleta> atleta = atletaService.get(id);
		if (atleta.isPresent()) {
			model.addAttribute(atleta.get());
			model.addAttribute("atletaId", atleta.get().getId());
			return "detalle";
		}
		return "notFound";
	}

	@RequestMapping(value = "/actualizarAtleta/{id}", method = RequestMethod.POST)
	public String actualizarAtleta(Atleta atleta, Model model, @PathVariable long id) {

		Optional<Atleta> atletaToUpdate = atletaService.get(id);
		if (atletaToUpdate.isPresent()) {
			
			boolean pesoChanged = atletaToUpdate.get().getPeso() != atleta.getPeso() ? true : false;
			
			atletaToUpdate.get().setPeso(atleta.getPeso());
			atletaToUpdate.get().setDireccion(atleta.getDireccion());
			atletaToUpdate.get().setProvincia(atleta.getProvincia());
			atletaToUpdate.get().setCanton(atleta.getCanton());
			atletaToUpdate.get().setDistrito(atleta.getDistrito());
			atletaToUpdate.get().setContacto(atleta.getContacto());
			atletaService.save(atletaToUpdate.get());
			
			if(pesoChanged) {
				AtletaIMC imc = new AtletaIMC();

				double tempIMC = atleta.getPeso() / (atletaToUpdate.get().getEstatura() * atletaToUpdate.get().getEstatura());
				Double truncatedDouble = BigDecimal.valueOf(tempIMC)
						.setScale(3, RoundingMode.HALF_UP).doubleValue();

				imc.setIMC(truncatedDouble);
				
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
				LocalDateTime now = LocalDateTime.now();
				
				imc.setFecha(dtf.format(now));
				imc.setAtleta(atleta);
				imcService.save(imc);
			}
			return "index";
		}

		return "error";
	}
}
