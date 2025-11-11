package com.portfolio.denuncias.repository;

import com.portfolio.denuncias.model.BaseEntity;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class InMemoryRepository<T extends BaseEntity> implements Repository<T, Long> {
    private final Map<Long, T> store = new LinkedHashMap<>();
    private final AtomicLong seq = new AtomicLong(1);

    @Override
    public synchronized T save(T entity) {
        if (entity.getId() == null) {
            entity.setId(seq.getAndIncrement());
        }
        store.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<T> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void delete(Long id) {
        store.remove(id);
    }

    @Override
    public void clear() {
        store.clear();
    }

    public List<T> findBy(Predicate<T> predicate) {
        return store.values().stream().filter(predicate).collect(Collectors.toList());
    }
}
