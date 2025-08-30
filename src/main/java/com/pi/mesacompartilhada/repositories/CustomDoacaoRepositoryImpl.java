package com.pi.mesacompartilhada.repositories;

import com.pi.mesacompartilhada.models.Doacao;
import com.pi.mesacompartilhada.records.doacao.DoacaoFilter;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;

public class CustomDoacaoRepositoryImpl implements CustomDoacaoRepository {

    private final MongoTemplate mongoTemplate;

    public CustomDoacaoRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Doacao> findByFilter(DoacaoFilter filter) {
        List<Criteria> criteria = new ArrayList<>();

        if(filter.status() != null && !filter.status().isEmpty()) {
            List<Criteria> orCriteria = new ArrayList<>();
            for(String status : filter.status()) {
                orCriteria.add(Criteria.where("status").is(status));
            }

            criteria.add(new Criteria().orOperator(
                    orCriteria.toArray(new Criteria[0])
            ));
        }
        if(filter.dataFabricacaoMin() != null) {
            criteria.add(Criteria.where("dataFabricacao").gte(filter.dataFabricacaoMin()));
        }
        if(filter.dataFabricacaoMax() != null) {
            criteria.add(Criteria.where("dataFabricacao").lte(filter.dataFabricacaoMax()));
        }
        if(filter.dataValidadeMin() != null) {
            criteria.add(Criteria.where("dataValidade").gte(filter.dataValidadeMin()));
        }
        if(filter.dataValidadeMax() != null) {
            criteria.add(Criteria.where("dataValidade").lte(filter.dataValidadeMax()));
        }
        if(filter.dataCriadaMin() != null) {
            criteria.add(Criteria.where("dataCriada").gte(filter.dataCriadaMin()));
        }
        if(filter.dataCriadaMax() != null) {
            criteria.add(Criteria.where("dataCriada").lte(filter.dataCriadaMax()));
        }
        if(filter.dataEncerradaMin() != null) {
            criteria.add(Criteria.where("dataEncerrada").gte(filter.dataEncerradaMin()));
        }
        if(filter.dataEncerradaMax() != null) {
            criteria.add(Criteria.where("dataEncerrada").lte(filter.dataEncerradaMax()));
        }
        if(filter.dataRetiradaMin() != null) {
            criteria.add(Criteria.where("dataRetirada").gte(filter.dataRetiradaMin()));
        }
        if(filter.dataRetiradaMax() != null) {
            criteria.add(Criteria.where("dataRetirada").lte(filter.dataRetiradaMax()));
        }
        if(filter.tipoAlimento() != null && !filter.tipoAlimento().isEmpty()) {
            List<Criteria> orCriteria = new ArrayList<>();
            for(Integer tipoAlimento : filter.tipoAlimento()) {
                orCriteria.add(Criteria.where("tipoAlimento").is(tipoAlimento));
            }
            criteria.add(new Criteria().orOperator(
                    orCriteria.toArray(new Criteria[0])
            ));
        }
        if(filter.tipoArmazenamento() != null && !filter.tipoArmazenamento().isEmpty()) {
            List<Criteria> orCriteria = new ArrayList<>();
            for(Integer tipoArmazenamento : filter.tipoArmazenamento()) {
                orCriteria.add(Criteria.where("tipoArmazenamento").is(tipoArmazenamento));
            }
            criteria.add(new Criteria().orOperator(
                    orCriteria.toArray(new Criteria[0])
            ));
        }
        if(filter.empresaDoadoraId() != null && !filter.empresaDoadoraId().isEmpty()) {
            criteria.add(Criteria.where("empresaDoadora").is(filter.empresaDoadoraId()));
        }
        if(filter.empresaRecebedoraId() != null && !filter.empresaRecebedoraId().isEmpty()) {
            criteria.add(Criteria.where("empresaRecebedora").is(filter.empresaRecebedoraId()));
        }

        org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
        if(!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        }

        return mongoTemplate.find(query, Doacao.class);
    }
}
