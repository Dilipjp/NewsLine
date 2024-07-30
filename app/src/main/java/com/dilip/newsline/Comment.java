package com.dilip.newsline;

public class Comment {
    public String commentId;
    public String commentTitle;
    public String commentText;
    public String commentUserId;
    public String commentDate;



    public Comment() {
    }

    public Comment(String commentTitle, String commentText, String commentUserId) {
        this.commentTitle = commentTitle;
        this.commentText = commentText;
        this.commentUserId = commentUserId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentTitle() {
        return commentTitle;
    }

    public void setCommentTitle(String commentTitle) {
        this.commentTitle = commentTitle;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(String commentUserId) {
        this.commentUserId = commentUserId;
    }
    public String getCommentDate() {return commentDate;}

    public void setCommentDate(String commentDate) {this.commentDate = commentDate;}
}
