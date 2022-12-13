package com.cocoballdiary.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArticle is a Querydsl query type for Article
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArticle extends EntityPathBase<Article> {

    private static final long serialVersionUID = 997653211L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArticle article = new QArticle("article");

    public final QAuditingFields _super = new QAuditingFields(this);

    public final StringPath address = createString("address");

    public final NumberPath<Long> aid = createNumber("aid", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final SetPath<Image, QImage> images = this.<Image, QImage>createSet("images", Image.class, QImage.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath placeName = createString("placeName");

    public final NumberPath<Long> score = createNumber("score", Long.class);

    public final StringPath title = createString("title");

    public final QUser user;

    public QArticle(String variable) {
        this(Article.class, forVariable(variable), INITS);
    }

    public QArticle(Path<? extends Article> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArticle(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArticle(PathMetadata metadata, PathInits inits) {
        this(Article.class, metadata, inits);
    }

    public QArticle(Class<? extends Article> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

