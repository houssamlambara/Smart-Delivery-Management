package com.houssam.Smart_Delivery_Management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.houssam.Smart_Delivery_Management.enums.StatutColis;
import com.houssam.Smart_Delivery_Management.model.Colis;
import com.houssam.Smart_Delivery_Management.service.ColisService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ColisController implements Controller {
    private ColisService colisService;

    public void setColisService(ColisService colisService) {
        this.colisService = colisService;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String method = request.getMethod();
        String path = request.getRequestURI();
        String[] parts = path.split("/");

        Long id = null;
        String lastPart = parts[parts.length - 1];
        if (lastPart.matches("\\d+")) {
            id = Long.parseLong(lastPart);
        }

        if (method.equals("GET")) {
            if (id != null) {
                return findById(id);
            } else {
                return list();
            }
        }

        if (method.equals("POST")) {
            return save(request);
        } else if (method.equals("PUT")) {
            return update(request, id);
        } else if (method.equals("DELETE")) {
            return delete(id);
        }
        return null;
    }

    public ModelAndView list() {
        List<Colis> colis = colisService.findAll();
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("colis", colis);
        return mav;
    }

    public ModelAndView save(HttpServletRequest request) throws IOException {
        ModelAndView mav = new ModelAndView("jsonView");
        try {
            String idLivreurParam = request.getParameter("idLivreur");
            if (idLivreurParam == null || idLivreurParam.isEmpty()) {
                mav.addObject("error", "Le paramètre idLivreur est obligatoire");
                return mav;
            }

            Long idLivreur = Long.parseLong(idLivreurParam);
            Colis colis = readJson(request);

            Colis savedColis = colisService.creerColis(colis, idLivreur);
            mav.addObject("message", "Colis créé avec succès");
            mav.addObject("colis", savedColis);
        } catch (NumberFormatException e) {
            mav.addObject("error", "L'ID du livreur doit être un nombre valide");
        } catch (IllegalArgumentException e) {
            mav.addObject("error", e.getMessage());
        } catch (IOException e) {
            mav.addObject("error", "Erreur lors de la lecture du corps de la requête.");
        }
        return mav;
    }

    public ModelAndView update(HttpServletRequest request, Long id) throws IOException {
        ModelAndView mav = new ModelAndView("jsonView");

        if (id == null) {
            mav.addObject("error", "ID manquant pour la mise à jour.");
            return mav;
        }

        try {
            String json = readJsonString(request);
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> updates = mapper.readValue(json, Map.class);

            if (updates.containsKey("statut")) {
                String statutStr = (String) updates.get("statut");
                StatutColis statut = StatutColis.valueOf(statutStr);
                Colis updatedColis = colisService.updateStatut(id, statut);
                mav.addObject("message", "Statut du colis mis à jour avec succès");
                mav.addObject("colis", updatedColis);
            } else {
                mav.addObject("error", "Seul le statut peut être mis à jour");
            }
        } catch (IllegalArgumentException e) {
            mav.addObject("error", "Statut invalide. Valeurs possibles: PREPARATION, EN_TRANSIT, LIVRE");
        } catch (IOException e) {
            mav.addObject("error", "Erreur lors de la lecture du corps de la requête.");
        }

        return mav;
    }

    public ModelAndView delete(Long id) throws IOException {
        ModelAndView mav = new ModelAndView("jsonView");

        if (id == null) {
            mav.addObject("error", "ID manquant pour la suppression");
            return mav;
        }

        try {
            colisService.deleteColis(id);
            mav.addObject("message", "Colis supprimé avec succès!");
        } catch (RuntimeException e) {
            mav.addObject("error", "Erreur lors de la suppression du colis avec id: " + id);
        }
        return mav;
    }

    public ModelAndView findById(Long id) throws IOException {
        ModelAndView mav = new ModelAndView("jsonView");
        try {
            Colis colis = colisService.findAll().stream()
                    .filter(c -> c.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Colis non trouvé"));
            mav.addObject("colis", colis);
        } catch (IllegalArgumentException e) {
            mav.addObject("error", e.getMessage());
        }
        return mav;
    }

    public Colis readJson(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        String json = sb.toString();

        if (json.isEmpty()) {
            throw new IllegalArgumentException("Request body est vide");
        }

        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(json, Colis.class);
        } catch (com.fasterxml.jackson.databind.exc.MismatchedInputException e) {
            throw new IllegalArgumentException("Invalid JSON format");
        }
    }

    private String readJsonString(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }
}
