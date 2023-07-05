package ua.dmytrolutsyuk.phonecontactsapi.exception.handler;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

@RequiredArgsConstructor
public class ErrorResponseImpl implements ErrorResponse {
    private final HttpStatusCode errorCode;
    private final String errorMessage;

    @Override
    @NonNull
    public HttpStatusCode getStatusCode() {
        return errorCode;
    }

    @Override
    @NonNull
    public ProblemDetail getBody() {
        ProblemDetail details = ProblemDetail.forStatus(errorCode);
        details.setDetail(errorMessage);
        return details;
    }
}