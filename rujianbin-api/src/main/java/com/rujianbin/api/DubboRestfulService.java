package com.rujianbin.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.rujianbin.api.dto.DtoParam;
import com.rujianbin.api.dto.PersonBean;
import com.rujianbin.dubbo.autoconfiguration.annotation.DubboService;

@Path("/dubbo-restful")
@DubboService
public interface DubboRestfulService {

	@Path("/test/{param}")
	@GET
    @Produces("application/json; charset=UTF-8")
	public String test(@PathParam("param") String param,@QueryParam("v") Long version);
	
	@Path("/age")
	@GET
    @Produces("application/json; charset=UTF-8")
	public String getAge();
	
	@Path("/update/person")
	@POST
    @Produces("application/json; charset=UTF-8")
	@Consumes("application/json; charset=UTF-8")
	public DtoParam updatePerson(DtoParam p);
	
	@Path("/name/{name}")
	@GET
    @Produces("application/json; charset=UTF-8")
	public PersonBean getPersonBean(@PathParam("name")String name);
}