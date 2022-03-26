package nl.thecheerfuldev.multitenancy.core;

public final class TenantContext {

    private TenantContext() {
    }

    private static final ThreadLocal<String> TENANT = new InheritableThreadLocal<>();

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

