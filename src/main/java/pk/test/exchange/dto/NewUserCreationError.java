package pk.test.exchange.dto;

public enum NewUserCreationError {
    USERNAME_OCCUPIED   ("Имя пользователя уже занято."),
    PASSWORDS_DONT_MATCH("Подтверждение пароля не совпадает с паролем."),
    DATABASE_ERROR      ("Внутренняя ошибка.");

    private final String explanation;

    NewUserCreationError(String explanation) {
        this.explanation = explanation;
    }

    public String getExplanation() {
        return explanation;
    }
}
