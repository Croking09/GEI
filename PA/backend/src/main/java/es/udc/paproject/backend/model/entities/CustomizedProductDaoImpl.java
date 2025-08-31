package es.udc.paproject.backend.model.entities;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

public class CustomizedProductDaoImpl implements CustomizedProductDao {

    @PersistenceContext
    private EntityManager entityManager;

    private String[] getTokens(String keywords) {

        if (keywords == null || keywords.length() == 0) {
            return new String[0];
        } else {
            return keywords.split("\\s");
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public Slice<Producto> find(Long categoriaId, String keywords, int page, int size) {

        String[] tokens = getTokens(keywords);
        String queryString = "SELECT p FROM Producto p WHERE p.fechaFinPuja > CURRENT_TIMESTAMP";

        if (categoriaId != null || tokens.length > 0) {
            queryString += " AND ";
        }

        if (categoriaId != null) {
            queryString += "p.categoria.id = :categoriaId";
        }

        if (tokens.length != 0) {

            if (categoriaId != null) {
                queryString += " AND ";
            }

            for (int i = 0; i<tokens.length-1; i++) {
                queryString += "LOWER(p.nombre) LIKE LOWER(:token" + i + ") AND ";
            }

            queryString += "LOWER(p.nombre) LIKE LOWER(:token" + (tokens.length-1) + ")";

        }

        queryString += " ORDER BY p.nombre";

        Query query = entityManager.createQuery(queryString).setFirstResult(page*size).setMaxResults(size+1);

        if (categoriaId != null) {
            query.setParameter("categoriaId", categoriaId);
        }

        if (tokens.length != 0) {
            for (int i = 0; i<tokens.length; i++) {
                query.setParameter("token" + i, '%' + tokens[i] + '%');
            }

        }

        List<Producto> products = query.getResultList();
        boolean hasNext = products.size() == (size+1);

        if (hasNext) {
            products.remove(products.size()-1);
        }

        return new SliceImpl<>(products, PageRequest.of(page, size), hasNext);
    }
}
