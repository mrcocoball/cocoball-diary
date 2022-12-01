package com.cocoballdiary.fixture;

import com.cocoballdiary.domain.Article;
import com.cocoballdiary.domain.Comment;
import com.cocoballdiary.domain.User;

public class Fixture {

    public static User createUser() {
        User user = User.of(
                "testuser",
                "password",
                "email",
                false,
                false
        );

        return user;
    }

    public static Article createArticle() {
        Article article = Article.of(
                createUser(),
                "title",
                "description",
                5L,
                1L,
                5L
        );

        return article;

    }

    public static Comment createComment() {
        Comment comment = Comment.of(
                createUser(),
                createArticle(),
                "description",
                5L
        );

        return comment;

    }


	/*

	public static UserDto createUserDto() {

	}

	public static ArticleDto createArticleDto() {

	}

	public static CommentDto createCommentDto() {

	}

	public static ArticleWithCommentsDto createArticleWithCommentsDto() {

	}

	*/

}
