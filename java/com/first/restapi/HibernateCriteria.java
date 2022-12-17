package com.first.restapi;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

@Component
public class HibernateCriteria {
	public final String GE = "greaterThanOrEqual";
	public final String GT = "greaterThan";
	public final String LE = "lessThanOrEqual";
	public final String LT = "lessThan";
	public final String EQ = "equal";
	public final String EQ_FRONT = "equalFromFront";
	public final String ASC = "ascending";
	public final String DESC = "descending";
	
	public List<Customer> filter(String condition, String propertyName, String value) {
		StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
		Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
		SessionFactory factory = meta.getSessionFactoryBuilder().build();
		Session session = factory.openSession();
		Criteria criteria = session.createCriteria(Customer.class);
		int noValue = 0;
		if(propertyName.equals("age"))
			try {
				noValue = (int) Integer.parseInt(value);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("age cannot be string");
			}
		
		switch (condition) {
		case GE:
			criteria.add(Restrictions.ge(propertyName, noValue));
			break;
		case GT:
			criteria.add(Restrictions.gt(propertyName, noValue));
			break;
		case LE:
			criteria.add(Restrictions.le(propertyName, noValue));
			break;
		case LT:
			criteria.add(Restrictions.lt(propertyName, noValue));
			break;
		case EQ:
			criteria.add(Restrictions.ilike(propertyName, value));
			break;
		case EQ_FRONT:
			criteria.add(Restrictions.ilike(propertyName, value + "%"));
			break;
		default:
			criteria.add(Restrictions.ilike(propertyName, value));
			break;
		}
		
		List<Customer> result = criteria.list();
		factory.close();
		session.close();
		return result;
	}
	
	public List<Customer> order(String order, String propertyName){
		StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
		Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
		SessionFactory factory = meta.getSessionFactoryBuilder().build();
		Session session = factory.openSession();
		Criteria criteria = session.createCriteria(Customer.class);
		if(order.equals(DESC))
			criteria.addOrder(Order.desc(propertyName));
		else
			criteria.addOrder(Order.asc(propertyName));
		List<Customer> result = criteria.list();
		factory.close();
		session.close();
		return result;
	}
	
	public List<Customer> getResult(){
		StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
		Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
		SessionFactory factory = meta.getSessionFactoryBuilder().build();
		Session session = factory.openSession();
		Criteria criteria = session.createCriteria(Customer.class);
		List<Customer> result = criteria.list();
		factory.close();
		session.close();
		return result;
	}
	
}
