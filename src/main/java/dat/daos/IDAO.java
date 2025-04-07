package dat.daos;

import java.util.List;

public interface IDAO<T, Long> {

    T read(Long id);
    List<T> readAll();
    T create(T t);
    T update(Long id, T t);
    void delete(Long id);
    boolean validatePrimaryKey(Long id);

}
