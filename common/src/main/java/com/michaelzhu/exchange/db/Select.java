package com.michaelzhu.exchange.db;

import java.util.Arrays;

@SuppressWarnings("rawtypes")
public final class Select<T> extends CriteriaQuery<T> {
    @SuppressWarnings("unchecked")
    Select(Criteria criteria, String... selectFields) {
        super(criteria);
        if (selectFields.length > 0) {
            this.criteria.select = Arrays.asList(selectFields);
        }
    }

    /**
     * Add from clause.
     *
     * @param entityClass The entity class.
     * @return The criteria object.
     */
    @SuppressWarnings("unchecked")
    public <T> From<T> from(Class<T> entityClass) {
        return new From<T>((Criteria<T>) this.criteria, this.criteria.db.getMapper(entityClass));
    }
}
