package org.appfuse.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;
import org.appfuse.dao.PersonDao;
import org.appfuse.model.Person;
import org.appfuse.service.PersonManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("personManager")
@WebService(serviceName = "PersonService", endpointInterface = "org.appfuse.tutorial.service.PersonManager")
public class PersonManagerImpl extends GenericManagerImpl<Person, Long> implements PersonManager {
    PersonDao personDao;

    public PersonManagerImpl() {}

    @Autowired
    public PersonManagerImpl(PersonDao personDao) {
        super(personDao);
        this.personDao = personDao;
    }

    private List<Person> search(String searchTerm) {
    	if(StringUtils.isNotEmpty(searchTerm)) {
    		return personDao.search(searchTerm);
    	} else {
    		return personDao.getAll();
    	}
    }
    
    public long count(String searchTerm) {
        return search(searchTerm).size();
    }
    
    public List<Person> search(String searchTerm, Integer firstResult, Integer maxResults) {
    	List<Person> people = search(searchTerm);
    	if(firstResult != null || maxResults != null) {
	    	int fromIndex = firstResult != null? Math.min(firstResult, people.size()) : 0;
	    	int toIndex = maxResults != null? Math.min(fromIndex + maxResults, people.size()) : people.size();
	    	return people.subList(fromIndex, toIndex);
    	}
    	return people;
    }
}