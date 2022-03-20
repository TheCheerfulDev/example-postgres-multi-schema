package nl.thecheerfuldev.multitenancy.core;

public class ThreadLocalStorage {

    private static final ThreadLocal<String> TENANT = new ThreadLocal<>();

    public static void setTenantName(String tenantName) {
        TENANT.set(tenantName);
    }

    public static String getTenantName() {
        return TENANT.get();
    }

    public static void removeTenantName() {
        TENANT.remove();
    }

}

