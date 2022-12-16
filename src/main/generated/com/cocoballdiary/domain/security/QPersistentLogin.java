package com.cocoballdiary.domain.security;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPersistentLogin is a Querydsl query type for PersistentLogin
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPersistentLogin extends EntityPathBase<PersistentLogin> {

    private static final long serialVersionUID = -861726497L;

    public static final QPersistentLogin persistentLogin = new QPersistentLogin("persistentLogin");

    public final DateTimePath<java.util.Date> lastUsed = createDateTime("lastUsed", java.util.Date.class);

    public final StringPath series = createString("series");

    public final StringPath token = createString("token");

    public final StringPath username = createString("username");

    public QPersistentLogin(String variable) {
        super(PersistentLogin.class, forVariable(variable));
    }

    public QPersistentLogin(Path<? extends PersistentLogin> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPersistentLogin(PathMetadata metadata) {
        super(PersistentLogin.class, metadata);
    }

}

