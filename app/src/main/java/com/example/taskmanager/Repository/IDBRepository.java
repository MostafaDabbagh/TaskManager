package com.example.taskmanager.Repository;

import java.util.List;
import java.util.UUID;

public interface IDBRepository<E> {
    void add(E e);
    void remove(E e);
    E get(UUID id);
    void update(E e);
    List<E> getAll();
}
