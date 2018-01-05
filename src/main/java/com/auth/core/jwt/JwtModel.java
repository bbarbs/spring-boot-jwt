package com.auth.core.jwt;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.util.Date;

public class JwtModel implements Serializable {

    private static final long serialVersionUID = 6156025155138084388L;

    private String token;
    private String issuer;
    private String subject;
    private SecretKey secretKey;
    private Date issueDate;
    private Date expDate;

    public JwtModel() {
    }

    public JwtModel(String token, String issuer, String subject, SecretKey secretKey, Date issueDate, Date expDate) {
        this.token = token;
        this.issuer = issuer;
        this.subject = subject;
        this.secretKey = secretKey;
        this.issueDate = issueDate;
        this.expDate = expDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JwtModel{");
        sb.append("token='").append(token).append('\'');
        sb.append(", issuer='").append(issuer).append('\'');
        sb.append(", subject='").append(subject).append('\'');
        sb.append(", secretKey=").append(secretKey);
        sb.append(", issueDate=").append(issueDate);
        sb.append(", expDate=").append(expDate);
        sb.append('}');
        return sb.toString();
    }
}
