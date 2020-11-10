package gr.dit.tenants.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseView {

    @JsonView(Views.Public.class)
    private ResponseStatus status;

    @JsonView(Views.Public.class)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonView({Views.Public.class,Views.CurrentUser.class})
    private Object details;

    public ResponseView(ResponseStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseView(ResponseStatus status, String message, Object details) {
        this.status = status;
        this.message = message;
        this.details = details;
    }
}
