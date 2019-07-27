package com.ala.resources;

import com.ala.model.User;
import com.ala.repository.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RedisExampleResource {

    private static Log LOG = LogFactory.getLog(RedisExampleResource.class);

    private final UserRepository userRepository;

    public RedisExampleResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GET
    @Path("/{id}")
    public User getUser(@PathParam("id") int id) {
        return userRepository.getUser(id);
    }

    @POST
    public void storeUser(User user) {
        LOG.info(user);
        userRepository.storeUser(user);
    }
}
