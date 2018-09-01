package com.mercateo.spring.boot.postgres.tests.users;

import com.mercateo.common.rest.schemagen.link.relation.RelType;
import com.mercateo.common.rest.schemagen.link.relation.Relation;
import com.mercateo.common.rest.schemagen.link.relation.RelationContainer;

enum UserRelation implements RelationContainer {

    GET_USERS {
        @Override
        public Relation getRelation() {
            return Relation.of("get-users", RelType.OTHER);
        }
    },

    GET_USER_INSTANCE {
        @Override
        public Relation getRelation() {
            return Relation.of("get-user-instance", RelType.OTHER);
        }
    }

}
