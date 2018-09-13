package com.mercateo.spring.boot.postgres.tests.users.rest;

import static com.mercateo.common.rest.schemagen.util.OptionalUtil.collect;

import java.util.List;
import java.util.Optional;

import javax.inject.Named;
import javax.ws.rs.core.Link;

import com.mercateo.common.rest.schemagen.link.LinkFactory;
import com.mercateo.common.rest.schemagen.link.relation.Rel;
import com.mercateo.rest.jersey.utils.listing.IdParameterBean;
import com.mercateo.rest.jersey.utils.listing.SearchQueryParameterBean;
import com.mercateo.spring.boot.postgres.tests.users.UserId;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class UsersLinkCreator {

    @NonNull
    private final LinkFactory<UsersResource> linkFactory;

    Optional<Link> getCreateLink() {
        return linkFactory.forCall(
                Rel.CREATE, r -> r.create(new IdParameterBean(), null));
    }

    Optional<Link> getUpdateLink(UserId userId) {
        return linkFactory.forCall(
                Rel.UPDATE, r -> r.update(new IdParameterBean(userId.getValue().toString()), null));
    }

    public List<Link> getLinksToUsers() {
        return collect(getUsersLink(), userInstanceLink());
    }

    private Optional<Link> getUsersLink() {
        return linkFactory.forCall(
                UserRelation.GET_USERS, r -> r.getListing(new SearchQueryParameterBean()));
    }

    private Optional<Link> userInstanceLink() {
        return linkFactory.forCall(
                UserRelation.GET_USER_INSTANCE, r -> r.get(new IdParameterBean()));
    }

}
