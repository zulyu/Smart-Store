package com.se300.ledger.repository;

import com.se300.ledger.model.Account;
import com.se300.ledger.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class FakeTransactionRepository implements TransactionRepository {

    private final Map<String, Transaction> db = new HashMap<>();

    @Override
    public <S extends Transaction> S save(S entity) {
        this.db.put(entity.getTransactionId(), entity);
        return entity;
    }

    @Override
    public <S extends Transaction> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Transaction> findById(String s)  {
        return Optional.ofNullable(this.db.get(s));
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<Transaction> findAll() {
        return null;
    }

    @Override
    public Iterable<Transaction> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(Transaction entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Transaction> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
