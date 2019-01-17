package io.pantherhub.aws.dynamodb;

public class Action {
    private Integer status;
    private String message;
    private Object result;

    /**
     * Construct Action object.
     * @param status - status code
     * @param message - message about the status
     * @param result - result object
     */
    public Action(Integer status, String message, Object result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getResult() {
        return result;
    }

}
