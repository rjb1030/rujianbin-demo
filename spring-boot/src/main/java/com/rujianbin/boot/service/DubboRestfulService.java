package com.rujianbin.boot.service;



import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.stereotype.Component;

import com.rujianbin.boot.entity.Person;

@Path("/dubbo-restful")
@Component
public interface DubboRestfulService {

	@Path("/name/{param}")
	@GET
    @Produces("application/json; charset=UTF-8")
	public String getName(@PathParam("param") String param,@QueryParam("v") Long version);
	
	@Path("/age")
	@GET
    @Produces("application/json; charset=UTF-8")
	public String getAge();
	
	@Path("/update/person")
	@POST
    @Produces("application/json; charset=UTF-8")
	@Consumes("application/json; charset=UTF-8")
	public Person updatePerson(Person p);
}
