package br.com.treinaweb.personalexpensesmanager.api.v1.routes;

public class AccountRoutes {

    private AccountRoutes() {}

    public static final String BASE_URI = BaseRoutes.BASE_URI + "/accounts";
    public static final String CREATE_URI = BASE_URI;
    public static final String FIND_ALL_URI = BASE_URI;
    public static final String FIND_BY_ID_URI = BASE_URI + "/{accountId}";

}
