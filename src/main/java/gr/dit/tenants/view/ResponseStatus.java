package gr.dit.tenants.view;

public enum ResponseStatus {

    SUCCESSFUL_OPERATION("SUCCESSFUL_OPERATION"),
    FAILED_OPERATION("FAILED_OPERATION"),
    VALIDATION_FAILED("VALIDATION_FAILED"),
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR");

    private String status;

    ResponseStatus(String status) {
        this.status = status;
    }
}
