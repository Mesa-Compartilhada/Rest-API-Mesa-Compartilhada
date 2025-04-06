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

        if(filter.status() != null && !filter.status().isBlank()) {
            criteria.add(Criteria.where("status").is(filter.status().toUpperCase()));
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
        if(filter.tipoAlimento() != null) {
            criteria.add(Criteria.where("tipoAlimento").is(filter.tipoAlimento()));
        }
        if(filter.tipoArmazenamento() != null) {
            criteria.add(Criteria.where("tipoArmazenamento").is(filter.tipoArmazenamento()));
        }

        org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
        if(!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        }

        return mongoTemplate.find(query, Doacao.class);
    }
}
