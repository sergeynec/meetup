package ru.necs.client.rest.model;

public class RestConfigContext {

    private String url;

    public String getUrl() {
        return url;
    }

    public RestConfigContext setUrl(final String url) {
        this.url = url;
        return this;
    }

    @Override
    public String toString() {
        return "RestConfigContext{"
                + " url='" + url + '\''
                + '}';
    }
}
