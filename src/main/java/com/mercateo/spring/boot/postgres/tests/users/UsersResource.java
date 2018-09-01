package com.mercateo.spring.boot.postgres.tests.users;

import static com.mercateo.common.rest.schemagen.util.OptionalUtil.collect;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;

import com.mercateo.common.rest.schemagen.link.LinkFactory;
import com.mercateo.common.rest.schemagen.types.ObjectWithSchema;
import com.mercateo.rest.jersey.utils.listing.AbstractListingResourceWithGenericId;
import com.mercateo.rest.jersey.utils.listing.IdParameterBean;
import com.mercateo.rest.jersey.utils.listing.SearchQueryParameterBean;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("users")
public class UsersResource extends
        AbstractListingResourceWithGenericId<UserJson, UserJson, SearchQueryParameterBean, UsersResource, UserId> {

    private final UrlSchemaCreator urlSchemaCreator;

    private final UsersLinkCreator linkCreator;

    private final UsersService usersService;

    @Inject
    public UsersResource(@NonNull UrlSchemaCreator urlSchemaCreator,
            @NonNull UsersLinkCreator linkCreator, @NonNull UsersService usersService) {
        super(UsersResource.class, UserId::fromString);
        this.urlSchemaCreator = urlSchemaCreator;
        this.linkCreator = linkCreator;
        this.usersService = usersService;
    }

    @Override
    protected ListingResult<UserJson> getSummaryListing(
            SearchQueryParameterBean searchQueryParameterBean) {

        List<UserJson> users = usersService.getAll().stream()
                .map(UserJson::from)
                .collect(Collectors.toList());

        return new ListingResult<>(users, users.size());

    }

    @Override
    protected UserJson getSummaryForId(UserId id) {
        return getForId(id);
    }

    @Override
    protected UserJson getForId(UserId id) {
        return usersService.getFor(id)
                .map(UserJson::from)
                .orElseThrow(() -> new NotFoundException());
    }

    @POST
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ObjectWithSchema<Void> create(
            @NotNull @BeanParam IdParameterBean id,
            @NotNull @Valid CreateUserJson json) {

        UserId userId = tryToConvertId(id.getId());

        sanityIdCheck(id, json.getId());

        log.info("create " + userId);

        usersService.create(json.toUser());

        return urlSchemaCreator.create();

    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ObjectWithSchema<Void> update(
            @NotNull @BeanParam IdParameterBean id,
            @NotNull @Valid UpdateUserJson json) {

        UserId userId = tryToConvertId(id.getId());

        sanityIdCheck(id, json.getId().getValue());

        log.info("update " + userId);

        usersService.update(json.toUser());

        return urlSchemaCreator.create();

    }

    @Override
    protected List<Optional<Link>> createAdditionalLinksForFullType(@NonNull UserJson json,
            @NonNull LinkFactory<UsersResource> factoryForImplementation) {
        return Arrays.asList(linkCreator.getUpdateLink(json.getId()));
    }

    @Override
    protected List<Link> createAdditionalLinksForListing(SearchQueryParameterBean searchQueryBean) {
        return collect(linkCreator.getCreateLink());
    }

    private void sanityIdCheck(IdParameterBean pathId, UUID jsonId) {
        if (!pathId.getId().equals(jsonId.toString())) {
            throw new BadRequestException("path param id " + pathId.getId()
                    + " conflicts with payload id " + jsonId);
        }
    }

}
