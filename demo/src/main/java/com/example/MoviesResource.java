package com.example;

import javax.print.attribute.standard.Media;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.h2.command.dml.NoOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/movies")
@Tag(name = "Movie Resource", description = "Movie REST APIS")
public class MoviesResource {

    public static List<Movie> movies = new ArrayList<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    NoOperation(
            operationId = "getMovie",
            summary = "Get Movies",
            description = "Get all movies inside the list"
    )
    @APIResponse(
            responseCode = "200",
            description = "Operation completed",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response getMovie(){
        return Response.ok(movies).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/size")
    @Operation(
            operationId = "countMovies",
            summary = "Count Movies",
            description = "Size off the list movies"
    )
    @APIResponse(
            responseCode = "200",
            description = "Operation completed",
            content = @Content(mediaType = MediaType.TEXT_PLAIN)
    )
    public  Integer countMovies(){
        return movies.size();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "createMovie",
            summary = "Create a new Movie",
            description = "Create a new movie to add inside the list"
    )
    @APIResponse(
            responseCode = "201",
            description = "Movie created",
            content = @Content(mediaType = MediaType.TEXT_PLAIN)
    )
    public Response createMovie(
            @RequestBody(
                    description = "Movie to create",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Movie.class))
            )
            Movie newMovies){
        movies.add(newMovies);
        return Response.status(Response.Status.CREATED).entity(movies).build();
    }

    @PUT
    @Path("{id}/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "updateMovie",
            summary = "Update an existing Movie",
            description = "Update a movie inside the list"
    )
    @APIResponse(
            responseCode = "200",
            description = "Movie updated",
            content = @Content(mediaType = MediaType.TEXT_PLAIN)
    )
    public Response updateMovie(
            @Parameter(
                description = "Movie id",
                required = true
            )
            @PathParam("id") Long id,
            @PathParam("title") String title)
    {
        movies = movies.stream().map(movie -> {
            if (movie.getId().equals(id)){
                movie.setTitle(title);
            }
            return movie;
        }).collect(Collectors.toList());
        return Response.ok(movies).build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Operation(
            operationId = "deleteMovie",
            summary = "Delete an existing Movie",
            description = "Update a movie inside the list"
    )
    @APIResponse(
            responseCode = "204",
            description = "Movie delete",
            content = @Content(mediaType = MediaType.TEXT_PLAIN)
    )
    @APIResponse(
            responseCode = "400",
            description = "Movie not valid",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response deleteMovie(@PathParam("id") Long id)
    {
        Optional<Movie> movieToDelete = movies.stream().filter(movie -> movie.getId().equals(id)).findFirst();
        boolean removed = false;
        if (movieToDelete.isPresent()){
            removed = movies.remove(movieToDelete.get());
        }

        if (removed){
            return Response.noContent().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "getMovie",
            summary = "Get Movies",
            description = "Get all movies inside the list"
    )
    @APIResponse(
            responseCode = "200",
            description = "Operation completed",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response getMovie2(){
        return Response.ok(movies).build();
    }


}
