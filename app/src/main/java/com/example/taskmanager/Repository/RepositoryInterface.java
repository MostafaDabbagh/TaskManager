package com.example.taskmanager.Repository;

import java.util.List;
import java.util.UUID;

public interface RepositoryInterface<E> {
    void add(E e);
    void remove(E e);
    E get(UUID id);
    void replace(E e);
    List<E> getAll();
    void setAll(List<E> list);
}
