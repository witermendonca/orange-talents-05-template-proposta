package br.com.zupacademy.witer.proposta.validators;

import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class IdExistenteValidator implements ConstraintValidator<IdExistente ,Object> {

    private String domainAttribute;
    private Class<?> klass;

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void initialize(IdExistente params) {
        domainAttribute = params.fieldName();
        klass = params.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        Query query = manager.createQuery("select 1 from " + klass.getName() + " where "
                + domainAttribute + "=:value");
        query.setParameter("value", value);

        List<?> list = query.getResultList();
        Assert.isTrue(list.size() <= 1, "aconteceu algo bizarro e você tem mais de um "
                + klass + " com o atributo " + domainAttribute + " com o valor = " + value);

        return !list.isEmpty();
    }
}
